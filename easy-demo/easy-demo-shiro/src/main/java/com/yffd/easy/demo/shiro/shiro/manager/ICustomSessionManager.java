package com.yffd.easy.demo.shiro.shiro.manager;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月1日 下午3:06:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICustomSessionManager {

	void update(Session session) throws UnknownSessionException;
	
	void delete(Session session);

	Collection<Session> getActiveSessions();

	Serializable doCreate(Session session);

	Session doReadSession(Serializable sessionId);
	
}

