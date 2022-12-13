package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.OrderStatus;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalDate;

@UiController("bookstore_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("confirmedOrdersTable")
@Route(value = "orders")
public class OrderBrowse extends StandardLookup<Order> {
    @Autowired
    private DataGrid<Order> confirmedOrdersTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Messages messages;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataGrid<Order> newOrdersTable;
    @Autowired
    private NotificationFacet orderConfirmedNotification;

    @Subscribe("confirmedOrdersTable.trackDelivery")
    public void onAllOrdersTableTrackDelivery(Action.ActionPerformedEvent event) {
        Order orderToTrack = confirmedOrdersTable
                .getSingleSelected();

        if (!orderToTrack.getStatus().equals(OrderStatus.IN_DELIVERY)) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messageBundle.formatMessage("trackDeliveryInvalidStatus", messages.getMessage(OrderStatus.IN_DELIVERY)))
                    .show();
        } else {
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

    @Subscribe("newOrdersTable.confirm")
    public void onNewOrdersTableConfirm(Action.ActionPerformedEvent event) {
        screenBuilders.editor(newOrdersTable)
                .withScreenClass(ConfirmOrder.class)
                .withAfterCloseListener(confirmOrderAfterScreenCloseEvent -> {
                    if (confirmOrderAfterScreenCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        orderConfirmedNotification.show();
                        getScreenData().loadAll();
                    }
                })
                .show();
    }

}
