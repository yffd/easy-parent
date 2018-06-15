package com.yffd.easy.uumc.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.mvc.model.RespModel;
import com.yffd.easy.uumc.pojo.entity.UumcOrganizationEntity;
import com.yffd.easy.uumc.service.UumcOrganizationService;
import com.yffd.easy.uumc.web.model.UumcTreeOrganizationModel;
import com.yffd.easy.uumc.web.model.factory.UumcOrganizationModelFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月13日 14时16分48秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/organization")
public class UumcOrganizationController extends UumcWebController {

	@Autowired
	private UumcOrganizationService uumcOrganizationService;
	@Autowired
	private UumcOrganizationModelFactory uumcOrganizationModelFactory;
	
	@RequestMapping(value="/listTree", method=RequestMethod.POST)
	public RespModel listTree(@RequestParam Map<String, Object> paramMap) {
		List<UumcOrganizationEntity> listResult = this.uumcOrganizationService.findAll();
		if(null!=listResult && !listResult.isEmpty()) {
			List<UumcTreeOrganizationModel> treeList = this.uumcOrganizationModelFactory.buildTree(listResult);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcOrganizationEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getOrgCode())) return this.errorAjax("参数无效");
		UumcOrganizationEntity model = new UumcOrganizationEntity();	// 存在校验
		model.setOrgCode(paramModel.getOrgCode());
		boolean exsist = this.uumcOrganizationService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcOrganizationService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcOrganizationEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcOrganizationEntity oldModel = new UumcOrganizationEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcOrganizationService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
	public RespModel delByIds(String ids) {
		if (EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
		List<String> idList = JSON.parseArray(ids, String.class);
		Set<String> idSet = new HashSet<>(idList);
		int result = this.uumcOrganizationService.delByIds(idSet);
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
}
