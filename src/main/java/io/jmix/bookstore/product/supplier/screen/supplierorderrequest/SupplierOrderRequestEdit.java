package io.jmix.bookstore.product.supplier.screen.supplierorderrequest;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;

@UiController("bookstore_SupplierOrderRequest.edit")
@UiDescriptor("supplier-order-request-edit.xml")
@EditedEntityContainer("supplierOrderRequestDc")
public class SupplierOrderRequestEdit extends StandardEditor<SupplierOrderRequest> {
}
