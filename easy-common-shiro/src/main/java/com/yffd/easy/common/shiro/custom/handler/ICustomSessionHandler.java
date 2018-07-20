package com.yffd.easy.common.shiro.custom.handler;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月4日 下午6:10:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICustomSessionHandler {

	public void update(Session session) throws UnknownSessionException;

	public void delete(Session session);
	
	public void delete(List<Session> sessions);

	public Collection<Session> getActiveSessions();

	public Serializable doCreate(Session session);

	public Session doReadSession(Serializable sessionId);
	
}

