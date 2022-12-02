package io.jmix.bookstore.notification;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.test_support.Employees;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.entity.test_support.Users;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.notifications.entity.InAppNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class NotificationCenterTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    Employees employees;
    @Autowired
    Users users;
    @Autowired
    ApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();

        databaseCleanup.removeAllEntities(InAppNotification.class);
    }

    @Test
    void given_testEventForEmployee_when_publishEvent_then_notificationIsCreatedForUser() {
        User user = users.save(
                users.defaultData()
                        .build()
        );

        Employee employee = employees.create(
                employees.defaultData()
                        .user(user)
                        .build()
        );

        // when: publish event through Spring
        publisher.publishEvent(new TestEventWithNotificationSourceSupport(employee));

        // then: due to @Async annotation, the test is polling for changes on the DB
        await()
                .atMost(5, SECONDS)
                .untilAsserted(() -> notificationRetrievedFor(user));

    }

    private void notificationRetrievedFor(User user) {
        systemAuthenticator.runWithSystem(() -> {
            List<InAppNotification> userNotifications = dataManager.load(InAppNotification.class)
                    .condition(PropertyCondition.equal("recipient", user.getUsername()))
                    .list();
            assertThat(userNotifications)
                    .hasSize(1);
        });
    }

    public record TestEventWithNotificationSourceSupport(Employee recipient) implements InAppNotificationSource {};

    static class TestEventDataProvider  implements NotificationDetailDataProvider<TestEventWithNotificationSourceSupport> {

        @Override
        public boolean supports(InAppNotificationSource event) {
            return event instanceof TestEventWithNotificationSourceSupport;
        }

        @Override
        public NotificationDetailData provideData(TestEventWithNotificationSourceSupport event) {
            return new NotificationDetailData(
                    List.of(event.recipient().getUser().getUsername()),
                    "subject",
                    "body"
            );
        }
    }

    @TestConfiguration
    static class NotificationCenterTestConfiguration {
        @Bean
        public NotificationDetailDataProvider testEventDataProvider() {
            return new TestEventDataProvider();
        }
    }

}
