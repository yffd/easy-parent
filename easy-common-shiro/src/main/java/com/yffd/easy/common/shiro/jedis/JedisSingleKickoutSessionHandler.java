package com.yffd.easy.common.shiro.jedis;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.shiro.ShiroException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.shiro.custom.handler.ICustomKickoutSessionHandler;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月5日 下午3:58:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSingleKickoutSessionHandler implements ICustomKickoutSessionHandler, Initializable, Destroyable {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSingleKickoutSessionHandler.class);

	private JedisPool jedisPool;
	private Integer dbNum = 1;
	private String cacheName = "shiro-current-user";
	
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
	
	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
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
	public Deque<Serializable> getSessionIds(String userId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			value = jedis.hget(this.getCacheName(), userId);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			this.close(jedis);
		}
		if(null==value) return null;
		return JSON.parseObject(value, LinkedList.class);
	}

	@Override
	public void putSessionIds(String userId, Deque<Serializable> sessionIds) {
		String json = JSON.toJSONString(sessionIds);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(this.getCacheName(), userId, json);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			this.close(jedis);
		}
		
	}


}

