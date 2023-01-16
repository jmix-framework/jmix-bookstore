package io.jmix.bookstore.product.supplier.event;

import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;

public record SupplierOrderRequestCreatedEvent(SupplierOrderRequest request) implements InAppNotificationSource {
    public String productName() {
        return request.getProduct().getName();
    }
}
