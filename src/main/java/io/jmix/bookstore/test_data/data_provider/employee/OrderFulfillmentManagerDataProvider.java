package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.region.AvailableRegions.Entry.US_SOUTH;

@Component("bookstore_OrderFulfillmentManagerDataProvider")
public class OrderFulfillmentManagerDataProvider implements TestDataProvider<Employee, OrderFulfillmentManagerDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public OrderFulfillmentManagerDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    private static List<String> orderFulfillmentManagerRoles() {
        return List.of(
                OrderFulfillmentRole.CODE
        );
    }

    private static Position orderFulfillmentManager(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_MANAGER);
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        dataContext.tenantId(),
                        "adrian",
                        Title.MR,
                        "Adrian",
                        "Adams",
                        orderFulfillmentManager(dataContext),
                        LocalDate.now().minusYears(7).minusMonths(2).minusDays(9),
                        null,
                        orderFulfillmentManagerRoles(),
                        List.of(),
                        dataContext.territories().findTerritoriesFromRegion(US_SOUTH),
                        Set.of(
                                dataContext.regions().find(US_SOUTH)
                        )
                )
        );

        return employeeDataProvider.save(employees);
    }

    public record DataContext(EmployeePositions employeePositions, AvailableRegions regions, AvailableTerritories territories, String tenantId) {
    }

}
