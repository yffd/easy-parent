package com.yffd.easy.framework.common.persist.dao.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  mybatis dao常用操作类.
 * @Date		 2018年4月18日 下午5:39:08 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class MybatisCommonCustomDaoSupport {
	private static final Logger LOG = LoggerFactory.getLogger(MybatisCommonCustomDaoSupport.class);
	
    private SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	protected String getStatement(String sqlId) {
		String name = this.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        return sb.toString();
	}
    
	protected Integer customSelectCountBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[selectCount]:" + sqlId);
        return this.getSqlSession().selectOne(sqlId, parameter);
	}

	protected <T> List<T> customSelectListBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[selectList]:" + sqlId);
		return this.getSqlSession().selectList(sqlId, parameter);
	}

	protected <T> T customSelectOneBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[selectOne]:" + sqlId);
		return this.getSqlSession().selectOne(sqlId, parameter);
	}

	protected Integer customInsertBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[insert]:" + sqlId);
		return this.getSqlSession().insert(sqlId, parameter);
	}

	protected Integer customUpdateBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[update]:" + sqlId);
		return this.getSqlSession().update(sqlId, parameter);
	}

	protected Integer customDeleteBy(String sqlId, Object parameter, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[delete]:" + sqlId);
		return this.getSqlSession().delete(sqlId, parameter);
	}
	
	/*************************************************************************************/
	/*************************************************************************************/
	
	protected <T> PageResult<T> customSelectPageBy(Map<String, Object> paramMap, PageParam pageParam, String sqlId, String countSqlId, boolean shortName) {
		if(EasyStringCheckUtils.isEmpty(sqlId) || EasyStringCheckUtils.isEmpty(countSqlId)) throw CommonBizException.newInstance("sqlId或 countSqlId 不能为空");
		List<T> recordList = this.customSelectRangeListBy(paramMap, pageParam, sqlId, countSqlId, shortName); // 获取分页数据集
		return new PageResult<T>(pageParam, recordList);
	}
	
	protected <T> List<T> customSelectRangeListBy(Map<String, Object> paramMap, PageParam pageParam, String sqlId, String countSqlId, boolean shortName) {
		if(null==paramMap) paramMap = new HashMap<String, Object>();
		if(null!=pageParam) {
			if(EasyStringCheckUtils.isEmpty(countSqlId)) throw CommonBizException.newInstance("countSqlId 不能为空");
			if(shortName) countSqlId = this.getStatement(countSqlId);
			Integer totalRecord = this.getSqlSession().selectOne(countSqlId, paramMap); // 统计总记录数
			pageParam.setTotalRecord(totalRecord);
			paramMap.put("page", pageParam); // 根据页面传来的分页参数构造SQL分页参数
		}
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		if(shortName) sqlId = this.getStatement(sqlId);
		LOG.info("===sqlId[selectPage]:" + sqlId);
		List<T> recordList = this.getSqlSession().selectList(sqlId, paramMap); // 获取分页数据集
		return recordList;
	}
	
	protected String makeOrderBy(String[] orderPropertyNames, String[] orderByTypes) {
		if(null==orderPropertyNames) return null;
		String defaultType = "asc";
		StringBuilder sb = new StringBuilder();
		if(null==orderByTypes) {
			for(String name : orderPropertyNames) {
				sb.append(name).append(" ").append(defaultType).append(", ");
			}
		} else {
			Integer len1 = orderPropertyNames.length;
			Integer len2 = orderByTypes.length;
			for(Integer i=0;i<len1;i++) {
				sb.append(orderPropertyNames[i]).append(" ");
				if(i<len2) {
					sb.append(orderByTypes[i]).append(", ");
				} else {
					sb.append(defaultType).append(", ");
				}
			}
		}
		return sb.length()>0 ? sb.substring(0, sb.lastIndexOf(",")) : null;
	}
	
	protected String makeOrderBy(String orderPropertyName, String orderByType) {
		String orderBy = null;
		if(!EasyStringCheckUtils.isEmpty(orderPropertyName)) {
			if(!EasyStringCheckUtils.isEmpty(orderByType)) {
				orderBy = orderPropertyName + " " + orderByType;
			} else {
				orderBy = orderPropertyName + " asc";
			}
		}
		return orderBy;
	}
	
}

