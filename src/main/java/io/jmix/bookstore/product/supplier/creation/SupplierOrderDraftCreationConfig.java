package io.jmix.bookstore.product.supplier.creation;

import io.jmix.bookstore.multitenancy.test_environment.cleanup.TenantCleanupJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class SupplierOrderDraftCreationConfig {

    @Bean
    JobDetail supplierOrderDraft() {
        return JobBuilder.newJob()
                .ofType(TenantCleanupJob.class)
                .storeDurably()
                .withIdentity("tenantCleanup")
                .build();
    }

    @Bean
    Trigger supplierOrderDraftTrigger(JobDetail supplierOrderDraft) {
        return TriggerBuilder.newTrigger()
                .forJob(supplierOrderDraft)
                .startNow()
                .withSchedule(
                    simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever()
                )
                .build();
    }
}
