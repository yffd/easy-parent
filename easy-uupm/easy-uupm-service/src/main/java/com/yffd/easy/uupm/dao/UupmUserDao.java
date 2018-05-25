package com.yffd.easy.uupm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.constants.CommonConstants;
import com.yffd.easy.framework.common.persist.mybatis.dao.MybatisCommonDao;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月10日 10时26分54秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Repository
public class UupmUserDao extends MybatisCommonDao<UupmUserEntity> {

	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_VO, userInfo);
		params.put(CommonConstants.PARAM_NAME_PAGE, pageParam);
		return this.selectPaginationByCustom(
				"findUserInfoList", 
				"findUserInfoCount", 
				params, pageParam, true);
	}
	
	public List<UupmUserInfoVo> findUserInfoList(UupmUserInfoVo userInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CommonConstants.PARAM_NAME_PROPS_MAP, userInfo);
		return this.selectListByCustom("findUserInfoList", params, true);
	}
	
}
