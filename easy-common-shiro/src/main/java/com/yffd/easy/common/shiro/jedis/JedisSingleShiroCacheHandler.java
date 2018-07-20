package com.yffd.easy.common.shiro.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomCacheHandler;
import com.yffd.easy.common.support.io.util.EasySerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @Description  单机实例，hash结构实现.
 * @Date		 2018年5月31日 下午3:43:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSingleShiroCacheHandler implements ICustomCacheHandler, Initializable, Destroyable {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSingleShiroCacheHandler.class);

	private JedisPool jedisPool;
	private Integer dbNum = 1;
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Integer getDbNum() {
		return dbNum;
	}

	public void setDbNum(Integer dbNum) {
		this.dbNum = dbNum;
	}

	@Override
	public void destroy() throws Exception {
		if(null!=this.getJedisPool()) this.getJedisPool().destroy();
	}

	@Override
	public void init() throws ShiroException {
		if(null==this.getJedisPool()) throw new RuntimeException("jedisPool为空");
		try {
            this.getJedisPool().getResource();
        } catch (JedisConnectionException e) {
        	throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
	}
	
	public Jedis getClient() {
		Jedis jedis = this.jedisPool.getResource();
		jedis.select(this.getDbNum());	//选择数据库号
		return jedis;
	}
	
	@Override
	public <K, V> V get(String cacheName, K key) throws CacheException {
		byte[] byteHkey = cacheName.getBytes();
		byte[] byteField = EasySerializeUtils.serialize(key);
		byte[] byteValue = new byte[0];
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			byteValue = jedis.hget(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("======= [jedis cache]=%s, #get(%s) =======", cacheName, key), e);
		} finally {
			this.close(jedis);
		}
		if(null==byteValue) return null;
		Object obj = EasySerializeUtils.deserialize(byteValue);
		return (V) obj;
	}

	@Override
	public <K, V> V put(String cacheName, K key, V value) throws CacheException {
		V previos = this.get(cacheName, key);
		byte[] byteHkey = cacheName.getBytes();
		byte[] byteField = EasySerializeUtils.serialize(key);
		byte[] byteValue = EasySerializeUtils.serialize(value);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(byteHkey, byteField, byteValue);
		} catch (Exception e) {
			LOG.error(String.format("======= [jedis cache]=%s, #put(%s, %s) =======", cacheName, key, value), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

	@Override
	public <K, V> V remove(String cacheName, K key) throws CacheException {
		V previos = this.get(cacheName, key);
		byte[] byteHkey = cacheName.getBytes();
		byte[] byteField = EasySerializeUtils.serialize(key);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis cache]=%s, #remove(%s) =======", cacheName, key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

	@Override
	public void clear(String cacheName) throws CacheException {
		byte[] byteHkey = cacheName.getBytes();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.del(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis cache]=%s, #clear() =======", cacheName), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public int size(String cacheName) {
		byte[] byteHkey = cacheName.getBytes();
		Long num = 0L;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			num = jedis.hlen(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis cache]=%s, #size() =======", cacheName), e);
		} finally {
			this.close(jedis);
		}
		return num.intValue();
	}

	@Override
	public <K> Set<K> keys(String cacheName) {
		byte[] byteHkey = cacheName.getBytes();
		Set<byte[]> keys = null;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			keys = jedis.hkeys(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis cache]=%s, #keys() =======", cacheName), e);
		} finally {
			this.close(jedis);
		}
		if(null==keys || keys.size()==0) {
			return Collections.emptySet();
		}  else {
			Set<K> ret = new HashSet<>();
			for(byte[] keyBytes : keys) {
				if(null==keyBytes) continue;
				K key = (K) EasySerializeUtils.deserialize(keyBytes);
				ret.add(key);
			}
			return Collections.unmodifiableSet(ret);
		}
	}

	@Override
	public <V> Collection<V> values(String cacheName) {
		byte[] byteHkey = cacheName.getBytes();
		List<byte[]> values = null;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			values = jedis.hvals(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis cache]=%s, #values() =======", cacheName), e);
		} finally {
			this.close(jedis);
		}
		if(null==values || values.size()==0) {
			return Collections.emptyList();
		}  else {
			List<V> ret = new ArrayList<>();
			for(byte[] valueBytes : values) {
				if(null==valueBytes) continue;
				V value = (V) EasySerializeUtils.deserialize(valueBytes);
				ret.add(value);
			}
			return Collections.unmodifiableList(ret);
		}
	}
	
}

