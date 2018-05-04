package com.yffd.easy.framework.common.service;

import java.util.List;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 下午6:22:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICommonBaseService<VO> {
	
	/**
	 * 添加：单条
	 * @Date	2018年4月19日 上午10:32:50 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	Integer save(VO vo);
	
	/**
	 * 添加：批量
	 * @Date	2018年4月19日 上午10:33:10 <br/>
	 * @author  zhangST
	 * @param voList
	 * @return
	 */
	Integer save(List<VO> voList);
	
	/**
	 * 修改
	 * @Date	2018年4月19日 上午10:33:20 <br/>
	 * @author  zhangST
	 * @param vo				待修改“属性名-值”集合
	 * @param voOld				条件-旧对象“属性名-值”集合
	 * @return
	 */
	Integer update(VO vo, VO voOld);
	
	/**
	 * 删除
	 * @Date	2018年4月19日 上午10:35:20 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	Integer delete(VO vo);
	
	/**
	 * 查询：统计
	 * @Date	2018年4月20日 下午2:55:01 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	Integer findCount(VO vo);
	
	/**
	 * 查询：单条
	 * @Date	2018年4月20日 下午2:55:16 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	VO findOne(VO vo);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月20日 下午2:55:27 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param orderBy
	 * @return
	 */
	List<VO> findListWithOrder(VO vo, String orderBy);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月25日 下午1:44:01 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	List<VO> findList(VO vo);
	
	/**
	 * 查询：全部
	 * @Date	2018年4月25日 下午1:49:38 <br/>
	 * @author  zhangST
	 * @return
	 */
	List<VO> findAll();
	
	/**
	 * 查询：全部
	 * @Date	2018年4月25日 下午1:49:43 <br/>
	 * @author  zhangST
	 * @param orderBy
	 * @return
	 */
	List<VO> findAllWithOrder(String orderBy);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月20日 下午2:55:40 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param orderBy
	 * @param page
	 * @return
	 */
	PageResult<VO> findPageWithOrder(VO vo, String orderBy, PageParam page);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月25日 下午1:46:27 <br/>
	 * @author  zhangST
	 * @param vo
	 * @param page
	 * @return
	 */
	PageResult<VO> findPage(VO vo, PageParam page);
	
	/**
	 * 查询：存在并且唯一校验
	 * @Date	2018年4月19日 上午11:12:57 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	Boolean exsistAndUnique(VO vo);

	/**
	 * 查询：存在校验
	 * @Date	2018年4月20日 下午2:55:52 <br/>
	 * @author  zhangST
	 * @param vo
	 * @return
	 */
	Boolean exsist(VO vo);
	
}

