package io.jmix.bookstore.employee.screen;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Employee;

@UiController("bookstore_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@Route(value = "employees")
public class EmployeeBrowse extends StandardLookup<Employee> {
}
