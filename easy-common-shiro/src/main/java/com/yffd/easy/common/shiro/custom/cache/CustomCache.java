package com.yffd.easy.common.shiro.custom.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomCacheHandler;

/**
 * @Description  jedis 的hash类型实现缓存.
 * @Date		 2018年7月3日 上午9:50:53 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class CustomCache<K, V> implements Cache<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(CustomCache.class);
	
	private String name;
	private ICustomCacheHandler customCacheHandler;

	public CustomCache(String name, ICustomCacheHandler customCacheHandler) {
		this.name = name;
		this.customCacheHandler = customCacheHandler;
	}
	
	//根据Key获取缓存中的值
	@Override
	public V get(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom cache]=%s, #get(%s) =======", this.name, key));
		return this.customCacheHandler.get(this.name, key);
	}

	//往缓存中放入key-value，返回缓存中之前的值
	@Override
	public V put(K key, V value) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom cache]=%s, #put(%s, %s) =======", this.name, key, value));
		return this.customCacheHandler.put(this.name, key, value);
	}

	//移除缓存中key对应的值，返回该值
	@Override
	public V remove(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("=======  [custom cache]=%s, #remove(%s) =======", this.name, key));
		return this.customCacheHandler.remove(this.name, key);
	}

	//清空整个缓存
	@Override
	public void clear() throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("=======  [custom cache]=%s, #clear() =======", this.name));
		this.customCacheHandler.clear(this.name);
	}

	//返回缓存大小
	@Override
	public int size() {
		if(LOG.isInfoEnabled()) LOG.info(String.format("=======  [custom cache]=%s, #size() =======", this.name));
		return this.customCacheHandler.size(this.name);
	}

	//获取缓存中所有的key
	@Override
	public Set<K> keys() {
		if(LOG.isInfoEnabled()) LOG.info(String.format("=======  [custom cache]=%s, #keys() =======", this.name));
		return this.customCacheHandler.keys(this.name);
	}

	//获取缓存中所有的value
	@Override
	public Collection<V> values() {
		if(LOG.isInfoEnabled()) LOG.info(String.format("=======  [custom cache]=%s, #values() =======", this.name));
		return this.customCacheHandler.values(this.name);
	}
	
}
