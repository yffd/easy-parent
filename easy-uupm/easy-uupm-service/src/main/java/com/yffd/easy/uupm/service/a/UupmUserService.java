package com.yffd.easy.uupm.service.a;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.common.exception.CommonBizException;
import com.yffd.easy.framework.common.persist.mybatis.constants.MybatisConstants;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.entity.a.UupmAccountEntity;
import com.yffd.easy.uupm.entity.a.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年05月10日 14时34分16秒 <br/>
 * @author		 ZhangST <br/>
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmUserService extends UupmBaseService<UupmUserEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	
	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam, LoginInfo loginInfo) {
		this.beforeSetPropertiesForQuery(userInfo, loginInfo);
		return this.findUserInfoPage(userInfo, pageParam);
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserWithAccount(UupmUserEntity user, UupmAccountEntity account, LoginInfo loginInfo) {
		int result = this.save(user, loginInfo);
		this.uupmAccountService.addUserAccount(account, loginInfo);	// 生成用户账号
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delByUserCode(String userCode, LoginInfo loginInfo) {
		if(EasyStringCheckUtils.isEmpty(userCode)) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		UupmUserEntity model = new UupmUserEntity();
		model.setUserCode(userCode);
		int num = this.delete(model, loginInfo);	// 删除用户
		this.uupmUserRoleService.delByUserCode(userCode, loginInfo);	// 删除 用户-角色关联
		return num;
	}
	
	
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
