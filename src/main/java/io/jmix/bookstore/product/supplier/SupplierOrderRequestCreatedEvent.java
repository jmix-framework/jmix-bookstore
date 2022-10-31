package io.jmix.bookstore.product.supplier;

public record SupplierOrderRequestCreatedEvent(SupplierOrderRequest request) {
    public String productName() {
        return request.getProduct().getName();
    }
}
