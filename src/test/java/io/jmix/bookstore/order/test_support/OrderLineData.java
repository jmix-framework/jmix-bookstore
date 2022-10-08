package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.product.Product;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

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
