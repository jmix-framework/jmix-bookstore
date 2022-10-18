package io.jmix.bookstore.customer.test_support;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.product.supplier.test_support.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerRepository implements EntityRepository<CustomerData, Customer> {

    @Autowired
    DataManager dataManager;

    @Autowired
    CustomerMapper mapper;

    @Override
    public Customer save(CustomerData dto) {
        return dataManager.save(mapper.toEntity(dto));
    }

}
