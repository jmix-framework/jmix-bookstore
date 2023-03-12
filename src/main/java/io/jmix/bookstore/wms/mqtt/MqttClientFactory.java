package io.jmix.bookstore.wms.mqtt;

import io.jmix.bookstore.wms.entity.Equipment;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Copyright © xxx有限公司 版权所有
 *
 * @author 周广明
 * v1 2023/3/11 15:12
 */
public class MqttClientFactory {

    public final static Map<String, TdMqttClient> MQTT_CLIENTS = new HashMap<>();
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MqttClientFactory.class);

    public static TdMqttClient create(Equipment equipment) throws Exception {
        String clientId = equipment.getClientId();
        MqttProtocol protocol = equipment.getProtocolVersion();

        TdMqttClient mqttClient;

        if (protocol.equals(MqttProtocol.MQTT_VERSION_5)) {
            mqttClient = new Mqtt5Client(equipment);
        } else {
            mqttClient = new Mqtt3Client(equipment);
        }

        remove(clientId);

        MQTT_CLIENTS.put(clientId, mqttClient);

        return mqttClient;
    }


    public static void remove(String clientId) {
        var mqtt3Client = get(clientId);
        if (mqtt3Client.isPresent()) {
            try {
                mqtt3Client.get().disconnect();
                mqtt3Client.get().close();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }


    public static Optional<TdMqttClient> get(String clientId) {
        if (MQTT_CLIENTS.containsKey(clientId)) {
            return Optional.of(MQTT_CLIENTS.get(clientId));
        }
        return Optional.empty();
    }
}
