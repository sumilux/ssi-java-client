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

/**
 * Manage all the operations of cache
 * 
 * @author  kevin 2012/04/10
 * @version 1.0
 */
public class CacheManager {

    /**
     * The instance of cache
     */
	private static SSICache ssiCache = null;

    /**
     * Initialize the Cache Manager.
     *
     * @param capacity The cache's capacity
     */
	public CacheManager(int capacity) {
		createSSICache(capacity);
	}
	
    /**
     * Initialize the Cache Manager.
     */
	public CacheManager() {
		createSSICache();
	}
	
    /**
     * Create a singleton instance of SSICache.
     *
     * @param capacity The cache's capacity
     */
	private void createSSICache(int capacity) {
		if (ssiCache == null) {
			ssiCache = new SSICache(capacity);
		}
	}
	
    /**
     * Create a singleton instance of SSICache.
     */
	private void createSSICache() {
		if (ssiCache == null) {
			ssiCache = new SSICache();
		}
	}
	
    /**
     * Create a singleton instance of SSICache.
     * @return the instance of SSICache
     */
	public SSICache getSSICache() {
		return ssiCache;
	}
	
    /**
     * Retrieve the cache object from memory.
     *
     * @param key The cache key
     * @return The cache object
     */
	public CacheObject getFromCache(String key) {
		return getSSICache().getFromCache(key);
	}
	
    /**
     * put the cache object into memory.
     *
     * @param key The cache key
     * @param content The cache content
     * @param expires The cache's expires
     */
	public void putIntoCache(String key, Object object, int expires) {
		getSSICache().putIntoCache(key, object, expires);
	}
	
    /**
     * Remove the cache object from memory.
     *
     * @param key The cache key
     */
	public void removeFromCache(String key) {
		getSSICache().removeFromCache(key);
	}
	
    /**
     * Check if the cache valid or not
     *
     * @param ket The cache's
     * @return true/false
     */
	public boolean isValidCache(String key) {
		CacheObject object = getFromCache(key);
		if (object == null) 
			return false;
        if (System.currentTimeMillis() - object.getFlushDateTime().getTime() > object.getExpires() * 1000) 
        	return false;
        return true;
	}
}
