package com.yffd.easy.demo.shiro.shiro.jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.demo.shiro.shiro.manager.ICustomSessionManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午3:17:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisCustomSessionManager extends JedisCustomBaseManager implements ICustomSessionManager {
	private static final Logger LOG = LoggerFactory.getLogger(JedisCustomSessionManager.class);
	
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
	public void update(Session session) throws UnknownSessionException {
		if(null==session || null==session.getId()) throw new NullPointerException("session is empty");
		String key = this.buildSessionKey(session.getId());
		String value = JSON.toJSONString(session);
		String tmpValue = session.getClass().getName() + "#" + value;
		int ttl = (int) (session.getTimeout() / 1000); //秒
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.set(key, tmpValue);
			if(ttl > 0) jedis.expire(key, ttl);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro-session:update【key:%s， value:%s】 =======", key, tmpValue), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public void delete(Session session) {
		if(null==session || null==session.getId()) throw new NullPointerException("session is empty");
		String key = this.buildSessionKey(session.getId());
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.del(key);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro-session:delete【key:%s】 =======", key), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			String keyPattern = this.buildSessionKey(this.getName()) + ":*";
			Set<String> keys = jedis.keys(keyPattern);
			if(null==keys || keys.size()==0) return null;
			Set<Session> sessions = new HashSet<Session>();
			for(String key : keys) {
				String value = jedis.get(key);
				if(null==value) continue;
				if(value.indexOf("#")>0) {
					String[] arr = value.split("#", 2);
					String clazzName = arr[0];
					Class<?> clazz = Class.forName(clazzName);
					Object obj = JSON.parseObject(arr[1], clazz);
					if(obj instanceof Session) {
						Session one = (Session) obj;
						sessions.add(one);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro-session:getActiveSessions ======="), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	@Override
	public Serializable doCreate(Session session) {
//		String sessionId = this.buildSessionKey(session.getId());
//		((SimpleSession) session).setId(sessionId);
		if(null==session || null==session.getId()) throw new NullPointerException("session is empty");
		String key = this.buildSessionKey(session.getId());
		String value = JSON.toJSONString(session);
		String tmpValue = session.getClass().getName() + "#" + value;
		int ttl = (int) (session.getTimeout() / 1000); //秒
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.set(key, tmpValue);
			if(ttl > 0) jedis.expire(key, ttl);
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro-session:doCreate【key:%s， value:%s】 =======", key, tmpValue), e);
		} finally {
			this.close(jedis);
		}
		return session.getId();
	}

	@Override
	public Session doReadSession(Serializable sessionId) {
		if(null==sessionId) throw new NullPointerException("sessionId is empty");
		String key = this.buildSessionKey(sessionId);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			String value = jedis.get(key);
			if(null==value) return null;
			String[] arr = value.split("#", 2);
			String clazzName = arr[0];
			Class<?> clazz = Class.forName(clazzName);
			Object obj = JSON.parseObject(arr[1], clazz);
			if(obj instanceof Session) {
				return (Session) obj;
			}
		} catch (Exception e) {
			LOG.error(String.format("======= jedis shiro-session:getActiveSessions ======="), e);
		} finally {
			this.close(jedis);
		}
		return null;
	}

	private String buildSessionKey(Serializable sessionId) {
		return sessionId.toString();
//		return this.getName() + ":" + sessionId;
	}
	
}

