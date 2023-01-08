package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.bookstore.security.ShowOnlyActiveSuppliersRole;
import io.jmix.bookstore.test_data.data_provider.RandomValues;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProcurementSpecialistDataProvider")
public class ProcurementSpecialistDataProvider implements TestDataProvider<Employee, ProcurementSpecialistDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> managers, EmployeePositions employeePositions, String tenantId) {
    }

    public ProcurementSpecialistDataProvider(EmployeeDataProvider employeeDataProvider) {
        this.employeeDataProvider = employeeDataProvider;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        List<EmployeeData> employees = List.of(
                new EmployeeData(
                        dataContext.tenantId(),
                        "william",
                        Title.MR,
                        "William",
                        "Linville",
                        procurementSpecialist(dataContext),
                        LocalDate.now().minusYears(0).minusMonths(5).minusDays(1),
                        RandomValues.randomOfList(dataContext.managers()),
                        procurementSpecialistResourceRoles(),
                        procurementSpecialistRowLevelRoles(),
                        Set.of()
                ),
                new EmployeeData(
                        dataContext.tenantId(),
                        "sophia",
                        Title.MRS,
                        "Sophia",
                        "Burnett",
                        procurementSpecialist(dataContext),
                        LocalDate.now().minusYears(4).minusMonths(4).minusDays(2),
                        RandomValues.randomOfList(dataContext.managers()),
                        procurementSpecialistResourceRoles(),
                        procurementSpecialistRowLevelRoles(),
                        Set.of()
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static List<String> procurementSpecialistResourceRoles() {
        return List.of(
                ProcurementSpecialistRole.CODE
        );
    }

    private static List<String> procurementSpecialistRowLevelRoles() {
        return List.of(
                ShowOnlyActiveSuppliersRole.CODE
        );
    }

    private static Position procurementSpecialist(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.PROCUREMENT_SPECIALIST);
    }
}
