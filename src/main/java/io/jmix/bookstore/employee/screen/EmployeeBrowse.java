package io.jmix.bookstore.employee.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Employee;

@UiController("bookstore_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
public class EmployeeBrowse extends StandardLookup<Employee> {
}
