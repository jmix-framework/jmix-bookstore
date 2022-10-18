package io.jmix.bookstore.product.supplier.test_support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressData {
    String street;
    String postCode;
    String city;
}
