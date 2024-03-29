package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.security.session.BookstoreSessionData;
import io.jmix.core.Messages;
import io.jmix.core.TimeSource;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private NotificationFacet orderMarkedAsInDeliveryNotification;

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

    @Install(to = "newOrdersTable.create", subject = "afterCommitHandler")
    private void newOrdersTableCreateAfterCommitHandler(Order order) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(messageBundle.formatMessage("orderCreated", order.getOrderNumber())).show();
    }

    @Install(to = "confirmMarkAsInDeliveryDialog.ok", subject = "actionHandler")
    private void confirmMarkAsInDeliveryDialogOkActionHandler(ActionsAwareDialogFacet.DialogActionPerformedEvent<OptionDialogFacet> dialogActionPerformedEvent) {
        Order orderToMarkAsInDelivery = confirmedOrdersTable.getSingleSelected();

        orderToMarkAsInDelivery.setStatus(OrderStatus.IN_DELIVERY);
        orderToMarkAsInDelivery.setShippingDate(timeSource.now().toLocalDate());

        dataContext.commit();

        orderMarkedAsInDeliveryNotification.show();
    }
}
