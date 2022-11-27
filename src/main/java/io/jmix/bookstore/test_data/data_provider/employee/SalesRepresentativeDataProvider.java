package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.SalesRepresentativeRole;
import io.jmix.bookstore.security.ShowOnlyAssociatedRegionsDataRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("bookstore_SalesRepresentativeDataProvider")
public class SalesRepresentativeDataProvider implements TestDataProvider<Employee, SalesRepresentativeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(EmployeePositions employeePositions, List<Territory> territories) {
    }

    public SalesRepresentativeDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "lois",
                        Title.MR,
                        "Lois",
                        "Marsh",
                        salesRepresentative(dataContext),
                        LocalDate.now().minusYears(4).minusMonths(7).minusDays(9),
                        null,
                        salesRepresentativeResourceRoles(),
                        salesRepresentativeRowLevelRoles(),
                        territoriesForRegions(dataContext.territories(), List.of("US-South", "US-East"))
                ),
                new EmployeeData(
                        "jessica",
                        Title.MRS,
                        "Jessica",
                        "Musgrave",
                        salesRepresentative(dataContext),
                        LocalDate.now().minusYears(8).minusMonths(1).minusDays(1),
                        null,
                        salesRepresentativeResourceRoles(),
                        salesRepresentativeRowLevelRoles(),
                        territoriesForRegions(dataContext.territories(), List.of("US-North", "US-West"))
                )
        );

        return employeeDataProvider.save(employees);
    }

    private Set<Territory> territoriesForRegions(List<Territory> territories, List<String> regionName) {
        return territories.stream()
                .filter(territory -> regionName.stream().anyMatch(it -> it.equals(territory.getRegion().getName())))
                .collect(Collectors.toSet());
    }

    private static List<String> salesRepresentativeResourceRoles() {
        return List.of(
                SalesRepresentativeRole.CODE
        );
    }
    private static List<String> salesRepresentativeRowLevelRoles() {
        return List.of(
                ShowOnlyAssociatedRegionsDataRole.CODE
        );
    }

    private static Position salesRepresentative(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE);
    }

}
