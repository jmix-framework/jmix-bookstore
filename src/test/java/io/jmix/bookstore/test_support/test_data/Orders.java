package io.jmix.bookstore.test_support.test_data;

import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.test_support.OrderData;
import io.jmix.bookstore.order.test_support.OrderMapper;
import io.jmix.bookstore.order.test_support.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Orders
        implements TestDataProvisioning<OrderData, OrderData.OrderDataBuilder, Order> {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderLines orderLines;
    @Autowired
    Customers customers;
    @Autowired
    private OrderMapper orderMapper;

    public static final LocalDate DEFAULT_ORDER_DATE = LocalDate.now().plusDays(1);


    @Override
    public OrderData.OrderDataBuilder defaultData() {
        return OrderData.builder()
                .orderDate(DEFAULT_ORDER_DATE)
                .customer(customers.createDefault())
                .orderLines(List.of());
    }

    @Override
    public Order save(OrderData orderData)  {
        return orderRepository.save(orderData);
    }

    @Override
    public Order create(OrderData orderData) {
        return orderMapper.toEntity(orderData);
    }

    @Override
    public Order createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Order saveDefault() {
        return save(defaultData().build());
    }

}
