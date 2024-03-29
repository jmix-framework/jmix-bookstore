package io.jmix.bookstore.employee.screen;

import io.jmix.bookstore.entity.AddressPositionUpdate;
import io.jmix.bookstore.screen.addressmap.AddressMap;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@Route(value = "employees/edit", parentPrefix = "employees")
public class EmployeeEdit extends StandardEditor<Employee> {


    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private AddressPositionUpdate addressPositionUpdate;

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
        updateAddressPosition(event);
    }

    @Subscribe("addressPostCodeField")
    public void onAddressPostCodeFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateAddressPosition(event);
    }

    @Subscribe("addressCityField")
    public void onAddressCityFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateAddressPosition(event);
    }

    @Subscribe("addressStateField")
    public void onAddressStateFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateAddressPosition(event);
    }

    @Subscribe("addressCountryField")
    public void onAddressCountryFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        updateAddressPosition(event);
    }

    private void updateAddressPosition(HasValue.ValueChangeEvent<String> event) {
        if (event.isUserOriginated()) {
            addressPositionUpdate.updateAddressPosition(getEditedEntity().getAddress());
        }
    }
}
