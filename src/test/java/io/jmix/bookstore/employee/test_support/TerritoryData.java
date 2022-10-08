package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Region;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TerritoryData {
    String name;
    Region region;
}
