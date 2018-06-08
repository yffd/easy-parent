package com.yffd.easy.demo.shiro.custom.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.demo.shiro.custom.token.LoginStatus;
import com.yffd.easy.demo.shiro.custom.util.ShiroFilterUtils;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 下午4:47:49 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class PermissionPathMatchingFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(PermissionPathMatchingFilter.class);
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro filter permission] : PermissionPathMatchingFilter#isAccessAllowed(%s, %s, %s) =======", request, response, mappedValue));
		// 未指定权限，拒绝通过
		if(null==mappedValue) return Boolean.FALSE;
		String[] checkedPermissions = (String[]) mappedValue;
		Subject subject = this.getSubject(request, response);
		for(String permission : checkedPermissions) {
			if(subject.isPermitted(permission)) return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro filter permission] : PermissionPathMatchingFilter#onAccessDenied(%s, %s) =======", request, response));
		if(LOG.isInfoEnabled()) LOG.info("当前用户没有匹配的权限");
		if(ShiroFilterUtils.isAjax(request)) {	// ajax请求
			String msgJson = JSON.toJSONString(LoginStatus.FAILED());
			ShiroFilterUtils.responseAjax(response, msgJson);
			return Boolean.FALSE; 
		}
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}

}
