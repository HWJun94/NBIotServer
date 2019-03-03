package com.hwj.mqtt;

import com.hwj.util.ConfigUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BEMqttClient {
    private static final Logger logger = LoggerFactory.getLogger(BEMqttClient.class);

    private MqttClient mqttClient;
    private MqttConnectOptions mqttOptions;
    private String clientId = "NewClient";

    public BEMqttClient() {

    }

    /**
     * mqtt 客户端初始化方法
     * @return 成功返回true 否则返回false
     */
    public boolean init() {
        try {
            mqttClient = new MqttClient(ConfigUtil.getString("mqtt.host"), clientId, new MemoryPersistence());
        } catch (MqttException e) {
            logger.error("{}", e);
            return false;
        }
        //设置客户端各参数
        mqttOptions = new MqttConnectOptions();
        mqttOptions.setCleanSession(false);
        mqttOptions.setUserName(ConfigUtil.getString("mqtt.username"));
        mqttOptions.setPassword(ConfigUtil.getString("mqtt.psw").toCharArray());
        mqttOptions.setConnectionTimeout(10);
        mqttOptions.setKeepAliveInterval(20);

        //设置订阅回调
        mqttClient.setCallback(new BEMqttCallback(this));
        return true;
    }

    /**
     * mqtt客户端与broker建立连接的方法
     */
    public void connect() {
        if (mqttOptions == null) {
            init();
        }
        try {
            mqttClient.connect(mqttOptions);
            subscribe(ConfigUtil.getStringArray("mqtt.topics"));
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

    /**
     * 客户端订阅主题方法
     * @param topics 订阅的主题数组
     * @throws Exception NullPointerException MqttException
     */
    public void subscribe(String[] topics) throws Exception {
        if (topics == null) {
            throw new NullPointerException("The topic array can't be null!");
        }
        mqttClient.subscribe(topics);
    }

    /**
     * 客户端publish方法
     * @param topic 发布消息的topic
     * @param qos 发布消息的质量
     * @param message 发布消息的内容
     * @throws Exception MqttPersistenceException, MqttException
     */
    public void publish(String topic, int qos, String message) throws Exception {
        if (mqttClient.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setRetained(false);
            mqttMessage.setPayload(message.getBytes());
            MqttTopic mqttTopic = mqttClient.getTopic(topic);
            MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } else {
            throw new MqttException(32104);
        }
    }

    /**
     * 判断客户端是否是连接状态的方法
     * @return true 连接 false 未连接
     */
    public boolean isConnected() {
        return mqttClient.isConnected();
    }
}
