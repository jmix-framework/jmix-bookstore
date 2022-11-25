package io.jmix.bookstore.security;

import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Procurement Manager", code = ProcurementManagerRole.CODE)
public interface ProcurementManagerRole {
    String CODE = "procurement-manager";

    @EntityAttributePolicy(entityClass = Supplier.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Supplier.class, actions = EntityPolicyAction.ALL)
    void supplier();

    @EntityAttributePolicy(entityClass = SupplierOrder.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrder.class, actions = EntityPolicyAction.ALL)
    void supplierOrder();

    @EntityAttributePolicy(entityClass = SupplierOrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrderLine.class, actions = EntityPolicyAction.ALL)
    void supplierOrderLine();

    @EntityAttributePolicy(entityClass = SupplierOrderRequest.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrderRequest.class, actions = EntityPolicyAction.READ)
    void supplierOrderRequest();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.READ)
    void product();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.READ)
    void productCategory();

    @MenuPolicy(menuIds = {"bookstore_Supplier.browse", "bookstore_SupplierOrder.browse"})
    @ScreenPolicy(screenIds = {"bookstore_Supplier.edit", "bookstore_Supplier.browse", "bookstore_SupplierOrderRequest.edit", "bookstore_SupplierOrder.browse", "bookstore_SupplierOrder.edit", "bookstore_MainScreen", "bookstore_LoginScreen", "bookstore_SupplierOrder.approval"})
    void screens();

    @EntityAttributePolicy(entityClass = FulfillmentCenter.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FulfillmentCenter.class, actions = EntityPolicyAction.READ)
    void fulfillmentCenter();
}
