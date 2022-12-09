package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrder;

@UiController("bookstore_SupplierOrder.edit")
@UiDescriptor("supplier-order-edit.xml")
@EditedEntityContainer("supplierOrderDc")
@Route(value = "supplier-orders/edit", parentPrefix = "supplier-orders")
public class SupplierOrderEdit extends StandardEditor<SupplierOrder> {
}
