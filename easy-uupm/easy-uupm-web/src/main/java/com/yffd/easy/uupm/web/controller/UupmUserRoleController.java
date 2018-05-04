package com.yffd.easy.uupm.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.uupm.entity.UupmUserRoleEntity;
import com.yffd.easy.uupm.service.UupmUserRoleService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月06日 13时23分41秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/user/role")
public class UupmUserRoleController extends UupmCommonController {

	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	
	// 用户授权
	@RequestMapping(value="/saveUserRole", method=RequestMethod.POST)
	public RespData saveUserRole(HttpServletRequest req) {
		// TODO租户信息
		String tenantCode = req.getParameter("tenantCode");
		String userCode = req.getParameter("userCode");
		String roleCodes = req.getParameter("roleCodes");
		if(EasyStringCheckUtils.isEmpty(userCode) || EasyStringCheckUtils.isEmpty(roleCodes)) return this.errorAjax("参数错误");
		ArrayList<Map<String, String>> list = JSON.parseObject(roleCodes, new TypeReference<ArrayList<Map<String, String>>>(){});
		if(null==list || list.size()==0) return this.errorAjax("参数错误");
		List<UupmUserRoleEntity> modelList = new ArrayList<UupmUserRoleEntity>();
		for(Map<String, String> map : list) {
			String roleCode = map.get("roleCode");
			UupmUserRoleEntity model = new UupmUserRoleEntity();
			model.setTenantCode(tenantCode);
			model.setRoleCode(roleCode);
			model.setUserCode(userCode);
			modelList.add(model);
		}
		if(modelList.size()==0) return this.errorAjax("参数错误");
		
		this.uupmUserRoleService.saveUserRole(tenantCode, userCode, modelList);
		return this.successAjax();
	}
	
	// 用户授权
	@RequestMapping(value="/findRoleByUserCode", method=RequestMethod.POST)
	public RespData findRoleByUserCode(String tenantCode, String userCode) {
		if(EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数错误");
		UupmUserRoleEntity model = new UupmUserRoleEntity();
		model.setTenantCode(tenantCode);
		model.setUserCode(userCode);
		List<UupmUserRoleEntity> listResult = this.uupmUserRoleService.findList(model);
		return this.successAjax(listResult);
	}
	
}
