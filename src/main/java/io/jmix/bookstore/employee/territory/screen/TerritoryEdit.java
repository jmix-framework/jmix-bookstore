package io.jmix.bookstore.employee.territory.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Territory;

@UiController("bookstore_Territory.edit")
@UiDescriptor("territory-edit.xml")
@EditedEntityContainer("territoryDc")
public class TerritoryEdit extends StandardEditor<Territory> {
}
