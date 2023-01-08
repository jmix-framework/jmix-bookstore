package io.jmix.bookstore.multitenancy.test_environment.cleanup;

import io.jmix.bookstore.multitenancy.TestEnvironmentTenant;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.multitenancy.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("bookstore_TenantCleanup")
public class TenantCleanup {

    private final DataManager dataManager;
    private final TimeSource timeSource;
    private final DatabaseCleanup databaseCleanup;

    public TenantCleanup(DataManager dataManager, TimeSource timeSource, DatabaseCleanup databaseCleanup) {
        this.dataManager = dataManager;
        this.timeSource = timeSource;
        this.databaseCleanup = databaseCleanup;
    }

    public int cleanupUnusedTenantsOlderThan(int hoursAgo) {

        List<TestEnvironmentTenant> unusedTenants = dataManager.load(TestEnvironmentTenant.class)
                .condition(PropertyCondition.less(
                        "lastLogin",
                        timeSource.now().minusHours(hoursAgo).toOffsetDateTime())
                ).list();

        unusedTenants.forEach(databaseCleanup::removeAllEntitiesForTenant);

        databaseCleanup.removeAllEntitiesWithoutSoftDeletion(Tenant.class, unusedTenants.stream().map(Tenant::getId).collect(Collectors.toSet()));

        return unusedTenants.size();
    }

}
