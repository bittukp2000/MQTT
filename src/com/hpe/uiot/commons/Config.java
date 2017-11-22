package com.hpe.uiot.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	static Properties props = new Properties();

	static {
		try {
			props.load(new FileInputStream("src/config.properties"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static String getValue(String key) {
		return props.getProperty(key);
	}

}
