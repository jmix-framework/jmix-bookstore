package io.jmix.bookstore.screen.main;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.bookstore.screen.bookstoremytasksbrowse.BookstoreMyTasksBrowse;
import io.jmix.bookstore.security.session.EmployeeSessionData;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.multitenancy.BpmTenantProvider;
import io.jmix.bpm.service.UserGroupService;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.multitenancy.core.TenantProvider;
import io.jmix.notificationsui.component.NotificationsIndicator;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.*;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.TaskLifeCycle;
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
    protected TaskService taskService;
    @Autowired(required = false)
    protected BpmTenantProvider bpmTenantProvider;
    protected List<String> userGroupCodes;
    protected String currentUserName;
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
    private ScreenBuilders screenBuilders;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private Label<String> tasksIndicator_counterLabel;
    @Autowired
    private EmployeeSessionData employeeSessionData;
    @Autowired
    private TenantProvider tenantProvider;
    @Autowired
    private Label<String> tenantBadgeLabel;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private TestEnvironmentTenants testEnvironmentTenants;
    @Autowired
    private Notifications notifications;

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

        initTenantBadge();

        initTenantTestdataIfNecessary();
    }

    private void initTenantTestdataIfNecessary() {
        String tenantId = tenantProvider.getCurrentUserTenantId();
        if (testEnvironmentTenants.isPresent(tenantId) && !testEnvironmentTenants.hasGeneratedTestdata(tenantId)) {

            dialogs.createBackgroundWorkDialog(this, new BackgroundTask<Number, Boolean>(10_000) {
                        @Override
                        public Boolean run(TaskLifeCycle<Number> taskLifeCycle) {
                            try {
                                taskLifeCycle.publish();
                                testEnvironmentTenants.generateRandomTestdata(tenantId);
                            }
                            catch (Exception e) {
                                return false;
                            }
                            return true;
                        }

                        @Override
                        public void done(Boolean result) {

                            if (Boolean.TRUE.equals(result)) {
                                notifications.create(Notifications.NotificationType.TRAY)
                                        .withCaption(messageBundle.getMessage("testdataCreated"))
                                        .show();

                            }
                            else {

                                notifications.create(Notifications.NotificationType.ERROR)
                                        .withCaption(messageBundle.getMessage("testdataNotCreatedCaption"))
                                        .withDescription(messageBundle.getMessage("testdataNotCreatedDescription"))
                                        .show();
                            }
                        }


                    })
                    .withCaption(messageBundle.getMessage("createTestdataCaption"))
                    .withMessage(messageBundle.formatMessage("createTestdataMessage", tenantId))
                    .withShowProgressInPercentage(false)
                    .withCancelAllowed(false)
                    .build()
                    .show();
        }
    }

    private void initTenantBadge() {
        String currentTenantId = currentTenant();
        if (!currentTenantId.equals(TenantProvider.NO_TENANT)) {
            tenantBadgeLabel.setValue(currentTenant());
        }
    }

    private String currentTenant() {
        return tenantProvider.getCurrentUserTenantId();
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
        welcomeMessage.setValue(messageBundle.formatMessage("welcomeMessageUser", currentUser().getFirstName()));
        employeeSessionData.employee()
                .ifPresent(it -> {
                    positionBadgeLabel.setValue(it.getPosition().getName());
                    String colorStyleName = it.getPosition().getColor().getStyleName();
                    positionBadgeLabel.setStyleName("position-badge " + colorStyleName);
                    userAvatar.setStyleName("user-avatar user-avatar-border-" + colorStyleName);
                    userAvatarMainScreen.setStyleName("user-avatar user-avatar-border-" + colorStyleName);

                });

    }

    private void initUserAvatar() {
        String username = currentUser().getUsername().replaceAll(currentTenant()+"\\|", "");

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
