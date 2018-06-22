package com.yffd.easy.common.security.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.web.filter.PathMatchingFilter;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月22日 下午5:44:51 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class ShiroConfigFactory {

    public String createLoginUrl() {
		return null;
	}
    
    public String createSuccessUrl() {
		return null;
	}
    
    public String createUnauthorizedUrl() {
		return null;
	}
    
	public LinkedHashMap<String, String> createFilterChainDefinitionMap() {
		return null;
	}
	
	public Map<String, PathMatchingFilter> createFilters() {
		return null;
	}
}

