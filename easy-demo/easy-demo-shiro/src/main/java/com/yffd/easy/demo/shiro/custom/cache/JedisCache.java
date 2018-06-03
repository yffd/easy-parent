package com.yffd.easy.demo.shiro.custom.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.custom.jedis.IJedisManager;
import com.yffd.easy.demo.shiro.uitl.SerializeUtils;

/**
 * jedis 的hash类型实现缓存
 * @author sks
 *
 * @param <K>
 * @param <V>
 */
public class JedisCache<K, V> implements Cache<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(JedisCache.class);

	private IJedisManager jedisManager;
	
	public JedisCache(IJedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	public IJedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(IJedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	//根据Key获取缓存中的值
	@Override
	public V get(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro] : JedisCache#get(%s) =======", key));
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		byte[] byteValue = new byte[0];
		this.getJedisManager().hget(byteHkey, byteField);
		if(null==byteValue) return null;
		return (V) SerializeUtils.deserialize(byteValue);
	}

	//往缓存中放入key-value，返回缓存中之前的值
	@Override
	public V put(K key, V value) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro] : JedisCache#put(%s, %s) =======", key, value));
		V previos = this.get(key);
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		byte[] byteValue = SerializeUtils.serialize(value);
		this.getJedisManager().hset(byteHkey, byteField, byteValue);
		return previos;
	}

	//移除缓存中key对应的值，返回该值
	@Override
	public V remove(K key) throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro] : JedisCache#remove(%s) =======", key));
		V previos = this.get(key);
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		this.getJedisManager().hdel(byteHkey, byteField);
		return previos;
	}

	//清空整个缓存
	@Override
	public void clear() throws CacheException {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro] : JedisCache#clear() =======");
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		this.getJedisManager().del(byteHkey);
	}

	//返回缓存大小
	@Override
	public int size() {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro] : JedisCache#size() =======");
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		Long num = this.getJedisManager().hlen(byteHkey);
		if(null==num) return 0;
		return num.intValue();
	}

	//获取缓存中所有的key
	@Override
	public Set<K> keys() {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro] : JedisCache#keys() =======");
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		Set<byte[]> keys = this.getJedisManager().hkeys(byteHkey);
		if(null==keys || keys.size()==0) {
			return Collections.emptySet();
		}  else {
			Set<K> ret = new HashSet<>();
			for(byte[] keyBytes : keys) {
				if(null==keyBytes) continue;
				K key = (K) SerializeUtils.deserialize(keyBytes);
				ret.add(key);
			}
			return Collections.unmodifiableSet(ret);
		}
		
	}

	//获取缓存中所有的value
	@Override
	public Collection<V> values() {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro] : JedisCache#values() =======");
		byte[] byteHkey = this.getJedisManager().getName().getBytes();
		List<byte[]> values = this.getJedisManager().hvals(byteHkey);
		if(null==values || values.size()==0) {
			return Collections.emptyList();
		}  else {
			List<V> ret = new ArrayList<>();
			for(byte[] valueBytes : values) {
				if(null==valueBytes) continue;
				V value = (V) SerializeUtils.deserialize(valueBytes);
				ret.add(value);
			}
			return Collections.unmodifiableList(ret);
		}
	}
	
}
