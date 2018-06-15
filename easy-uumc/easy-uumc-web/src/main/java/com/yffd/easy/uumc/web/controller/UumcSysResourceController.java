package com.yffd.easy.uumc.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.mvc.model.RespModel;
import com.yffd.easy.uumc.pojo.entity.UumcSysResourceEntity;
import com.yffd.easy.uumc.service.UumcSysResourceService;
import com.yffd.easy.uumc.web.model.UumcTreeResourceModel;
import com.yffd.easy.uumc.web.model.factory.UumcSysResourceModelFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月12日 10时42分47秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/sys/resource")
public class UumcSysResourceController extends UumcWebController {

	@Autowired
	private UumcSysResourceService uumcSysResourceService;
	@Autowired
	private UumcSysResourceModelFactory uumcSysResourceModelFactory;
	
	@RequestMapping(value="/listTree", method=RequestMethod.POST)
	public RespModel listTree(String appCode) {
		if(EasyStringCheckUtils.isEmpty(appCode)) return this.successAjax();
		UumcSysResourceEntity model = new UumcSysResourceEntity();
		model.setAppCode(appCode);
		List<UumcSysResourceEntity> listResult = this.uumcSysResourceService.findList(model, getLoginInfo());
		if(null!=listResult && !listResult.isEmpty()) {
			List<UumcTreeResourceModel> treeList = this.uumcSysResourceModelFactory.buildMultiTree(listResult, appCode);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcSysResourceEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAppCode())
				|| EasyStringCheckUtils.isEmpty(paramModel.getRsCode())
				|| EasyStringCheckUtils.isEmpty(paramModel.getParentCode())) return this.errorAjax("参数无效");
		UumcSysResourceEntity model = new UumcSysResourceEntity();	// 存在校验
		model.setAppCode(paramModel.getAppCode());
		model.setRsCode(paramModel.getRsCode());
		boolean exsist = this.uumcSysResourceService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcSysResourceService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcSysResourceEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcSysResourceEntity oldModel = new UumcSysResourceEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcSysResourceService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
	public RespModel delByIds(String ids) {
		if (EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
		List<String> idList = JSON.parseArray(ids, String.class);
		Set<String> idSet = new HashSet<>(idList);
		int result = this.uumcSysResourceService.delCascadeByIds(idSet);
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
}
