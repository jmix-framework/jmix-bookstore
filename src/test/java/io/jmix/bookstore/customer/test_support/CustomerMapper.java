package io.jmix.bookstore.customer.test_support;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface CustomerMapper {

    Customer toEntity(CustomerData customerData);
}
