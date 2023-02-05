package io.jmix.bookstore.customer.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.CustomerAddressUpdate;
import io.jmix.bookstore.screen.addressmap.AddressMap;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@UiController("bookstore_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@Route(value = "customers/edit", parentPrefix = "customers")
public class CustomerEdit extends StandardEditor<Customer> {
    @Autowired
    private Label<String> customerNameHeaderLabel;
    private final String DEFAULT_UNKNOWN_CUSTOMER_HEADER_LABEL = "Unknown Customer \uD83D\uDC71";
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private CustomerAddressUpdate customerAddressUpdate;

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

    @Subscribe("addressStreetField")
    public void onAddressStreetFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateCustomerAddressLocation(event);
    }

    @Subscribe("addressPostCodeField")
    public void onAddressPostCodeFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateCustomerAddressLocation(event);
    }

    @Subscribe("addressCityField")
    public void onAddressCityFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateCustomerAddressLocation(event);
    }

    @Subscribe("addressStateField")
    public void onAddressStateFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateCustomerAddressLocation(event);
    }

    @Subscribe("addressCountryField")
    public void onAddressCountryFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateCustomerAddressLocation(event);
    }

    private void updateCustomerAddressLocation(HasValue.ValueChangeEvent<String> event) {
        if (event.isUserOriginated()) {
            customerAddressUpdate.updateCustomerAddress(getEditedEntity());
        }
    }

}
