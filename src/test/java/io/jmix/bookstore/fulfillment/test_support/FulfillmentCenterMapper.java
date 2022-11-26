package io.jmix.bookstore.fulfillment.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface FulfillmentCenterMapper {

    FulfillmentCenter toEntity(FulfillmentCenterData dto);
}
