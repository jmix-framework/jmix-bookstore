package io.jmix.bookstore.security;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Show only active Suppliers", code = ShowOnlyActiveSuppliersRole.CODE)
public interface ShowOnlyActiveSuppliersRole {
    String CODE = "show-only-active-suppliers";

    @JpqlRowLevelPolicy(entityClass = Supplier.class, where = "{E}.cooperationStatus <> 'ON_HOLD'")
    void regularSuppliersWithoutOnHold();

    @JpqlRowLevelPolicy(entityClass = Product.class, where = "{E}.supplier.cooperationStatus <> 'ON_HOLD'")
    void productsOfRegularSuppliersWithoutOnHold();
}
