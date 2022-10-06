package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component("bookstore_OrderLineRepository")
public class OrderLineRepository implements EntityRepository<OrderLineData, OrderLine> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderLineMapper mapper;

    @Override
    public OrderLine save(OrderLineData dto) {
        return dataManager.save(mapper.toEntity(dto));
    }

}
