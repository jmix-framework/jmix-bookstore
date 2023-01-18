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
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.region.AvailableRegions.Entry.*;

@Component("bookstore_SalesRepresentativeDataProvider")
public class SalesRepresentativeDataProvider implements TestDataProvider<Employee, SalesRepresentativeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public SalesRepresentativeDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
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
                                US_EAST,
                                US_SOUTH
                        ),
                        Set.of(
                                dataContext.regions().find(US_EAST),
                                dataContext.regions().find(US_SOUTH)
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
                                US_NORTH,
                                US_WEST
                        ),
                        Set.of(
                                dataContext.regions().find(US_NORTH),
                                dataContext.regions().find(US_WEST)
                        )
                )
        );

        return employeeDataProvider.save(employees);
    }

    public record DataContext(
            EmployeePositions employeePositions,
            AvailableRegions regions, AvailableTerritories availableTerritories,
            String tenantId
    ) {
    }

}
