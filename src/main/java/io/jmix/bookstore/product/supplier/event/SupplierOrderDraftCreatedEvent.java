package io.jmix.bookstore.product.supplier.event;

import io.jmix.bookstore.notification.InAppNotificationSource;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;

public record SupplierOrderDraftCreatedEvent(SupplierOrder supplierOrder) implements InAppNotificationSource {
}
