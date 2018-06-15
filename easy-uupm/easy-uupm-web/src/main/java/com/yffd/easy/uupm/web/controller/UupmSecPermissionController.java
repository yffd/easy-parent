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
import com.yffd.easy.framework.web.model.RespModel;
import com.yffd.easy.uupm.service.UupmSecPermissionService;
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
@RequestMapping("/uupm/sec/pms")
public class UupmSecPermissionController extends UupmBaseController {

	@Autowired
	private UupmSecPermissionService uupmSecPermissionService;
	
	// 租户资源授权
	@RequestMapping(value="/savePms", method=RequestMethod.POST)
	public RespModel savePms(HttpServletRequest req) {
		String ttCode = req.getParameter("ttCode");
		String rsCodes = req.getParameter("rsCodes");
		if(EasyStringCheckUtils.isEmpty(ttCode)) return this.errorAjax("参数错误");
		List<String> rsCodesList = JSON.parseArray(rsCodes, String.class);
		int result = this.uupmSecPermissionService.savePermission(ttCode, rsCodesList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
	}
		
	// 查找某租户已拥有的资源编号
	@RequestMapping(value="/findPmsRsCodes", method=RequestMethod.POST)
	public RespModel findPmsRsCodes(String ttCode) {
		if(EasyStringCheckUtils.isEmpty(ttCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uupmSecPermissionService.findPmsRsCodes(ttCode);
		return this.successAjax(result);
	}
	
	// 当前登录租户所拥有的所有资源
//	@RequestMapping(value="/findTenantResource", method=RequestMethod.POST)
//	public RespModel findTenantResource() {
//		String ttCode = this.getLoginInfo().getTtCode();
//		List<UupmResourceEntity> result = this.uupmTenantResourceService.findTenantResource(ttCode);
//		if(null!=result && !result.isEmpty()) {
//			List<UupmEasyuiTreeResVo> treeList = this.uupmResourceModelFactory.buildMultiTree(result);
//			return this.successAjax(treeList);
//		}
//		return this.successAjax();
//	}
	
}
