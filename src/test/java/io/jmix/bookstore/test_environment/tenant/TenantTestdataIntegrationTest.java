package io.jmix.bookstore.test_environment.tenant;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.test_environment.cleanup.TenantCleanup;
import io.jmix.bookstore.multitenancy.test_environment.TenantCreation;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_data.TestDataCreation;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.multitenancy.entity.Tenant;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
class TenantTestdataIntegrationTest {

    @Autowired
    TenantCleanup tenantCleanup;
    @Autowired
    TenantCreation tenantCreation;
    @Autowired
    TestDataCreation testDataCreation;
    @Autowired
    TestEnvironmentTenants testEnvironmentTenants;

    @Autowired
    TimeSource timeSource;
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    SystemAuthenticator systemAuthenticator;
    private String TENANT_ID;

    @BeforeEach
    void setUp() {
        asSystem(() -> {
            databaseCleanup.removeAllEntities();
            databaseCleanup.removeNonAdminUsers();
            testDataCreation.importInitialReport();
        });

        TENANT_ID = tenantCreation.generateRandomTenantId();
    }

    private void asTenantAdmin(Runnable action) {
        systemAuthenticator.runWithUser(TENANT_ID + "|admin", action);
    }

    private <T> T asTenantAdmin(SystemAuthenticator.AuthenticatedOperation<T> operation) {
        return systemAuthenticator.withUser(TENANT_ID + "|admin", operation);
    }

    private void asSystem(Runnable action) {
        systemAuthenticator.runWithSystem(action);
    }
    private <T> T asSystem(SystemAuthenticator.AuthenticatedOperation<T> operation) {
        return systemAuthenticator.withSystem(operation);
    }

    @NotNull
    private List<Product> dbProducts() {
        return allEntitiesOf(Product.class);
    }

    @NotNull
    private <T> List<T> allEntitiesOf(Class<T> entityClass) {
        return dataManager.load(entityClass).all().list();
    }

    @Nested
    class CleanupUnusedTenantsOlderThan {

        @Test
        void given_tenantTestDataIsPresent_when_cleanup_then_tenantTestDataIsDeleted() {

            // given:
            asSystem(() ->
                testEnvironmentTenants.createTenant(TENANT_ID)
            );

            asTenantAdmin(() ->
                    testDataCreation.generateRandomTestdataForTenant(TestDataCreation.TestdataAmount.SMALL)
            );

            // and:
            assertThat(asTenantAdmin(() -> dbProducts().size()))
                    .isGreaterThan(0);

            // and:
            asSystem(() ->
                testEnvironmentTenants.trackUserLoginForTenant(TENANT_ID, OffsetDateTime.now().minusHours(2))
            );

            // when:
            int hoursAgo = 1;

            asSystem(() ->
                    tenantCleanup.cleanupUnusedTenantsOlderThan(hoursAgo)
            );

            // then:
            assertThat(asSystem(() -> dataManager.load(Tenant.class).condition(PropertyCondition.equal("tenantId", TENANT_ID)).optional()))
                    .isEmpty();
            assertThat(asSystem(() -> dataManager.load(User.class).condition(PropertyCondition.equal("tenant", TENANT_ID)).list()))
                    .isEmpty();
        }

        @Test
        void given_newerTenant_when_cleanup_then_tenantTestDataIsNotDeleted() {

            // given:
            asSystem(() ->
                    testEnvironmentTenants.createTenant(TENANT_ID)
            );

            asTenantAdmin(() ->
                    testDataCreation.generateRandomTestdataForTenant(TestDataCreation.TestdataAmount.SMALL)
            );

            // and:
            assertThat(asTenantAdmin(() -> dbProducts().size()))
                    .isGreaterThan(0);

            // and:
            asSystem(() ->
                testEnvironmentTenants.trackUserLoginForTenant(TENANT_ID, OffsetDateTime.now().minusHours(2))
            );

            // when:
            int hoursAgo = 24;

            int amountOfCleanedUpTenants = asSystem(() ->
                    tenantCleanup.cleanupUnusedTenantsOlderThan(hoursAgo)
            );

            // then:
            assertThat(amountOfCleanedUpTenants)
                    .isEqualTo(0);
        }
    }
}
