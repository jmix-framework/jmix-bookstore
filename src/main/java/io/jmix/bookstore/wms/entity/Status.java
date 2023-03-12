package io.jmix.bookstore.wms.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Status implements EnumClass<Integer> {

    ONLINE(1),
    OFFLINE(0);

    private Integer id;

    Status(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Status fromId(Integer id) {
        for (Status at : Status.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}