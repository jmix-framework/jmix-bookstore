package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.order.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderMapper {

    Order toEntity(OrderData orderData);
}
