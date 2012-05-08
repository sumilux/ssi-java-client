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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.sumilux.ssi.client.IdmeException;

/**
 * HttpResponse that get the response with the HttpURLConnection 
 * 
 * @author  kevin 2012/04/10
 * @version 1.0
 */
public class HttpResponse {
	
	private InputStream stream;
	private Map<String, String> headers;
	private String strBody;
	private int code;
	
    /**
     * Build the response with the HttpURLConnection
     * @param conn The http connect to remote service address
     * @return the response from remote url 
     */
	public HttpResponse(HttpURLConnection conn) throws IdmeException {
		try {
			conn.connect();
			code = conn.getResponseCode();
			stream = (code >= 200 && code < 400) ? conn.getInputStream() : conn
					.getErrorStream();
			parseHeaders(conn);
			parseBodyContents();
		} catch (Exception e) {
			throw new IdmeException(e);
		}
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public String getBody() {
		return strBody;
	}
	
	public int getCode() {
		return code;
	}
	
    /**
     * Parse the HeaderMassage from the response into memory
     * @param conn The http connect to remote service address
     */
	private void parseHeaders(HttpURLConnection conn) {
		headers = new TreeMap<String, String>();
		Set<String> headerSet = conn.getHeaderFields().keySet();
		if (null == headerSet || headerSet.isEmpty()) {
			return;
		}
		for (String key : headerSet) {
			List<String> value = conn.getHeaderFields().get(key);
			if (key == null || null == value || value.isEmpty()) {
				continue;
			}
			String v = value.get(0);
			headers.put(key, v);
		}
	}
	
    /**
     * Parse the bodyMassage from the response into memory
     * the format is json
     */
	private void parseBodyContents() throws IdmeException {
		try {
			final char[] buffer = new char[0x10000];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(stream, "UTF-8");
			int read;
			do {
				read = in.read(buffer, 0, buffer.length);
				if (read > 0) {
					out.append(buffer, 0, read);
				}
			} while (read >= 0);
			in.close();
			strBody = out.toString();
		} catch (IOException e) {
			throw new IdmeException(e);
		}
	}
}
