package io.jmix.bookstore.employee.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Employee;

@UiController("bookstore_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeEdit extends StandardEditor<Employee> {
}
