package com.yffd.easy.demo.shiro.custom.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yffd.easy.demo.shiro.custom.jedis.IJedisManager;

public class JedisChacheSessionDao extends AbstractSessionDAO {
	private static final Logger LOG = LoggerFactory.getLogger(JedisChacheSessionDao.class);
	
	private IJedisManager jedisManager;
	
	public JedisChacheSessionDao() {
	}

	public IJedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(IJedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro session] : JedisChacheSessionDao#update =======");
		this.storeSession(session.getId(), session);
	}

	@Override
	public void delete(Session session) {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro session] : JedisChacheSessionDao#delete =======");
		if(session==null) throw new NullPointerException("session argument cannot be null.");
        Serializable id = session.getId();
        if(id!=null) {
            String key = this.buildSessionKey(id);
            this.getJedisManager().del(key);
        }
	}

	@Override
	public Collection<Session> getActiveSessions() {
		String keyPattern = this.buildSessionKeyPattern();
		Set<String> keys = this.getJedisManager().keys(keyPattern);
		if(null==keys || keys.size()==0) return Collections.emptySet();
		Set<Session> sessions = new HashSet<Session>();
		for(String key : keys) {
			String sessionJson = this.getJedisManager().get(key);
			if(null==sessionJson) continue;
			Session session = this.fmtSessionValue(sessionJson);
			sessions.add(session);
		}
		return Collections.unmodifiableCollection(sessions);
	}

	@Override
	protected Serializable doCreate(Session session) {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro session] : JedisChacheSessionDao#doCreate =======");
		Serializable sessionId = generateSessionId(session);
//        assignSessionId(session, sessionId);
        
        Serializable newSessionId = this.getJedisManager().getName() + ":" + sessionId;
		assignSessionId(session, newSessionId);
		
        storeSession(newSessionId, session);
        return newSessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(LOG.isInfoEnabled()) LOG.info("======= [shiro session] : JedisChacheSessionDao#doReadSession =======");
		String key = this.buildSessionKey(sessionId);
		String sessionJson = this.getJedisManager().get(key);
		if(null==sessionJson) return null;
        return this.fmtSessionValue(sessionJson);
	}
	
	protected Serializable storeSession(Serializable id, Session session) {
        if(id==null) {
            throw new NullPointerException("id argument cannot be null.");
        }
//        String key = this.buildSessionKey(id);
        String key = id.toString();
        String value = this.buildSessionValue(session);
        int expireSeconds = (int) (session.getTimeout() / 1000);	// 秒
        this.jedisManager.setex(key, value, expireSeconds);
        return key;
    }
	
	protected String buildSessionKey(Serializable sessionId) {
		return sessionId.toString();
//		return this.getJedisManager().getName() + ":" + sessionId;
	}
	
	protected String buildSessionKeyPattern() {
		return this.getJedisManager().getName() + ":";
	}
	
	protected String buildSessionValue(Session session) {
		if(null==session) return null;
		SimpleSession simpleSession = (SimpleSession) session;
		Serializable id = simpleSession.getId();
		String startTimestamp = null==simpleSession.getStartTimestamp() ? null : simpleSession.getStartTimestamp().getTime() + "";
		String stopTimestamp = null==simpleSession.getStopTimestamp() ? null : simpleSession.getStopTimestamp().getTime() + "";
		String lastAccessTime = null==simpleSession.getLastAccessTime() ? null : simpleSession.getLastAccessTime().getTime() + "";
		String timeout = simpleSession.getTimeout() + "";
		boolean expired = simpleSession.isExpired();
		String host = simpleSession.getHost();
		Map<Object, Object> attributes = simpleSession.getAttributes();
		
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("id", id);
        tmp.put("startTimestamp", startTimestamp);
        tmp.put("stopTimestamp", stopTimestamp);
        tmp.put("lastAccessTime", lastAccessTime);
        tmp.put("timeout", timeout);
        tmp.put("expired", expired);
        tmp.put("host", host);
        tmp.put("attributes", attributes);
		String sessionJson = JSON.toJSONString(tmp);
		return sessionJson;
//		return session.getClass().getName() + "#" + sessionJson;
	}
	
	protected Session fmtSessionValue(String sessionJson) {
		if(null==sessionJson) return null;
		JSONObject jsonObject = JSONObject.parseObject(sessionJson);
		Map<String,Object> map = (Map<String,Object>) jsonObject;
		Serializable id = (Serializable) map.get("id");
		String startTimestamp = (String) map.get("startTimestamp");
		String stopTimestamp = (String) map.get("stopTimestamp");
		String lastAccessTime = (String) map.get("lastAccessTime");
		String timeout = (String) map.get("timeout");
		boolean expired = (boolean) map.get("expired");
		String host = (String) map.get("host");
		Map<Object, Object> attributes = (Map<Object, Object>) map.get("attributes");
		SimpleSession simpleSession = new SimpleSession();
		simpleSession.setId(id);
		if(null!=startTimestamp) simpleSession.setStartTimestamp(new Date(Long.parseLong(startTimestamp)));
		if(null!=stopTimestamp) simpleSession.setStopTimestamp(new Date(Long.parseLong(stopTimestamp)));
		if(null!=lastAccessTime) simpleSession.setLastAccessTime(new Date(Long.parseLong(lastAccessTime)));
		simpleSession.setTimeout(Long.parseLong(timeout));
		simpleSession.setExpired(expired);
		simpleSession.setHost(host);
		simpleSession.setAttributes(attributes);
		return simpleSession;
//		if(sessionJson.indexOf("#")>0) {
//			String[] arr = sessionJson.split("#", 2);
//			String clazzName = arr[0];
//			try {
//				Class<?> clazz = (Class<Session>) Class.forName(clazzName);
//				Object obj = JSON.parseObject(arr[1], clazz);
//				if(obj instanceof Session) {
//					return (Session) obj;
//				} else {
//					throw new RuntimeException("org.apache.shiro.session类型错误：" + clazz);
//				}
//			} catch (ClassNotFoundException e) {
//				throw new RuntimeException(e);
//			}
//		}
//		return null;
	}
}
