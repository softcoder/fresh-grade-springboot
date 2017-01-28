/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.services.cache;

import java.lang.NullPointerException;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import org.springframework.stereotype.Component;

/**
 * This service provides caching services to the application.
 * @author softcoder
 *
 */
@Component
public class StudentManagerCacheService {

	/** The caching mechanism on google cloud platform */
    private MemcacheService memcache;
    // Dummy cache for local debugging since memcached only exists on Google App Engine
    private Map localeDebugCache = new ConcurrentHashMap();

    /**
     * Set a generic value for a given key in the cache
     * @param key - the lookup key
     * @param value - the value to cache
     */
    public <T> void setValueForKey(String key, T value) {
        if(getCache() == null) {
            localeDebugCache.put(key,value);
        }
        else {
            getCache().put(key,value);
        }
    }

    /**
     * Get a generic value for a given key in the cache
     * @param key - the lookup 
     * @return - the generic value from the cache
     */
    public <T> T getValueForKey(String key) {
        if(getCache() == null) {
            return (T)localeDebugCache.get(key);
        }
        return (T)getCache().get(key);
    }

    private Cache getCache() {
        Cache cache = null;
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            if(cacheFactory != null) {
                cache = cacheFactory.createCache(Collections.emptyMap());
            }
        } 
        catch (CacheException e) {
            // ...
        }
        catch (NullPointerException e) {
            // ...
        }

        return cache;
    }

    MemcacheService getMemcache() {
        if(memcache == null) {
            memcache = MemcacheServiceFactory.getMemcacheService();
        }
        return memcache;
    }
}
