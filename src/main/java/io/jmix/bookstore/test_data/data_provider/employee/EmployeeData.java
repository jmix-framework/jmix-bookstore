package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;

import java.time.LocalDate;
import java.util.List;

public record EmployeeData(
        String username,
        Title title,
        String firstName,
        String lastName,
        Position position,
        LocalDate hireDate,
        Employee manager,
        List<String> roleCodes
) { }
