package com.hpe.uiot.dataflow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hpe.uiot.commons.Config;
import com.hpe.uiot.commons.JsonFileRead;

import junit.framework.TestCase;

public class Http_mcaClient {

	String Content_Type = Config.getValue("Content_Type");
	String URL, app, auth;

	public Http_mcaClient(String tapp, String tauth) {
	
		app = tapp;
		auth = tauth;
	}

	private String getUniqueRI() {
		Calendar d = Calendar.getInstance();
		return String.valueOf(d.getTimeInMillis());

	}
	
	public void createAE(String url){
		
	}
	
	public static void main(String args[]) throws Exception  
	{ 
		Http_mcaClient tst =new Http_mcaClient("3973786829240057271","QzI5QTdBMDlBLWE0Y2QyMGYzOnBhc3N3b3Jk");
		tst.testUpLink("http://15.153.133.154:8080/HPE_IoT/mqtttdc-01-new1/default/latest");
		
		
	}

	public <T> String testUpLink(String url) throws Exception {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("src/config.properties");

		// load a properties file
		prop.load(input);
		String X_M2M_Origin = prop.getProperty("X_M2M_Origin");
		String Content_Type = prop.getProperty("Content_Type");
		String Authorization = prop.getProperty("Authorization");
		String urlUpLink = prop.getProperty("urlUpLink");
		String X_M2M_RI = prop.getProperty("X_M2M_RI");
       	HttpHeaders headers = new HttpHeaders();
		headers.add("X-M2M-Origin", X_M2M_Origin);
		headers.add("X-M2M-RI", getUniqueRI());
		headers.add("Content-Type", Content_Type);
		headers.add("Authorization", Authorization);
		HttpEntity<String> entity = new HttpEntity("parameters", headers);
		// restTemplate = new RestTemplate();
		// log.debug("request :" + entity.toString());
		ResponseEntity<String> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, entity,
				String.class, new Object[0]);
		// log.debug("Response body :" + (String)responseEntity.getBody());
		String jsonResponse = responseEntity.getBody().toString();

		System.out.println(jsonResponse);

		return jsonResponse;
	}

	public <T> void pushDownLink(String message,String url) throws Exception {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("src/config.properties");

		// load a properties file
		prop.load(input);
		String X_M2M_Origin = prop.getProperty("X_M2M_Origin");
		String Content_Type = prop.getProperty("Content_Type");
		String Authorization = prop.getProperty("Authorization");
		String urlUpLink = prop.getProperty("urlUpLink");
		String X_M2M_RI = prop.getProperty("X_M2M_RI");
       	HttpHeaders headers = new HttpHeaders();
		headers.add("X-M2M-Origin", X_M2M_Origin);
		headers.add("X-M2M-RI", getUniqueRI());
		headers.add("Content-Type", Content_Type);
		headers.add("Authorization", Authorization);

		/* 
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-M2M-Origin",app);
		headers.add("X-M2M-RI",getUniqueRI());
		headers.add("Content-Type",Content_Type);
		headers.add("Authorization",auth);*/
		HttpEntity<String> entity = new HttpEntity<String>(message, headers);

		System.out.println(entity);
		new RestTemplate().postForLocation(url,entity);

	}
	
	
	public <T> void createApp(String message,String url) throws Exception {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("src/config.properties");

		// load a properties file
		prop.load(input);
		String X_M2M_Origin = prop.getProperty("X_M2M_Origin");
		String Content_Type = prop.getProperty("Content_Type");
		String Authorization = prop.getProperty("Authorization");
		String urlUpLink = prop.getProperty("urlUpLink");
		String X_M2M_RI = prop.getProperty("X_M2M_RI");
       	HttpHeaders headers = new HttpHeaders();
		headers.add("X-M2M-Origin", X_M2M_Origin);
		headers.add("X-M2M-RI", getUniqueRI());
		headers.add("Content-Type", Content_Type);
		headers.add("Authorization", Authorization);
		/*
		//HttpHeaders headers = new HttpHeaders();
		headers.add("X-M2M-Origin", app);
		headers.add("X-M2M-RI", getUniqueRI());
		headers.add("Content-Type", Content_Type);
		headers.add("Authorization", auth);*/
		HttpEntity<String> entity = new HttpEntity<String>(message, headers);

		System.out.println(entity);
		new RestTemplate().postForLocation(URL, entity);

	}


}
