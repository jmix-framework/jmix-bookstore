package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.notification.InAppNotificationSource;

public record SupplierOrderRequestCreatedEvent(SupplierOrderRequest request) implements InAppNotificationSource {
    public String productName() {
        return request.getProduct().getName();
    }
}
