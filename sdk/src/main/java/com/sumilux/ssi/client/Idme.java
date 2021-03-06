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

/*
 * Social-sign-in's SDK
 * 2012/02/20 by kevin
 *
 */
package com.sumilux.ssi.client;

import java.util.ArrayList;
import java.util.List;

import com.sumilux.ssi.client.http.HttpResponse;
import com.sumilux.ssi.client.json.JSONException;
import com.sumilux.ssi.client.json.JSONObject;

/**
 * The Function set that Idme provides 
 * and this is one and only entrance to invoke idme's function.
 * 
 * @author  kevin 2012/11/26
 * @version 1.0
 */
public class Idme {

	private String token;
	TemplcateClient client = new TemplcateClient();

    /** 
     * Idme's constructor
     * @param token your token
     * @param apiURL idme service's address
     */
	public Idme(String token,String apiURL) throws IdmeException {
		this.token = token;
		TemplcateClient.setBaseUrl(apiURL);
	}
	
    /** 
     * Idme's constructor
     * @param token your token
     */
	public Idme(String token){
		this.token = token;
	}
	
	//*********** auth info ***************
    /** 
     * make token invalid
     * 
     * @return true:succedd false:failed
     * @exception IdmeException
     */
	public boolean expireToken() throws IdmeException {
		try {
			HttpResponse response = client.execute("/auth/v2/expireToken", newList("ssi_token", token), null);
			return TemplateParser.parserBoolean(response.getBody());
		} catch (JSONException e) {
			throw new IdmeException(e);
		}
	}
	
    /** 
     * verify token's validity
     * 
     * @return true:valid false:invalid
     * @exception IdmeException
     */
	public boolean isValidToken() throws IdmeException {
		HttpResponse response = client.execute("/auth/v2/isValidToken", newList("ssi_token", token), null);
		return TemplateParser.parserBoolean(response.getBody());
	}
	
    /**
     * verify appName is matched with appSecret or not.
     *
     * @param appName widget name
     * @return true:match false:not match
     * @exception IdmeException
     */
	public static boolean isMatchAppNameAndSecret(String appName, String appSecret) throws IdmeException {
		HttpResponse response = (new TemplcateClient()).execute("/app/v2/isMatchAppNameAndSecret", null, newList("appName", appName, "appSecret", appSecret));
		return TemplateParser.parserBoolean(response.getBody());
	}
	
	public JSONObject getAuthIdentity() throws IdmeException {
		HttpResponse response = client.execute("/auth/v2/getAuthIdentity", newList("ssi_token", token), null);
		return TemplateParser.parserJSONObject(response.getBody());
	}
	
    /**
     * Get Identity's attribute value from auth source
     *
     * @return JSON String(the return value are different for the different auth sources)
     * @exception IdmeException
     */
	public JSONObject getIdentityAttr() throws IdmeException {
		HttpResponse response = client.execute("/auth/v2/getIdentityAttr", newList("ssi_token", token), null);
		return TemplateParser.parserJSONObject(response.getBody());
	}
	
	//*********** auth info ***************

	
	//*********** user info ***************
    /**
     * Get user profile from idme server
     *
     * @return JSON String key:{userID, salutation, firstName, lastName, displayName, defaultOrg, emails}
     *          emails is JSON Array[{"primary": "dummy@dummy.com"},{"secondary": "dummy@dummy.com"}]
     * @exception IdmeException
     */
	public JSONObject getUserProfile() throws IdmeException {
		HttpResponse response = client.execute("/user/v2/getUserProfile", newList("ssi_token", token), null);
		return TemplateParser.parserJSONObject(response.getBody());
	}
	//*********** user info ***************
	
    private static List<Parameter> newList(String... parameters) {
        List<Parameter> list = new ArrayList<Parameter>(parameters.length / 2);
        for (int p = 0; p + 1 < parameters.length; p += 2) {
            list.add(new Parameter(parameters[p], parameters[p + 1]));
        }
        return list;
    }
}
