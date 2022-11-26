package io.jmix.bookstore.fulfillment.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FulfillmentCenterRepository implements EntityRepository<FulfillmentCenterData, FulfillmentCenter> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    FulfillmentCenterMapper mapper;

    @Override
    public FulfillmentCenter save(FulfillmentCenterData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
