package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.ProcurementEmployeeRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProcurementEmployeeDataProvider")
public class ProcurementEmployeeDataProvider implements TestDataProvider<Employee, ProcurementEmployeeDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> supervisors) {
    }

    public ProcurementEmployeeDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        "william",
                        Title.MR,
                        "William",
                        "Linville",
                        LocalDate.now().minusYears(0).minusMonths(5).minusDays(1),
                        randomOfList(dataContext.supervisors()),
                        List.of(ProcurementEmployeeRole.CODE, UiMinimalRole.CODE, BpmProcessActorRole.CODE)
                ),
                new EmployeeData(
                        "sophia",
                        Title.MRS,
                        "Sophia",
                        "Burnett",
                        LocalDate.now().minusYears(4).minusMonths(4).minusDays(2),
                        randomOfList(dataContext.supervisors()),
                        List.of(ProcurementEmployeeRole.CODE, UiMinimalRole.CODE, BpmProcessActorRole.CODE)
                )
        );

        return employeeDataProvider.save(employees);
    }
}
