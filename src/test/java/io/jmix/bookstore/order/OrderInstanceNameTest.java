package io.jmix.bookstore.order;

import io.jmix.bookstore.customer.Customer;

import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.order.test_support.Orders;
import io.jmix.core.MetadataTools;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class OrderInstanceNameTest {

    @Autowired
    MetadataTools metadataTools;

    @Autowired
    private Orders orders;
    @Autowired
    private Customers customers;


    @Test
    void given_orderContainsCustomerAndOrderDate_expect_instanceNameContainsFormattedValues() {

        // given
        Customer customer = customers.save(
                customers.defaultData()
                        .firstName("Mr.")
                        .lastName("Miyagi")
                        .build()
        );

        // and
        Order order = orders.create(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(LocalDate.parse("2022-01-01"))
                        .build()
        );

        // expect
        assertThat(metadataTools.getInstanceName(order))
                .isEqualTo("Mr. Miyagi - 01/01/2022");
    }
}
