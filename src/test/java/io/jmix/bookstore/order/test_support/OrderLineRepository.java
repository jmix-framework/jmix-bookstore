package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.product.supplier.test_support.EntityRepository;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class OrderLineRepository implements EntityRepository<OrderLineData, OrderLine> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;

    @Autowired
    OrderLineMapper mapper;

    @Override
    public OrderLine save(OrderLineData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
