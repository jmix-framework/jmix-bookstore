package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.security.ShowOnlyAssociatedRegionsDataRole;
import io.jmix.bookstore.test_data.data_provider.RandomValues;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.region.AvailableRegions.Entry.US_EAST;
import static io.jmix.bookstore.test_data.data_provider.region.AvailableRegions.Entry.US_SOUTH;

@Component("bookstore_OrderFulfillmentEmployeeDataProvider")
public class OrderFulfillmentSpecialistDataProvider implements TestDataProvider<Employee, OrderFulfillmentSpecialistDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public OrderFulfillmentSpecialistDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    private static List<String> orderFulfillmentSpecialistResourceRoles() {
        return List.of(
                OrderFulfillmentRole.CODE
        );
    }

    private static Position orderFulfillmentSpecialist(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST);
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        dataContext.tenantId(),
                        "melissa",
                        Title.MR,
                        "Melissa",
                        "Arendt",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(5).minusMonths(1).minusDays(10),
                        RandomValues.randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistResourceRoles(),
                        orderFulfillmentSpecialistRowLevelRoles(),
                        dataContext.territories().findTerritoriesFromRegion(US_SOUTH),
                        Set.of(dataContext.regions().find(US_SOUTH))
                ),
                new EmployeeData(
                        dataContext.tenantId(),
                        "hikari",
                        Title.MRS,
                        "Hikari",
                        "Miyama",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(12).minusMonths(1).minusDays(0),
                        RandomValues.randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistResourceRoles(),
                        orderFulfillmentSpecialistRowLevelRoles(),
                        dataContext.territories().findTerritoriesFromRegion(US_EAST),
                        Set.of(dataContext.regions().find(US_EAST))
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static List<String> orderFulfillmentSpecialistRowLevelRoles() {
        return List.of(
                ShowOnlyAssociatedRegionsDataRole.CODE
        );
    }

    public record DataContext(
            List<Employee> managers,
            EmployeePositions employeePositions,
            AvailableRegions regions,
            AvailableTerritories territories,
            String tenantId
    ) {
    }
}
