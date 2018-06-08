package com.yffd.easy.demo.shiro.custom.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.custom.jedis.IJedisManager;

public class JedisCacheManager implements CacheManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisCacheManager.class);
	
	private IJedisManager jedisManager;

	public IJedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(IJedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}


	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro cache manager] : JedisCacheManager#getCache(%s) =======", name));
		return new JedisCache<K, V>(this.getJedisManager());
	}
	
}
