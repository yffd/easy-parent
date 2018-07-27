package com.yffd.easy.bcap.workflow.javaconfig;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月23日 下午5:02:40 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Configuration
@PropertySource("classpath:/properties/workflow.jdbc.properties")
public class AppDataSourceJavaConfig {
	private static final Logger LOG = LoggerFactory.getLogger(AppDataSourceJavaConfig.class);
	
	@Value("${workflow.jdbc.url}")
	private String dbUrl;
	
	@Value("${workflow.jdbc.username}")
	private String username;
	
	@Value("${workflow.jdbc.password}")
	private String password;
	
	@Value("${workflow.jdbc.driverClassName}")
	private String driverClassName;
	
	@Value("${workflow.jdbc.initialSize}")
	private int initialSize;
	
	@Value("${workflow.jdbc.minIdle}")
	private int minIdle;
	
	@Value("${workflow.jdbc.maxActive}")
	private int maxActive;
	
	@Value("${workflow.jdbc.maxWait}")
	private int maxWait;
	
	@Value("${workflow.jdbc.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${workflow.jdbc.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	
	@Value("${workflow.jdbc.validationQuery}")
	private String validationQuery;
	
	@Value("${workflow.jdbc.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${workflow.jdbc.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${workflow.jdbc.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${workflow.jdbc.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${workflow.jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	
	@Value("${workflow.jdbc.filters}")
	private String filters;
	
	@Value("{workflow.jdbc.connectionProperties}")
	private String connectionProperties;
	
	/** 数据源bean */
	@Bean(name = "wfDataSource")
	public DataSource wfDataSource() {
		LOG.info("------------------数据库配置初始化开始--------------");
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
		LOG.info("------------------数据库配置初始化结束--------------");
		return datasource;
	}
	
	/** 注入 jdbcTemplate 配置bean */
	@Bean(name = "wfJdbcTemplate")
	public JdbcTemplate wfJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(wfDataSource());
		return jdbcTemplate;
	}
	
	/** 注入 namedParameterJdbcTemplate 配置bean，参数名称绑定方式 */
	@Bean(name = "wfNamedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate wfNamedParameterJdbcTemplate() {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(wfDataSource());
		return namedParameterJdbcTemplate;
	}
	
	/** 配置JDBC事务管理器  */
	@Bean(name = "wfTxManager")
	public DataSourceTransactionManager wfDataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(wfDataSource());
		return dataSourceTransactionManager;
	}
	
}

