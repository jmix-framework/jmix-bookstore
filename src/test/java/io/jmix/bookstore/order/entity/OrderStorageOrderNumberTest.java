package io.jmix.bookstore.order.entity;


import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.order.test_support.OrderData;
import io.jmix.bookstore.order.test_support.OrderMapper;
import io.jmix.bookstore.order.test_support.Orders;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.data.Sequences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.jmix.bookstore.entity.Assertions.assertThat;


/**
 * Test case to check for correct generation and storage of the order number
 * orders bean is not used for saving here, since it performs bean validation. The order number assignment
 * happens during entity saving event, which is executed only after successful validation in the orders bean.
 */
@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderStorageOrderNumberTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Sequences sequences;
    @Autowired
    Customers customers;
    @Autowired
    Orders orders;
    @Autowired
    OrderMapper mapper;
    private Customer customer;
    private OrderData orderData;


    @BeforeEach
    void setUp() {

        // given
        customer = customers.saveDefault();

        // and
        orderData = orders.defaultData()
                .orderNumber(null)
                .customer(customer)
                .build();
    }

    @Test
    void given_noOrderNumber_when_storingOrder_then_orderNumberIsGenerated() {

        // given
        Order order = newOrderWithoutOrderNumber();

        // and
        assertThat(order.getOrderNumber())
                .isNull();

        // when
        Order savedOrder = dataManager.save(order);

        Long firstOrderNumber = savedOrder.getOrderNumber();

        // and
        assertThat(firstOrderNumber)
                .isNotNull()
                .isPositive();
    }

    @Test
    void given_twoOrders_when_storingOrders_then_orderNumberValuesIncrease() {

        // when
        Order savedFirstOrder = dataManager.save(newOrderWithoutOrderNumber());

        // then
        assertThat(savedFirstOrder.getOrderNumber())
                .isNotNull();

        // when
        Order savedSecondOrder = dataManager.save(newOrderWithoutOrderNumber());

        // then
        assertThat(savedSecondOrder.getOrderNumber())
                .isGreaterThan(savedFirstOrder.getOrderNumber());
    }

    private Order newOrderWithoutOrderNumber() {
        return mapper.toEntity(orderData);
    }
}
