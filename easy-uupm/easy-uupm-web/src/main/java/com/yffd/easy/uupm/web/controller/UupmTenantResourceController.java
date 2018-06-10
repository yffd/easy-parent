package com.yffd.easy.uupm.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//	@Autowired
//	private UupmTenantResourceService uupmTenantResourceService;
//	@Autowired
//	private UupmResourceFactory uupmResourceModelFactory;
//	
//	// 租户资源授权
//	@RequestMapping(value="/saveTenantResource", method=RequestMethod.POST)
//	public RespData saveTenantResource(HttpServletRequest req) {
//		String ttCode = req.getParameter("ttCode");
//		String rsCodes = req.getParameter("rsCodes");
//		if(EasyStringCheckUtils.isEmpty(ttCode) || EasyStringCheckUtils.isEmpty(rsCodes)) return this.errorAjax("参数错误");
//		List<String> rsCodesList = JSON.parseArray(rsCodes, String.class);
//		if(null==rsCodesList || rsCodesList.size()==0) return this.errorAjax("参数错误");
//		int result = this.uupmTenantResourceService.saveTenantResource(ttCode, rsCodesList, getLoginInfo());
//		if(result==0) return this.errorAjax("授权失败");
//		return this.successAjax();
//	}
//		
//	// 查找某租户已拥有的资源编号
//	@RequestMapping(value="/findTenantResourceCodes", method=RequestMethod.POST)
//	public RespData findTenantResourceCodes(String ttCode) {
//		if(EasyStringCheckUtils.isEmpty(ttCode)) return this.errorAjax("参数错误");
//		Set<String> result = this.uupmTenantResourceService.findRsCodesByTtCode(ttCode);
//		return this.successAjax(result);
//	}
//	
//	// 当前登录租户所拥有的所有资源
//	@RequestMapping(value="/findTenantResource", method=RequestMethod.POST)
//	public RespData findTenantResource() {
//		String ttCode = this.getLoginInfo().getTtCode();
//		List<UupmResourceEntity> result = this.uupmTenantResourceService.findTenantResource(ttCode);
//		if(null!=result && !result.isEmpty()) {
//			List<UupmEasyuiTreeResVo> treeList = this.uupmResourceModelFactory.buildMultiTree(result);
//			return this.successAjax(treeList);
//		}
//		return this.successAjax();
//	}
	
}
