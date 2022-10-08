package io.jmix.bookstore.employee.territory.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Territory;

@UiController("bookstore_Territory.browse")
@UiDescriptor("territory-browse.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowse extends StandardLookup<Territory> {
}
