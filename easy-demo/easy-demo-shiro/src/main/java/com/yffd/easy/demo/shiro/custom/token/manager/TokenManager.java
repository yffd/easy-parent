package com.yffd.easy.demo.shiro.custom.token.manager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;

import com.yffd.easy.demo.shiro.custom.token.AccountInfo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月4日 下午3:31:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class TokenManager {

	/**
	 * 获取当前用户的登录token
	 * @Date	2018年6月4日 下午3:40:41 <br/>
	 * @author  zhangST
	 * @return
	 */
	public static <T extends AccountInfo> T getToken() {
		T token = (T) SecurityUtils.getSubject().getPrincipal();
		return token ;
	}
	
	/**
	 * 登录
	 * @Date	2018年6月4日 下午4:01:18 <br/>
	 * @author  zhangST
	 * @param token
	 * @param rememberMe
	 * @return
	 */
	public static <T extends AccountInfo> T login(T token, Boolean rememberMe){
		UsernamePasswordToken upToken = new UsernamePasswordToken(token.getAccountId(), token.getAccountPwd());
		if(rememberMe) upToken.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(upToken);
		return getToken();
	}


	/**
	 * 判断是否已登录
	 * @Date	2018年6月4日 下午4:01:24 <br/>
	 * @author  zhangST
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 退出登录
	 * @Date	2018年6月4日 下午4:02:01 <br/>
	 * @author  zhangST
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	
	/**
	 * 获取当前用户的Session
	 * @Date	2018年6月4日 下午4:06:56 <br/>
	 * @author  zhangST
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 把值放入到当前登录用户的Session里
	 * @Date	2018年6月4日 下午4:08:07 <br/>
	 * @author  zhangST
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	/**
	 * 从当前登录用户的Session里取值
	 * @Date	2018年6月4日 下午4:08:20 <br/>
	 * @author  zhangST
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}
	
	
//	/**
//	 * 清空当前用户权限信息
//	 * @Date	2018年6月4日 下午4:10:38 <br/>
//	 * @author  zhangST
//	 */
//	public static void clearNowUserAuth() {
//		// 方法一
//		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
//		JdbcRealm realm = (JdbcRealm)securityManager.getRealms().iterator().next();
//		// 方法二
//		
//		realm.clearCachedAuthorizationInfo();
//	}
//	
//	
//	
//	
//	/**
//	 * 根据UserIds 	清空权限信息。
//	 * @param id	用户ID
//	 */
//	public static void clearUserAuthByUserId(Long...userIds){
//		
//		if(null == userIds || userIds.length == 0)	return ;
//		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
//		
//		for (SimplePrincipalCollection simplePrincipalCollection : result) {
//			realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
//		}
//	}
//
//
//	/**
//	 * 方法重载
//	 * @param userIds
//	 */
//	public static void clearUserAuthByUserId(List<Long> userIds) {
//		if(null == userIds || userIds.size() == 0){
//			return ;
//		}
//		clearUserAuthByUserId(userIds.toArray(new Long[0]));
//	}
	
}

