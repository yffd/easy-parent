package com.yffd.easy.demo.shiro.shiro.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.shiro.manager.ICustomManager;
import com.yffd.easy.demo.shiro.uitl.SerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午3:14:39 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisCustomManager extends JedisCustomBaseManager implements ICustomManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisCustomManager.class);

	private JedisPool jedisPool;
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
	}
	
	public Jedis getClient() {
		Jedis jedis = this.jedisPool.getResource();
		jedis.select(this.getDbIndex());	//选择数据库号
		return jedis;
	}
		
    @Override
    public <K, V> V get(K key) throws CacheException {
		byte[] byteHkey = this.getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		byte[] byteValue = new byte[0];
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			byteValue = jedis.hget(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:get【hkey:%s， field:%s】 =======", getName(), key), e);
		} finally {
			this.close(jedis);
		}
		if(null==byteValue) return null;
		return (V) SerializeUtils.deserialize(byteValue);
	}

    @Override
	public <K, V> V put(K key, V value) throws CacheException {
		V previos = this.get(key);
		byte[] byteHkey = this.getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		byte[] byteValue = SerializeUtils.serialize(value);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(byteHkey, byteField, byteValue);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:put【hkey:%s， field:%s】 =======", getName(), key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

    @Override
	public <K, V> V remove(K key) throws CacheException {
		V previos = this.get(key);
		byte[] byteHkey = this.getName().getBytes();
		byte[] byteField = SerializeUtils.serialize(key);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:remove【hkey:%s， field:%s】 =======", getName(), key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

    @Override
	public void clear() throws CacheException {
		byte[] byteHkey = this.getName().getBytes();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.del(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:clear【hkey:%s】 =======", getName()), e);
		} finally {
			this.close(jedis);
		}
	}

    @Override
	public int size() {
		byte[] byteHkey = this.getName().getBytes();
		int result = 0;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			result = jedis.hlen(byteHkey).intValue();
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:size【hkey:%s】 =======", getName()), e);
		} finally {
			this.close(jedis);
		}
		return result;
	}

    @Override
	public <K> Set<K> keys() {
		byte[] byteHkey = this.getName().getBytes();
		Set<K> result = new HashSet<>();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<byte[]> keys = jedis.hkeys(byteHkey);
			if(null!=keys && keys.size()>0) {
				for(byte[] keyBytes : keys) {
					if(null==keyBytes) continue;
					K key = (K) SerializeUtils.deserialize(keyBytes);
					result.add(key);
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:keys【hkey:%s】 =======", getName()), e);
		} finally {
			this.close(jedis);
		}
		return result;
	}

    @Override
	public <V> Collection<V> values() {
		byte[] byteHkey = this.getName().getBytes();
		List<V> result = new ArrayList<>();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			List<byte[]> valueList = jedis.hvals(byteHkey);
			if(null!=valueList && valueList.size()>0) {
				for(byte[] valueBytes : valueList) {
					if(null==valueBytes) continue;
					V value = (V) SerializeUtils.deserialize(valueBytes);
					result.add(value);
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro:values【hkey:%s】 =======", getName()), e);
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

}

