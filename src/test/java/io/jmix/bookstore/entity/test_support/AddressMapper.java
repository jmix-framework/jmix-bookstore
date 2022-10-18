package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface AddressMapper {

    Address toEntity(AddressData addressData);
}
