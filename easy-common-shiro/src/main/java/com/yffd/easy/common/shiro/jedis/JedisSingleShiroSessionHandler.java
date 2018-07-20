package com.yffd.easy.common.shiro.jedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.shiro.ShiroException;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.AbstractValidatingSessionManager;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomSessionHandler;
import com.yffd.easy.common.support.io.util.EasySerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月5日 上午10:43:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class JedisSingleShiroSessionHandler implements ICustomSessionHandler, Initializable, Destroyable {
	private static final Logger LOG = LoggerFactory.getLogger(JedisSingleShiroSessionHandler.class);
	private static final String SESSION_CACHE_NAME = "shiro-session";
	private static final int DB_NUM = 0;
	
	private JedisPool jedisPool;
	private Integer dbNum = DB_NUM;
	private String cacheName = SESSION_CACHE_NAME;
	
	private SessionManager sessionManager;
	private ICustomSessionHandler shiroSessionHandler;
	
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

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public ICustomSessionHandler getShiroSessionHandler() {
		return shiroSessionHandler;
	}

	public void setShiroSessionHandler(ICustomSessionHandler shiroSessionHandler) {
		this.shiroSessionHandler = shiroSessionHandler;
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
	public void update(Session session) throws UnknownSessionException {
		if(session instanceof ValidatingSession
				&& !((ValidatingSession) session).isValid()) {
			// 会话过期 或 停止
			this.delete(session);
			return;	
		}
		// jedis 缓存
        byte[] byteHkey = this.getCacheName().getBytes();
		byte[] byteField = EasySerializeUtils.serialize(session.getId());
		byte[] byteValue = EasySerializeUtils.serialize(session);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(byteHkey, byteField, byteValue);
		} catch (Exception e) {
			LOG.error(String.format("======= [jedis session]=%s, #update(%s) =======", this.getCacheName(), session), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public void delete(Session session) {
		byte[] byteHkey = this.getCacheName().getBytes();
		byte[] byteField = EasySerializeUtils.serialize(session.getId());
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis session]=%s, #delete(%s) =======", this.getCacheName(), session), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public void delete(List<Session> sessions) {
		if(null==sessions || sessions.size()==0) return;
		byte[][] byteFields = new byte[sessions.size()][];
		for(int i=0;i<sessions.size();i++) {
			Session session = sessions.get(i);
			byteFields[i] = EasySerializeUtils.serialize(session.getId());
		}
		byte[] byteHkey = this.getCacheName().getBytes();
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hdel(byteHkey, byteFields);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis session]=%s, #delete(%s) =======", this.getCacheName(), sessions), e);
		} finally {
			this.close(jedis);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		byte[] byteHkey = this.getCacheName().getBytes();
		List<byte[]> values = null;
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			values = jedis.hvals(byteHkey);
		} catch (Exception e) {
			LOG.error(String.format("=======  [jedis session]=%s, #getActiveSessions() =======", this.getCacheName()), e);
		} finally {
			this.close(jedis);
		}
		if(null==values || values.size()==0) return Collections.emptyList();
		
		List<Session> inActive = new ArrayList<>();
		List<Serializable> inActiveId = new ArrayList<>();
		for(byte[] valueBytes : values) {
			if(null==valueBytes) continue;
			Session session = (Session) EasySerializeUtils.deserialize(valueBytes);
			
			try {
				if (session instanceof ValidatingSession) {
		            ((ValidatingSession) session).validate();
		        } else {
		            throw new IllegalStateException("Session类型不支持，" + session + " not implements " + ValidatingSession.class.getName());
		        }
	        } catch (InvalidSessionException ise) {
	        	inActive.add(session);
	        	inActiveId.add(session.getId());
	        }
		}
		if(inActive.size()>0) {
			this.getShiroSessionHandler().delete(inActive);
			if(LOG.isInfoEnabled()) 
				LOG.info("======= [jedis session]=%s, delete invalide session:" + inActiveId);
		}
		
		return Collections.emptySet();
	}

	@Override
	public Serializable doCreate(Session session) {
		Serializable sessionId = session.getId();
		byte[] byteHkey = this.getCacheName().getBytes();
		byte[] byteField = EasySerializeUtils.serialize(sessionId);
		byte[] byteValue = EasySerializeUtils.serialize(session);
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			jedis.hset(byteHkey, byteField, byteValue);
		} catch (Exception e) {
			LOG.error(String.format("======= [jedis session]=%s, #doCreate(%s) =======", this.getCacheName(), session), e);
		} finally {
			this.close(jedis);
		}
        
        return sessionId;
	}

	@Override
	public Session doReadSession(Serializable sessionId) {
		byte[] byteHkey = this.getCacheName().getBytes();
		byte[] byteField = EasySerializeUtils.serialize(sessionId);
		byte[] byteValue = new byte[0];
		Jedis jedis = null;
		try {
			jedis = this.getClient();
			byteValue = jedis.hget(byteHkey, byteField);
		} catch (Exception e) {
			LOG.error(String.format("======= [jedis session]=%s, #doReadSession(%s) =======", this.getCacheName(), sessionId), e);
		} finally {
			this.close(jedis);
		}
		if(null==byteValue) return null;
		return (Session) EasySerializeUtils.deserialize(byteValue);
	}
	
}

