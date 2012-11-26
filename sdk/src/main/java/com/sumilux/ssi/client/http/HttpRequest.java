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
import java.util.Collection;
import java.util.Map;

import com.sumilux.ssi.client.IdmeException;

public class HttpRequest {
	
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    
    private final String url;
    private final String method;
	private HttpURLConnection conn;

	public HttpRequest(String method, String url) {
		this.method = method;
		this.url = url;
	}

	public HttpResponse execute(Collection<? extends Map.Entry> headerParas, Collection<? extends Map.Entry> queryParas) throws IdmeException {
		try {
			URL destURL = buildURI(url, queryParas);
			conn = (HttpURLConnection) destURL.openConnection();
			
			if(headerParas != null) {
			    for (Map.Entry p : headerParas) {
				    conn.setRequestProperty(toString(p.getKey()), toString(p.getValue()));
			    }
			}
			
			conn.setRequestMethod(this.method);
            return new HttpResponse(conn);
		} catch (Exception e) {
			throw new IdmeException("Error occur while creating connection");
		}
	}

	private URL buildURI(String url, Collection<? extends Map.Entry> queryParas) throws IdmeException {
		try {
			if(queryParas == null || queryParas.size() == 0) {
				return new URL(url);
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			for (Map.Entry p : queryParas) {
				stringBuffer.append("&").append(toString(p.getKey())).append("=").append(toString(p.getValue()));
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
	
	private static String toString(Object from) {
		return (from == null) ? null : from.toString();
	}
}
