package io.jmix.bookstore.screen.main;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.screen.bookstoremytasksbrowse.BookstoreMyTasksBrowse;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.multitenancy.BpmTenantProvider;
import io.jmix.bpm.service.UserGroupService;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.notificationsui.component.NotificationsIndicator;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.flowable.engine.TaskService;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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
    private NotificationsIndicator ntfIndicator;


    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Label<String> welcomeMessage;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Label<String> positionBadgeLabel;
    @Autowired
    private Label<String> positionBadgeLabel2;

    @Autowired
    private DataManager dataManager;
    @Autowired
    protected TaskService taskService;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired(required = false)
    protected BpmTenantProvider bpmTenantProvider;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;
    @Autowired
    private UserGroupService userGroupService;


    protected List<String> userGroupCodes;
    protected String currentUserName;
    @Autowired
    private Label<String> tasksIndicator_counterLabel;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        currentUserName = currentUserSubstitution.getEffectiveUser().getUsername();
    }



    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();

        initUserAvatar();

        initMainScreenUserAvatar();

        updateTaskCounter();
    }

    private void updateTaskCounter() {
        int myTaskCount = taskService.createTaskQuery()
                .taskAssignee(currentUserName)
                .active()
                .list().size();

        TaskQuery candidatesTaskQuery = taskService.createTaskQuery();
        if (bpmTenantProvider != null && bpmTenantProvider.isMultitenancyActive()) {
            candidatesTaskQuery.taskTenantId(bpmTenantProvider.getCurrentUserTenantId());
        }
        if (!getUserGroupCodes().isEmpty()) {
            candidatesTaskQuery.taskCandidateGroupIn(getUserGroupCodes());
        }
        candidatesTaskQuery.taskCandidateUser(currentUserName);
        int allGroupTasksCount = candidatesTaskQuery
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list().size();

        int taskCount = myTaskCount + allGroupTasksCount;
        tasksIndicator_counterLabel.setValue("%s".formatted(taskCount));
    }

    protected List<String> getUserGroupCodes() {
        if (userGroupCodes == null) {
            userGroupCodes = userGroupService.getUserGroups(currentUserName).stream()
                    .map(UserGroup::getCode)
                    .collect(Collectors.toList());
        }
        return userGroupCodes;
    }

    private void initMainScreenUserAvatar() {
        welcomeMessage.setValue(messageBundle.formatMessage("welcomeMessageUser", currentUser().getDisplayName()));
        List<Employee> currentEmployees = dataManager.load(Employee.class).condition(PropertyCondition.equal("user", currentUser()))
                .fetchPlan(fetchPlanBuilder -> fetchPlanBuilder.add("position", FetchPlan.BASE)).list();

        if (currentEmployees.size() == 1)  {
            Position position = currentEmployees.get(0).getPosition();
            positionBadgeLabel.setValue(position.getName());
            positionBadgeLabel2.setValue(position.getName());
            String colorStyleName = position.getColor().getStyleName();
            positionBadgeLabel.setStyleName("position-badge " + colorStyleName);
            positionBadgeLabel2.setStyleName("position-badge-side-menu " + colorStyleName);
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

    @Subscribe("taskIndicator_rootBox")
    public void onTaskIndicator_rootBoxLayoutClick(LayoutClickNotifier.LayoutClickEvent event) {
        screenBuilders.screen(this)
                .withScreenClass(BookstoreMyTasksBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .build()
                .show();
    }

    @Subscribe("taskNotificationCounterTimer")
    public void onTaskNotificationCounterTimerTimerAction(Timer.TimerActionEvent event) {
        updateTaskCounter();
        ntfIndicator.reloadCounter();
    }

}
