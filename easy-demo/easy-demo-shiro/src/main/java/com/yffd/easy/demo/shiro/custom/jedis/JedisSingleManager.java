package com.yffd.easy.demo.shiro.custom.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.ShiroException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisSingleManager extends JedisConfig implements IJedisManager, Initializable, Destroyable {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSingleManager.class);
	
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
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.get(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] :get(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.get(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : get(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.set(key, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : set(%s, %s) =======", key, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.set(key, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : set(%s, %s) =======", key, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String setex(byte[] key, byte[] value, int expireSeconds) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.setex(key, expireSeconds, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : setex(%s, %s) =======", key, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String setex(String key, String value, int expireSeconds) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.setex(key, expireSeconds, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : setex(%s, %s) =======", key, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.del(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : del(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.del(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : del(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long len(byte[] keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<byte[]> keys = jedis.keys(keyPattern);
			if(null==keys || keys.size()==0) return 0L;
			return (long) keys.size();
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : len(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long len(String keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<String> keys = jedis.keys(keyPattern);
			if(null==keys || keys.size()==0) return 0L;
			return (long) keys.size();
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : len(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Set<byte[]> keys(byte[] keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.keys(keyPattern);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : keys(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Set<String> keys(String keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.keys(keyPattern);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : keys(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public List<byte[]> vals(byte[] keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<byte[]> keys = this.keys(keyPattern);
			if(null==keys || keys.size()==0) return null;
			List<byte[]> values = new ArrayList<>();
			for(byte[] key : keys) {
				values.add(this.get(key));
			}
			return values;
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : vals(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public List<String> vals(String keyPattern) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			Set<String> keys = this.keys(keyPattern);
			if(null==keys || keys.size()==0) return null;
			List<String> values = new ArrayList<>();
			for(String key : keys) {
				values.add(this.get(key));
			}
			return values;
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : vals(%s) =======", keyPattern), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hget(key, field);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hget(%s, %s) =======", key, field), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hget(key, field);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hget(%s, %s) =======", key, field), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hset(%s, %s, %s) =======", key, field, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hset(%s, %s, %s) =======", key, field, value), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hdel(byte[] key, byte[]... fields) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hdel(key, fields);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hdel(%s, %s) =======", key, fields), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hdel(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hdel(key, fields);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hdel(%s, %s) =======", key, fields), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hlen(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hlen(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hlen(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Long hlen(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hlen(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hlen(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hkeys(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hkeys(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hkeys(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hkeys(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public List<byte[]> hvals(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hvals(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hvals(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public List<String> hvals(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			return jedis.hvals(key);
		} catch (Exception e) {
			LOG.error(String.format("======= [JedisSingleManager] : hvals(%s) =======", key), e);
		} finally {
			this.close(jedis);
		}
		return null;
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
}
