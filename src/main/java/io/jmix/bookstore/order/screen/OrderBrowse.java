package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.order.OrderStatus;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.order.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
    @Autowired
    private DataGrid<Order> ordersTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Messages messages;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("ordersTable.trackDelivery")
    public void onOrdersTableTrackDelivery(Action.ActionPerformedEvent event) {
        Order orderToTrack = ordersTable
                .getSingleSelected();

        if (!orderToTrack.getStatus().equals(OrderStatus.IN_DELIVERY)) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messageBundle.formatMessage("trackDeliveryInvalidStatus", messages.getMessage(OrderStatus.IN_DELIVERY)))
                    .show();
        }
        else {
            TrackDeliveryMap trackDeliveryMap = screenBuilders
                    .screen(this)
                    .withScreenClass(TrackDeliveryMap.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build();
            trackDeliveryMap.setStart(orderToTrack.getFulfilledBy().getAddress());
            trackDeliveryMap.setEnd(orderToTrack.getCustomer().getAddress());
            trackDeliveryMap.show();
        }
    }
}
