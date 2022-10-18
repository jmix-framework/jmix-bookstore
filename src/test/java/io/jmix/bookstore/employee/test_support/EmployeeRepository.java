package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.product.supplier.test_support.EntityRepository;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmployeeRepository implements EntityRepository<EmployeeData, Employee> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    EmployeeMapper mapper;

    @Override
    public Employee save(EmployeeData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
