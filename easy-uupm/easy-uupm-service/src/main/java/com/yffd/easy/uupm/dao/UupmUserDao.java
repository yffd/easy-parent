package com.yffd.easy.uupm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yffd.easy.framework.common.persist.mybatis.constants.MybatisConstants;
import com.yffd.easy.framework.common.persist.mybatis.dao.DefaultMybatisDao;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.entity.a.UupmUserEntity;
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
public class UupmUserDao extends DefaultMybatisDao<UupmUserEntity> {

	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_VO, userInfo);
		params.put(MybatisConstants.PARAM_NAME_PAGE, pageParam);
		return this.selectPageByCustom(
				"findUserInfoList", 
				"findUserInfoCount", 
				params, pageParam, true);
	}
	
	public List<UupmUserInfoVo> findUserInfoList(UupmUserInfoVo userInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, userInfo);
		return this.selectListByCustom("findUserInfoList", params, true);
	}
	
	public UupmUserInfoVo findUserInfo(UupmUserInfoVo userInfoVo) {
		return this.selectOneByCustom("findUserInfo", userInfoVo, true);
	}
	
}
