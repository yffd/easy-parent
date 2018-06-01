package com.yffd.easy.demo.shiro.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.demo.shiro.shiro.manager.ICustomSessionManager;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 上午11:48:53 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomSessionDAO extends AbstractSessionDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CustomSessionDAO.class);
	
	private ICustomSessionManager customSessionManager;
	
	public ICustomSessionManager getCustomSessionManager() {
		return customSessionManager;
	}

	public void setCustomSessionManager(ICustomSessionManager customSessionManager) {
		this.customSessionManager = customSessionManager;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisSessionDAO#update =======");
		this.getCustomSessionManager().update(session);
	}

	@Override
	public void delete(Session session) {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisSessionDAO#delete =======");
		this.getCustomSessionManager().delete(session);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisSessionDAO#getActiveSessions =======");
		return this.getCustomSessionManager().getActiveSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisSessionDAO#doCreate =======");
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        this.getCustomSessionManager().doCreate(session);
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(LOG.isInfoEnabled()) LOG.info("======= shiro:JedisSessionDAO#doReadSession =======");
		return this.getCustomSessionManager().doReadSession(sessionId);
	}

}

