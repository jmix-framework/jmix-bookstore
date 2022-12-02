package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.entity.test_support.AddressData;
import io.jmix.bookstore.entity.test_support.AddressMapper;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class Employees
        implements TestDataProvisioning<EmployeeData, EmployeeData.EmployeeDataBuilder, Employee> {

    @Autowired
    EmployeeRepository repository;
    @Autowired
    AddressMapper addressMapper;

    public static final String DEFAULT_FIRST_NAME = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1980,1,1);
    public static final LocalDate DEFAULT_HIRE_DATE = LocalDate.of(2022,1,1);
    public static final LocalDate DEFAULT_PROBATION_END_DATE = LocalDate.of(2022,7,1);
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";
    public static final String DEFAULT_STATE = "state";
    public static final String DEFAULT_COUNTRY = "country";
    public static final String DEFAULT_NOTES = "notes";
    public static final Title DEFAULT_TITLE = Title.MRS;
    @Autowired
    private EmployeeMapper mapper;

    @Override
    public EmployeeData.EmployeeDataBuilder defaultData() {
        return EmployeeData.builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .hireDate(DEFAULT_HIRE_DATE)
                .probationEndDate(DEFAULT_PROBATION_END_DATE)
                .title(DEFAULT_TITLE)
                .notes(DEFAULT_NOTES)
                .address(defaultAddress());
    }
    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .country(DEFAULT_COUNTRY)
                .build());
    }

    @Override
    public Employee save(EmployeeData data) {
        return repository.save(data);
    }

    @Override
    public Employee create(EmployeeData data) {
        return mapper.toEntity(data);
    }

    @Override
    public Employee createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Employee saveDefault() {
        return save(defaultData().build());
    }
}
