package io.jmix.bookstore.wms.mqtt;

import cn.treedeep.upaas.core.exception.BizException;
import io.jmix.bookstore.wms.util.SSLUtils;
import io.jmix.bookstore.wms.entity.Equipment;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;

import javax.net.ssl.SSLSocketFactory;

/**
 * Copyright © xxx有限公司 版权所有
 *
 * @author 周广明
 * v1 2023/3/12 14:35
 */
public class Mqtt3Client implements TdMqttClient {

    private final MqttConnectOptions options;
    private final MqttClient client;

    @Override
    public org.eclipse.paho.mqttv5.client.MqttClient get5() {
        return null;
    }

    @Override
    public MqttClient get3() {
        return client;
    }

    public Mqtt3Client(Equipment equipment) throws Exception {

        this.options = new MqttConnectOptions();

        String host = equipment.getHost();
        int port = equipment.getPort();
        boolean tls = equipment.getTls();


        if (tls) {

            // Set socket factory
            String caFilePath = "/cacert.pem";
            String clientCrtFilePath = "/client.pem";
            String clientKeyFilePath = "/client.key";
            SSLSocketFactory socketFactory = SSLUtils.getSocketFactory(caFilePath, clientCrtFilePath, clientKeyFilePath, "");


            // options.setSocketFactory(socketFactory);

            tls = false;

        }

        // String broker = String.format("%s://%s:%s", tls ? "wss" : "ws", host, port);
        String broker = String.format("%s://%s:%s", tls ? "ssl" : "tcp", host, port);
        String clientId = equipment.getClientId();

        this.client = new MqttClient(broker, clientId, new MemoryPersistence());
        client.subscribe(equipment.getSubscription(), equipment.getQos().getId());


        String username = equipment.getUsername();
        String password = equipment.getPassword();

        if (StringUtils.isNotBlank(username)) {
            options.setUserName(username);
        }
        if (StringUtils.isNotBlank(password)) {
            options.setPassword(password.toCharArray());
        }

        options.setMqttVersion(equipment.getVersion());
        options.setCleanSession(equipment.getCleanStart());
        options.setKeepAliveInterval(equipment.getKeepalive());

        Long expiryInterval = equipment.getSessionExpiryInterval();
        if (expiryInterval != null) {
            options.setConnectionTimeout(expiryInterval.intValue());
        }


    }

    @Override
    public void connect() {
        check();
        try {
            client.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect3(MqttConnectOptions options) {
        check();
        try {
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        check();
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect(long quiesceTimeout) {
        check();
        try {
            client.disconnect(quiesceTimeout);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setCallback3(MqttCallback callback) {
        check();
        client.setCallback(callback);
    }


    @Override
    public void connect5(MqttConnectionOptions options) {

    }

    @Override
    public void setCallback5(org.eclipse.paho.mqttv5.client.MqttCallback callback) {

    }

    @Override
    public boolean isConnected() {
        check();
        return client.isConnected();
    }

    @Override
    public String getClientId() {
        check();
        return client.getClientId();
    }

    @Override
    public void close() {
        check();
        try {
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    private void check() {
        if (client == null || options == null) {
            throw new BizException("客户端或连接参数为空！");
        }
    }
}
