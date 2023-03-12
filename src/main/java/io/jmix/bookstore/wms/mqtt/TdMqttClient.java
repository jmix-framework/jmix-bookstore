package io.jmix.bookstore.wms.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;

/**
 * Copyright © xxx有限公司 版权所有
 *
 * @author 周广明
 * v1 2023/3/12 14:56
 */
 public interface TdMqttClient {

    org.eclipse.paho.mqttv5.client.MqttClient get5();

    org.eclipse.paho.client.mqttv3.MqttClient get3();
     void connect();
    
     void connect3(MqttConnectOptions options);
     void connect5(MqttConnectionOptions options);

     void disconnect();

     void disconnect(long quiesceTimeout);

    void setCallback3(org.eclipse.paho.client.mqttv3.MqttCallback callback);
    void setCallback5(MqttCallback callback);

     boolean isConnected();

     String getClientId();

     void close();



}
