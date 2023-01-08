package io.jmix.bookstore.test_data;

import io.jmix.core.security.Authenticated;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("!integration-test")
@Component("bookstore_CreateTestdataOnApplicationStart")
public class CreateTestdataOnApplicationStart {

    private final TestDataCreation testDataCreation;

    public CreateTestdataOnApplicationStart(TestDataCreation testDataCreation) {
        this.testDataCreation = testDataCreation;
    }

    @Authenticated
    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        testDataCreation.importInitialReport();
    }
}
