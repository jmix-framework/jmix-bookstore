package io.jmix.bookstore.order.event;

import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.order.entity.Order;

public record OrderConfirmedEvent(Order order) implements InAppNotificationSource {
}
