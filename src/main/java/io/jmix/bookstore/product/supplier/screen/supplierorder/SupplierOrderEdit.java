package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrder;

@UiController("bookstore_SupplierOrder.edit")
@UiDescriptor("supplier-order-edit.xml")
@EditedEntityContainer("supplierOrderDc")
public class SupplierOrderEdit extends StandardEditor<SupplierOrder> {
}
