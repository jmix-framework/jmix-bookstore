package io.jmix.bookstore.product.supplier.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.Supplier;

@UiController("bookstore_Supplier.edit")
@UiDescriptor("supplier-edit.xml")
@EditedEntityContainer("supplierDc")
public class SupplierEdit extends StandardEditor<Supplier> {
}
