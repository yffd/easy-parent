package com.yffd.easy.bcap.workflow.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午5:09:16 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
@Import({AppDataSourceJavaConfig.class, AppTransactionManagerJavaConfig.class, AppBeansJavaConfig.class})
@ComponentScan(basePackages = "com.yffd.easy.bcap.workflow.service")
public class AppContextJavaConfig {

	/** 用${}占位符注入属性 */
	public @Bean static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}

