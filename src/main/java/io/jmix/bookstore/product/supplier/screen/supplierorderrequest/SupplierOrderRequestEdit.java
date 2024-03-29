package io.jmix.bookstore.product.supplier.screen.supplierorderrequest;

import io.jmix.bookstore.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@UiController("bookstore_SupplierOrderRequest.edit")
@UiDescriptor("supplier-order-request-edit.xml")
@EditedEntityContainer("supplierOrderRequestDc")
@Route(value = "supplier-order-requests/edit", parentPrefix = "supplier-order-requests")
public class SupplierOrderRequestEdit extends StandardEditor<SupplierOrderRequest> {

}
