package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.security.EmployeeRole;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_ProcurementSpecialistDataProvider")
public class ProcurementSpecialistDataProvider implements TestDataProvider<Employee, ProcurementSpecialistDataProvider.DataContext> {

    protected final EmployeeDataProvider employeeDataProvider;

    public record DataContext(List<Employee> managers, EmployeePositions employeePositions) {
    }

    public ProcurementSpecialistDataProvider(EmployeeDataProvider employeeDataProvider) {
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
                        procurementSpecialist(dataContext),
                        LocalDate.now().minusYears(0).minusMonths(5).minusDays(1),
                        randomOfList(dataContext.managers()),
                        procurementSpecialistRoles()
                ),
                new EmployeeData(
                        "sophia",
                        Title.MRS,
                        "Sophia",
                        "Burnett",
                        procurementSpecialist(dataContext),
                        LocalDate.now().minusYears(4).minusMonths(4).minusDays(2),
                        randomOfList(dataContext.managers()),
                        procurementSpecialistRoles()
                )
        );

        return employeeDataProvider.save(employees);
    }

    private static List<String> procurementSpecialistRoles() {
        return List.of(
                ProcurementSpecialistRole.CODE
        );
    }

    private static Position procurementSpecialist(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.PROCUREMENT_SPECIALIST);
    }
}
