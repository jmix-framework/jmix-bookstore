package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.product.supplier.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface AddressMapper {

    Address toEntity(AddressData addressData);
}
