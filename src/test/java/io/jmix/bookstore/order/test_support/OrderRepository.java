package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.order.Order;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component("bookstore_OrderRepository")
public class OrderRepository implements EntityRepository<OrderData, Order> {

    @Autowired
    DataManager dataManager;
    @Autowired
    OrderMapper mapper;

    @Override
    public Order save(OrderData dto) {
        return dataManager.save(mapper.toEntity(dto));
    }

}
