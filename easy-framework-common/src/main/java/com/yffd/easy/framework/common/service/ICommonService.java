package com.yffd.easy.framework.common.service;

import java.util.List;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月24日 下午6:22:09 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public interface ICommonService<POJO> {
	
	/**
	 * 添加：单条
	 * @Date	2018年4月19日 上午10:32:50 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	Integer save(POJO pojo, LoginInfo loginInfo);
	
	/**
	 * 添加：批量
	 * @Date	2018年4月19日 上午10:33:10 <br/>
	 * @author  zhangST
	 * @param entityList
	 * @param loginInfo
	 * @return
	 */
	Integer save(List<POJO> entityList, LoginInfo loginInfo);
	
	/**
	 * 修改
	 * @Date	2018年4月19日 上午10:33:20 <br/>
	 * @author  zhangST
	 * @param pojo				待修改“属性名-值”集合
	 * @param entityOld				条件-旧对象“属性名-值”集合
	 * @param loginInfo
	 * @return
	 */
	Integer update(POJO pojo, POJO entityOld, LoginInfo loginInfo);
	
	/**
	 * 删除
	 * @Date	2018年4月19日 上午10:35:20 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	Integer delete(POJO pojo, LoginInfo loginInfo);
	
	/**
	 * 查询：统计
	 * @Date	2018年4月20日 下午2:55:01 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	Integer findCount(POJO pojo, LoginInfo loginInfo);
	
	/**
	 * 查询：单条
	 * @Date	2018年4月20日 下午2:55:16 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	POJO findOne(POJO pojo, LoginInfo loginInfo);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月20日 下午2:55:27 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param orderBy
	 * @param loginInfo
	 * @return
	 */
	List<POJO> findListWithOrder(POJO pojo, String orderBy, LoginInfo loginInfo);
	
	/**
	 * 查询：列表
	 * @Date	2018年4月25日 下午1:44:01 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	List<POJO> findList(POJO pojo, LoginInfo loginInfo);
	
	/**
	 * 查询：全部
	 * @Date	2018年4月25日 下午1:49:38 <br/>
	 * @author  zhangST
	 * @return
	 */
	List<POJO> findAll();
	
	/**
	 * 查询：全部
	 * @Date	2018年4月25日 下午1:49:43 <br/>
	 * @author  zhangST
	 * @param orderBy
	 * @return
	 */
	List<POJO> findAllWithOrder(String orderBy);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月20日 下午2:55:40 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param orderBy
	 * @param page
	 * @param loginInfo
	 * @return
	 */
	PageResult<POJO> findPageWithOrder(POJO pojo, String orderBy, PageParam page, LoginInfo loginInfo);
	
	/**
	 * 查询：分页
	 * @Date	2018年4月25日 下午1:46:27 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param page
	 * @param loginInfo
	 * @return
	 */
	PageResult<POJO> findPage(POJO pojo, PageParam page, LoginInfo loginInfo);
	
	/**
	 * 查询：存在并且唯一校验
	 * @Date	2018年4月19日 上午11:12:57 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	Boolean exsistAndUnique(POJO pojo, LoginInfo loginInfo);

	/**
	 * 查询：存在校验
	 * @Date	2018年4月20日 下午2:55:52 <br/>
	 * @author  zhangST
	 * @param pojo
	 * @param loginInfo
	 * @return
	 */
	Boolean exsist(POJO pojo, LoginInfo loginInfo);

}

