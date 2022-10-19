package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum SupplierOrderStatus implements EnumClass<String> {

    DRAFT("DRAFT"),
    APPROVED("APPROVED"),
    ORDERED("ORDERED"),
    DELIVERED("DELIVERED");

    private String id;

    SupplierOrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SupplierOrderStatus fromId(String id) {
        for (SupplierOrderStatus at : SupplierOrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
