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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides some interfaces to the cache manager.
 * 
 * @author  kevin 2012/04/10
 * @version 1.0
 */
class SSICache {

    /**
     * The map to hold all the cache Object
     */
	private static ConcurrentHashMap<String, CacheObject> cacheMap = new ConcurrentHashMap<String, CacheObject>();
    /**
     * The default cache capacity
     */
	private static final int DEFAULT_CACHE_CAPACITY = 2000;
    /**
     * The cache capacity
     */
	private static int capacity;
    /**
     * The list to hold the key of cache
     */
	private static List<String> keyList = new ArrayList<String>();
	
    /**
     * Initialize the Cache.
     *
     * @param capacity The capacity
     */
	public SSICache(int capacity) {
		SSICache.capacity = capacity;
	}
	
    /**
     * Initialize the Cache.
     */
	public SSICache() {
		this(DEFAULT_CACHE_CAPACITY);
	}
	
    /**
     * Retrieve the cache's capacity.
     *
     * @return The cache capacity
     */
	public int getCacheCapacity() {
		return capacity;
	}
	
    /**
     * put the cache object into memory.
     *
     * @param key The cache key
     * @param content The cache content
     * @param expires The cache's expires
     */
	public synchronized void putIntoCache(String key, Object content, int expires) {
		
		int currentCount = cacheMap.size();
		if(currentCount >= capacity && !cacheMap.contains(key)) {
			cacheMap.remove(0);
		}

		CacheObject cacheObject = cacheMap.get(key);
		if(cacheObject == null) {
			cacheObject = new CacheObject();
			keyList.add(key);
		}
		cacheMap.put(key, cacheObject);
		cacheObject.setFlushDateTime(new Date());
		cacheObject.setObject(content);
		cacheObject.setExpires(expires);
	}
	
    /**
     * Retrieve the cache object from memory.
     *
     * @param key The cache key
     * @return The cache object
     */
	public CacheObject getFromCache(String key) {
		return cacheMap.get(key);
	}
	
    /**
     * Remove the cache object from memory.
     *
     * @param key The cache key
     */
	public synchronized void removeFromCache(String key) {
		keyList.remove(key);
		cacheMap.remove(key);
	}
}
