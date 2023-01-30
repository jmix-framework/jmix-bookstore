package io.jmix.bookstore.customer.screen;

import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Customer.lookup")
@UiDescriptor("customer-lookup.xml")
@LookupComponent("customersTable")
public class CustomerLookup extends StandardLookup<Customer> {
    @Autowired
    private CollectionLoader<Customer> customersDl;
    @Autowired
    private CollectionContainer<Customer> customersDc;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private DataGrid<Customer> customersTable;
    @Autowired
    private TextField<String> customerFilterField;

    @Subscribe("customerFilterField")
    public void onCustomerFilterFieldValueChange(HasValue.ValueChangeEvent<String> event) {

        String searchString = event.getValue();

        customersDl.setParameter("searchString", "%" + searchString + "%");
        customersDl.load();


        if (searchString != null) {
            customersTable.focus();
            if (customersDc.getItems().isEmpty()) {
                customersTable.setEmptyStateMessage(messageBundle.formatMessage("noCustomerFoundForSearch", searchString));
                customersTable.setEmptyStateLinkMessage(messageBundle.formatMessage("trySearchAgain"));
            }
            else {
                customersTable.setSelected(customersDc.getItems().get(0));
            }
        }
    }

    @Install(to = "customersTable", subject = "emptyStateLinkClickHandler")
    private void customersTableEmptyStateLinkClickHandler(DataGrid.EmptyStateClickEvent<Customer> emptyStateClickEvent) {
        customerFilterField.focus();
        customerFilterField.setValue(null);
        customersTable.setEmptyStateLinkMessage(null);
        customersTable.setEmptyStateMessage(messageBundle.getMessage("searchForCustomersFirst"));
    }
}
