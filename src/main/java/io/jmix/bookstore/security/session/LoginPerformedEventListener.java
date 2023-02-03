package io.jmix.bookstore.security.session;

import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.core.TimeSource;
import io.jmix.multitenancy.core.TenantProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component("bookstore_LoginPerformedEventListener")
public class LoginPerformedEventListener {

    private static final Logger log = LoggerFactory.getLogger(LoginPerformedEventListener.class);
    private final BookstoreSessionData bookstoreSessionData;
    private final TenantProvider tenantProvider;
    private final TestEnvironmentTenants testEnvironmentTenants;
    private final TimeSource timeSource;
    public LoginPerformedEventListener(BookstoreSessionData bookstoreSessionData, TenantProvider tenantProvider, TestEnvironmentTenants testEnvironmentTenants, TimeSource timeSource) {
        this.bookstoreSessionData = bookstoreSessionData;
        this.tenantProvider = tenantProvider;
        this.testEnvironmentTenants = testEnvironmentTenants;
        this.timeSource = timeSource;
    }

    @EventListener
    public void onInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {

        log.info("Login happened. Initialising Employee Session");
        bookstoreSessionData.initSession();

        testEnvironmentTenants.trackUserLoginForTenant(
                tenantProvider.getCurrentUserTenantId(),
                timeSource.now().toOffsetDateTime()
        );
    }

}
