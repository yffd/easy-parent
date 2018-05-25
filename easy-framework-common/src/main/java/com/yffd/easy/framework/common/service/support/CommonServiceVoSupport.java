package com.yffd.easy.framework.common.service.support;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.persist.dao.support.MybatisCommonGenericDaoSupport;
import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.common.persist.mybatis.dao.MybatisCustomDao;
import com.yffd.easy.framework.common.pojo.converter.CommonPojoConverter;
import com.yffd.easy.framework.common.util.SpringBeanUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  service常用操作工具，VO与PO不一致情况下，会自动完成默认方式的转换.
 * @Date		 2018年4月25日 上午10:41:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CommonServiceVoSupport<E> {

	private MybatisCustomDao commonCustomDao = (MybatisCustomDao) SpringBeanUtils.getBean("mybatisCustomDao");
	
	private Class<E> entityClazz;
	private IMybatisCommonDao<E> bindDao;
	
	
	public CommonServiceVoSupport(IMybatisCommonDao<E> bindDao) {
		this.bindDao = bindDao;
	}

	protected Class<E> getEntityClazz() {
		if(null==entityClazz) {
			Class<E> daoGenericTypeClazz = (Class<E>) ((ParameterizedType) this.bindDao.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			entityClazz = daoGenericTypeClazz;
		}
		return entityClazz;
	}

	protected String getStatement(String sqlId) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.bindDao.getSqlNamespace()).append(".").append(sqlId);
        return sb.toString();
	}
	
	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_INSERT_ONE_BY}
	 * @Date	2018年5月9日 上午10:56:45 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	public <VO> Integer save(VO vo) {
		if(null==vo) return 0;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		return this.commonCustomDao.customInsertBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_INSERT_ONE_BY), bean);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_INSERT_LIST_BY}
	 * @Date	2018年5月9日 上午10:57:15 <br/>
	 * @author  zhangST
	 * @param voList
	 * @return
	 */
	public <VO> Integer save(List<VO> voList) {
		if(null==voList || voList.isEmpty()) return 0;
		List<?> beanList = CommonPojoConverter.getInstance().convert2BeanListWithSameProps(voList, getEntityClazz());
		return this.commonCustomDao.customInsertBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_INSERT_LIST_BY), beanList);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_UPDATE_BY}
	 * @Date	2018年5月9日 上午10:57:36 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param voOld
	 * @return
	 */
	public <VO> Integer update(VO vo, VO voOld) {
		if(null==vo || null==voOld) return 0;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Object beanOld = CommonPojoConverter.getInstance().convert2BeanWithSameProps(voOld, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(voOld, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY_OLD, beanOld);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		return this.commonCustomDao.customUpdateBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_UPDATE_BY), params);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_DELETE_BY}
	 * @Date	2018年5月9日 上午10:59:51 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	public <VO> Integer delete(VO vo) {
		if(null==vo) return 0;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(vo, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		return this.commonCustomDao.customDeleteBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_DELETE_BY), params);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年5月9日 上午11:00:51 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	public <VO> Integer findCount(VO vo) {
		if(null==vo) return 0;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(vo, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		return this.commonCustomDao.customSelectCountBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_SELECT_COUNT_BY), params);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_SELECT_ONE_BY}
	 * @Date	2018年5月9日 上午11:06:23 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	public <VO> E findOne(VO vo) {
		if(null==vo) return null;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(vo, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		
		Object result = this.commonCustomDao.customSelectOneBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_SELECT_ONE_BY), params);
		return (E) result;
//		return (VO) CommonPojoConverter.getInstance().convert2BeanWithSameProps(result, vo.getClass());
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_SELECT_LIST_BY}
	 * @Date	2018年5月9日 上午11:06:27 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param orderBy
	 * @return
	 */
	public <VO> List<E> findListWithOrder(VO vo, String orderBy) {
		if(null==vo) return null;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(vo, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		
		List<?> result = this.commonCustomDao.customSelectListBy(this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_SELECT_LIST_BY), params);
		return (List<E>) result;
//		return (List<VO>) CommonPojoConverter.getInstance().convert2BeanListWithSameProps(result, vo.getClass());
	}

	public <VO> List<E> findList(VO vo) {
		return this.findListWithOrder(vo, null);
	}

	public <VO> List<E> findAll() {
		return this.findListWithOrder(null, null);
	}

	public <VO> List<E> findAllWithOrder(String orderBy) {
		return this.findListWithOrder(null, orderBy);
	}

	/**
	 * sqlid : {@link MybatisCommonGenericDaoSupport#SQL_ID_SELECT_LIST_BY}</br>
	 * sqlid count : {@link MybatisCommonGenericDaoSupport#SQL_ID_SELECT_COUNT_BY}
	 * @Date	2018年5月9日 上午11:37:16 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param orderBy
	 * @param page
	 * @return
	 */
	public <VO> PageResult<E> findPageWithOrder(VO vo, String orderBy, PageParam page) {
		if(null==vo || null==page) return null;
		Object bean = CommonPojoConverter.getInstance().convert2BeanWithSameProps(vo, getEntityClazz());
		Map<String, Object> diffPropsMap = CommonPojoConverter.getInstance().convert2MapWithDiffProps(vo, getEntityClazz());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_ENTITY, bean);
		params.put(MybatisCommonGenericDaoSupport.PARAM_NAME_PROPS_MAP, diffPropsMap);
		
		PageResult<?> result = this.commonCustomDao.customSelectPaginationBy(
				this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_SELECT_LIST_BY), 
				this.getStatement(MybatisCommonGenericDaoSupport.SQL_ID_SELECT_COUNT_BY),
				params, page);
		return (PageResult<E>) result;
//		return (PageResult<VO>) CommonPojoConverter.getInstance().convert2PaginationWithSameProps(result, vo.getClass());
	}

	public <VO> PageResult<E> findPage(VO vo, PageParam page) {
		return this.findPageWithOrder(vo, null, page);
	}

	public <VO> Boolean exsistAndUnique(VO vo) {
		Integer count = this.findCount(vo);
		return count==0;
	}

	public <VO> Boolean exsist(VO vo) {
		Integer count = this.findCount(vo);
		return count>0;
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

