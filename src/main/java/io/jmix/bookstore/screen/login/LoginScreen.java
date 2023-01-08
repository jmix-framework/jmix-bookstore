package io.jmix.bookstore.screen.login;

import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.bookstore.test_data.data_provider.RandomValues;
import io.jmix.core.MessageTools;
import io.jmix.core.Messages;
import io.jmix.multitenancy.MultitenancyProperties;
import io.jmix.securityui.authentication.AuthDetails;
import io.jmix.securityui.authentication.LoginScreenSupport;
import io.jmix.ui.JmixApp;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.*;
import io.jmix.ui.security.UiLoginProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@UiController("bookstore_LoginScreen")
@UiDescriptor("login-screen.xml")
@Route(path = "login", root = true)
public class LoginScreen extends Screen {

    private final Logger log = LoggerFactory.getLogger(LoginScreen.class);
    @Autowired
    private TextField<String> usernameField;
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private CheckBox rememberMeCheckBox;
    @Autowired
    private ComboBox<Locale> localesField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private LoginScreenSupport loginScreenSupport;
    @Autowired
    private UiLoginProperties loginProperties;
    @Autowired
    private JmixApp app;
    @Autowired
    private UrlRouting urlRouting;
    @Autowired
    private MessageDialogFacet possibleUsersDialog;
    @Autowired
    private TextField<String> tenantField;
    @Autowired
    private MultitenancyProperties multitenancyProperties;
    @Autowired
    private TestEnvironmentTenants TestEnvironmentTenants;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        setTenantId(findOrGenerateTenantId());
    }


    @Subscribe
    public void onUrlParamsChanged(UrlParamsChangedEvent event) {
        setTenantId(findOrGenerateTenantId());
    }

    private void setTenantId(String tenantId) {
        tenantField.setValue(tenantId);
        app.addCookie("tenantId", tenantId);
    }


    @Subscribe("tenantField")
    public void onTenantFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        app.addCookie("tenantId", event.getValue());
    }

    private String findOrGenerateTenantId() {

        String tenantId = tenantIdFromUrlParams()
                .orElse(app.getCookieValue(multitenancyProperties.getTenantIdUrlParamName()));

        return Optional.ofNullable(tenantId)
                .orElse(generateTenantId());

    }

    private String generateTenantId() {
        List<String> verbs = List.of(
                "grumpy",
                "funny",
                "cute",
                "lovely"
        );

        List<String> animals = List.of(
                "cat",
                "dog",
                "horse",
                "pig"
        );
        return "%s-%s".formatted(
                RandomValues.randomOfList(verbs),
                RandomValues.randomOfList(animals)
        );
    }

    private Optional<String> tenantIdFromUrlParams() {
        Map<String, String> params = urlRouting.getState().getParams();

        if (params == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(params.get(multitenancyProperties.getTenantIdUrlParamName()));
    }


    @Subscribe
    private void onInit(InitEvent event) {
        usernameField.focus();
        initLocalesField();
        initDefaultCredentials();
    }

    private void initLocalesField() {
        localesField.setOptionsMap(messageTools.getAvailableLocalesMap());
        localesField.setValue(app.getLocale());
        localesField.addValueChangeListener(this::onLocalesFieldValueChangeEvent);
    }

    private void onLocalesFieldValueChangeEvent(HasValue.ValueChangeEvent<Locale> event) {
        //noinspection ConstantConditions
        app.setLocale(event.getValue());
        UiControllerUtils.getScreenContext(this).getScreens()
                .create(this.getClass(), OpenMode.ROOT)
                .show();
    }

    private void initDefaultCredentials() {
        String defaultUsername = loginProperties.getDefaultUsername();
        if (!StringUtils.isBlank(defaultUsername) && !"<disabled>".equals(defaultUsername)) {
            usernameField.setValue(defaultUsername);
        } else {
            usernameField.setValue("");
        }

        String defaultPassword = loginProperties.getDefaultPassword();
        if (!StringUtils.isBlank(defaultPassword) && !"<disabled>".equals(defaultPassword)) {
            passwordField.setValue(defaultPassword);
        } else {
            passwordField.setValue("");
        }
    }

    @Subscribe("submit")
    private void onSubmitActionPerformed(Action.ActionPerformedEvent event) {
        login();
    }

    private void login() {
        String tenantId = tenantField.getValue();
        String username = "%s|%s".formatted(tenantId, usernameField.getValue());
        String password = passwordField.getValue();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "emptyUsernameOrPassword"))
                    .show();
            return;
        }


        if (!TestEnvironmentTenants.isPresent(tenantId)) {
            TestEnvironmentTenants.createTenant(tenantId);
        }

        performLogin(username, password, this);
    }


    private void performLogin(String username, String password, FrameOwner frameOwner) {
        try {
            loginScreenSupport.authenticate(
                    AuthDetails.of(username, password)
                            .withLocale(localesField.getValue())
                            .withRememberMe(rememberMeCheckBox.isChecked()), frameOwner);
        } catch (BadCredentialsException | DisabledException | LockedException e) {
            log.warn("Login failed for user '{}': {}", username, e.toString());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMessage(getClass(), "loginFailed"))
                    .withDescription(messages.getMessage(getClass(), "badCredentials"))
                    .show();
        }
    }

    @Subscribe("possibleUsersBtn")
    public void onPossibleUsersBtnClick(Button.ClickEvent event) {
        possibleUsersDialog.show();
    }

    @Subscribe("editTenantField")
    public void onEditTenantFieldClick(Button.ClickEvent event) {
        tenantField.setEditable(true);
    }

}
