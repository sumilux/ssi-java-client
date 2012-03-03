/**     
 * LICENSE:
 *       
 * Copyright (c) 2012, Sumilux Technologies, LLC 
 * All rights reserved.
 *       
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions
 * are met:
 *       
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *       
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
*/

package com.sumilux.ssi.client;

import java.util.Map;

import com.sumilux.ssi.client.http.HttpRequest;
import com.sumilux.ssi.client.http.HttpResponse;
import com.sumilux.ssi.client.json.JSONArray;
import com.sumilux.ssi.client.json.JSONObject;
import com.sumilux.ssi.client.json.JSONTokener;

public class IdmeClient {

	private static final String DEFAULT_BASE_URL = "http://idmedemo.sumilux.com/smx/api";
	private static String baseUrl = null;
	private HttpRequest client;
	private HttpResponse response;
	
	public IdmeClient(String path) {
		client = new HttpRequest(HttpRequest.GET, buildUrl(path));
	}
	
	public static void setBaseUrl(String url) {
		baseUrl = url;
	}
	
	private String getBaseUrl() {
		return baseUrl == null ? DEFAULT_BASE_URL : baseUrl;
	}
	
	private String buildUrl(String path) {
		return getBaseUrl() + path;
	}
	
	public Object execute(Map<String, String> queryParas) throws IdmeException {
		response = client.execute(queryParas);
		if(response.getCode() == 200) {
			return parseJson(response.getBody());
	    } else {
	    	throw new IdmeException(response.getBody());
	    }
	}
	
	public Map<String, String> getHeaders() {
		return response.getHeaders();
	}
	
	public HttpResponse getResponse() {
		return response;
	}
	/**
     * parse string into jsonobject
     * @param para
     * @return
     * @throws IdmeException
     */
	private Object parseJson(String para) throws IdmeException {
		if (null == para || "".equals(para)) {
			throw new IdmeException("Not found", 94);
		}

		try {
			JSONTokener jsonTokener = new JSONTokener(para);
			Object o = jsonTokener.nextValue();
			if (o instanceof JSONObject) {
				JSONObject jsonObj = (JSONObject) o;
				return jsonObj;
			} else {
				JSONArray jsonArray = (JSONArray) o;
				return jsonArray;
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