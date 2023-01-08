package io.jmix.bookstore.multitenancy.test_environment.cleanup;

import io.jmix.core.security.Authenticated;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantCleanupJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(TenantCleanupJob.class);

    private final TenantCleanup tenantCleanup;
    private final TenantCleanupProperties tenantCleanupProperties;

    public TenantCleanupJob(TenantCleanup tenantCleanup, TenantCleanupProperties tenantCleanupProperties) {
        this.tenantCleanup = tenantCleanup;
        this.tenantCleanupProperties = tenantCleanupProperties;
    }

    @Override
    @Authenticated
    public void execute(JobExecutionContext context) {

        int deleteTenantsOlderThanHours = tenantCleanupProperties.getDeleteTenantsOlderThanHours();
        log.info("Starting Tenant Cleanup Job for Tenants {} older than hours", deleteTenantsOlderThanHours);
        int amountOfCleanedUpTenants = tenantCleanup.cleanupUnusedTenantsOlderThan(deleteTenantsOlderThanHours);
        log.info("Finished Tenant Cleanup Job: {} tenants removed", amountOfCleanedUpTenants);
    }
}
