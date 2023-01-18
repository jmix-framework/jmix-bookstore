package io.jmix.bookstore.multitenancy;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.test_environment.TenantCreation;
import io.jmix.bookstore.test_data.TestDataCreation;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.Authenticated;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;

@Component("bookstore_TestEnvironmentTenants")
public class TestEnvironmentTenantsBean implements TestEnvironmentTenants {

    private final DataManager dataManager;
    private final TenantCreation tenantCreation;
    private final SystemAuthenticator systemAuthenticator;
    private final TestDataCreation testDataCreation;

    public TestEnvironmentTenantsBean(DataManager dataManager, TenantCreation tenantCreation, SystemAuthenticator systemAuthenticator, TestDataCreation testDataCreation) {
        this.dataManager = dataManager;
        this.tenantCreation = tenantCreation;
        this.systemAuthenticator = systemAuthenticator;
        this.testDataCreation = testDataCreation;
    }

    @Override
    public void trackUserLoginForTenant(String tenantId, OffsetDateTime lastLogin) {
        optionalTenant(tenantId)
                .ifPresent(testEnvironmentTenant -> updateLastLoginForTenant(testEnvironmentTenant, lastLogin));
    }

    private void updateLastLoginForTenant(TestEnvironmentTenant testEnvironmentTenant, OffsetDateTime lastLogin) {
        testEnvironmentTenant.setLastLogin(lastLogin);
        dataManager.save(testEnvironmentTenant);
    }

    @Override
    @Authenticated
    public boolean isPresent(String tenantId) {
        return optionalTenant(tenantId)
                .isPresent();
    }

    @Override
    @Authenticated
    public void createTenant(String tenantId) {
        tenantCreation.initialiseTenant(tenantId, adminUsername(tenantId));
    }


    @Override
    @Authenticated
    public void generateRandomTestdata(String tenantId) {
        User tenantAdmin = findAdminForTenant(tenantId);
        systemAuthenticator.runWithUser(tenantAdmin.getUsername(), () -> testDataCreation.generateRandomTestdataForTenant(TestDataCreation.TestdataAmount.MEDIUM));

        markAsTestdataInitialised(tenantId);
    }

    @Override
    public String generateRandomTenantId() {
        return tenantCreation.generateRandomTenantId();
    }

    private Optional<TestEnvironmentTenant> optionalTenant(String tenantId) {
        return dataManager.load(TestEnvironmentTenant.class)
                .condition(PropertyCondition.equal("tenantId", tenantId))
                .optional();
    }

    @Override
    public boolean hasGeneratedTestdata(String tenantId) {
        return optionalTenant(tenantId)
                .map(TestEnvironmentTenant::getTestdataInitialised)
                .orElse(false);
    }

    public void markAsTestdataInitialised(String tenantId) {
        optionalTenant(tenantId)
                .ifPresent(this::setTestateInitialised);
    }

    private void setTestateInitialised(TestEnvironmentTenant testEnvironmentTenant) {
        testEnvironmentTenant.setTestdataInitialised(true);
        dataManager.save(testEnvironmentTenant);
    }

    public User findAdminForTenant(String tenantId) {
        return dataManager.load(User.class).condition(PropertyCondition.equal("username", adminUsername(tenantId))).one();
    }
    private String adminUsername(String tenantId) {
        return "%s|%s".formatted(tenantId, "admin");
    }
}
