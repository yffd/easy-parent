
package com.yffd.easy.uupm.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.service.UupmResourceService;
import com.yffd.easy.uupm.web.common.UupmCommonController;
/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月03日 14时09分26秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/resource")
public class UupmResourceController extends UupmCommonController {

	@Autowired
	private UupmResourceService uupmResourceService;
	
	@RequestMapping(value="/listRoot", method=RequestMethod.POST)
	public RespData listRoot(@RequestParam Map<String, Object> paramMap) {
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		List<UupmResourceEntity> result = this.uupmResourceService.findRootNodes(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmResourceEntity> treeList = this.uupmResourceService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/listApp", method=RequestMethod.POST)
	public RespData listApp(@RequestParam Map<String, Object> paramMap) {
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setRsType("A");
		List<UupmResourceEntity> result = this.uupmResourceService.findList(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmResourceEntity> treeList = this.uupmResourceService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.POST)
	public RespData listAll(@RequestParam Map<String, Object> paramMap) {
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		List<UupmResourceEntity> result = this.uupmResourceService.findList(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmResourceEntity> treeList = this.uupmResourceService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/listChildren", method=RequestMethod.POST)
	public RespData listChildren(@RequestParam Map<String, Object> paramMap) {
		String treeId = (String) paramMap.get("treeId");
		String rsCode = (String) paramMap.get("rsCode");
		if(EasyStringCheckUtils.isEmpty(treeId) || EasyStringCheckUtils.isEmpty(rsCode)) return this.error("参数无效");
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setTreeId(treeId);
		paramNode.setRsCode(rsCode);
		List<UupmResourceEntity> result = this.uupmResourceService.findChildrenNodes(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmResourceEntity> treeList = this.uupmResourceService.convertToMultiTree(result, rsCode);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/addRoot", method=RequestMethod.POST)
	public RespData addRoot(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getRsCode())) return this.error("参数无效");
		model.setTreeId(model.getRsCode());
		// 存在判断
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		boolean existRootNode = this.uupmResourceService.existRootNode(paramNode);
		if(existRootNode) return this.error("编号已存在");
		this.uupmResourceService.addRootNode(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/addChild", method=RequestMethod.POST)
	public RespData addChild(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getRsCode())) return this.error("参数无效");
		// 存在判断
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setRsCode(model.getRsCode());	// 资源编号全局唯一
		boolean exsist = this.uupmResourceService.exsist(paramNode);
		if(exsist) return this.error("编号已存在");
		this.uupmResourceService.addChildNode(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getRsCode())) return this.error("参数无效");
		UupmResourceEntity oldNode = new UupmResourceEntity();
		oldNode.setTreeId(model.getTreeId());
		oldNode.setRsCode(model.getRsCode());
		this.uupmResourceService.update(model, oldNode, null);
		return this.successAjax();
	}
	
	@RequestMapping(value="/editStatus", method=RequestMethod.POST)
	public RespData editStatus(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getRsCode())
				|| EasyStringCheckUtils.isEmpty(model.getRsStatus())) return this.error("参数无效");
		UupmResourceEntity newNode = new UupmResourceEntity();
		newNode.setRsStatus(model.getRsStatus());	// 待修改的属性
		
		UupmResourceEntity oldNode = new UupmResourceEntity();	// 更新条件
		oldNode.setTreeId(model.getTreeId());
		oldNode.setRsCode(model.getRsCode());
		this.uupmResourceService.updateNodes(model, oldNode);
		return this.successAjax();
	}
	
	@RequestMapping(value="/del", method=RequestMethod.POST)
	public RespData del(UupmResourceEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getRsCode())) return this.error("参数无效");
		UupmResourceEntity paramNode = new UupmResourceEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setTreeId(model.getTreeId());
		paramNode.setRsCode(model.getRsCode());
		this.uupmResourceService.deleteNodes(model);
		return this.successAjax();
	}
	
}