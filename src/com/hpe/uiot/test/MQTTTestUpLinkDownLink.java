package com.hpe.uiot.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.hpe.uiot.commons.Config;
import com.hpe.uiot.commons.JsonFileRead;
import com.hpe.uiot.commons.MqttCallBackImpl;
import com.hpe.uiot.commons.MqttClientPublish;
import com.hpe.uiot.commons.MqttClientSubscribe;
import com.hpe.uiot.commons.NanoHTTPServer;
import com.hpe.uiot.dataflow.Http_mcaClient;

import fi.iki.elonen.NanoHTTPD;
import junit.framework.TestCase;

public class MQTTTestUpLinkDownLink  extends TestCase{

	private static JSONObject JsonMessage;
	private static String downlinkURL,uplinkURL,urlApp,app,auth;
 public  void setInit(){
	 downlinkURL = Config.getValue("urlDownLink");
	 uplinkURL = Config.getValue("urlUpLink");
	app = Config.getValue("X_M2M_Origin");
	auth = Config.getValue("Authorization");
	urlApp= Config.getValue("urlApp");
	/*System.out.print("downlinkURL"+downlinkURL);
	System.out.print("uplinkURL"+uplinkURL);
	System.out.print("urlApp"+urlApp);*/

}
	
 public String  setUp(String filePath) {
		// TODO Auto-generated method stub
		JsonFileRead json = new JsonFileRead();
		//String filepath="C:\\Users\\peterbi\\Desktop\\IOT\\MQTT\\test.json";
		try {
			JsonMessage = json.returnJson(filePath);
			System.out.println("JsonMessage: " + JsonMessage);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
return JsonMessage.toString();
		
	}


	/*public boolean UpLinkMessage(String publish) {
			try {
				MQTTTestUpLinkDownLink link = new MQTTTestUpLinkDownLink();
				link.setInit();
			Http_mcaClient uplink = new Http_mcaClient(app,auth);
			System.out.print("uplinkURL"+uplinkURL);
			String message = uplink.testUpLink(uplinkURL);
			System.out.println("publish retrieve message " +message);
			if (message.contains(publish)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Test
	public void testMcaUpLink() {
		String pubMessage="Publish message Nov 21 2017";
		MqttClientPublish smc = new MqttClientPublish(Config.getValue("BROKER_URL"),Config.getValue("M2MIO_USERNAME"), Config.getValue("M2MIO_PASSWORD_MD5"));
		MqttCallBackImpl callback = new MqttCallBackImpl();
		smc.runClient(callback, "demo-MQTT/resp/mqtttdc-01-new1/sd",pubMessage);
		MQTTTestUpLinkDownLink tst = new MQTTTestUpLinkDownLink();
		
		assertTrue(tst.UpLinkMessage(pubMessage));

	}*/
	
	@Test
	public void testAppUpLink() throws Exception {
		
		String pubMessage = "Hello Http";
		

		new NanoHTTPServer();
		//server.start();
		MQTTTestUpLinkDownLink tst = new MQTTTestUpLinkDownLink();
		tst.setInit();
        tst.setUp("C:\\Users\\peterbi\\Desktop\\IOT\\MQTT\\appCreate.json");
		Http_mcaClient mcaClient = new Http_mcaClient(app,auth);
		mcaClient.createApp(JsonMessage.toJSONString(),urlApp);
		
		//sleep
		Thread.sleep(5000);
		
		

		// simulate a device uplink by posting to topic
		MqttClientPublish smc = new MqttClientPublish(Config.getValue("BROKER_URL"),Config.getValue("M2MIO_USERNAME"), Config.getValue("M2MIO_PASSWORD_MD5"));
		MqttCallBackImpl callback = new MqttCallBackImpl();
		smc.runClient(callback, "demo-MQTT/resp/mqtttdc-01-new1/sd",pubMessage);
		
		//sleep
		Thread.sleep(5000);

		//server.getMessage();
		//assertTrue(pubMessage.contains(server.getMessage()));
		//server.stop();

	}

	/*@Test
	public void testDownLink() throws Exception {
		MqttClientSubscribe smc = new MqttClientSubscribe(Config.getValue("BROKER_URL"),
				Config.getValue("M2MIO_USERNAME"), Config.getValue("M2MIO_PASSWORD_MD5"));
		MqttCallBackImpl callback = new MqttCallBackImpl();
		smc.runClient(callback, "demo-MQTT/req/mqtttdc-01-new1/cmd");

		System.out.println("subscribe message " + callback.getSubMessage());
		assertTrue(JsonMessage.toString().contains(callback.getSubMessage()));

	}*/
}
