package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_OrderFulfillmentEmployeeDataProvider")
public class OrderFulfillmentEmployeeDataProvider implements TestDataProvider<Employee, OrderFulfillmentEmployeeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> supervisors) {
    }

    public OrderFulfillmentEmployeeDataProvider(EmployeeDataProvider employeeDataProvider) {
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
                        LocalDate.now().minusYears(5).minusMonths(1).minusDays(10),
                        randomOfList(dataContext.supervisors()),
                        List.of(
                                OrderFulfillmentRole.CODE,
                                UiMinimalRole.CODE,
                                BpmProcessActorRole.CODE
                        )
                ),
                new EmployeeData(
                        "emma",
                        Title.MRS,
                        "Emma",
                        "McAlister",
                        LocalDate.now().minusYears(12).minusMonths(1).minusDays(0),
                        randomOfList(dataContext.supervisors()),
                        List.of(
                                OrderFulfillmentRole.CODE,
                                UiMinimalRole.CODE,
                                BpmProcessActorRole.CODE
                        )
                )
        );

        return employeeDataProvider.save(employees);
    }
}
