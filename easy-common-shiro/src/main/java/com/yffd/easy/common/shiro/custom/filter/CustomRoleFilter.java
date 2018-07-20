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
import com.yffd.easy.common.shiro.custom.conf.mgt.ICustomConfigManager;
import com.yffd.easy.common.shiro.support.constants.ShiroConstants;
import com.yffd.easy.common.support.web.util.EasyServletUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 下午4:47:49 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CustomRoleFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(CustomRoleFilter.class);
	
	private ICustomConfigManager customConfigManager;

	public void setCustomConfigManager(ICustomConfigManager customConfigManager) {
		this.customConfigManager = customConfigManager;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter role =========");
		// 未指定角色，拒绝通过
		if(null==mappedValue) {
			request.setAttribute(ShiroConstants.ATTRIBUTE_KEY_LOGIN_FAILURE, ShiroConstants.MSG_KEY_CODE_100005);
			return Boolean.FALSE;
		}
		String[] checkedRoles = (String[]) mappedValue;
		Subject subject = this.getSubject(request, response);
		for(String role : checkedRoles) {
			if(subject.hasRole(role)) return Boolean.TRUE;
		}
		
		request.setAttribute(ShiroConstants.ATTRIBUTE_KEY_LOGIN_FAILURE, ShiroConstants.MSG_KEY_CODE_100005);
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info("========= custom filter role error : " + ShiroConstants.MSG_KEY_DESC_100005);
		// 输出信息
		Map<String, String> msg = new HashMap<>();
		msg.put("code", ShiroConstants.MSG_KEY_CODE_100005);
		msg.put("desc", ShiroConstants.MSG_KEY_DESC_100005);
		
		this.saveRequest(request);
		if(EasyServletUtils.isAjax(request)) {
			EasyServletUtils.respAjax(response, JSON.toJSONString(msg));
		} else {
			WebUtils.issueRedirect(request, response, this.customConfigManager.getUnauthorizedUrl(), msg);
		}
		
		return Boolean.FALSE;
	}

}

