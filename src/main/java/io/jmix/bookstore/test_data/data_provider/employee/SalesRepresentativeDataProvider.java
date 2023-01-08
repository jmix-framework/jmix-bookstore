package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.SalesRepresentativeRole;
import io.jmix.bookstore.security.ShowOnlyAssociatedRegionsDataRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("bookstore_SalesRepresentativeDataProvider")
public class SalesRepresentativeDataProvider implements TestDataProvider<Employee, SalesRepresentativeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(
            EmployeePositions employeePositions,
            AvailableTerritories availableTerritories,
            String tenantId
    ) {}

    public SalesRepresentativeDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        dataContext.tenantId(),
                        "lois",
                        Title.MR,
                        "Lois",
                        "Marsh",
                        salesRepresentative(dataContext),
                        LocalDate.now().minusYears(4).minusMonths(7).minusDays(9),
                        null,
                        salesRepresentativeResourceRoles(),
                        salesRepresentativeRowLevelRoles(),
                        dataContext.availableTerritories().findTerritoriesFromRegion(
                                AvailableRegions.Entry.US_EAST,
                                AvailableRegions.Entry.US_SOUTH
                        )
                ),
                new EmployeeData(
                        dataContext.tenantId(),
                        "jessica",
                        Title.MRS,
                        "Jessica",
                        "Musgrave",
                        salesRepresentative(dataContext),
                        LocalDate.now().minusYears(8).minusMonths(1).minusDays(1),
                        null,
                        salesRepresentativeResourceRoles(),
                        salesRepresentativeRowLevelRoles(),
                        dataContext.availableTerritories().findTerritoriesFromRegion(
                                AvailableRegions.Entry.US_NORTH,
                                AvailableRegions.Entry.US_WEST
                        )
                )
        );

        return employeeDataProvider.save(employees);
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
