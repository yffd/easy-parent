package com.yffd.easy.uupm.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.pojo.factory.UupmResourceFactory;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIResTreeVo;
import com.yffd.easy.uupm.service.UupmTenantResourceService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

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
public class UupmTenantResourceController extends UupmBaseController {

	@Autowired
	private UupmTenantResourceService uupmTenantResourceService;
	@Autowired
	private UupmResourceFactory uupmResourceModelFactory;
	
	// 租户资源授权
	@RequestMapping(value="/saveTenantResource", method=RequestMethod.POST)
	public RespData saveTenantResource(HttpServletRequest req) {
		String tenantCode = req.getParameter("tenantCode");
		String rsCodes = req.getParameter("rsCodes");
		if(EasyStringCheckUtils.isEmpty(tenantCode) || EasyStringCheckUtils.isEmpty(rsCodes)) return this.errorAjax("参数错误");
		List<String> rsCodesList = JSON.parseArray(rsCodes, String.class);
		if(null==rsCodesList || rsCodesList.size()==0) return this.errorAjax("参数错误");
		int result = this.uupmTenantResourceService.saveTenantResource(tenantCode, rsCodesList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
	}
		
	// 查找某租户已拥有的资源编号
	@RequestMapping(value="/findTenantResourceCodes", method=RequestMethod.POST)
	public RespData findTenantResourceCodes(String tenantCode) {
		if(EasyStringCheckUtils.isEmpty(tenantCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uupmTenantResourceService.findRsCodesByTenantCode(tenantCode);
		return this.successAjax(result);
	}
	
	// 当前登录租户所拥有的所有资源
	@RequestMapping(value="/findTenantResource", method=RequestMethod.POST)
	public RespData findTenantResource() {
		String tenantCode = this.getLoginInfo().getTenantCode();
		List<UupmResourceEntity> result = this.uupmTenantResourceService.findResourceByTenantCode(tenantCode);
		if(null!=result && !result.isEmpty()) {
			List<UupmUIResTreeVo> treeList = this.uupmResourceModelFactory.buildMultiTree(result);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
}
