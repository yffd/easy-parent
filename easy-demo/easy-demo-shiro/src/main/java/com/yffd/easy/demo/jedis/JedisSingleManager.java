package com.yffd.easy.demo.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.custom.util.ShiroSerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月31日 下午3:43:36 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSingleManager implements IJedisManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSingleManager.class);
	private static final int DB_INDEX = 0;
	private static final String HKEY_ID = "shiro";

	private JedisPool jedisPool;
    private Integer dbIndex;
    private String hkeyId;
	
    @Override
    public <K, V> V get(K key) throws CacheException {
		byte[] byteHkey = this.getHkeyId().getBytes();
		byte[] byteField = ShiroSerializeUtils.serialize(key);
		byte[] byteValue = new byte[0];
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			byteValue = jedis.hget(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("shiro:get【hkey:%s， field:%s】", getHkeyId(), key), e);
		} finally {
			this.close(jedis);
		}
		if(null==byteValue) return null;
		return (V) ShiroSerializeUtils.deserialize(byteValue);
	}

    @Override
	public <K, V> V put(K key, V value) throws CacheException {
		V previos = this.get(key);
		byte[] byteHkey = this.getHkeyId().getBytes();
		byte[] byteField = ShiroSerializeUtils.serialize(key);
		byte[] byteValue = ShiroSerializeUtils.serialize(value);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(byteHkey, byteField, byteValue);
		} catch (Exception e) {
			LOG.error(String.format("shiro:put【hkey:%s， field:%s】", getHkeyId(), key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

    @Override
	public <K, V> V remove(K key) throws CacheException {
		V previos = this.get(key);
		byte[] byteHkey = this.getHkeyId().getBytes();
		byte[] byteField = ShiroSerializeUtils.serialize(key);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("shiro:remove【hkey:%s， field:%s】", getHkeyId(), key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

    @Override
	public void clear() throws CacheException {
		byte[] byteHkey = this.getHkeyId().getBytes();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.del(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("shiro:clear【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
	}

    @Override
	public int size() {
		byte[] byteHkey = this.getHkeyId().getBytes();
		int result = 0;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			result = jedis.hlen(byteHkey).intValue();
		} catch (Exception e) {
			LOG.error(String.format("shiro:size【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
		return result;
	}

    @Override
	public <K> Set<K> keys() {
		byte[] byteHkey = this.getHkeyId().getBytes();
		Set<K> result = new HashSet<>();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<byte[]> keys = jedis.hkeys(byteHkey);
			if(null!=keys && keys.size()>0) {
				for(byte[] keyBytes : keys) {
					if(null==keyBytes) continue;
					K key = (K) ShiroSerializeUtils.deserialize(keyBytes);
					result.add(key);
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("shiro:keys【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
		return result;
	}

    @Override
	public <V> Collection<V> values() {
		byte[] byteHkey = this.getHkeyId().getBytes();
		List<V> result = new ArrayList<>();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			List<byte[]> valueList = jedis.hvals(byteHkey);
			if(null!=valueList && valueList.size()>0) {
				for(byte[] valueBytes : valueList) {
					if(null==valueBytes) continue;
					V value = (V) ShiroSerializeUtils.deserialize(valueBytes);
					result.add(value);
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("shiro:values【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
		return result;
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

	@Override
	public void destroy() throws Exception {
		if(null!=this.getJedisPool()) this.getJedisPool().destroy();
	}

	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
	}
	
    public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public int getDbIndex() {
		if(null==this.dbIndex) return DB_INDEX;
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public String getHkeyId() {
		if(null==this.hkeyId) return HKEY_ID;
		return hkeyId;
	}

	public void setHkeyId(String hkeyId) {
		this.hkeyId = hkeyId;
	}

	public void checked() {
		if(null==this.getJedisPool())
			throw new RuntimeException("jedisPool为空");
		try {
            this.getJedisPool().getResource();
        } catch (JedisConnectionException e) {
        	throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public Jedis getClient() {
		Jedis jedis = this.jedisPool.getResource();
		jedis.select(this.getDbIndex());	//选择数据库号
		return jedis;
	}
	
}

