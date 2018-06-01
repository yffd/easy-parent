package com.yffd.easy.demo.jedis;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

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
public class JedisJsonSingleManager extends JedisConfigManager implements IJedisManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisJsonSingleManager.class);

	private JedisPool jedisPool;
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public Jedis getClient() {
		Jedis jedis = this.jedisPool.getResource();
		jedis.select(this.getDbIndex());	//选择数据库号
		return jedis;
	}
	
	public void close(Jedis jedis) {
		if(null!=jedis) jedis.close();
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
	
    @Override
    public <K, V> V get(K key) throws CacheException {
    	if(null==key) return null;
		String strHkey = this.getHkeyId();
		String strField = JSON.toJSONString(key);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			String strValue = jedis.hget(strHkey, strField);
			if(strValue.indexOf("#")>0) {
				String[] arr = strValue.split("#", 2);
				String clazzName = arr[0];
				Class<?> clazz = Class.forName(clazzName);
				Object obj = JSON.parseObject(arr[1], clazz);
				System.out.println(obj);
				return (V) obj;
			} else {
				System.out.println(strValue);
				return (V) strValue;
			}
		} catch (Exception e) {
			LOG.error(String.format("shiro:get【hkey:%s， field:%s】", getHkeyId(), key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

    @Override
	public <K, V> V put(K key, V value) throws CacheException {
    	if(null==key || null==value) return null;
		V previos = this.get(key);
		String strHkey = this.getHkeyId();
		String strField = JSON.toJSONString(key);
		String strValue = JSON.toJSONString(value);
		String tmpValue = value.getClass().getName() + "#" + strValue;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(strHkey, strField, tmpValue);
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
		String strHkey = this.getHkeyId();
		String strField = JSON.toJSONString(key);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(strHkey, strField);
		} catch (Exception e) {
			LOG.error(String.format("shiro:remove【hkey:%s， field:%s】", getHkeyId(), key), e);
		} finally {
			this.close(jedis);
		}
		return previos;
	}

	@Override
	public void clear() throws CacheException {
		String strHkey = this.getHkeyId();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.del(strHkey);
		} catch (Exception e) {
			LOG.error(String.format("shiro:clear【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public int size() {
		String strHkey = this.getHkeyId();
		int result = 0;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			result = jedis.hlen(strHkey).intValue();
		} catch (Exception e) {
			LOG.error(String.format("shiro:size【hkey:%s】", getHkeyId()), e);
		} finally {
			this.close(jedis);
		}
		return result;
	}

	@Override
	public <K> Set<K> keys() {
		return null;
	}

	@Override
	public <V> Collection<V> values() {
		return null;
	}
	
}

