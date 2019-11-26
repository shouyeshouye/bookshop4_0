package com.example.bookshop1_0.utils;



import lombok.extern.log4j.Log4j2;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;

import java.io.IOException;

@Log4j2
public class EhCacheUtil {
    //ehcache.xml 保存在src/main/resources/
    private static final String path = "classpath:config/ehcache.xml";
    private CacheManager manager;
    private EhCacheManager ehCacheManager;

    private static EhCacheUtil ehCache;

    public EhCacheUtil() {
        try {
            manager = CacheManager.create(ResourceUtils.getInputStreamForPath(path));
            EhCacheManager ehCacheManager = new EhCacheManager();
            ehCacheManager.setCacheManager(manager);
            this.ehCacheManager =ehCacheManager;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String cacheName, String key, Object value) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    public Cache get(String cacheName) {
        return manager.getCache(cacheName);
    }

    public void remove(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }
    public CacheManager getManager(){
        return manager;
    }
    public EhCacheManager getEhCacheManager(){
        return ehCacheManager;
    }
}
