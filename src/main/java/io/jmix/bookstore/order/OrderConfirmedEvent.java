package io.jmix.bookstore.order;

import io.jmix.bookstore.notification.InAppNotificationSource;

public record OrderConfirmedEvent(Order order) implements InAppNotificationSource {
}
