package io.jmix.bookstore.entity.test_support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.locationtech.jts.geom.Point;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressData {
    String street;
    String postCode;
    String city;
    String state;
    String country;
    Point position;
}
