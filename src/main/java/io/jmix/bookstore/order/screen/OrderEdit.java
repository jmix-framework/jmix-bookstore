package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@UiController("bookstore_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@Route(value = "orders/edit", parentPrefix = "orders")
public class OrderEdit extends StandardEditor<Order> {
    @Autowired
    private VBoxLayout customerSearchBox;
    @Autowired
    private VBoxLayout customerDetailsBox;
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private EntityPicker<FulfillmentCenter> fulfilledByField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        event.getEntity().setStatus(OrderStatus.NEW);
        event.getEntity().setOrderDate(timeSource.now().toLocalDate());

        fulfilledByField.setVisible(false);
    }

    @Subscribe("customerField")
    public void onCustomerFieldValueChange(HasValue.ValueChangeEvent<Customer> event) {
        customerSearchBox.setVisible(false);
        customerDetailsBox.setVisible(true);
    }

    @Subscribe("clearCustomerBtn")
    public void onClearCustomerBtnClick(Button.ClickEvent event) {
        getEditedEntity().setCustomer(null);
        customerSearchBox.setVisible(true);
        customerDetailsBox.setVisible(false);
    }
}
