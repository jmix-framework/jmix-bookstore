package io.jmix.bookstore.product.supplier.creation;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
@Profile("!integration-test")
public class SupplierOrderDraftCreationConfig {

    @Bean
    JobDetail supplierOrderDraft() {
        return JobBuilder.newJob()
                .ofType(SupplierOrderDraftCreationJob.class)
                .storeDurably()
                .withIdentity("supplierOrderDraftCreation")
                .build();
    }

    @Bean
    Trigger supplierOrderDraftTrigger(JobDetail supplierOrderDraft) {
        return TriggerBuilder.newTrigger()
                .forJob(supplierOrderDraft)
                .startNow()
                .withSchedule(
                        cronSchedule("0 0/5 * * * ?")
                )
                .build();
    }
}
