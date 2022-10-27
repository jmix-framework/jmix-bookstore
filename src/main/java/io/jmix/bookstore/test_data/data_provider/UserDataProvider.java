package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.security.OrderFulfillmentRole;
import io.jmix.bookstore.security.ProcurementEmployeeRole;
import io.jmix.bookstore.security.ProcurementSupervisorRole;
import io.jmix.bpmui.security.role.BpmProcessActorRole;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.UiMinimalRole;
import net.datafaker.Address;
import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_UserDataProvider")
public class UserDataProvider implements TestDataProvider<User, UserDataProvider.Dependencies> {

    protected final DataManager dataManager;
    protected final PasswordEncoder passwordEncoder;

    public UserDataProvider(DataManager dataManager, PasswordEncoder passwordEncoder) {
        this.dataManager = dataManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> create(int amount, Dependencies dependencies) {

        List<Object> storedOrderFulfillmentSupervisors = commit(createOrderFulfillmentSupervisors());

        List<Object> storedOrderFulfillmentUsers = commit(
                createOrderFulfillmentUsers(
                        findEmployeesOf(
                                storedOrderFulfillmentSupervisors
                        )
                )
        );

        List<Object> storedProcurementSupervisors = commit(createProcurementSupervisors());


        List<Object> storedProcurementEmployees = commit(
                createProcurementEmployees(
                        findEmployeesOf(storedProcurementSupervisors)
                )
        );


        return findUsersOf(
                joinLists(
                        storedOrderFulfillmentUsers,
                        storedProcurementSupervisors,
                        storedProcurementEmployees
                )
        );
    }

    private List<Object> createProcurementEmployees(List<Employee> supervisors) {

        List<EmployeeData> procurementEmployees = List.of(
                new EmployeeData(
                        "william",
                        Title.MR,
                        "William",
                        "Linville",
                        LocalDate.now().minusYears(0).minusMonths(5).minusDays(1),
                        randomOfList(supervisors),
                        UserRole.PROCUREMENT_EMPLOYEE
                ),
                new EmployeeData(
                        "sophia",
                        Title.MRS,
                        "Sophia",
                        "Burnett",
                        LocalDate.now().minusYears(4).minusMonths(4).minusDays(2),
                        randomOfList(supervisors),
                        UserRole.PROCUREMENT_EMPLOYEE
                )
        );

        return toEmployee(procurementEmployees);
    }

    private List<Object> toEmployee(List<EmployeeData> employeeData) {
        return employeeData.stream()
                .flatMap(this::toEmployeeWithUser)
                .collect(Collectors.toList());
    }

    private List<Object> createProcurementSupervisors() {

        List<EmployeeData> procurementSupervisors = List.of(
                new EmployeeData(
                        "nicole",
                        Title.MRS,
                        "Nicole",
                        "Berry",
                        LocalDate.now().minusYears(5).minusMonths(2).minusDays(9),
                        null,
                        UserRole.PROCUREMENT_EMPLOYEE
                )
        );

        return toEmployee(procurementSupervisors);
    }
    private List<Object> createOrderFulfillmentSupervisors() {

        List<EmployeeData> procurementSupervisors = List.of(
                new EmployeeData(
                        "adrian",
                        Title.MR,
                        "Adrian",
                        "Adams",
                        LocalDate.now().minusYears(7).minusMonths(2).minusDays(9),
                        null,
                        UserRole.ORDER_FULFILLMENT
                )
        );

        return toEmployee(procurementSupervisors);
    }

    private List<Object> createOrderFulfillmentUsers(List<Employee> supervisors) {

        List<EmployeeData> orderFulfillmentUsers = List.of(
//                new UserData(
//                        "mike",
//                        Title.MR,
//                        "Mike",
//                        "Holloway",
//                        LocalDate.now().minusYears(2).minusMonths(3),
//                        null,
//                        UserRole.ORDER_FULFILLMENT
//                ),
                new EmployeeData(
                        "oliver",
                        Title.MR,
                        "Oliver",
                        "Bolick",
                        LocalDate.now().minusYears(5).minusMonths(1).minusDays(10),
                        randomOfList(supervisors),
                        UserRole.ORDER_FULFILLMENT
                ),
                new EmployeeData(
                        "emma",
                        Title.MRS,
                        "Emma",
                        "McAlister",
                        LocalDate.now().minusYears(12).minusMonths(1).minusDays(0),
                        randomOfList(supervisors),
                        UserRole.ORDER_FULFILLMENT
                )
        );

        return toEmployee(orderFulfillmentUsers);
    }

    private Stream<Object> toEmployeeWithUser(EmployeeData employeeData) {
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
        employee.setHireDate(employeeData.hireDate());
        employee.setTitle(employeeData.title());
        employee.setReportsTo(employeeData.supervisor());

        employee.setBirthDate(randomBirthday());
        employee.setAddress(toAddress(new Faker().address()));



        Stream<RoleAssignmentEntity> roleAssignments = employeeData.userRole().getRoleCodes().stream().map(roleCode -> toRoleAssignment(user, roleCode));

        return Stream.concat(
                Stream.of(user, employee), roleAssignments
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

    private enum UserRole {
        ORDER_FULFILLMENT(
                List.of(
                        OrderFulfillmentRole.CODE,
                        UiMinimalRole.CODE,
                        BpmProcessActorRole.CODE
                )
        ),
        PROCUREMENT_EMPLOYEE(
                List.of(
                        ProcurementEmployeeRole.CODE,
                        UiMinimalRole.CODE,
                        BpmProcessActorRole.CODE
                )
        ),
        PROCUREMENT_SUPERVISOR(
                List.of(
                        ProcurementSupervisorRole.CODE,
                        UiMinimalRole.CODE,
                        BpmProcessActorRole.CODE
                )
        );

        private final List<String> roleCodes;

        UserRole(List<String> roleCodes) {
            this.roleCodes = roleCodes;
        }

        public List<String> getRoleCodes() {
            return roleCodes;
        }
    }

    public record Dependencies() {
    }

    public record EmployeeData(String username, Title title, String firstName, String lastName, LocalDate hireDate, Employee supervisor,
                               UserRole userRole) {
    }


    public <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<Employee> findEmployeesOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof Employee)
                .map(user -> (Employee) user)
                .collect(Collectors.toList());
    }

    private List<User> findUsersOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof User)
                .map(user -> (User) user)
                .collect(Collectors.toList());
    }

}
