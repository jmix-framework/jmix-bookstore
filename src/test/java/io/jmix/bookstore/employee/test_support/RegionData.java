package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Title;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionData {
    String name;
}
