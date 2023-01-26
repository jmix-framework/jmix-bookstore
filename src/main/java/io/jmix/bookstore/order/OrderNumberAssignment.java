package io.jmix.bookstore.order;

import io.jmix.bookstore.order.entity.Order;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.multitenancy.core.TenantProvider;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("bookstore_OrderNumberAssignment")
public class OrderNumberAssignment {

    private final Sequences sequences;
    private final TenantProvider tenantProvider;

    public OrderNumberAssignment(Sequences sequences, TenantProvider tenantProvider) {
        this.sequences = sequences;
        this.tenantProvider = tenantProvider;
    }

    @EventListener
    public void onOrderSaving(EntitySavingEvent<Order> event) {

        if (event.isNewEntity() && event.getEntity().getOrderNumber() == null) {
            long nextOrderNumberForTenant = sequences.createNextValue(Sequence.withName("%s_order_orderNumber".formatted(tenantId())));
            event.getEntity().setOrderNumber(nextOrderNumberForTenant);
        }

    }

    private String tenantId() {
        return tenantProvider.getCurrentUserTenantId().replaceAll("-", "_");
    }
}
