package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.security.FullAccessRole;
import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import net.datafaker.Address;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomBirthday;


@Component("bookstore_AdminDataProvider")
public class ItAdministratorEmployeeDataProvider implements TestDataProvider<Employee, ItAdministratorEmployeeDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(EmployeePositions employeePositions) {
    }

    public ItAdministratorEmployeeDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Employee> create(DataContext dataContext) {

        EmployeeData employeeData = new EmployeeData(
                "admin",
                Title.MR,
                "Mike",
                "Holloway",
                itAdministrator(dataContext),
                LocalDate.now().minusYears(7).minusMonths(1).minusDays(30),
                null,
                List.of(FullAccessRole.CODE),
                List.of(),
                Set.of()
        );

        Employee employee = dataManager.create(Employee.class);

        employee.setUser(dataManager.load(User.class).condition(PropertyCondition.equal("username", "admin")).one());
        employee.setFirstName(employeeData.firstName());
        employee.setLastName(employeeData.lastName());
        employee.setPosition(employeeData.position());
        employee.setHireDate(employeeData.hireDate());
        employee.setTitle(employeeData.title());
        employee.setReportsTo(employeeData.manager());

        employee.setBirthDate(randomBirthday());
        employee.setAddress(toAddress(new Faker().address()));

        return List.of(dataManager.save(employee));
    }


    private io.jmix.bookstore.entity.Address toAddress(Address address) {
        io.jmix.bookstore.entity.Address addressEntity = dataManager.create(io.jmix.bookstore.entity.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(String.format("%s %s", address.streetName(), address.buildingNumber()));
        addressEntity.setPostCode(address.postcode());
        return addressEntity;
    }

    private static Position itAdministrator(DataContext dataContext) {
        return dataContext.employeePositions().find(EmployeePositions.AvailablePosition.IT_ADMINISTRATOR);
    }
}
