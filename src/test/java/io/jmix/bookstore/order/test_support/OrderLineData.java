package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.order.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderLineData {
    Order order;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
}
