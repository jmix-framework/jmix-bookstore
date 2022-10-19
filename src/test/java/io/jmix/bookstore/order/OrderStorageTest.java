package io.jmix.bookstore.order;


import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.order.test_support.OrderLines;
import io.jmix.bookstore.order.test_support.Orders;
import io.jmix.bookstore.product.Product;

import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.jmix.bookstore.order.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
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
                        .product(product)
                        .build()
        );

        // and
        OrderLine orderLine2 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .product(product)
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
