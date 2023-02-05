package io.jmix.bookstore.employee.listener;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.AddressPositionUpdate;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("bookstore_UpdateEmployeeAddressOnSave")
public class UpdateEmployeeAddressOnSave {
    private final AddressPositionUpdate addressPositionUpdate;

    public UpdateEmployeeAddressOnSave(AddressPositionUpdate addressPositionUpdate) {
        this.addressPositionUpdate = addressPositionUpdate;
    }


    @EventListener
    public void onEmployeeSaving(EntitySavingEvent<Employee> event) {

        Employee employee = event.getEntity();

        if (employee.getAddress().getPosition() == null) {
            addressPositionUpdate.updateAddressPosition(employee.getAddress());
        }
    }
}
