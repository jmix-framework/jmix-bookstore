package io.jmix.bookstore.test_data;

import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("petclinic_CreateVisitTestdataOnApplicationStart")
public class CreateTestdataOnApplicationStart {

    @Autowired
    protected TestDataCreation testDataCreation;

    @Authenticated
    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        testDataCreation.createData();
    }
}
