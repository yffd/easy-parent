package com.yffd.easy.demo.javaconfig.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月20日 上午9:39:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
// 说明类为IoC容器
@Configuration
// 事务
@EnableTransactionManagement
// 指定使用CGLIB代理，并开启
@EnableAspectJAutoProxy(proxyTargetClass = true)
// 配置扫描包
//@Import({OrmMybatisJavaConfig.class})
@Import({DruidDataSourceJavaConfig.class})
@ComponentScan(basePackages = "com.yffd.easy.demo.ssm")
public class SpringApplicationContextJavaConfig {

//	public @Bean UserDao userDao() {	// 方法名为该对象在spring容器中的bean名称
//		return new UserDao();
//	}
	
	/** 用${}占位符注入属性 */
	public @Bean static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}

