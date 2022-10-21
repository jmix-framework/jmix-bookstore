package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrder;

@UiController("bookstore_SupplierOrder.browse")
@UiDescriptor("supplier-order-browse.xml")
@LookupComponent("supplierOrdersTable")
public class SupplierOrderBrowse extends StandardLookup<SupplierOrder> {
}
