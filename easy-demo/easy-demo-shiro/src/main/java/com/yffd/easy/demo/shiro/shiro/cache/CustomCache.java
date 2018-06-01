package com.yffd.easy.demo.shiro.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.shiro.manager.ICustomManager;


/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月31日 下午3:35:12 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomCache<K, V> implements Cache<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(CustomCache.class);
	
	private ICustomManager cacheManager;
	
	public CustomCache(ICustomManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public ICustomManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(ICustomManager cacheManager) {
		this.cacheManager = cacheManager;
	}


	//根据Key获取缓存中的值
	@Override
	public V get(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= shiro:JedisCacheManager#get(%s) =======", key));
		return this.getCacheManager().get(key);
	}

	//往缓存中放入key-value，返回缓存中之前的值
	@Override
	public V put(K key, V value) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= shiro:JedisCacheManager#put(%s, %s) =======", key, value));
		return this.getCacheManager().put(key, value);
	}

	//移除缓存中key对应的值，返回该值
	@Override
	public V remove(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= shiro:JedisCacheManager#remove(%s) =======", key));
		return this.getCacheManager().remove(key);
	}

	//清空整个缓存
	@Override
	public void clear() throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#clear(%s) =======");
		this.getCacheManager().clear();
	}

	//返回缓存大小
	@Override
	public int size() {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#size(%s) =======");
		return this.getCacheManager().size();
	}

	//获取缓存中所有的key
	@Override
	public Set<K> keys() {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#keys(%s) =======");
		return this.getCacheManager().keys();
	}

	//获取缓存中所有的value
	@Override
	public Collection<V> values() {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#values(%s) =======");
		return this.getCacheManager().values();
	}

}

