package com.yffd.easy.uumc.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.framework.web.mvc.model.RespModel;
import com.yffd.easy.framework.web.mvc.model.easyui.DataGridModel;
import com.yffd.easy.uumc.pojo.entity.UumcSysApplicationEntity;
import com.yffd.easy.uumc.service.UumcSysApplicationService;

import com.yffd.easy.uumc.web.controller.UumcWebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月12日 10时06分26秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/sys/application")
public class UumcSysApplicationController extends UumcWebController {

	@Autowired
	private UumcSysApplicationService uumcSysApplicationService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UumcSysApplicationEntity paramModel = this.getModelParam(paramMap, UumcSysApplicationEntity.class);
		PageResult<UumcSysApplicationEntity> pageResult = this.uumcSysApplicationService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridModel dataGrid = this.toDataGrid(pageResult);
		return this.successAjax(dataGrid);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcSysApplicationEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAppCode())) return this.errorAjax("参数无效");
		// 存在校验
		UumcSysApplicationEntity model = new UumcSysApplicationEntity();
		model.setAppCode(paramModel.getAppCode());
		boolean exsist = this.uumcSysApplicationService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcSysApplicationService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcSysApplicationEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcSysApplicationEntity oldModel = new UumcSysApplicationEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcSysApplicationService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespModel delById(String id) {
		if (EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		UumcSysApplicationEntity model = new UumcSysApplicationEntity();
		model.setId(id);
		int result = this.uumcSysApplicationService.delete(model, getLoginInfo());
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
}
