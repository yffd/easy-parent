package com.yffd.easy.uupm.service;

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
import com.yffd.easy.uupm.pojo.entity.UupmAccountEntity;
import com.yffd.easy.uupm.pojo.entity.UupmSecRelationRoleUser;
import com.yffd.easy.uupm.pojo.entity.UupmSecUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 上午11:24:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmSecUserService extends UupmBaseService<UupmSecUserEntity> {

	@Autowired
	private UupmAccountService uupmAccountService;
	@Autowired
	private UupmSecRelationRoleUserService uupmSecRelationRoleUserService;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserWithAccount(UupmSecUserEntity user, UupmAccountEntity account, LoginInfo loginInfo) {
		int result = this.save(user, loginInfo);
		this.uupmAccountService.addUserAccount(account, loginInfo);	// 生成用户账号
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delCascadeByUser(UupmSecUserEntity user, LoginInfo loginInfo) {
		if (null == user || EasyStringCheckUtils.isEmpty(user.getTtCode())
				|| EasyStringCheckUtils.isEmpty(user.getUserCode())) throw CommonBizException.BIZ_PARAMS_IS_EMPTY();
		
		UupmSecUserEntity result = this.findOne(user, loginInfo);
		if (null != result && !EasyStringCheckUtils.isEmpty(result.getAccountId())) {
			// 删除账号
			UupmAccountEntity account = new UupmAccountEntity();
			account.setId(result.getAccountId());
			this.uupmAccountService.delete(account, loginInfo);
		}
		// 删除用户
		int num = this.delete(user, loginInfo);	
		// 删除关联关系：用户-角色
		UupmSecRelationRoleUser relationRole = new UupmSecRelationRoleUser();
		relationRole.setTtCode(user.getTtCode());
		relationRole.setUserCode(user.getUserCode());
		this.uupmSecRelationRoleUserService.delete(relationRole, loginInfo);
		return num;
	}
	
//	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam, LoginInfo loginInfo) {
//		this.beforeSetPropertiesForQuery(userInfo, loginInfo);
//		return this.findUserInfoPage(userInfo, pageParam);
//	}
//	
//	public PageResult<UupmUserInfoVo> findUserInfoPage(UupmUserInfoVo userInfo, PageParam pageParam) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MybatisConstants.PARAM_NAME_VO, userInfo);
//		params.put(MybatisConstants.PARAM_NAME_PAGE, pageParam);
//		return this.selectPageByCustom(
//				"findUserInfoList", 
//				"findUserInfoCount", 
//				params, pageParam, true);
//	}
	
//	public List<UupmUserInfoVo> findUserInfoList(UupmUserInfoVo userInfo, LoginInfo loginInfo) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MybatisConstants.PARAM_NAME_PROPS_MAP, userInfo);
//		return this.selectListByCustom("findUserInfoList", userInfo, true);
//	}
//	
//	public UupmUserInfoVo findUserInfo(UupmUserInfoVo userInfoVo) {
//		return this.selectOneByCustom("findUserInfo", userInfoVo, true);
//	}
}

