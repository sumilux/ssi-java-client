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

package com.sumilux.ssi.client.cache;

import java.util.Date;

/**
 * Helper class to hold the relational cache info.
 * <p/>
 * 
 * @author  kevin 2012/04/10
 * @version 1.0
 */
public class CacheObject {

    /**
     * The cache Object
     */
	private Object object;

    /**
     * The flush time
     */
	private Date flushDateTime;
	
    /**
     * The expires of cache object
     */
	private long expires;
	
    /**
     * Returns the expires of cache object
     * @return the long value of this expires.
     */
	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

    /**
     * Returns cache object
     * @return the object value.
     */
	public Object getObject() {
		return object;
	}

    /**
     * Set the cache object
     * @param object the flush object.
     */
	public void setObject(Object object) {
		this.object = object;
	}

    /**
     * Returns the flush time of cache object
     * @return the date value of flushDateTime
     */
	public Date getFlushDateTime() {
		return flushDateTime;
	}

    /**
     * Set the flushDateTime
     * @param flushDateTime the flush date time.
     */
	public void setFlushDateTime(Date flushDateTime) {
		this.flushDateTime = flushDateTime;
	}
}
