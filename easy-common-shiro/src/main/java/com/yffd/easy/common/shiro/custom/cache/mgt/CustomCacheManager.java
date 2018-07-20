package com.yffd.easy.common.shiro.custom.cache.mgt;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.cache.CustomCache;
import com.yffd.easy.common.shiro.custom.handler.ICustomCacheHandler;

public class CustomCacheManager implements CacheManager {
	private static final Logger LOG = LoggerFactory.getLogger(CustomCacheManager.class);

	private ICustomCacheHandler customCacheHandler;
	
	public ICustomCacheHandler getCustomCacheHandler() {
		return customCacheHandler;
	}

	public void setCustomCacheHandler(ICustomCacheHandler customCacheHandler) {
		this.customCacheHandler = customCacheHandler;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom cache manager] : #getCache(%s) =======", name));
		return new CustomCache<K, V>(name, this.getCustomCacheHandler());
	}
	
}
