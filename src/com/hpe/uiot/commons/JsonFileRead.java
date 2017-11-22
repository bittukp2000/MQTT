package com.hpe.uiot.commons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JsonFileRead {

	public JSONObject returnJson(String filePath) throws Exception {

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;

		Object obj = parser.parse(new FileReader(filePath));
		jsonObject = (JSONObject) obj;

		return jsonObject;
	}

}