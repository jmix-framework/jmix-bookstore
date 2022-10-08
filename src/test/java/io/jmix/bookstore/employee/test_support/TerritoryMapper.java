package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface TerritoryMapper {

    Territory toEntity(TerritoryData dto);
}
