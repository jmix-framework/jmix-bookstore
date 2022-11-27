package io.jmix.bookstore.security.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component("bookstore_LoginPerformedEventListener")
public class LoginPerformedEventListener {

    public LoginPerformedEventListener(EmployeeSessionData employeeSessionData) {
        this.employeeSessionData = employeeSessionData;
    }

    private static final Logger log = LoggerFactory.getLogger(LoginPerformedEventListener.class);
    private final EmployeeSessionData employeeSessionData;
    @EventListener
    public void onInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {

        log.info("Login happened. Initialising Employee Session");
        employeeSessionData.initSession();
    }

}
