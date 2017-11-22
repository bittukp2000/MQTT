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


import com.hpe.uiot.dataflow.Http_mcaClient;

public class MqttClientPublish implements MqttCallback {
	MqttClient myClient;
	MqttConnectOptions connOpt;

	String BROKER_URL = "";
	String M2MIO_USERNAME = "";
	String M2MIO_PASSWORD_MD5 = "";

	
	
	public MqttClientPublish(String brokerUrl, String user, String password) {
		BROKER_URL = brokerUrl;
		M2MIO_USERNAME = user;
		M2MIO_PASSWORD_MD5 = password;
	}

	Properties prop = new Properties();
	InputStream input = null;




	
	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	
	public void runClient(MqttCallBackImpl obj, String pubTopic, String pubMsg) {
		
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
			myClient.setCallback( obj);
			myClient.connect(connOpt);
			
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Connected to " + BROKER_URL);

		// setup topic	
		MqttTopic topic = myClient.getTopic(pubTopic);

		// publish messages if publisher
	

		   		// pubMsg = "{\"pubmsg nov 16\":" + i + "}";
		   		int pubQoS = 0;
				MqttMessage message = new MqttMessage(pubMsg.getBytes());
		    	message.setQos(pubQoS);
		    	message.setRetained(false);

		    	// Publish the message
		    	System.out.println("Publishing message" + message);
		    	System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
		    	MqttDeliveryToken token = null;
		    	try {
		    		// publish message to broker
					token = topic.publish(message);
			    	// Wait until the message has been delivered to the broker
					token.waitForCompletion();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
		
	
		
		// disconnect
		try {
			
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	
		
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
			
	}

	@Override
	public void connectionLost(Throwable arg0) {
		
		
	}
}
