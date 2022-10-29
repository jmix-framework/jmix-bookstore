package io.jmix.bookstore.screen.main;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Autowired
    private Label<String> positionBadgeLabel;

    @Autowired
    private DataManager dataManager;

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

        initMainScreenUserAvatar();
    }

    private void initMainScreenUserAvatar() {
        welcomeMessage.setValue(messageBundle.formatMessage("welcomeMessageUser", currentUser().getDisplayName()));
        List<Employee> currentEmployees = dataManager.load(Employee.class).condition(PropertyCondition.equal("user", currentUser()))
                .fetchPlan(fetchPlanBuilder -> fetchPlanBuilder.add("position", FetchPlan.BASE)).list();

        if (currentEmployees.size() == 1)  {
            Position position = currentEmployees.get(0).getPosition();
            positionBadgeLabel.setValue(position.getName());
            String colorStyleName = position.getColor().getStyleName();
            positionBadgeLabel.setStyleName("position-badge " + colorStyleName);
            userAvatar.setStyleName("user-avatar user-avatar-border-" + colorStyleName);
            userAvatarMainScreen.setStyleName("user-avatar user-avatar-border-" + colorStyleName);
        }
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
