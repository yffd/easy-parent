package com.yffd.easy.framework.common.persist.mybatis.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.core.db.dialect.IDialect;
import com.yffd.easy.common.core.db.dialect.MySql5Dialect;
import com.yffd.easy.common.core.log.EasyCommonLogFormat;
import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.util.CommonUtils;
import com.yffd.easy.common.core.util.ValidUtils;

/**
 * @Description  mybatis分页拦截器--拦截查询.
 * @Date		 2017年8月4日 上午10:31:18 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationExecutorQueryInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PaginationExecutorQueryInterceptor.class);
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Object parameterObject = boundSql.getParameterObject();
        
        PageParam pageParam = null;
        // 提取分页对象
        if(null!=parameterObject && parameterObject instanceof HashMap) {
            Map<?,?> paramMapObject = (HashMap<?,?>) parameterObject;
            for(Object key : paramMapObject.keySet()) {
                if(paramMapObject.get(key) instanceof PageParam){
                    pageParam = (PageParam) paramMapObject.get(key);
                    break;
                }
            }
        }
        // PageParam对象存在，开始分页处理
//        if(null!=pageParam) {
//            Configuration configuration = mappedStatement.getConfiguration();
//            String dbType = configuration.getVariables().getProperty("dbtype");
//            if(ValidUtils.isEmpty(dbType)) {
//                throw new Exception("分页拦截器获取数据库类型失败");
//            }
//            
//            IDialect.Type databaseType = IDialect.Type.valueOf(dbType.toUpperCase());
//            IDialect dialect = null;
//            switch (databaseType) {
//                case MYSQL:
//                    dialect = new MySql5Dialect(); break;
//                case ORACLE:
//                    break;
//            }
//            
//            Long pageNum = pageParam.getPageNum();
//            Long pageLimit = pageParam.getPageLimit();
//            
//            // 校验当前页码值的有效范围
//            pageNum = pageParam.countPageNum(pageNum);
//            pageParam.setPageNum(pageNum); // 重新设值
//         	
//     		// 校验每页码值的有效范围
//     		pageLimit = pageParam.countPageLimit(pageLimit);
//     		pageParam.setPageLimit(pageLimit); // 重新设值
//     		
//     		pageParam.setPageStartRow(pageParam.countPageStartRow(pageNum, pageLimit));
//     		pageParam.setPageEndRow(pageParam.countPageEndRow(pageNum, pageLimit));
//
//            Long pageStartRow = pageParam.countPageStartRow(pageNum, pageLimit);
//            Long pageEndRow = pageParam.countPageEndRow(pageNum, pageLimit);
//            pageParam.setPageStartRow(pageStartRow);
//            pageParam.setPageEndRow(pageEndRow);
//            
//            String originalSql = boundSql.getSql().trim();
//            String newSql = dialect.getLimitString(originalSql, pageStartRow, pageEndRow);
//            BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, newSql.toString());
//            LoggerFormat.info(LOG, "分页拦截器生成的分页SQL : " + CommonUtils.getLineSeparator() + newBoundSql.getSql());
//            MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
//            invocation.getArgs()[0] = newMs;
//        }
        // 将执行权交给下一个拦截器
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
     * copyFromBoundSql:复制BoundSql对象. <br/>
     * @Date	2017年8月7日 上午11:01:33 <br/>
     * @author  zhangST
     * @param ms
     * @param boundSql
     * @param sql
     * @return
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * 
     * copyFromMappedStatement:复制MappedStatement对象. <br/>
     * @Date	2017年8月7日 上午11:01:23 <br/>
     * @author  zhangST
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        // builder.keyProperty(ms.getKeyProperty());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }
    
    public class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}

