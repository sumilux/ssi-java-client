package com.sumilux.ssi.client;

import java.util.Iterator;

import com.sumilux.ssi.client.json.JSONArray;
import com.sumilux.ssi.client.json.JSONObject;
import com.sumilux.ssi.client.json.JSONTokener;

public class TemplateParser {
	public static boolean parserBoolean(String body) throws IdmeException {
		JSONObject jo = parseJSONObject(body);
		checkErr(jo);
		
		if(!jo.has("result")) {
			throw new RuntimeException("Using a wrong parser method");
		}
		String result = jo.getString("result");
		
		return "1".equals(result) ? true : false;
	}
	
	private static void checkErr(JSONObject jo) throws IdmeException {
		if(jo.has("err_code")) {
			throw new IdmeException(jo.getString("err_msg"), jo.getString("err_code"));
		}
	}
	
	public static String parserString(String body) throws IdmeException {
		JSONObject jo = parseJSONObject(body);
		checkErr(jo);
		if(!jo.has("result")) {
			throw new RuntimeException("Using a wrong parser method");
		}
		
		return jo.getString("result");
	}
	
	public static JSONObject parserJSONObject(String body) throws IdmeException {
		JSONObject jo = parseJSONObject(body);
		checkErr(jo);
		
		if(!jo.has("data")) {
			throw new RuntimeException("Using a wrong parser method");
		}
		
		return parseJSONObject(jo.get("data").toString());
	}
	
	public static JSONArray parserJSONArray(String body) throws IdmeException {
		JSONObject jo = parseJSONObject(body);
		checkErr(jo);
		
		if(!jo.has("data")) {
			throw new RuntimeException("Using a wrong parser method");
		}
		
		return jo.getJSONArray("data");
	}
	
	/**
     * parse string into JSONObject(Include sub-json)
     * @param para
     * @return JSONObject
     * @throws IdmeException
     */
	private static JSONObject parseJSONObject(String para) throws IdmeException {
		if (null == para || "".equals(para)) {
			throw new IdmeException("Not found", String.valueOf(94));
		}
        return (JSONObject)parseStrJson(para);
	}
//	
//	/**
//     * parse string into JSONArray(Include sub-json)
//     * @param para
//     * @return JSONObject
//     * @throws IdmeException
//     */
//	private static JSONArray parseJSONArray(String para) throws IdmeException {
//		if (null == para || "".equals(para)) {
//			throw new IdmeException("Not found", String.valueOf(94));
//		}
//        return (JSONArray)parseStrJson(para);
//	}
	
	/**
     * parse string into jsonobject(Include sub-json)
     * @param para
     * @return JSONObject/JSONArray/String
     * @throws IdmeException
     */
	@SuppressWarnings("unchecked")
	private static Object parseStrJson(String para) throws IdmeException {
		try {
			JSONTokener jsonTokener = new JSONTokener(para);
			Object o = jsonTokener.nextValue();
			if (o instanceof JSONObject) {
				JSONObject jsonObj = (JSONObject) o;
				JSONObject jsonRet = new JSONObject();
				for (Iterator<Object> iter = jsonObj.keys(); iter.hasNext();) {
					String key = (String)iter.next();
					String value = jsonObj.get(key).toString();
					if(value == null || "".equals(value)) {
						jsonRet.accumulate(key, "");
					} else {
						jsonRet.accumulate(key, parseStrJson(value));
					}
			    }
				return jsonRet.length() > 0 ? jsonRet : jsonObj;
			} else if(o instanceof JSONArray){
				JSONArray jsonArray = (JSONArray) o;
				for(int i = 0; i < jsonArray.length(); i++) {
					jsonArray.put(i, parseStrJson(jsonArray.get(i).toString()));
				}
				return jsonArray;
			} else {
				return o.toString();
			}
		} catch (Exception e) {
			if (e instanceof IdmeException) {
				throw (IdmeException) e;
			} else {
				throw new IdmeException("parse error", e);
			}
		}
	}
}
