package io.jmix.bookstore.product.supplier.creation;

import io.jmix.core.security.Authenticated;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupplierOrderDraftCreationJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(SupplierOrderDraftCreationJob.class);

    private final SupplierOrderDraftCreation supplierOrderDraftCreation;

    public SupplierOrderDraftCreationJob(SupplierOrderDraftCreation supplierOrderDraftCreation) {
        this.supplierOrderDraftCreation = supplierOrderDraftCreation;
    }


    @Override
    @Authenticated
    public void execute(JobExecutionContext context) {

        log.info("Starting Tenant Cleanup Job for Tenants {} older than hours", deleteTenantsOlderThanHours);
        int amountOfCleanedUpTenants = supplierOrderDraftCreation.cleanupUnusedTenantsOlderThan(deleteTenantsOlderThanHours);
        log.info("Finished Tenant Cleanup Job: {} tenants removed", amountOfCleanedUpTenants);
    }
}
