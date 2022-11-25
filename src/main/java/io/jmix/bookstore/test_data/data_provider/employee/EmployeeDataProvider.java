package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.security.EmployeeRole;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.reportsui.role.ReportsRunRole;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.UiMinimalRole;
import net.datafaker.Address;
import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_EmployeeDataProvider")
public class EmployeeDataProvider {

    protected final DataManager dataManager;
    protected final PasswordEncoder passwordEncoder;

    public EmployeeDataProvider(DataManager dataManager, PasswordEncoder passwordEncoder) {
        this.dataManager = dataManager;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Employee> save(List<EmployeeData> employeeData) {
        return findEmployeesOf(commit(toEmployeesWithUser(employeeData)));
    }

    public List<Object> toEmployeesWithUser(List<EmployeeData> employeeData) {
        return employeeData.stream()
                .flatMap(this::toEmployeeWithUser)
                .collect(Collectors.toList());
    }

    public Stream<Object> toEmployeeWithUser(EmployeeData employeeData) {
        User user = dataManager.create(User.class);

        user.setFirstName(employeeData.firstName());
        user.setLastName(employeeData.lastName());
        user.setUsername(employeeData.username());
        user.setPassword(passwordEncoder.encode(employeeData.username()));
        user.setEmail("%s@jmix-bookstore.com".formatted(employeeData.username()));


        Employee employee = dataManager.create(Employee.class);

        employee.setUser(user);
        employee.setFirstName(employeeData.firstName());
        employee.setLastName(employeeData.lastName());
        employee.setPosition(employeeData.position());
        employee.setHireDate(employeeData.hireDate());
        employee.setProbationEndDate(employeeData.hireDate().plusMonths(6));
        employee.setTitle(employeeData.title());
        employee.setReportsTo(employeeData.manager());

        employee.setBirthDate(randomBirthday());
        employee.setAddress(toAddress(new Faker().address()));


        Stream<RoleAssignmentEntity> roleAssignments =
                Stream.concat(employeeData.roleCodes().stream(), baseEmployeeRoles()).map(roleCode -> toRoleAssignment(user, roleCode));

        return Stream.concat(
                Stream.of(user, employee), roleAssignments
        );
    }

    private Stream<String> baseEmployeeRoles() {
        return Stream.of(
                UiMinimalRole.CODE,
                BpmProcessActorRole.CODE,
                ReportsRunRole.CODE,
                "notifications-in-app-notifications-user",
                EmployeeRole.CODE
        );
    }

    private io.jmix.bookstore.entity.Address toAddress(Address address) {
        io.jmix.bookstore.entity.Address addressEntity = dataManager.create(io.jmix.bookstore.entity.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(String.format("%s %s", address.streetName(), address.buildingNumber()));
        addressEntity.setPostCode(address.postcode());
        return addressEntity;
    }
    private static LocalDate randomBirthday() {
        DateAndTime date = new Faker().date();
        return date.birthday().toLocalDateTime().toLocalDate();
    }

    private RoleAssignmentEntity toRoleAssignment(User user, String roleCode) {
        RoleAssignmentEntity userRole = dataManager.create(RoleAssignmentEntity.class);
        userRole.setUsername(user.getUsername());
        userRole.setRoleType(RoleAssignmentRoleType.RESOURCE);
        userRole.setRoleCode(roleCode);
        return userRole;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

    private List<Employee> findEmployeesOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof Employee)
                .map(user -> (Employee) user)
                .collect(Collectors.toList());
    }

}
