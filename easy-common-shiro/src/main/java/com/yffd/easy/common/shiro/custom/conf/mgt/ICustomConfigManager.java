package com.yffd.easy.common.shiro.custom.conf.mgt;

import java.util.LinkedHashMap;
import java.util.Set;

import com.yffd.easy.common.shiro.support.AccountInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月6日 下午4:34:48 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICustomConfigManager {

	/**
	 * session过期时间，单位：毫秒
	 * @Date	2018年7月6日 下午5:17:31 <br/>
	 * @author  zhangST
	 * @return
	 */
	long getSessionTimeout();
	
	/**
	 * session有效性校验间隔，单位：毫秒
	 * @Date	2018年7月6日 下午5:20:26 <br/>
	 * @author  zhangST
	 * @return
	 */
	long getSessionValidationInterval();
	
	
	/**
	 * cookie加密秘钥
	 * @Date	2018年7月9日 上午11:51:32 <br/>
	 * @author  zhangST
	 * @return
	 */
	String getCookieCipher();
	
	/**
	 * cookie名称
	 * @Date	2018年7月9日 上午11:51:46 <br/>
	 * @author  zhangST
	 * @return
	 */
	String getCookieName();
	
	/**
	 * cookie有效期，单位：秒
	 * @Date	2018年7月9日 上午11:52:02 <br/>
	 * @author  zhangST
	 * @return
	 */
	int getCookieMaxAge();
	
	
	
	/**
	 * 同一用户多处登录session踢出，true=踢出后登陆者、false=踢出前登陆者
	 * @Date	2018年7月6日 下午5:30:44 <br/>
	 * @author  zhangST
	 * @return
	 */
	boolean kickoutAfter();
	
	/**
	 * 同一用户多处登录session踢出，最大上限
	 * @Date	2018年7月6日 下午5:32:01 <br/>
	 * @author  zhangST
	 * @return
	 */
	int getMaxSession();
	
	/**
	 * 同一用户多处登录session踢出，踢出后跳转的地址
	 * @Date	2018年7月6日 下午5:32:50 <br/>
	 * @author  zhangST
	 * @return
	 */
	String getKickoutUrl();
	
	/**
	 * 登录页面地址
	 * @Date	2018年7月6日 下午5:33:33 <br/>
	 * @author  zhangST
	 * @return
	 */
	String getLoginUrl();
    
	/**
	 * 登录成功后的页面地址
	 * @Date	2018年7月6日 下午5:33:47 <br/>
	 * @author  zhangST
	 * @return
	 */
    String getSuccessUrl();
    
    /**
     * 权限校验失败后的页面地址
     * @Date	2018年7月6日 下午5:34:17 <br/>
     * @author  zhangST
     * @return
     */
    String getUnauthorizedUrl();
    
    /**
     * 自定义配置
     * @Date	2018年7月9日 上午11:29:06 <br/>
     * @author  zhangST
     * @return
     */
    LinkedHashMap<String, String> getFilterChainDefinitionMap();
    
    /**
     * 获取账号信息
     * @Date	2018年7月9日 上午11:41:14 <br/>
     * @author  zhangST
     * @param identityId
     * @return
     */
    AccountInfoVo getAccountInfoVo(String identityId);
	
    /**
     * 获取角色信息
     * @Date	2018年7月9日 上午11:41:25 <br/>
     * @author  zhangST
     * @param identityId
     * @return
     */
	Set<String> getRoleCodes(String identityId);
	
	/**
	 * 获取权限信息
	 * @Date	2018年7月9日 上午11:41:40 <br/>
	 * @author  zhangST
	 * @param identityId
	 * @return
	 */
	Set<String> getPmsCodes(String identityId);
	
	int getDbNum();
	
}

