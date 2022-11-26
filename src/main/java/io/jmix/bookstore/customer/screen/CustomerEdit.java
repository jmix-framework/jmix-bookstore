package io.jmix.bookstore.customer.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.screen.addressmap.AddressMap;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

@UiController("bookstore_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
    @Autowired
    private Label<String> customerNameHeaderLabel;
    private final String DEFAULT_UNKNOWN_CUSTOMER_HEADER_LABEL = "Unknown Customer \uD83D\uDC71";
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("customerNameHeaderLabel")
    public void onCustomerNameHeaderLabelValueChange(HasValue.ValueChangeEvent<String> event) {
        if (!StringUtils.hasText(event.getValue())) {
            customerNameHeaderLabel.setValue(DEFAULT_UNKNOWN_CUSTOMER_HEADER_LABEL);
        }
    }

    @Subscribe("showOnMapBtn")
    public void onShowOnMapBtnClick(Button.ClickEvent event) {
        AddressMap addressMap = screenBuilders
                .screen(this)
                .withScreenClass(AddressMap.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        addressMap.setAddress(getEditedEntity().getAddress());
        addressMap.show();

    }
}
