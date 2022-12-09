package io.jmix.bookstore.product.supplier.screen;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.Supplier;

@UiController("bookstore_Supplier.edit")
@UiDescriptor("supplier-edit.xml")
@EditedEntityContainer("supplierDc")
@Route(value = "suppliers/edit", parentPrefix = "suppliers")
public class SupplierEdit extends StandardEditor<Supplier> {
}
