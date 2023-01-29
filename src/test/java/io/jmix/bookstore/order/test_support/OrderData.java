package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.order.entity.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderData {
    Long orderNumber;
    LocalDate orderDate;
    Customer customer;
    List<OrderLine> orderLines;
    Address shippingAddress;
    FulfillmentCenter fulfilledBy;
    OrderStatus status;
}
