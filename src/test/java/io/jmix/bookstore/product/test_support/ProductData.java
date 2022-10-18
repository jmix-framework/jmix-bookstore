package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductData {
    String name;
    ProductCategory category;
    Money unitPrice;
    Integer unitsInStock;
    Integer unitsOnOrder;
    Boolean active;
    Supplier supplier;
}
