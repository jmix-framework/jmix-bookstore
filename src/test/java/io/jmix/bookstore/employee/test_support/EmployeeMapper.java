package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.product.supplier.test_support.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface EmployeeMapper {

    Employee toEntity(EmployeeData employeeData);
}
