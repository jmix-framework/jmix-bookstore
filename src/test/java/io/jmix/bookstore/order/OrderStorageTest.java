package io.jmix.bookstore.order;


import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.product.Product;

import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.bookstore.test_support.test_data.*;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.jmix.bookstore.order.Assertions.assertThat;


@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderStorageTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Customers customers;
    @Autowired
    Products products;
    @Autowired
    Orders orders;
    @Autowired
    OrderLines orderLines;


    private final LocalDate TODAY = LocalDate.now();

    private final LocalDateTime IN_TWO_DAYS = LocalDateTime.now().plusDays(2);
    private final LocalDateTime IN_THREE_DAYS = LocalDateTime.now().plusDays(3);


    @Test
    void given_validOrder_when_storingOrderWithOrderLines_then_allEntitiesAreStored() {

        // given
        Customer customer = customers.saveDefault();

        // and
        Product product = products.save(
                products.defaultData().name("Giant Stance E+ 1").build()
        );

        // when
        Order order = orders.save(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(TODAY)
                        .build()
        );

        // and
        OrderLine orderLine1 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .endsAt(IN_TWO_DAYS)
                        .build()
        );

        // and
        OrderLine orderLine2 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .endsAt(IN_THREE_DAYS)
                        .build()
        );

        // then
        Order savedOrder = dataManager.load(Id.of(order)).one();

        // and
        assertThat(savedOrder)
                .hasCustomer(customer);

        // and
        assertThat(savedOrder)
                .hasOnlyOrderLines(orderLine1, orderLine2);
    }
}
