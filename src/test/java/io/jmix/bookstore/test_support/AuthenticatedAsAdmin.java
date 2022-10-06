package io.jmix.bookstore.test_support;

import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class AuthenticatedAsAdmin implements BeforeEachCallback, AfterEachCallback {


    private SystemAuthenticator systemAuthenticator;

    @Override
    public void beforeEach(ExtensionContext context) {

        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);

        systemAuthenticator = applicationContext.getBean(SystemAuthenticator.class);

        systemAuthenticator.begin("admin");
    }


    @Override
    public void afterEach(ExtensionContext context) {
        systemAuthenticator.end();
    }
}
