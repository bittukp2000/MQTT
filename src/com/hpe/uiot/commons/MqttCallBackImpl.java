package com.hpe.uiot.commons;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.hpe.uiot.test.MQTTTestUpLinkDownLink;

public class MqttCallBackImpl implements MqttCallback {
	
	private String subMessage;
	
  public void connectionLost(Throwable throwable) {
    System.out.println("Connection to MQTT broker lost!");
  }

  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
    System.out.println("Message received now:\n\t"+ new String(mqttMessage.getPayload()) );
    
       setSubMessage(new String(mqttMessage.getPayload()));
       System.out.println("Message received now from getSubMessage():\n\t"+ getSubMessage() );
    
  }

  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    // not used in this example
  }
  
  
  
  public String getSubMessage() {
      return this.subMessage;
  }

  public void setSubMessage(String subMessage) {
      this.subMessage = subMessage;
  }


  
}
