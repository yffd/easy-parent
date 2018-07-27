package com.yffd.easy.demo.javaconfig.spring;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月24日 下午5:02:45 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
@EnableWebMvc
@Import(WebFreemarkerJavaConfig.class)
@ComponentScan("com.yffd.easy.demo.ssm.controller" )
public class WebMvcJavaConfig extends WebMvcConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(WebMvcJavaConfig.class);
	
	/** JSP视图解析器，对模型视图名称的解析，在请求时模型视图名称添加前后缀 */
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/jsp/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}
	
	/** 文件上传 */
	@Bean(name = "multipartResolver")
	protected CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxInMemorySize(0);
		commonsMultipartResolver.setMaxUploadSize(5 * 1024 * 1024);	// 指定所上传文件的总大小，该限制不是针对单个文件，而是所有文件的容量之和
		return commonsMultipartResolver;
	}

	/** 静态资源处理 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 通过调用enable方法，要求DispatcherServelet将对静态资源的请求转发到Servlet容器中的默认的Servlet上，不是DispatcherServelet本身处理
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		LOG.info("------------------加载静态资源--------------");
		String[] pathPatterns = new String[] {
				"/favicon.ico",
				"/static/**"
		};
		String[] resourceLocations = new String[] {
				"/favicon.ico",
				"/static/"
		};
		registry.addResourceHandler(pathPatterns)
				.addResourceLocations(resourceLocations)
				.setCacheControl(CacheControl.maxAge(31536000, TimeUnit.MILLISECONDS).cachePublic());
	}
	
	
}

