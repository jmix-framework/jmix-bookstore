package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.FullAccessRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("bookstore_AdminDataProvider")
public class ItAdministratorEmployeeDataProvider implements TestDataProvider<Employee, ItAdministratorEmployeeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(EmployeePositions employeePositions) {
    }

    public ItAdministratorEmployeeDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "admin",
                        Title.MR,
                        "Mike",
                        "Holloway",
                        itAdministrator(dataContext),
                        LocalDate.now().minusYears(7).minusMonths(1).minusDays(30),
                        null,
                        List.of(FullAccessRole.CODE)
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static Position itAdministrator(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.IT_ADMINISTRATOR);
    }
}
