package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Currency implements EnumClass<String> {

    EUR("EUR"),
    USD("USD");

    private String id;

    Currency(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Currency fromId(String id) {
        for (Currency at : Currency.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
