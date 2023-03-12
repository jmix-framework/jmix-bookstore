package io.jmix.bookstore.wms.mqtt;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum MqttProtocol implements EnumClass<Integer> {

    MQTT_VERSION_5(5),
    MQTT_VERSION_3_1_1(4),
    MQTT_VERSION_3_1(3);

    private Integer id;

    MqttProtocol(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static MqttProtocol fromId(Integer id) {
        for (MqttProtocol at : MqttProtocol.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}