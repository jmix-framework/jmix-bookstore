package io.jmix.bookstore.screen.main;

import io.jmix.bookstore.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@UiController("bookstore_MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Image userAvatar;
    @Autowired
    private Image userAvatarMainScreen;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Label<String> welcomeMessage;
    @Autowired
    private MessageBundle messageBundle;


    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();

        initUserAvatar();

        welcomeMessage.setValue(messageBundle.formatMessage("welcomeMessageUser", currentUser().getDisplayName()));
    }

    private void initUserAvatar() {
        String username = currentUser().getUsername();

        userAvatar.setSource(ThemeResource.class)
                .setPath("avatars/%s.png".formatted(username));

        userAvatarMainScreen.setSource(ThemeResource.class)
                .setPath("avatars/%s.png".formatted(username));
    }

    private User currentUser() {
        return (User) currentAuthentication.getUser();
    }
}
