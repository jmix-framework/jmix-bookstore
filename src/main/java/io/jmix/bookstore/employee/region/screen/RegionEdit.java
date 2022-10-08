package io.jmix.bookstore.employee.region.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Region;

@UiController("bookstore_Region.edit")
@UiDescriptor("region-edit.xml")
@EditedEntityContainer("regionDc")
public class RegionEdit extends StandardEditor<Region> {
}
