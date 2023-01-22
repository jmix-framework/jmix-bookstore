package io.jmix.bookstore.security;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;


/**
 * data constraining role for 'Order Fulfillment' and 'Sales Representative' position
 * <ul>
 *   <li>limits customers to only ones that are in the associated regions of the current employee</li>
 *   <li>limits orders to only ones where the customers region is in the associated regions of the current employee</li>
 * </ul>
 */
@RowLevelRole(name = "Show Only Associated Regions Data", code = ShowOnlyAssociatedRegionsDataRole.CODE)
public interface ShowOnlyAssociatedRegionsDataRole {

    String CODE = "show-only-associated-regions-data";

    @JpqlRowLevelPolicy(entityClass = Customer.class, where = "{E}.associatedRegion in :current_user_associatedRegions")
    void customersFromAssociatedRegionsOfTheEmployee();

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.customer.associatedRegion in :current_user_associatedRegions")
    void customerOrdersFromAssociatedRegionsOfTheEmployee();
}
