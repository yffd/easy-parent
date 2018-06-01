package com.yffd.easy.demo.jedis;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 上午10:07:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface IJedisManager {

	void init() throws ShiroException;
	
	void destroy() throws Exception;
	
	//根据Key获取缓存中的值
	<K, V> V get(K key) throws CacheException;

	//往缓存中放入key-value，返回缓存中之前的值
	<K, V> V put(K key, V value) throws CacheException;

	//移除缓存中key对应的值，返回该值
	<K, V> V remove(K key) throws CacheException;

	//清空整个缓存
	void clear() throws CacheException;

	//返回缓存大小
	int size();

	//获取缓存中所有的key
	<K> Set<K> keys();

	//获取缓存中所有的value
	<V> Collection<V> values();
	
}

