package com.yffd.easy.demo.javaconfig.spring;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description	数据源配置.
 * @Date		 2018年7月20日 上午9:39:32 <br/>
 * @author zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
@PropertySources({@PropertySource("classpath:/properties/jdbc.properties")})
public class DruidDataSourceJavaConfig {
	private static final Logger LOG = LoggerFactory.getLogger(DruidDataSourceJavaConfig.class);
	
	@Value("${jdbc.url}")
	private String dbUrl;
	
	@Value("${jdbc.username}")
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	
	@Value("${jdbc.initialSize}")
	private int initialSize;
	
	@Value("${jdbc.minIdle}")
	private int minIdle;
	
	@Value("${jdbc.maxActive}")
	private int maxActive;
	
	@Value("${jdbc.maxWait}")
	private int maxWait;
	
	@Value("${jdbc.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${jdbc.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	
	@Value("${jdbc.validationQuery}")
	private String validationQuery;
	
	@Value("${jdbc.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${jdbc.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${jdbc.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${jdbc.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	
	@Value("${jdbc.filters}")
	private String filters;
	
	@Value("{jdbc.connectionProperties}")
	private String connectionProperties;
	
	/** 数据源bean */
	public @Bean DataSource dataSource() {
		LOG.info("------------------dataSource初始化--------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		
		//configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			LOG.error("druid configuration initialization filter----->>:", e);
		}
		datasource.setConnectionProperties(connectionProperties);
		return datasource;
	}
	
	/** 注入 jdbcTemplate 配置bean */
	public @Bean JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}
	
	/** 注入 namedParameterJdbcTemplate 配置bean，参数名称绑定方式 */
	public @Bean NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
		return namedParameterJdbcTemplate;
	}
	
}

