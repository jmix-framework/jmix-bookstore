package io.jmix.bookstore.customer.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@UiController("bookstore_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
    @Autowired
    private Label<String> customerNameHeaderLabel;
    private final String DEFAULT_UNKNOWN_CUSTOMER_HEADER_LABEL = "Unknown Customer \uD83D\uDC71";

    @Subscribe("customerNameHeaderLabel")
    public void onCustomerNameHeaderLabelValueChange(HasValue.ValueChangeEvent<String> event) {
        if (!StringUtils.hasText(event.getValue())) {
            customerNameHeaderLabel.setValue(DEFAULT_UNKNOWN_CUSTOMER_HEADER_LABEL);
        }
    }
}
