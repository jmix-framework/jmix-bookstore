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

@Component("bookstore_OrderFulfillmentManagerDataProvider")
public class OrderFulfillmentManagerDataProvider implements TestDataProvider<Employee, OrderFulfillmentManagerDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(EmployeePositions employeePositions, AvailableTerritories territories, String tenantId) {
    }

    public OrderFulfillmentManagerDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
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
                        dataContext.territories().findTerritoriesFromRegion(AvailableRegions.Entry.US_SOUTH)
                )
        );

        return employeeDataProvider.save(employees);
    }


    private static List<String> orderFulfillmentManagerRoles() {
        return List.of(
                OrderFulfillmentRole.CODE
        );
    }

    private static Position orderFulfillmentManager(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_MANAGER);
    }

}
