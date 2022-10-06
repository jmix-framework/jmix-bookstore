package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.order.Order;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
    @Autowired
    private VBoxLayout customerSearchBox;
    @Autowired
    private VBoxLayout customerDetailsBox;

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
