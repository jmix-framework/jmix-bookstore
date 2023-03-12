package io.jmix.bookstore.wms.mqtt;

import cn.treedeep.upaas.core.exception.BizException;
import io.jmix.bookstore.wms.util.SSLUtils;
import io.jmix.bookstore.wms.entity.Equipment;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;

import javax.net.ssl.SSLSocketFactory;

/**
 * Copyright © xxx有限公司 版权所有
 *
 * @author 周广明
 * v1 2023/3/12 14:35
 */
public class Mqtt5Client implements TdMqttClient {

    private final MqttConnectionOptions options;
    private final MqttClient client;

    @Override
    public MqttClient get5() {
        return client;
    }

    @Override
    public org.eclipse.paho.client.mqttv3.MqttClient get3() {
        return null;
    }

    @Override
    public String getClientId() {
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

    public MqttConnectionOptions getOptions() {
        return options;
    }

    public MqttClient getClient() {
        return client;
    }

    @Override
    public void connect() {
        check();
        try {
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect3(MqttConnectOptions options) {

    }

    @Override
    public void connect5(MqttConnectionOptions options) {
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
    public void setCallback3(org.eclipse.paho.client.mqttv3.MqttCallback callback) {

    }

    @Override
    public void setCallback5(MqttCallback callback) {
        check();
        client.setCallback(callback);
    }

    @Override
    public boolean isConnected() {
        check();
        return client.isConnected();
    }


    public MqttClient setCallback(MqttCallback callback) {
        check();
        client.setCallback(callback);
        return client;
    }


    public Mqtt5Client(Equipment equipment) throws Exception {
        this.options = new MqttConnectionOptions();

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

        String broker = String.format("%s://%s:%s", tls ? "ssl" : "tcp", host, port);
        String clientId = equipment.getClientId();

        this.client = new MqttClient(broker, clientId, new MemoryPersistence());

        // todo 先联机在订阅
        client.subscribe(equipment.getSubscription(), equipment.getQos().getId());

        String username = equipment.getUsername();
        String password = equipment.getPassword();

        if (StringUtils.isNotBlank(username)) {
            options.setUserName(username);
        }
        if (StringUtils.isNotBlank(password)) {
            options.setPassword(password.getBytes());
        }

        options.setCleanStart(equipment.getCleanStart());
        options.setKeepAliveInterval(equipment.getKeepalive());
        options.setSessionExpiryInterval(equipment.getSessionExpiryInterval());

    }


    private void check() {
        if (client == null || options == null) {
            throw new BizException("客户端或连接参数为空！");
        }
    }
}
