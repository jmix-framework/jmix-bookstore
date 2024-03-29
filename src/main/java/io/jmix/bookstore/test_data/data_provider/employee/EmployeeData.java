package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Title;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record EmployeeData(
        String tenantId,
        String username,
        Title title,
        String firstName,
        String lastName,
        Position position,
        LocalDate hireDate,
        Employee manager,
        List<String> resourceRoleCodes,
        List<String> rowLevelRoleCodes,
        Set<Territory> territories,
        Set<Region> associatedRegions
) {

    public String fullUsername() {
        return "%s|%s".formatted(tenantId, username);
    }
}
