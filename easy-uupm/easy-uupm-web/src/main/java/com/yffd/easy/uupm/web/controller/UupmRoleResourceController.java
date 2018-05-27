package com.yffd.easy.uupm.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.framework.pojo.vo.DataGridVo;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;
import com.yffd.easy.uupm.service.UupmRoleResourceService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月05日 16时49分36秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/role/resource")
public class UupmRoleResourceController extends UupmBaseController {

	@Autowired
	private UupmRoleResourceService uupmRoleResourceService;
	
	// 某租户下的某角色资源授权
	@RequestMapping(value="/saveRoleResource", method=RequestMethod.POST)
	public RespData saveRoleResource(HttpServletRequest req) {
		String roleCode = req.getParameter("roleCode");
		String rsCodes = req.getParameter("rsCodes");
		if(EasyStringCheckUtils.isEmpty(roleCode) || EasyStringCheckUtils.isEmpty(rsCodes)) return this.errorAjax("参数错误");
		List<String> rsCodesList = JSON.parseArray(rsCodes, String.class);
		if(null==rsCodesList || rsCodesList.size()==0) return this.errorAjax("参数错误");
		int result = this.uupmRoleResourceService.saveRoleResource(roleCode, rsCodesList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
	}
	
	// 查找某租户下的某角色已拥有的资源编号
	@RequestMapping(value="/findRsCodesByRoleCode", method=RequestMethod.POST)
	public RespData findRsCodesByRoleCode(String roleCode) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uupmRoleResourceService.findRsCodesByRoleCode(roleCode, getLoginInfo());
		return this.successAjax(result);
	}
		
//	// 角色授权
//	@RequestMapping(value="/findRoleResource", method=RequestMethod.POST)
//	public RespData findRoleResource(String tenantCode, String roleCode) {
//		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
//		UupmRoleResourceEntity model = new UupmRoleResourceEntity();
//		model.setTenantCode(tenantCode);
//		model.setRoleCode(roleCode);
//		List<UupmRoleResourceEntity> listResult = this.uupmRoleResourceService.findList(model);
//		return this.successAjax(listResult);
//	}
	
}
