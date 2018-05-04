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
import com.yffd.easy.uupm.entity.UupmDictionaryEntity;
import com.yffd.easy.uupm.service.UupmDictionaryService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月10日 17时19分46秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/dictionary")
public class UupmDictionaryController extends UupmCommonController {

	@Autowired
	private UupmDictionaryService uupmDictionaryService;
	
	@RequestMapping(value="/listRoot", method=RequestMethod.POST)
	public RespData listRoot(@RequestParam Map<String, Object> paramMap) {
		UupmDictionaryEntity paramNode = new UupmDictionaryEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		List<UupmDictionaryEntity> result = this.uupmDictionaryService.findRootNodes(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmDictionaryEntity> treeList = this.uupmDictionaryService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/listChildren", method=RequestMethod.POST)
	public RespData listChildren(@RequestParam Map<String, Object> paramMap) {
		String treeId = (String) paramMap.get("treeId");
		String keyCode = (String) paramMap.get("keyCode");
		if(EasyStringCheckUtils.isEmpty(treeId) || EasyStringCheckUtils.isEmpty(keyCode)) return this.error("参数无效");
		UupmDictionaryEntity paramNode = new UupmDictionaryEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setTreeId(treeId);
		paramNode.setKeyCode(keyCode);
		List<UupmDictionaryEntity> result = this.uupmDictionaryService.findChildrenNodes(paramNode);
		if(null!=result && !result.isEmpty()) {
			List<UupmDictionaryEntity> treeList = this.uupmDictionaryService.convertToMultiTree(result, "root");
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/addRoot", method=RequestMethod.POST)
	public RespData addRoot(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getKeyCode())) return this.error("参数无效");
		model.setTreeId(model.getKeyCode());
		// 存在判断
		UupmDictionaryEntity paramNode = new UupmDictionaryEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		boolean existRootNode = this.uupmDictionaryService.existRootNode(paramNode);
		if(existRootNode) return this.error("编号已存在");
		this.uupmDictionaryService.addRootNode(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/addChild", method=RequestMethod.POST)
	public RespData addChild(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getKeyCode())) return this.error("参数无效");
		// 存在判断
		UupmDictionaryEntity paramNode = new UupmDictionaryEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setKeyCode(model.getKeyCode());
		boolean exsist = this.uupmDictionaryService.exsist(paramNode);
		if(exsist) return this.error("编号已存在");
		this.uupmDictionaryService.addChildNode(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getKeyCode())) return this.error("参数无效");
		UupmDictionaryEntity oldNode = new UupmDictionaryEntity();
		oldNode.setTreeId(model.getTreeId());
		oldNode.setKeyCode(model.getKeyCode());
		this.uupmDictionaryService.update(model, oldNode, null);
		return this.successAjax();
	}
	
	@RequestMapping(value="/editStatus", method=RequestMethod.POST)
	public RespData editStatus(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getKeyCode())
				|| EasyStringCheckUtils.isEmpty(model.getValueStatus())) return this.error("参数无效");
		UupmDictionaryEntity newNode = new UupmDictionaryEntity();
		newNode.setValueStatus(model.getValueStatus());	// 待修改的属性
		
		UupmDictionaryEntity oldNode = new UupmDictionaryEntity();	// 更新条件
		oldNode.setTreeId(model.getTreeId());
		oldNode.setKeyCode(model.getKeyCode());
		this.uupmDictionaryService.updateNodes(newNode, oldNode);
		return this.successAjax();
	}
	
	@RequestMapping(value="/del", method=RequestMethod.POST)
	public RespData del(UupmDictionaryEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(model.getKeyCode())) return this.error("参数无效");
		UupmDictionaryEntity paramNode = new UupmDictionaryEntity();
		paramNode.setTenantCode(this.getLoginInfo().getTenantCode());
		paramNode.setTreeId(model.getTreeId());
		paramNode.setKeyCode(model.getKeyCode());
		this.uupmDictionaryService.deleteNodes(model);
		return this.successAjax();
	}
	
}
