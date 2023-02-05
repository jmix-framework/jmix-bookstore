package io.jmix.bookstore.customer.listener;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.CustomerAddressUpdate;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("bookstore_UpdateCustomerAddressOnSave")
public class UpdateCustomerAddressOnSave {
    private final CustomerAddressUpdate customerAddressUpdate;

    public UpdateCustomerAddressOnSave(CustomerAddressUpdate customerAddressUpdate) {
        this.customerAddressUpdate = customerAddressUpdate;
    }


    @EventListener
    public void onCustomerSaving(EntitySavingEvent<Customer> event) {

        Customer customer = event.getEntity();

        if (customer.getAddress().getPosition() == null) {
            customerAddressUpdate.updateCustomerAddress(customer);
        }
    }
}
