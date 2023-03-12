package io.jmix.bookstore.wms.mqtt;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum QoS implements EnumClass<Integer> {

    QOS_0(0),
    QOS_1(1),
    QOS_2(2);

    private Integer id;

    QoS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static QoS fromId(Integer id) {
        for (QoS at : QoS.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}