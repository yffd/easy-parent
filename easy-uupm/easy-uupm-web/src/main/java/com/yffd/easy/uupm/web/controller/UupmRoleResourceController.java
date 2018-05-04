package com.yffd.easy.uupm.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.framework.web.view.vo.DataGridVO;
import com.yffd.easy.uupm.entity.UupmRoleResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;
import com.yffd.easy.uupm.service.UupmRoleResourceService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

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
public class UupmRoleResourceController extends UupmCommonController {

	@Autowired
	private UupmRoleResourceService uupmRoleResourceService;
	
	// 角色授权
	@RequestMapping(value="/saveRoleResource", method=RequestMethod.POST)
	public RespData saveRoleResource(HttpServletRequest req) {
		String roleCode = req.getParameter("roleCode");
		String resource = req.getParameter("resource");
		if(EasyStringCheckUtils.isEmpty(roleCode) || EasyStringCheckUtils.isEmpty(resource)) return this.errorAjax("参数错误");
		ArrayList<Map<String, String>> list = JSON.parseObject(resource, new TypeReference<ArrayList<Map<String, String>>>(){});
		if(null==list || list.size()==0) return this.errorAjax("参数错误");
		List<UupmRoleResourceEntity> modelList = new ArrayList<UupmRoleResourceEntity>();
		for(Map<String, String> map : list) {
			String tenantCode = map.get("tenantCode");
			String rsCode = map.get("rsCode");
			UupmRoleResourceEntity model = new UupmRoleResourceEntity();
			model.setTenantCode(tenantCode);
			model.setRsCode(rsCode);
			model.setRoleCode(roleCode);
			modelList.add(model);
		}
		if(modelList.size()==0) return this.errorAjax("参数错误");
		
		this.uupmRoleResourceService.saveRoleResource(roleCode, modelList);
		return this.successAjax();
	}
	// 角色授权
	@RequestMapping(value="/findResourceByRoleCode", method=RequestMethod.POST)
	public RespData findResourceByRoleCode(String tenantCode, String roleCode) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
		UupmRoleResourceEntity model = new UupmRoleResourceEntity();
		model.setTenantCode(tenantCode);
		model.setRoleCode(roleCode);
		List<UupmRoleResourceEntity> listResult = this.uupmRoleResourceService.findList(model);
		return this.successAjax(listResult);
	}
	
}
