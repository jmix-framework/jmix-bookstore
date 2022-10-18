package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.order.Order;
import io.jmix.ui.screen.*;

@UiController("bookstore_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {

}
