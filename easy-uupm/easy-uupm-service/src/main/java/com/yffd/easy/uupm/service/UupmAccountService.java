package com.yffd.easy.uupm.service;

import org.springframework.stereotype.Service;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.pojo.enums.UupmAccountTypeEnum;
import com.yffd.easy.uupm.pojo.enums.UupmStatusStyleEnum;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月7日 上午11:24:31 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmAccountService extends UupmBaseService<UupmAccountEntity> {

	public PageResult<UupmAccountEntity> findTenantAccount(UupmAccountEntity paramModel, PageParam paramPage, LoginInfo loginInfo) {
		paramModel.setAcntType(UupmAccountTypeEnum.TENANT.getCode());
		return this.findPage(paramModel, paramPage, loginInfo);
	}
	
	public int addTenantAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAcntType(UupmAccountTypeEnum.TENANT.getCode());
		entity.setAcntStatus(UupmStatusStyleEnum.ACTIVE.getCode());
		return this.save(entity, loginInfo);
	}
	
	public int addUserAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAcntType(UupmAccountTypeEnum.USER.getCode());
		entity.setAcntStatus(UupmStatusStyleEnum.ACTIVE.getCode());
		return this.save(entity, loginInfo);
	}
	
	
//	public UupmLoginInfoVo findLoginInfo(String accountId) {
//	UupmLoginInfoVo paramVo = new UupmLoginInfoVo();
//	paramVo.setAccountId(accountId);
//	UupmLoginInfoVo resultVo = this.uupmAccountDao.findLoginInfo(paramVo);
//	// 角色
//	String userCode = paramVo.getUserCode();
//	UupmUserRoleEntity userRoleModel = new UupmUserRoleEntity();
//	userRoleModel.setUserCode(userCode);
//	List<UupmUserRoleEntity> userRoleList = this.uupmUserRoleService.findRoles(userRoleModel);
//	if(null==userRoleList || userRoleList.size()==0) return null;
//	Set<String> roles = new HashSet<String>();
//	for(UupmUserRoleEntity model : userRoleList) {
//		roles.add(model.getRoleCode());
//	}
//	UupmLoginInfoVo returnVo = new UupmLoginInfoVo();
//	returnVo.setRoleCodes(roles);
//	// 资源
//	Set<String> roleCodeSet = new HashSet<String>();
//	for(UupmUserRoleEntity model : userRoleList) {
//		roleCodeSet.add(model.getRoleCode());
//	}
//	Set<String> resourceCodes = this.uupmRoleResourceService.findRsCode(roleCodeSet);
//	returnVo.setResourceCodes(resourceCodes);
//	// 机构
//	String orgCode = resultVo.getOrgCode();
//	UupmOrganizationEntity orgModel = new UupmOrganizationEntity();
//	orgModel.setOrgCode(orgCode);
//	returnVo.setOrgCode(orgCode);
//	returnVo.setOrgName(resultVo.getOrgName());
//	
//	return returnVo;
//}
}

