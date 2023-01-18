package io.jmix.bookstore.security;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Show Only Associated Regions Data", code = ShowOnlyAssociatedRegionsDataRole.CODE)
public interface ShowOnlyAssociatedRegionsDataRole {

    String CODE = "show-only-associated-regions-data";

    @JpqlRowLevelPolicy(entityClass = Customer.class, where = "{E}.associatedRegion in :current_user_associatedRegions")
    void customersFromAssociatedRegionsOfTheEmployee();

    @JpqlRowLevelPolicy(entityClass = Order.class, where = "{E}.customer.associatedRegion in :current_user_associatedRegions")
    void customerOrdersFromAssociatedRegionsOfTheEmployee();
}
