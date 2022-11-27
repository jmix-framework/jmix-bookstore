package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.security.ShowOnlyAssociatedRegionsDataRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_OrderFulfillmentEmployeeDataProvider")
public class OrderFulfillmentSpecialistDataProvider implements TestDataProvider<Employee, OrderFulfillmentSpecialistDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> managers, EmployeePositions employeePositions, List<Territory> territories) {
    }

    public OrderFulfillmentSpecialistDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "melissa",
                        Title.MR,
                        "Melissa",
                        "Arendt",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(5).minusMonths(1).minusDays(10),
                        randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistResourceRoles(),
                        orderFulfillmentSpecialistRowLevelRoles(),
                        territoriesForRegion(dataContext.territories(), "US-South")
                ),
                new EmployeeData(
                        "hikari",
                        Title.MRS,
                        "Hikari",
                        "Miyama",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(12).minusMonths(1).minusDays(0),
                        randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistResourceRoles(),
                        orderFulfillmentSpecialistRowLevelRoles(),
                        territoriesForRegion(dataContext.territories(), "US-East")
                )
        );

        return employeeDataProvider.save(employees);
    }

    private List<String> orderFulfillmentSpecialistRowLevelRoles() {
        return List.of(
                ShowOnlyAssociatedRegionsDataRole.CODE
        );
    }
    private Set<Territory> territoriesForRegion(List<Territory> territories, String regionName) {
        return territories.stream()
                .filter(territory -> regionName.equals(territory.getRegion().getName()))
                .collect(Collectors.toSet());
    }
    private static List<String> orderFulfillmentSpecialistResourceRoles() {
        return List.of(
                OrderFulfillmentRole.CODE
        );
    }

    private static Position orderFulfillmentSpecialist(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST);
    }
}
