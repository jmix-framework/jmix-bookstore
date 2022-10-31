package io.jmix.bookstore;

import com.google.common.base.Strings;
import io.jmix.notifications.NotificationType;
import io.jmix.notifications.NotificationTypesRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@EnableAsync
@SpringBootApplication
public class JmixBookstoreApplication {

    @Autowired
    private Environment environment;

    @Autowired
    private NotificationTypesRepository notificationTypesRepository;

    public static void main(String[] args) {
        SpringApplication.run(JmixBookstoreApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @PostConstruct
    public void postConstruct() {
        notificationTypesRepository.registerTypes(
                new NotificationType("info", "INFO_CIRCLE"),
                new NotificationType("warn", "WARNING")
        );
    }

    @EventListener
    public void printApplicationUrl(ApplicationStartedEvent event) {
        LoggerFactory.getLogger(JmixBookstoreApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
}
