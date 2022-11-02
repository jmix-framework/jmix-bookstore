package io.jmix.bookstore.order.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.order.Order;

@UiController("bookstore_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}
