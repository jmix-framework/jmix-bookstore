package io.jmix.bookstore.security;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.OrderLine;
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

@ResourceRole(name = "Order Fulfillment", code = OrderFulfillmentRole.CODE)
public interface OrderFulfillmentRole {
    String CODE = "order-fulfillment";

    @EntityAttributePolicy(entityClass = SupplierOrderRequest.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrderRequest.class, actions = EntityPolicyAction.ALL)
    void supplierOrderRequest();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.READ)
    void product();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.READ)
    void productCategory();

    @MenuPolicy(menuIds = {"bookstore_Supplier.browse", "bookstore_SupplierOrder.browse", "bookstore_Order.browse", "bookstore_Customer.browse", "bookstore_Product.browse"})
    @ScreenPolicy(screenIds = {"bookstore_Supplier.edit", "bookstore_Supplier.browse", "bookstore_SupplierOrderRequest.edit", "bookstore_SupplierOrder.browse", "bookstore_SupplierOrder.edit", "bookstore_SupplierOrder.review", "bookstore_MainScreen", "bookstore_LoginScreen", "bookstore_Order.browse", "bookstore_Customer.browse", "bookstore_Product.browse"})
    void screens();

    @EntityAttributePolicy(entityClass = Supplier.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Supplier.class, actions = EntityPolicyAction.READ)
    void supplier();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();

    @EntityAttributePolicy(entityClass = OrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = OrderLine.class, actions = EntityPolicyAction.ALL)
    void orderLine();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = SupplierOrder.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrder.class, actions = EntityPolicyAction.READ)
    void supplierOrder();

    @EntityAttributePolicy(entityClass = SupplierOrderLine.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrderLine.class, actions = EntityPolicyAction.READ)
    void supplierOrderLine();

    @EntityAttributePolicy(entityClass = FulfillmentCenter.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FulfillmentCenter.class, actions = EntityPolicyAction.READ)
    void fulfillmentCenter();
}
