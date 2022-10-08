package io.jmix.bookstore.product.supplier.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.Supplier;

@UiController("bookstore_Supplier.browse")
@UiDescriptor("supplier-browse.xml")
@LookupComponent("table")
public class SupplierBrowse extends MasterDetailScreen<Supplier> {
}
