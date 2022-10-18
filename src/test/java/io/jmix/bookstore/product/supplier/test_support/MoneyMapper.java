package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.product.supplier.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface MoneyMapper {

    Money toEntity(MoneyData moneyData);
}
