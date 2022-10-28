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

@Component("bookstore_OrderFulfillmentSupervisorDataProvider")
public class OrderFulfillmentSupervisorDataProvider implements TestDataProvider<Employee, OrderFulfillmentSupervisorDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext() {
    }

    public OrderFulfillmentSupervisorDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "adrian",
                        Title.MR,
                        "Adrian",
                        "Adams",
                        LocalDate.now().minusYears(7).minusMonths(2).minusDays(9),
                        null,
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
