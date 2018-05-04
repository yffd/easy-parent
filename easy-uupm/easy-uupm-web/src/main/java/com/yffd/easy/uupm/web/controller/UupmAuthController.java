package com.yffd.easy.uupm.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;
import com.yffd.easy.uupm.entity.UupmUserRoleEntity;
import com.yffd.easy.uupm.service.UupmRoleResourceService;
import com.yffd.easy.uupm.service.UupmTenantResourceService;
import com.yffd.easy.uupm.service.UupmUserRoleService;
import com.yffd.easy.uupm.web.common.UupmCommonController;


/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年03月15日 10时02分10秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/auth")
public class UupmAuthController extends UupmCommonController {

	@Autowired
	private UupmRoleResourceService uupmRoleResourceService;
	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	@Autowired
	private UupmTenantResourceService uupmTenantResourceService;
	
//	// 角色授权
//	@RequestMapping(value="/saveRoleResource", method=RequestMethod.POST)
//	public RespModel saveRoleResource(HttpServletRequest req) {
//		String roleCode = req.getParameter("roleCode");
//		String rsCodes = req.getParameter("rsCodes");
//		if(EasyStringCheckUtils.isEmpty(roleCode) || EasyStringCheckUtils.isEmpty(rsCodes)) return this.errorAjax("参数错误");
//		ArrayList<Map<String, String>> list = JSON.parseObject(rsCodes, new TypeReference<ArrayList<Map<String, String>>>(){});
//		if(null==list || list.size()==0) return this.errorAjax("参数错误");
//		List<UupmRoleResourceModel> modelList = new ArrayList<UupmRoleResourceModel>();
//		for(Map<String, String> map : list) {
//			String rsType = map.get("rsType");
//			// 只对菜单（M）、操作（O）类型的资源处理，跳过资源为 应用系统（A）
//			if("A".equals(rsType)) continue;
//			String appCode = map.get("appCode");
//			String rsCode = map.get("rsCode");
//			UupmRoleResourceModel model = new UupmRoleResourceModel();
//			model.setRoleCode(roleCode);
//			model.setAppCode(appCode);
//			model.setRsCode(rsCode);
//			modelList.add(model);
//		}
//		if(modelList.size()==0) return this.errorAjax("参数错误");
//		
//		this.uupmRoleResourceService.saveRoleResource(roleCode, modelList, null);
//		return this.successAjax();
//	}
//	// 角色授权
//	@RequestMapping(value="/findResourceByRoleCode", method=RequestMethod.POST)
//	public RespModel findResourceByRoleCode(String roleCode) {
//		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("roleCode", roleCode);
//		List<UupmRoleResourceModel> listResult = this.uupmRoleResourceService.findList(paramMap);
//		return this.successAjax(listResult);
//	}
	
//	// 用户授权
//	@RequestMapping(value="/saveUserRole", method=RequestMethod.POST)
//	public RespModel saveUserRole(HttpServletRequest req) {
//		String userCode = req.getParameter("userCode");
//		String roleCodes = req.getParameter("roleCodes");
//		if(EasyStringCheckUtils.isEmpty(userCode) || EasyStringCheckUtils.isEmpty(roleCodes)) return this.errorAjax("参数错误");
//		ArrayList<Map<String, String>> list = JSON.parseObject(roleCodes, new TypeReference<ArrayList<Map<String, String>>>(){});
//		if(null==list || list.size()==0) return this.errorAjax("参数错误");
//		List<UupmUserRoleModel> modelList = new ArrayList<UupmUserRoleModel>();
//		for(Map<String, String> map : list) {
//			String roleCode = map.get("roleCode");
//			UupmUserRoleModel model = new UupmUserRoleModel();
//			model.setRoleCode(roleCode);
//			model.setUserCode(userCode);
//			modelList.add(model);
//		}
//		if(modelList.size()==0) return this.errorAjax("参数错误");
//		
//		this.uupmUserRoleService.saveUserRole(userCode, modelList, null);
//		return this.successAjax();
//	}
//	// 用户授权
//	@RequestMapping(value="/findRoleByUserCode", method=RequestMethod.POST)
//	public RespModel findRoleByUserCode(String userCode) {
//		if(EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数错误");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("userCode", userCode);
//		List<UupmUserRoleModel> listResult = this.uupmUserRoleService.findList(paramMap);
//		return this.successAjax(listResult);
//	}
	
//	// 租户授权
//	@RequestMapping(value="/saveTenantResource", method=RequestMethod.POST)
//	public RespModel saveTenantResource(HttpServletRequest req) {
//		String tenantCode = req.getParameter("tenantCode");
//		String rsCodes = req.getParameter("rsCodes");
//		if(EasyStringCheckUtils.isEmpty(tenantCode) || EasyStringCheckUtils.isEmpty(rsCodes)) return this.errorAjax("参数错误");
//		ArrayList<Map<String, String>> list = JSON.parseObject(rsCodes, new TypeReference<ArrayList<Map<String, String>>>(){});
//		if(null==list || list.size()==0) return this.errorAjax("参数错误");
//		List<UupmTenantResourceModel> modelList = new ArrayList<UupmTenantResourceModel>();
//		for(Map<String, String> map : list) {
//			String rsCode = map.get("rsCode");
//			UupmTenantResourceModel model = new UupmTenantResourceModel();
//			model.setTenantCode(tenantCode);
//			model.setRsCode(rsCode);
//			modelList.add(model);
//		}
//		if(modelList.size()==0) return this.errorAjax("参数错误");
//		
//		this.uupmTenantResourceService.addList(modelList, null);
//		return this.successAjax();
//	}
//	
//	// 租户授权
//	@RequestMapping(value="/findResourceByTenantCode", method=RequestMethod.POST)
//	public RespModel findResourceByTenantCode(String tenantCode) {
//		if(EasyStringCheckUtils.isEmpty(tenantCode)) return this.errorAjax("参数错误");
//		UupmTenantResourceModel model = new UupmTenantResourceModel();
//		model.setTenantCode(tenantCode);
//		List<UupmTenantResourceModel> listResult = this.uupmTenantResourceService.findList(model , null);
//		return this.successAjax(listResult);
//	}
	
}
