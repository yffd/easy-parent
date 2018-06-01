package com.yffd.easy.demo.jedis;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月31日 下午3:43:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSharedManager implements IJedisManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSharedManager.class);
	private static final int DB_INDEX = 0;
	private static final String HKEY_ID = "shiro";

	private ShardedJedisPool shardedJedisPool;
    private Integer dbIndex;
    private String hkeyId;
	
	@Override
	public <K, V> V get(K key) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V put(K key, V value) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V remove(K key) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <K> Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() throws ShiroException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
	}
	
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public Integer getDbIndex() {
		if(null==this.dbIndex) return DB_INDEX;
		return dbIndex;
	}

	public void setDbIndex(Integer dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getHkeyId() {
		if(null==this.hkeyId) return HKEY_ID;
		return hkeyId;
	}

	public void setHkeyId(String hkeyId) {
		this.hkeyId = hkeyId;
	}
	
}

