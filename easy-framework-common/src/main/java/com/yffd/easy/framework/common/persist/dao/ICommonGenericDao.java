package com.yffd.easy.framework.common.persist.dao;

import java.util.List;
import java.util.Map;

import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  持久化常用操作接口.
 * @Date		 2018年4月18日 下午5:38:43 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICommonGenericDao<E> extends ICommonBaseDao<E> {
	
	/**
	 * 修改
	 * @Date	2018年4月19日 上午10:33:20 <br/>
	 * @author  zhangST
	 * @param entity			待修改“属性名-值”集合
	 * @param entityOld			条件-旧对象“属性名-值”集合
	 * @param propsMap			条件-指定“属性名-值”对集合
	 * @return
	 */
	Integer update(E entity, E entityOld, Map<String, Object> propsMap);
	
	/**
	 * 删除
	 * @Date	2018年4月19日 上午10:35:20 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	Integer delete(E entity, Map<String, Object> propsMap);
	
	/**
	 * 查询：统计
	 * @Date	2018年4月20日 下午2:55:01 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	Integer findCount(E entity, Map<String, Object> propsMap);
	
	/**
	 * 查询：单条
	 * @Date	2018年4月20日 下午2:55:16 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	E findOne(E entity, Map<String, Object> propsMap);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月20日 下午2:55:27 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @return
	 */
	List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String orderBy);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月28日 下午5:26:22 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderPropertyNames
	 * @param orderByTypes
	 * @return
	 */
	List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String[] orderPropertyNames, String[] orderByTypes);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月28日 下午5:26:22 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderPropertyName
	 * @param orderByType
	 * @return
	 */
	List<E> findListWithOrder(E entity, Map<String, Object> propsMap, String orderPropertyName, String orderByType);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月28日 下午5:26:22 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	List<E> findList(E entity, Map<String, Object> propsMap);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月20日 下午2:55:40 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderBy
	 * @param page
	 * @return
	 */
	PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String orderBy, PageParam page);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月28日 下午5:26:10 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderPropertyNames
	 * @param orderByTypes
	 * @param page
	 * @return
	 */
	PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String[] orderPropertyNames, String[] orderByTypes, PageParam page);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月28日 下午5:26:10 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param orderPropertyName
	 * @param orderByType
	 * @param page
	 * @return
	 */
	PageResult<E> findPageWithOrder(E entity, Map<String, Object> propsMap, String orderPropertyName, String orderByType, PageParam page);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月28日 下午5:26:10 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @param page
	 * @return
	 */
	PageResult<E> findPage(E entity, Map<String, Object> propsMap, PageParam page);
	
	/**
	 * 查询：存在并且唯一校验
	 * @Date	2018年4月19日 上午11:12:57 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	Boolean exsistAndUnique(E entity, Map<String, Object> propsMap);

	/**
	 * 查询：存在校验
	 * @Date	2018年4月20日 下午2:55:52 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param propsMap
	 * @return
	 */
	Boolean exsist(E entity, Map<String, Object> propsMap);
	
	///////////////// 属性 ////////////////////////////////////
	
	Integer updateByProperty(E entity, String propertyName, Object value);
	Integer deleteByProperty(String propertyName, Object value);
	Integer findCountByProperty(String propertyName, Object value);
	
	E findOneByProperty(String propertyName, Object value);
	
	List<E> findListWithOrderByProperty(String propertyName, Object value, String orderBy);
	List<E> findListWithOrderByProperty(String propertyName, Object value, String[] orderPropertyNames, String[] orderByTypes);
	List<E> findListWithOrderByProperty(String propertyName, Object value, String orderPropertyName, String orderByType);
	List<E> findListByProperty(String propertyName, Object value);
	
	PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String orderBy, PageParam page);
	PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String[] orderPropertyNames, String[] orderByTypes, PageParam page);
	PageResult<E> findPageWithOrderByProperty(String propertyName, Object value, String orderPropertyName, String orderByType, PageParam page);
	PageResult<E> findPageByProperty(String propertyName, Object value, PageParam page);
	
}

