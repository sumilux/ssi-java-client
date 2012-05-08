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

package com.sumilux.ssi.client.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.sumilux.ssi.client.IdmeException;

/**
 * HttpRequest that sends the request to the remote url and 
 * return the response
 * 
 * @author  kevin 2012/04/10
 * @version 1.0
 */
public class HttpRequest {
	
    /**
     * The request method: GET
     */
    public static final String GET = "GET";
    /**
     * The request method: POST
     */
    public static final String POST = "POST";
    /**
     * The request method: PUT
     */
    public static final String PUT = "PUT";
    /**
     * The request method: DELETE
     */
    public static final String DELETE = "DELETE";
    
    /**
     * Hold the request url
     */
    private final String url;
    /**
     * Hold the request method
     */
    private final String method;
	private HttpURLConnection conn;

    /**
     * Initialize the HttpRequest.
     * @param method The request method
     * @param url The request url
     */
	public HttpRequest(String method, String url) {
		this.method = method;
		this.url = url;
	}

    /**
     * Execute the request with the specified url and parameter
     * return the response 
     * @param queryParas The query parameters
     * @return the response from remote url 
     */
	public HttpResponse execute(Map<String, String> queryParas) throws IdmeException {
		try {
			URL destURL = buildURI(url, queryParas);
			conn = (HttpURLConnection) destURL.openConnection();
			conn.setRequestMethod(this.method);
            return new HttpResponse(conn);
		} catch (Exception e) {
			throw new IdmeException("Error occur while creating connection");
		}
	}

	private URL buildURI(String url, Map<String, String> queryParas) throws IdmeException {
		try {
			if(queryParas == null || queryParas.size() == 0) {
				return new URL(url);
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			for (String key : queryParas.keySet()) {
				stringBuffer.append("&").append(key).append("=").append(queryParas.get(key));
			}
			
			if(url.contains("?")) {
				url = url + stringBuffer.toString();
			} else {
				url = url + "?" + stringBuffer.toString().substring(1);
			}
			return new URL(url);
		} catch (Exception e) {
			throw new IdmeException(e);
		}
	}

	public String getUrl() {
		return url;
	}
}
