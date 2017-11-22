package com.hpe.uiot.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONObject;

import com.hpe.uiot.dataflow.Http_mcaClient;
import com.hpe.uiot.test.MQTTTestUpLinkDownLink;

public class MqttClientSubscribe implements MqttCallback {

	MqttClient myClient;
	MqttConnectOptions connOpt;

	String BROKER_URL = "";
	String M2MIO_USERNAME = "";
	String M2MIO_PASSWORD_MD5 = "";
	String subTopic = "";

	public MqttClientSubscribe(String brokerUrl, String user, String password) {
		BROKER_URL = brokerUrl;
		M2MIO_USERNAME = user;
		M2MIO_PASSWORD_MD5 = password;
	}

	/**
	 * 
	 * connectionLost This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
		// code to reconnect to the broker would go here if desired
	}

	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {
		MqttClientSubscribe smc = new MqttClientSubscribe(Config.getValue("BROKER_URL"),
				Config.getValue("M2MIO_USERNAME"), Config.getValue("M2MIO_PASSWORD_MD5"));
		MqttCallBackImpl callback = new MqttCallBackImpl();
		smc.runClient(callback, "demo-MQTT/req/mqtttdc-01-new1/cmd");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * runClient The main functionality of this simple example. Create a MQTT
	 * client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient(MqttCallBackImpl obj, String subTopic) {

		// setup MQTT Client
		String clientID = "connect";
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		connOpt.setUserName(M2MIO_USERNAME);
		connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(obj);
			myClient.connect(connOpt);

		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Connected to " + BROKER_URL);

		// setup topic
		MqttTopic topic = myClient.getTopic(subTopic);

		// subscribe to topic if subscriber

		try {
			
			String downlinkURL = Config.getValue("urlDownLink");
			String app = Config.getValue("X_M2M_Origin");
			String auth = Config.getValue("Authorization");
			int subQoS = 0;
			myClient.subscribe(subTopic);

			Http_mcaClient mcaClient = new Http_mcaClient(app,auth);
			MQTTTestUpLinkDownLink tst = new MQTTTestUpLinkDownLink();
			tst.setInit();
			String JsonMessage= tst.setUp("C:\\Users\\peterbi\\Desktop\\IOT\\MQTT\\test.json");
			mcaClient.pushDownLink(JsonMessage,downlinkURL);


			System.out.println("subscription");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// disconnect
		try {
			// wait to ensure subscribed messages are delivered

			Thread.sleep(50000);

			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * deliveryComplete This callback is invoked when a message published by
	 * this client is successfully received by the broker.
	 * 
	 */
	public void deliveryComplete(MqttDeliveryToken token) {
		// System.out.println("Pub complete" + new
		// String(token.getMessage().getPayload()));
	}

	/**
	 * 
	 * messageArrived This callback is invoked when a message is received on a
	 * subscribed topic.
	 * 
	 */
	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic.getName());
		System.out.println("| Message: " + new String(message.getPayload()));
		System.out.println("-------------------------------------------------");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(message);
	}

}
