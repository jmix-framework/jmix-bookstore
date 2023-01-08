package io.jmix.bookstore.order.event;

import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("bookstore_OrderEventListener")
public class OrderEventListener {

    protected final ApplicationEventPublisher applicationEventPublisher;
    private final DataManager dataManager;

    public OrderEventListener(ApplicationEventPublisher applicationEventPublisher, DataManager dataManager) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.dataManager = dataManager;
    }

    @TransactionalEventListener
    public void onOrderChangedAfterCommit(EntityChangedEvent<Order> event) {

        OrderStatus previousStatus = event.getChanges().getOldValue("status");

        if (OrderStatus.NEW.equals(previousStatus)) {
            Id<Order> orderId = event.getEntityId();

            Order order = dataManager.load(orderId)
                    .joinTransaction(false)
                    .one();

            if (order.getStatus().equals(OrderStatus.CONFIRMED)) {
                applicationEventPublisher.publishEvent(new OrderConfirmedEvent(order));
            }
        }
    }
}
