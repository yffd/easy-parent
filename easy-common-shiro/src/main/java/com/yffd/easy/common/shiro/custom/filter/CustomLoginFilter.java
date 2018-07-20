package com.yffd.easy.common.shiro.custom.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.shiro.support.constants.ShiroConstants;
import com.yffd.easy.common.support.web.util.EasyServletUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午3:21:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomLoginFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(CustomLoginFilter.class);
	
	//是否允许访问，返回true表示允许
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter login =========");
		if(isLoginRequest(request, response)) {	// 登录请求通过
            return Boolean.TRUE;
        }
		
		Subject subject = this.getSubject(request, response);
		if(subject.isAuthenticated()) {
			return Boolean.TRUE;
		} else {
			request.setAttribute(ShiroConstants.ATTRIBUTE_KEY_LOGIN_FAILURE, ShiroConstants.MSG_KEY_CODE_100004);
			return Boolean.FALSE;
		}
	}

	//表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter login error : " + ShiroConstants.MSG_KEY_DESC_100004);
		// 输出信息
		Map<String, String> msg = new HashMap<>();
		msg.put("code", ShiroConstants.MSG_KEY_CODE_100004);
		msg.put("desc", ShiroConstants.MSG_KEY_DESC_100004);
		
		this.saveRequest(request);
		if(EasyServletUtils.isAjax(request)) {
			EasyServletUtils.respAjax(response, JSON.toJSONString(msg));
		} else {
			WebUtils.issueRedirect(request, response, getLoginUrl(), msg);
		}
		
		return Boolean.FALSE;
	}

}

