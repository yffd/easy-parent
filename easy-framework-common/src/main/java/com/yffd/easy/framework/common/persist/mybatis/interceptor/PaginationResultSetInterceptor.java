package com.yffd.easy.framework.common.persist.mybatis.interceptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.core.db.dialect.IDialect;
import com.yffd.easy.common.core.db.dialect.MySql5Dialect;
import com.yffd.easy.common.core.log.EasyCommonLogFormat;
import com.yffd.easy.common.core.util.CommonUtils;
import com.yffd.easy.framework.pojo.page.PageParam;

/**
 * @Description  mybatis拦截器--拦截结果.
 * @Date		 2017年8月4日 上午10:35:50 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Intercepts({@Signature(
        type= ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class PaginationResultSetInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PaginationResultSetInterceptor.class);
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) invocation.getTarget();
        MetaObject metaResultSetHandler = MetaObject.forObject(resultSetHandler, new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        ParameterHandler parameterHandler = (ParameterHandler) metaResultSetHandler.getValue("parameterHandler");
        Object parameterObject = parameterHandler.getParameterObject();
        
        PageParam pageParam = null;
        // 提取分页对象
        if(null!=parameterObject && parameterObject instanceof HashMap) {
            Map<?,?> paramMapObject = (HashMap<?,?>) parameterObject;
            for(Object key : paramMapObject.keySet()) {
                if(paramMapObject.get(key).getClass().getName().equals(PageParam.class.getName())) {
//                if(paramMapObject.get(key) instanceof PageParam) {
                    pageParam = (PageParam) paramMapObject.get(key);
                    break;
                }
            }
        }
        
        // PageParam对象存在，开始分页处理
//        if(null!=pageParam) {
//            if(pageParam.isAutoCount() && pageParam.getTotalRecord()<1) {
//                LoggerFormat.info(LOG, "分页拦截器自动计算总条数开启");
//            } else {
//                LoggerFormat.info(LOG, "分页拦截器自动计算总条数关闭");
//                return invocation.proceed();
//            }
//            
//            BoundSql boundSql = (BoundSql) metaResultSetHandler.getValue("parameterHandler.boundSql");
//            Configuration configuration = (Configuration) metaResultSetHandler.getValue("configuration");
//            
//            String dbtype = configuration.getVariables().getProperty("dbtype");
//            if(null==dbtype || "".equals(dbtype.trim())) {
//                throw new Exception("数据库类型位指定");
//            }
//            IDialect.Type databaseType = IDialect.Type.valueOf(dbtype.toUpperCase());
//            IDialect dialect = null;
//            switch (databaseType) {
//                case MYSQL:
//                    dialect = new MySql5Dialect(); break;
//                case ORACLE:
//                    break;
//            }
//            
//            String originalSql = boundSql.getSql();
//            String conuntSql = dialect.getCountString(originalSql);// 修改sql，用于返回总记录数
//            LoggerFormat.info(LOG, "分页拦截器生成的统计SQL :"  + CommonUtils.getLineSeparator() + conuntSql);
//            
//            Long totalRecord = getTotalRecord(configuration, parameterHandler, conuntSql);
//            pageParam.setTotalRecord(totalRecord);  //设置总记录数和总页数
//        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }
    
    /**
     * 
     * getTotalRecord:获取总记录数. <br/>
     * @Date	2017年8月7日 上午11:01:08 <br/>
     * @author  zhangST
     * @param configuration
     * @param parameterHandler
     * @param sql
     * @return
     */
    private Long getTotalRecord(Configuration configuration, ParameterHandler parameterHandler, String sql) {
        Long result = 0L;
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            parameterHandler.setParameters(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            EasyCommonLogFormat.error(LOG, "分页拦截器统计总条数查询失败", e);
        } finally {
            session.close();
        }

        return result;
    }
}

