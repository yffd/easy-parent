package com.yffd.easy.common.shiro.custom.handler;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.CacheException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月4日 下午5:51:14 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICustomCacheHandler {
	
	public <K, V> V get(String cacheName, K key) throws CacheException;

    public <K, V> V put(String cacheName, K key, V value) throws CacheException;

    public <K, V> V remove(String cacheName, K key) throws CacheException;

    public void clear(String cacheName) throws CacheException;

    public int size(String cacheName);

    public <K> Set<K> keys(String cacheName);

    public <V> Collection<V> values(String cacheName);
	
}

