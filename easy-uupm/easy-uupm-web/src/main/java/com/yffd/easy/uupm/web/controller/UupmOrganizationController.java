package com.yffd.easy.uupm.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.pojo.factory.UupmOrganizationFactory;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIOrgTreeVo;
import com.yffd.easy.uupm.service.UupmOrganizationService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年03月30日 11时47分01秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/organization")
public class UupmOrganizationController extends UupmBaseController {

	@Autowired
	private UupmOrganizationService uupmOrganizationService;
	@Autowired
	private UupmOrganizationFactory uupmOrganizationModelFactory;
	
	@RequestMapping(value="/listTree", method=RequestMethod.POST)
	public RespData listTree(@RequestParam Map<String, Object> paramMap) {
		UupmOrganizationEntity paramModel = this.getModelParam(paramMap, UupmOrganizationEntity.class);
		List<UupmOrganizationEntity> listResult = this.uupmOrganizationService.findList(paramModel, getLoginInfo());
		if(null!=listResult && !listResult.isEmpty()) {
			List<UupmUIOrgTreeVo> treeList = this.uupmOrganizationModelFactory.buildMultiTree(listResult);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmOrganizationEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getOrgCode())
				|| EasyStringCheckUtils.isEmpty(paramModel.getParentCode())) return this.error("参数无效");
		UupmOrganizationEntity entity = new UupmOrganizationEntity();	// 存在校验
		entity.setOrgCode(paramModel.getOrgCode());
		boolean exsist = this.uupmOrganizationService.exsist(entity, getLoginInfo());
		if(exsist) return this.error("编号已存在");
		this.uupmOrganizationService.save(paramModel, getLoginInfo());
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmOrganizationEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getOrgCode())) return this.error("参数无效");
		UupmOrganizationEntity entityOld = new UupmOrganizationEntity();
		entityOld.setTreeId(paramModel.getTreeId());
		entityOld.setOrgCode(paramModel.getOrgCode());
		this.uupmOrganizationService.update(paramModel, entityOld, getLoginInfo());
		return this.successAjax();
	}
	
	@RequestMapping(value="/delTree", method=RequestMethod.POST)
	public RespData delTree(@RequestParam Map<String, Object> paramMap) {
		String treeId = (String) paramMap.get("treeId");
		if(EasyStringCheckUtils.isEmpty(treeId)) return this.errorAjax("参数无效");
		UupmOrganizationEntity entity = new UupmOrganizationEntity();
		entity.setTreeId(treeId);
		this.uupmOrganizationService.delete(entity, getLoginInfo());
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
	public RespData delByIds(String ids) {
		if(EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
		int result = this.uupmOrganizationService.deleteByIds(ids);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
