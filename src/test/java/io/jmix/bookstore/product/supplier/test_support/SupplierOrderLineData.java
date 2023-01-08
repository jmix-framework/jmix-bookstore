package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierOrderLineData {
    SupplierOrder supplierOrder;
    Product product;
    Money unitPrice;
    Integer quantity;
    Money discount;
}
