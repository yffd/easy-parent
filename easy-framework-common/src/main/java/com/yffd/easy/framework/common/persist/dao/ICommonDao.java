package com.yffd.easy.framework.common.persist.dao;

import java.util.List;

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
public interface ICommonDao<E> {

	/**
	 * 查询：统计
	 * @Date	2018年4月20日 下午2:55:01 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	Integer selectCount(E entity);
	
	/**
	 * 查询：单条
	 * @Date	2018年4月20日 下午2:55:16 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	E selectOne(E entity);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月20日 下午2:55:27 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param orderBy
	 * @return
	 */
	List<E> selectListWithOrder(E entity, String orderBy);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月20日 下午2:55:40 <br/>
	 * @author  zhangST
	 * @param entity
	 * @param orderBy
	 * @param page
	 * @return
	 */
	PageResult<E> selectPageWithOrder(E entity, String orderBy, PageParam page);
	
	/**
	 * 查询：存在并且唯一校验
	 * @Date	2018年4月19日 上午11:12:57 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	Boolean exsistAndUnique(E entity);

	/**
	 * 查询：存在校验
	 * @Date	2018年4月20日 下午2:55:52 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	Boolean exsist(E entity);
	
	/**
	 * 添加：单条
	 * @Date	2018年4月19日 上午10:32:50 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	Integer insertOne(E entity);
	
	/**
	 * 添加：批量
	 * @Date	2018年4月19日 上午10:33:10 <br/>
	 * @author  zhangST
	 * @param entityList
	 * @return
	 */
	Integer insertList(List<E> entityList);
	
	/**
	 * 修改
	 * @Date	2018年4月19日 上午10:33:20 <br/>
	 * @author  zhangST
	 * @param entity			待修改“属性名-值”集合
	 * @param entityOld			条件-旧对象“属性名-值”集合
	 * @return
	 */
	Integer update(E entity, E entityOld);
	
	/**
	 * 删除
	 * @Date	2018年4月19日 上午10:35:20 <br/>
	 * @author  zhangST
	 * @param entity
	 * @return
	 */
	Integer delete(E entity);
	
}

