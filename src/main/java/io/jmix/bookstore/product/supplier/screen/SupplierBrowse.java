package io.jmix.bookstore.product.supplier.screen;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.Supplier;

@UiController("bookstore_Supplier.browse")
@UiDescriptor("supplier-browse.xml")
@LookupComponent("suppliersTable")
@Route(value = "suppliers")
public class SupplierBrowse extends StandardLookup<Supplier> {
}
