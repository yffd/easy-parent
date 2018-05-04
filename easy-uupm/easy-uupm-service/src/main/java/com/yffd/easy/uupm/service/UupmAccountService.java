package com.yffd.easy.uupm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yffd.easy.framework.common.dao.bak.BakICommonExtDao;
import com.yffd.easy.framework.common.service.impl.CommonSimpleServiceImpl;
import com.yffd.easy.uupm.dao.UupmAccountDao;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;
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
public class UupmAccountService extends CommonSimpleServiceImpl<UupmAccountEntity> {

	@Override
	protected BakICommonExtDao<UupmAccountEntity> getBindDao() {
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
	
	public int addAccount(UupmAccountEntity model, String encryptPwd, String salt) {
		model.setSalt(salt);
		model.setAccountPwd(encryptPwd);
		return this.getBindDao().save(model);
	}
	
	public UupmLoginInfoVo findLoginInfo(String accountId) {
		UupmLoginInfoVo paramVo = new UupmLoginInfoVo();
		paramVo.setAccountId(accountId);
		UupmLoginInfoVo resultVo = this.uupmAccountDao.findLoginInfo(paramVo);
		// 角色
		String userCode = paramVo.getUserCode();
		UupmUserRoleEntity userRoleModel = new UupmUserRoleEntity();
		userRoleModel.setUserCode(userCode);
		List<UupmUserRoleEntity> userRoleList = this.uupmUserRoleService.findRoles(userRoleModel);
		if(null==userRoleList || userRoleList.size()==0) return null;
		Set<String> roles = new HashSet<String>();
		for(UupmUserRoleEntity model : userRoleList) {
			roles.add(model.getRoleCode());
		}
		UupmLoginInfoVo returnVo = new UupmLoginInfoVo();
		returnVo.setRoleCodes(roles);
		// 资源
		List<UupmRoleResourceEntity> roleResourceList = this.uupmRoleResourceService.findResources(userRoleList);
		if(null==roleResourceList ||roleResourceList.size()==0) return null;
		Set<String> resources = new HashSet<String>();
		for(UupmRoleResourceEntity model : roleResourceList) {
			resources.add(model.getRsCode());
		}
		returnVo.setResourceCodes(resources);
		// 机构
		String orgCode = resultVo.getOrgCode();
		UupmOrganizationEntity orgModel = new UupmOrganizationEntity();
		orgModel.setOrgCode(orgCode);
		String orgNamePath = this.uupmOrganizationService.findParentNamePath(orgModel);
		returnVo.setOrgCode(orgCode);
		returnVo.setOrgName(resultVo.getOrgName());
		returnVo.setDataPath(orgNamePath);
		
		return returnVo;
	}
	
	
}
