package com.yffd.easy.bcap.workflow.web.javaconfig;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.cache.WebappTemplateLoader;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月26日 上午11:35:10 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
public class WebFreemarkerJavaConfig {
	/** 配置freeMarker视图解析器 */
	@Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setRequestContextAttribute("request");
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setOrder(0);
        return freeMarkerViewResolver;
    }
	
	/** freemarker配置 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(ServletContext servletContext) {
		freemarker.template.Configuration ftlCfg = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_0);
		ftlCfg.setTagSyntax(freemarker.template.Configuration.AUTO_DETECT_TAG_SYNTAX);
		ftlCfg.setTemplateUpdateDelayMilliseconds(1000);
		ftlCfg.setDefaultEncoding("UTF-8");
		ftlCfg.setOutputEncoding("UTF-8");
		ftlCfg.setLocale(Locale.SIMPLIFIED_CHINESE);
		ftlCfg.setDateFormat("yyyy-MM-dd");
		ftlCfg.setTimeFormat("HH:mm:ss");
		ftlCfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		ftlCfg.setClassicCompatible(true);	//空串显示""
		ftlCfg.setTemplateLoader(new WebappTemplateLoader(servletContext, "/WEB-INF/views/ftl/"));
		
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setConfiguration(ftlCfg);
		return freeMarkerConfigurer;
	}
}

