package com.yffd.easy.common.shiro.custom.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.shiro.custom.handler.ICustomSessionHandler;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月4日 下午3:59:21 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomSessionListener implements SessionListener {
	private static final Logger LOG = LoggerFactory.getLogger(CustomSessionListener.class);
	
	private ICustomSessionHandler customSessionHandler;
	
	public void setCustomSessionHandler(ICustomSessionHandler customSessionHandler) {
		this.customSessionHandler = customSessionHandler;
	}

	@Override
	public void onStart(Session session) {	//会话创建时触发
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session listener], #onStart(), sessionId:%s =======", session.getId()));
	}

	@Override
	public void onStop(Session session) {	//退出会话时触发
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session listener], #onStop(), sessionId:%s =======", session.getId()));
		this.customSessionHandler.delete(session);
	}

	@Override
	public void onExpiration(Session session) {	//会话过期时触发
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [custom session listener], #onExpiration(), sessionId:%s =======", session.getId()));
		this.customSessionHandler.delete(session);
	}

}

