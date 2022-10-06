package io.jmix.bookstore.customer;


import io.jmix.bookstore.JmixBookstoreApplication;
import io.jmix.bookstore.JmixBookstoreApplicationTestConfig;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.bookstore.test_support.test_data.Customers;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static io.jmix.bookstore.order.Assertions.assertThat;


@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
class CustomerStorageTest {

    @Autowired
    Customers customers;

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        // given
        Customer savedCustomer = customers.save(
                customers.defaultData()
                        .firstName("Foo")
                        .lastName("Bar")
                        .address(customers.defaultAddress())
                        .build()
        );

        // then
        assertThat(savedCustomer)
                .hasFirstName("Foo")
                .hasLastName("Bar");
    }
}
