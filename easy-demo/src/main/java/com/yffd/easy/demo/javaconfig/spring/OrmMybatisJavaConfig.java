package com.yffd.easy.demo.javaconfig.spring;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @Description  mybatis配置.
 * @Date		 2018年7月20日 上午11:20:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Import({DruidDataSourceJavaConfig.class})
@MapperScan(basePackages = {"com.yffd.easy.demo.javaconfig.mapper"})
public class OrmMybatisJavaConfig {
	private static final Logger LOG = LoggerFactory.getLogger(OrmMybatisJavaConfig.class);

	/** 注入 mybatis 的session工厂bean */
	public @Bean SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfiguration(this.mybatisConfiguration());
		
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		try {
			sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver
			        .getResources("classpath:mybatis/mapper/**/*Mapper.xml"));
		} catch (IOException e) {
			LOG.error("SqlSessionFactoryBean configuration exception ----->>:", e);
		}
		return sqlSessionFactoryBean;
	}
	
	/** mybatis的配置 */
	private org.apache.ibatis.session.Configuration mybatisConfiguration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(true);
        configuration.setDefaultStatementTimeout(3000);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setUseColumnLabel(true);
        return configuration;
	}
	
}

