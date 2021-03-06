package com.yffd.easy.demo.javaconfig.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月25日 下午2:03:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WebAppInitializerJavaConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializerJavaConfig.class);
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		LOG.info("------------------spring context配置类初始化--------------");
		return new Class<?>[] {SpringApplicationContextJavaConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		LOG.info("------------------spring mvc配置类初始化--------------");
		return new Class<?>[] {WebMvcJavaConfig.class};
	}

	/** DispatcherServlet mapping */
	@Override
	protected String[] getServletMappings() {
		LOG.info("------------------映射路径初始化--------------");
		return new String[] {"/"};
	}

}

