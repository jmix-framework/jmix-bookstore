package io.jmix.bookstore.employee.region.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Region;

@UiController("bookstore_Region.browse")
@UiDescriptor("region-browse.xml")
@LookupComponent("regionsTable")
public class RegionBrowse extends StandardLookup<Region> {
}
