package com.yffd.easy.framework.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月10日 下午1:38:34 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext appContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	public static Object getBean(String name){  
        return appContext.getBean(name);  
    }
	
}

