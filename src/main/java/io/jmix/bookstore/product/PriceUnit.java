package io.jmix.bookstore.product;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PriceUnit implements EnumClass<String> {

    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH");

    private String id;

    PriceUnit(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PriceUnit fromId(String id) {
        for (PriceUnit at : PriceUnit.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
