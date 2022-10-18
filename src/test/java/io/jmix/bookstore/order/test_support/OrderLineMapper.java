package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.product.supplier.test_support.JmixEntityFactory;
import io.jmix.bookstore.order.OrderLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderLineMapper {

    OrderLine toEntity(OrderLineData orderLineData);
}
