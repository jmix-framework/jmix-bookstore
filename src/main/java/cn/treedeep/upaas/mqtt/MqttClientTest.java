package cn.treedeep.upaas.mqtt;

import cn.treedeep.upaas.util.SSLUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.ssl.SSLSocketFactory;

/**
 * Copyright © xxx有限公司 版权所有
 *
 * @author 周广明
 * v1 2023/3/11 15:12
 */
public class MqttClientTest {

    public void t1() throws MqttException {

        String broker = "tcp://broker.emqx.io:1883";
        String username = "emqx";
        String password = "public";
        String clientid = "publish_client";

        MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);

    }

    public void t2() throws Exception {
        String username = "emqx";
        String password = "public";
        String clientid = "publish_client";

        String broker = "ssl://broker.emqx.io:8883";

        // Set socket factory
        String caFilePath = "/cacert.pem";
        String clientCrtFilePath = "/client.pem";
        String clientKeyFilePath = "/client.key";
        SSLSocketFactory socketFactory = SSLUtils.getSocketFactory(caFilePath, clientCrtFilePath, clientKeyFilePath, "");

        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(socketFactory);

        MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);
    }
}
