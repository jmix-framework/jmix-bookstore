package io.jmix.bookstore.employee.region.screen;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Region;

@UiController("bookstore_Region.edit")
@UiDescriptor("region-edit.xml")
@EditedEntityContainer("regionDc")
@Route(value = "regions/edit", parentPrefix = "regions")
public class RegionEdit extends StandardEditor<Region> {
}
