package com.yffd.easy.framework.common.persist.mybatis.dao;

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
public class MybatisDaoSupport {
	private static final Logger LOG = LoggerFactory.getLogger(MybatisDaoSupport.class);
	
    private SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Integer selectCountBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[selectCountBy]:" + sqlId);
        return this.getSqlSession().selectOne(sqlId, parameter);
	}

	public <T> List<T> selectListBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[selectListBy]:" + sqlId);
		return this.getSqlSession().selectList(sqlId, parameter);
	}

	public <T> T selectOneBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[selectOneBy]:" + sqlId);
		return this.getSqlSession().selectOne(sqlId, parameter);
	}

	public Integer insertBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[insertBy]:" + sqlId);
		return this.getSqlSession().insert(sqlId, parameter);
	}

	public Integer updateBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[updateBy]:" + sqlId);
		return this.getSqlSession().update(sqlId, parameter);
	}

	public Integer deleteBy(String sqlId, Object parameter) {
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[deleteBy]:" + sqlId);
		return this.getSqlSession().delete(sqlId, parameter);
	}
	
	/*************************************************************************************/
	/*************************************************************************************/
	
	public <T> PageResult<T> selectPageBy(String sqlId, String countSqlId, Map<String, Object> paramMap, PageParam pageParam) {
		if(null==paramMap) paramMap = new HashMap<String, Object>();
		if(null!=pageParam) {
			if(EasyStringCheckUtils.isEmpty(countSqlId)) throw CommonBizException.newInstance("countSqlId 不能为空");
			Integer totalRecord = this.getSqlSession().selectOne(countSqlId, paramMap); // 统计总记录数
			pageParam.setTotalRecord(totalRecord);
			paramMap.put("page", pageParam); // 根据页面传来的分页参数构造SQL分页参数
		}
		if(EasyStringCheckUtils.isEmpty(sqlId)) throw CommonBizException.newInstance("sqlId 不能为空");
		LOG.info("===sqlId[selectPageBy]:" + sqlId);
		List<T> recordList = this.getSqlSession().selectList(sqlId, paramMap); // 获取分页数据集
		return new PageResult<T>(pageParam, recordList);
	}
	
}

