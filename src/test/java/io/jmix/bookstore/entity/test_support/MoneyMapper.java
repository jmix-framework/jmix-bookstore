package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.entity.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface MoneyMapper {

    Money toEntity(MoneyData moneyData);
}
