package com.yffd.easy.demo.shiro.custom.session.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.custom.jedis.IJedisManager;
import com.yffd.easy.demo.shiro.custom.util.ShiroSerializeUtils;

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
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#update(%s) =======", session));
		this.storeSession(session.getId(), session);
	}

	@Override
	public void delete(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#delete(%s) =======", session));
		if(session==null) throw new NullPointerException("session argument cannot be null.");
        Serializable id = session.getId();
        if(id!=null) {
            String key = this.buildSessionKey(id);
            this.getJedisManager().del(key);
        }
	}

	@Override
	public Collection<Session> getActiveSessions() {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#getActiveSessions() ======="));
		String keyPattern = this.buildSessionKeyPattern();
		Set<String> keys = this.getJedisManager().keys(keyPattern);
		if(null==keys || keys.size()==0) return Collections.emptySet();
		Set<Session> sessions = new HashSet<Session>();
		for(String key : keys) {
			byte[] byteKey = ShiroSerializeUtils.serialize(key);
			byte[] byteSession = this.getJedisManager().get(byteKey);
			if(null==byteSession) return null;
			Session session = (Session) ShiroSerializeUtils.deserialize(byteSession);
			sessions.add(session);
		}
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#getActiveSessions() = %s =======", sessions));
		return Collections.unmodifiableCollection(sessions);
	}

	@Override
	protected Serializable doCreate(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#doCreate(%s) =======", session));
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
		storeSession(sessionId, session);
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro session dao] : JedisChacheSessionDao#doReadSession(%s) =======", sessionId));
		String key = this.buildSessionKey(sessionId);
		byte[] byteKey = ShiroSerializeUtils.serialize(key);
		byte[] byteSession = this.getJedisManager().get(byteKey);
		if(null==byteSession) return null;
        return (Session) ShiroSerializeUtils.deserialize(byteSession);
	}
	
	protected Serializable storeSession(Serializable id, Session session) {
        if(id==null) throw new NullPointerException("id argument cannot be null.");
        String key = this.buildSessionKey(id);
        byte[] byteKey = ShiroSerializeUtils.serialize(key);
        byte[] byteValue = ShiroSerializeUtils.serialize(session);
        int expireSeconds = (int) (session.getTimeout() / 1000);	// 秒
        this.jedisManager.setex(byteKey, byteValue, expireSeconds);
        return key;
    }
	
	protected String buildSessionKey(Serializable sessionId) {
		return this.getJedisManager().getName() + ":" + sessionId;
	}
	
	protected String buildSessionKeyPattern() {
		return this.getJedisManager().getName() + ":";
	}
	
//	protected String buildSessionValue(Session session) {
//		if(null==session) return null;
//		SimpleSession simpleSession = (SimpleSession) session;
//		Serializable id = simpleSession.getId();
//		String startTimestamp = null==simpleSession.getStartTimestamp() ? null : simpleSession.getStartTimestamp().getTime() + "";
//		String stopTimestamp = null==simpleSession.getStopTimestamp() ? null : simpleSession.getStopTimestamp().getTime() + "";
//		String lastAccessTime = null==simpleSession.getLastAccessTime() ? null : simpleSession.getLastAccessTime().getTime() + "";
//		String timeout = simpleSession.getTimeout() + "";
//		boolean expired = simpleSession.isExpired();
//		String host = simpleSession.getHost();
//		Map<Object, Object> attributes = simpleSession.getAttributes();
//		
//        Map<String, Object> tmp = new HashMap<>();
//        tmp.put("id", id);
//        tmp.put("startTimestamp", startTimestamp);
//        tmp.put("stopTimestamp", stopTimestamp);
//        tmp.put("lastAccessTime", lastAccessTime);
//        tmp.put("timeout", timeout);
//        tmp.put("expired", expired);
//        tmp.put("host", host);
//        tmp.put("attributes", attributes);
//		String sessionJson = JSON.toJSONString(tmp);
//		return sessionJson;
//	}
	
//	protected Session fmtSessionValue(String sessionJson) {
//		if(null==sessionJson) return null;
//		JSONObject jsonObject = JSONObject.parseObject(sessionJson);
//		Map<String,Object> map = (Map<String,Object>) jsonObject;
//		Serializable id = (Serializable) map.get("id");
//		String startTimestamp = (String) map.get("startTimestamp");
//		String stopTimestamp = (String) map.get("stopTimestamp");
//		String lastAccessTime = (String) map.get("lastAccessTime");
//		String timeout = (String) map.get("timeout");
//		boolean expired = (boolean) map.get("expired");
//		String host = (String) map.get("host");
//		Map<Object, Object> attributes = (Map<Object, Object>) map.get("attributes");
//		SimpleSession simpleSession = new SimpleSession();
//		simpleSession.setId(id);
//		if(null!=startTimestamp) simpleSession.setStartTimestamp(new Date(Long.parseLong(startTimestamp)));
//		if(null!=stopTimestamp) simpleSession.setStopTimestamp(new Date(Long.parseLong(stopTimestamp)));
//		if(null!=lastAccessTime) simpleSession.setLastAccessTime(new Date(Long.parseLong(lastAccessTime)));
//		simpleSession.setTimeout(Long.parseLong(timeout));
//		simpleSession.setExpired(expired);
//		simpleSession.setHost(host);
//		simpleSession.setAttributes(attributes);
//		return simpleSession;
//	}
}
