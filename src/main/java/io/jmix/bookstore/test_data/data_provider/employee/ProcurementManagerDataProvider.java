package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.ProcurementManagerRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProcurementManagerDataProvider")
public class ProcurementManagerDataProvider implements TestDataProvider<Employee, ProcurementManagerDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(EmployeePositions employeePositions, String tenantId) {
    }
    public ProcurementManagerDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        dataContext.tenantId(),
                        "nicole",
                        Title.MRS,
                        "Nicole",
                        "Berry",
                        procurementManager(dataContext),
                        LocalDate.now().minusYears(5).minusMonths(2).minusDays(9),
                        null,
                        procurementManagerRoles(),
                        List.of(),
                        Set.of(),
                        Set.of()
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static List<String> procurementManagerRoles() {
        return List.of(
                ProcurementManagerRole.CODE
        );
    }

    private static Position procurementManager(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.PROCUREMENT_MANAGER);
    }
}
