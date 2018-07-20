package com.yffd.easy.common.shiro.custom.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.shiro.custom.handler.ICustomKickoutSessionHandler;
import com.yffd.easy.common.shiro.support.constants.ShiroConstants;
import com.yffd.easy.common.support.web.util.EasyServletUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月4日 下午4:59:22 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomKickoutSessionFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(CustomKickoutSessionFilter.class);
	
	private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数，默认1
    
    private SessionManager sessionManager;
    private ICustomKickoutSessionHandler customKickoutSessionHandler;
    
	public String getKickoutUrl() {
		return kickoutUrl;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public boolean isKickoutAfter() {
		return kickoutAfter;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public int getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCustomKickoutSessionHandler(ICustomKickoutSessionHandler customKickoutSessionHandler) {
		this.customKickoutSessionHandler = customKickoutSessionHandler;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter Kickout =========");
		Subject subject = this.getSubject(request, response);
		// 如果没有登录，不用踢出处理
		if(!subject.isAuthenticated() && !subject.isRemembered()) {
			return true;
		}
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		String userName = (String) subject.getPrincipal();
		
		// 同一用户的账号多处登录限制--缓存实现
		// TODO 同步控制
		Deque<Serializable> deque = this.customKickoutSessionHandler.getSessionIds(userName);
		if(deque == null) deque = new LinkedList<Serializable>();
		boolean changed = false;
		//如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && null==session.getAttribute(ShiroConstants.ATTRIBUTE_KEY_KICK_OUT)) {
        	deque.add(sessionId);
        	changed = true;
        }
		
        //如果队列里的sessionId数超出最大会话数，开始踢人
		while(deque.size() > maxSession) {
			changed = true;
			Serializable kickoutSessionId = null;
			if(kickoutAfter) {	
				kickoutSessionId = deque.removeLast();
			} else {
				kickoutSessionId = deque.removeFirst();
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if(kickoutSession != null) {
				    //设置会话的kickout属性表示踢出了
				    kickoutSession.setAttribute(ShiroConstants.ATTRIBUTE_KEY_KICK_OUT, ShiroConstants.MSG_KEY_CODE_100002);
				    if(LOG.isInfoEnabled()) LOG.info(String.format("同一账户【%s】多处登录限制踢出，sessionId:%s", userName, sessionId));
				}
			} catch (Exception e) {
				// UnknownSessionException
			}
		}
		
		if(changed) {
			this.customKickoutSessionHandler.putSessionIds(userName, deque);
		}
		
		//如果被踢出了，直接退出，重定向到踢出后的地址
		String kickoutFlag = (String) session.getAttribute(ShiroConstants.ATTRIBUTE_KEY_KICK_OUT);
		if(null!=kickoutFlag) {
			// 先执行退出操作
			subject.logout();
			this.saveRequest(request);
			// 输出信息
			Map<String, String> msg = new HashMap<>();
			if(ShiroConstants.MSG_KEY_CODE_100002.equals(kickoutFlag)) {
				msg.put("code", ShiroConstants.MSG_KEY_CODE_100002);
				msg.put("desc", ShiroConstants.MSG_KEY_DESC_100002);
			} else if(ShiroConstants.MSG_KEY_CODE_100001.equals(kickoutFlag)) {
				if(LOG.isInfoEnabled()) LOG.info(String.format("用户【%s】被管理员强制踢出系统，sessionId:%s", userName, sessionId));
				msg.put("code", ShiroConstants.MSG_KEY_CODE_100001);
				msg.put("desc", ShiroConstants.MSG_KEY_DESC_100001);
			}
			
			if(EasyServletUtils.isAjax(request)) {
				EasyServletUtils.respAjax(response, JSON.toJSONString(msg));
			} else {
				this.saveRequest(request);
				WebUtils.issueRedirect(request, response, kickoutUrl, msg);
			}
			return false;
		}
		
		return true;
	}

}

