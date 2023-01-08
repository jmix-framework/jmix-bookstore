package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderLineData {
    Order order;
    Product product;
    Money unitPrice;
    Integer quantity;
    Money discount;
}
