package io.jmix.bookstore;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
//@PropertySource("classpath:my.properties")
@ComponentScan(basePackages = {"io.jmix.bookstore"})
public class JmixBookstoreApplicationTestConfig {
}
