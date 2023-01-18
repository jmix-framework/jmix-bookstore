package io.jmix.bookstore.multitenancy.test_environment.cleanup;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantCleanupConfig {

    @Bean
    JobDetail tenantCleanup() {
        return JobBuilder.newJob()
                .ofType(TenantCleanupJob.class)
                .storeDurably()
                .withIdentity("tenantCleanup")
                .build();
    }

    @Bean
    Trigger tenantCleanupTrigger(JobDetail tenantCleanup) {
        return TriggerBuilder.newTrigger()
                .withIdentity("tenantCleanupTrigger")
                .forJob(tenantCleanup)
                .startNow()
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(4, 0))
                .build();
    }
}
