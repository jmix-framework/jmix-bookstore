package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierOrderData {
    Supplier supplier;
    SupplierOrderStatus status;
    LocalDate orderDate;
    Customer customer;
    List<SupplierOrderLine> supplierOrderLines;
    Address shippingAddress;
}
