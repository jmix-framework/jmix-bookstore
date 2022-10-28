package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.ProcurementSupervisorRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProcurementSupervisorDataProvider")
public class ProcurementSupervisorDataProvider implements TestDataProvider<Employee, ProcurementSupervisorDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext() {
    }

    public ProcurementSupervisorDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "nicole",
                        Title.MRS,
                        "Nicole",
                        "Berry",
                        LocalDate.now().minusYears(5).minusMonths(2).minusDays(9),
                        null,
                        List.of(ProcurementSupervisorRole.CODE,
                                UiMinimalRole.CODE,
                                BpmProcessActorRole.CODE)
                )
        );

        return employeeDataProvider.save(employees);
    }
}
