package com.yffd.easy.uupm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.persist.mybatis.dao.IMybatisCommonDao;
import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.uupm.dao.UupmAccountDao;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.entity.UupmUserRoleEntity;
import com.yffd.easy.uupm.pojo.vo.UupmLoginInfoVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		2018年04月06日 13时40分09秒 <br/>
 * @author		ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Service
public class UupmAccountService extends UupmBaseService<UupmAccountEntity> {

	@Override
	protected IMybatisCommonDao<UupmAccountEntity> getBindDao() {
		return this.uupmAccountDao;
	}

	@Autowired
	private UupmAccountDao uupmAccountDao;
	
	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	@Autowired
	private UupmRoleResourceService uupmRoleResourceService;
	@Autowired
	private UupmOrganizationService uupmOrganizationService;
	
	public PageResult<UupmAccountEntity> findTenantAccount(UupmAccountEntity paramModel, PageParam paramPage, LoginInfo loginInfo) {
		paramModel.setAccountType("tenant");
		return this.findPage(paramModel, paramPage, loginInfo);
	}
	
	public int addTenantAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAccountType("tenant");
		return this.save(entity, loginInfo);
	}
	
	public int addDefaultAccount(UupmAccountEntity entity, LoginInfo loginInfo) {
		entity.setAccountType("default");
		return this.save(entity, loginInfo);
	}
	
//	public UupmLoginInfoVo findLoginInfo(String accountId) {
//		UupmLoginInfoVo paramVo = new UupmLoginInfoVo();
//		paramVo.setAccountId(accountId);
//		UupmLoginInfoVo resultVo = this.uupmAccountDao.findLoginInfo(paramVo);
//		// 角色
//		String userCode = paramVo.getUserCode();
//		UupmUserRoleEntity userRoleModel = new UupmUserRoleEntity();
//		userRoleModel.setUserCode(userCode);
//		List<UupmUserRoleEntity> userRoleList = this.uupmUserRoleService.findRoles(userRoleModel);
//		if(null==userRoleList || userRoleList.size()==0) return null;
//		Set<String> roles = new HashSet<String>();
//		for(UupmUserRoleEntity model : userRoleList) {
//			roles.add(model.getRoleCode());
//		}
//		UupmLoginInfoVo returnVo = new UupmLoginInfoVo();
//		returnVo.setRoleCodes(roles);
//		// 资源
//		Set<String> roleCodeSet = new HashSet<String>();
//		for(UupmUserRoleEntity model : userRoleList) {
//			roleCodeSet.add(model.getRoleCode());
//		}
//		Set<String> resourceCodes = this.uupmRoleResourceService.findRsCode(roleCodeSet);
//		returnVo.setResourceCodes(resourceCodes);
//		// 机构
//		String orgCode = resultVo.getOrgCode();
//		UupmOrganizationEntity orgModel = new UupmOrganizationEntity();
//		orgModel.setOrgCode(orgCode);
//		returnVo.setOrgCode(orgCode);
//		returnVo.setOrgName(resultVo.getOrgName());
//		
//		return returnVo;
//	}
	
}
