package com.yffd.easy.common.shiro.custom.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomSessionHandler;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月3日 下午1:53:40 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomSessionDao extends AbstractSessionDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CustomSessionDao.class);
	
	private ICustomSessionHandler customSessionHandler;
	
	public void setCustomSessionHandler(ICustomSessionHandler customSessionHandler) {
		this.customSessionHandler = customSessionHandler;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session], #update(), sessionId:%s =======", session.getId()));
		this.customSessionHandler.update(session);
	}

	@Override
	public void delete(Session session) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session], #delete(), sessionId:%s =======", session.getId()));
		this.customSessionHandler.delete(session);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		if(LOG.isInfoEnabled()) LOG.info("======= [custom session], #getActiveSessions() =======");
		return this.customSessionHandler.getActiveSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session], #doCreate(), sessionId:%s =======", session.getId()));
        // jedis 缓存
        return this.customSessionHandler.doCreate(session);
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session], #doReadSession(), sessionId:%s =======", sessionId));
		// jedis 缓存
		return this.customSessionHandler.doReadSession(sessionId);
	}
	
}

