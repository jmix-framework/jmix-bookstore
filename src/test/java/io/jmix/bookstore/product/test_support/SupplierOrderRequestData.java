package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierOrderRequestData {
    Product product;
    Integer requestedAmount;
    String comment;
    User requestedBy;
    SupplierOrderRequestStatus status;
}
