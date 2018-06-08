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
 * @Date		 2018年6月4日 下午3:21:58 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LoginPathMatchingFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(LoginPathMatchingFilter.class);
	
	//是否允许访问，返回true表示允许
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro filter login] : LoginPathMatchingFilter#isAccessAllowed(%s, %s, %s) =======", request, response, mappedValue));
		if(isLoginRequest(request, response)) {	// 登录请求通过
            return Boolean.TRUE;
        }
		Subject subject = this.getSubject(request, response);
		Object obj = subject.getPrincipal();
		if(null!=obj) {	// 已登录
            return Boolean.TRUE;
        }
		return Boolean.FALSE ;
	}

	//表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(LOG.isInfoEnabled()) LOG.info(String.format("======= [shiro filter login] : LoginPathMatchingFilter#onAccessDenied(%s, %s) =======", request, response));
		if(LOG.isInfoEnabled()) LOG.info("当前用户没有登录");
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

