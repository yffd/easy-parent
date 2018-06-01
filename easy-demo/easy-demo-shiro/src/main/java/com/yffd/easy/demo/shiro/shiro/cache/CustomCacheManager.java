package com.yffd.easy.demo.shiro.shiro.cache;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.shiro.manager.ICustomManager;


/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月31日 下午3:25:12 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomCacheManager implements CacheManager, Initializable, Destroyable {
	private static final Logger LOG = LoggerFactory.getLogger(CustomCacheManager.class);
	
	private ICustomManager customManager;

	public ICustomManager getCustomManager() {
		return customManager;
	}

	public void setCustomManager(ICustomManager customManager) {
		this.customManager = customManager;
	}

	@Override
	public void destroy() throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#destroy =======");
		this.getCustomManager().destroy();
	}

	@Override
	public void init() throws ShiroException {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#init =======");
		this.getCustomManager().init();
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisCacheManager#getCache =======");
		return new CustomCache<K, V>(getCustomManager());
	}

}

