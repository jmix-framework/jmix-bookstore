package io.jmix.bookstore.customer.test_support;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.test_support.AddressData;
import io.jmix.bookstore.entity.test_support.AddressMapper;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class Customers
        implements TestDataProvisioning<CustomerData, CustomerData.CustomerDataBuilder, Customer> {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CustomerMapper customerMapper;

    public static final String DEFAULT_FIRST_NAME = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final String DEFAULT_EMAIL = "first_name@last_name.com";
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";
    public static final String DEFAULT_STATE = "state";
    public static final String DEFAULT_COUNTRY = "country";

    @Override
    public CustomerData.CustomerDataBuilder defaultData() {
        return CustomerData.builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .country(DEFAULT_COUNTRY)
                        .position(new GeometryFactory().createPoint(new Coordinate( 0,0)))
                .build());
    }


    @Override
    public Customer save(CustomerData customerData)  {
        return customerRepository.save(customerData);
    }

    @Override
    public Customer create(CustomerData customerData) {
        return customerMapper.toEntity(customerData);
    }

    @Override
    public Customer createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Customer saveDefault() {
        return save(defaultData().build());
    }

}
