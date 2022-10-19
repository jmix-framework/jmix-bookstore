package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierData {
    String name;
    Address address;
    String contactName;
    String contactTitle;
    String phone;
    String email;
    String website;

}
