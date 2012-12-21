package com.sumilux.ssi.client;

import java.util.Collection;
import java.util.Map;

import com.sumilux.ssi.client.http.HttpRequest;
import com.sumilux.ssi.client.http.HttpResponse;


public class TemplcateClient {

	private static String baseUrl = "http://10.0.90.95:8670/smx/api";
	private static boolean isSSL = false;
	
	public static boolean isSSL() {
		return isSSL;
	}

	public static void setBaseUrl(String url) {
		baseUrl = url;
	}
	
	public static String getBaseUrl() {
		return baseUrl;
	}
	/**
	 * Execute the http request for the specified url and query parameters But
	 * if the cache object is valid,will retrieve it from memory directly
	 * 
	 * @param queryParas
	 *            The query parameters
	 * @return The response body's content
	 */
	public HttpResponse execute(String path,
			Collection<? extends Map.Entry> headerParas,
			Collection<? extends Map.Entry> generalParas) throws IdmeException {
		String finalURL = getBaseUrl() + path;
		HttpRequest client = new HttpRequest(HttpRequest.GET, finalURL);
		HttpResponse response = client.execute(headerParas, generalParas);
		if(response.getCode() != 200) {
			throw new IdmeException(String.valueOf(response.getCode()), "http error");
	    }
		return response;
	}
}
