package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.product.supplier.test_support.EntityRepository;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class OrderRepository implements EntityRepository<OrderData, Order> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    OrderMapper mapper;

    @Override
    public Order save(OrderData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
