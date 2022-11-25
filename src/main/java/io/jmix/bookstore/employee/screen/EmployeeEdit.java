package io.jmix.bookstore.employee.screen;

import io.jmix.bookstore.screen.addressmap.AddressMap;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeEdit extends StandardEditor<Employee> {


    @Autowired
    private ScreenBuilders screenBuilders;

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
