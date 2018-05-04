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
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.entity.UupmTenantResourceEntity;
import com.yffd.easy.uupm.service.UupmResourceService;
import com.yffd.easy.uupm.service.UupmTenantResourceService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月05日 15时48分42秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/tenant/resource")
public class UupmTenantResourceController extends UupmCommonController {

	@Autowired
	private UupmTenantResourceService uupmTenantResourceService;
	@Autowired
	private UupmResourceService uupmResourceService;
	
	// 租户授权
	@RequestMapping(value="/saveTenantResource", method=RequestMethod.POST)
	public RespData saveTenantResource(HttpServletRequest req) {
		String tenantCode = req.getParameter("tenantCode");
		String resource = req.getParameter("resource");
		if(EasyStringCheckUtils.isEmpty(tenantCode) || EasyStringCheckUtils.isEmpty(resource)) return this.errorAjax("参数错误");
		ArrayList<Map<String, String>> list = JSON.parseObject(resource, new TypeReference<ArrayList<Map<String, String>>>(){});
		if(null==list || list.size()==0) return this.errorAjax("参数错误");
		List<UupmTenantResourceEntity> modelList = new ArrayList<UupmTenantResourceEntity>();
		for(Map<String, String> map : list) {
			String rsCode = map.get("rsCode");
			UupmTenantResourceEntity model = new UupmTenantResourceEntity();
			model.setTenantCode(tenantCode);
			model.setRsCode(rsCode);
			modelList.add(model);
		}
		if(modelList.size()==0) return this.errorAjax("参数错误");
		
		this.uupmTenantResourceService.saveTenantResource(tenantCode, modelList);
		return this.successAjax();
	}
		
	// 租户授权
	@RequestMapping(value="/findResourceByTenantCode", method=RequestMethod.POST)
	public RespData findResourceByTenantCode(String tenantCode) {
		if(EasyStringCheckUtils.isEmpty(tenantCode)) return this.errorAjax("参数错误");
		UupmTenantResourceEntity model = new UupmTenantResourceEntity();
		model.setTenantCode(tenantCode);
		List<UupmTenantResourceEntity> listResult = this.uupmTenantResourceService.findList(model);
		return this.successAjax(listResult);
	}
	
	// 租户资源
	@RequestMapping(value="/findTenantResource", method=RequestMethod.POST)
	public RespData findTenantResource() {
		String tenantCode = "it3.com.cn";
		List<UupmResourceEntity> result = this.uupmTenantResourceService.findTenantResource(tenantCode);
		if(null!=result && !result.isEmpty()) {
//			List<UupmResourceComboTreeVO> treeList = this.uupmResourceSupport.toSyncTreeVO(result, "root");
//			return this.successAjax(treeList);
			List<UupmResourceEntity> treeList = this.uupmResourceService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
}
