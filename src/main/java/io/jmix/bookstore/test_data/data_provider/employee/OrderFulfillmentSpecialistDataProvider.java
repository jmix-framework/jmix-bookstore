package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.EmployeeRole;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_OrderFulfillmentEmployeeDataProvider")
public class OrderFulfillmentSpecialistDataProvider implements TestDataProvider<Employee, OrderFulfillmentSpecialistDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> managers, EmployeePositions employeePositions) {
    }

    public OrderFulfillmentSpecialistDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "oliver",
                        Title.MR,
                        "Oliver",
                        "Bolick",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(5).minusMonths(1).minusDays(10),
                        randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistRoles()
                ),
                new EmployeeData(
                        "hikari",
                        Title.MRS,
                        "Hikari",
                        "Miyama",
                        orderFulfillmentSpecialist(dataContext),
                        LocalDate.now().minusYears(12).minusMonths(1).minusDays(0),
                        randomOfList(dataContext.managers()),
                        orderFulfillmentSpecialistRoles()
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static List<String> orderFulfillmentSpecialistRoles() {
        return List.of(
                EmployeeRole.CODE,
                OrderFulfillmentRole.CODE,
                UiMinimalRole.CODE,
                BpmProcessActorRole.CODE
        );
    }

    private static Position orderFulfillmentSpecialist(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST);
    }
}
