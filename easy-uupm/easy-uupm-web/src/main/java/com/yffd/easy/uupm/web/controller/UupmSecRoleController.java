package com.yffd.easy.uupm.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.framework.web.model.RespModel;
import com.yffd.easy.framework.web.model.easyui.EasyuiDataGridModel;
import com.yffd.easy.uupm.pojo.entity.UupmSecRoleEntity;
import com.yffd.easy.uupm.service.UupmSecRoleService;
import com.yffd.easy.uupm.web.base.UupmBaseController;


/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年03月15日 10时02分10秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/sec/role")
public class UupmSecRoleController extends UupmBaseController {

	@Autowired
	private UupmSecRoleService uupmSecRoleService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmSecRoleEntity paramModel = this.getModelParam(paramMap, UupmSecRoleEntity.class);
		paramModel.setTtCode(getLoginInfo().getTtCode());	// 指定租户
		PageResult<UupmSecRoleEntity> pageResult = this.uupmSecRoleService.findPage(paramModel, paramPage, getLoginInfo());
		EasyuiDataGridModel dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UupmSecRoleEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getRoleCode())) return this.errorAjax("参数无效");
		UupmSecRoleEntity model = new UupmSecRoleEntity();
		model.setTtCode(getLoginInfo().getTtCode());	// 指定租户
		model.setRoleCode(paramModel.getRoleCode());
		boolean exsist = this.uupmSecRoleService.exsist(model, getLoginInfo());
		if(exsist) return this.error("编号已存在");
		
		paramModel.setTtCode(getLoginInfo().getTtCode());	// 指定租户
		int result = this.uupmSecRoleService.save(paramModel, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UupmSecRoleEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getRoleCode())) return this.errorAjax("参数无效");
		UupmSecRoleEntity oldModel = new UupmSecRoleEntity();
		oldModel.setTtCode(getLoginInfo().getTtCode());	// 指定租户
		oldModel.setRoleCode(paramModel.getRoleCode());
		int result = this.uupmSecRoleService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByRoleCode", method=RequestMethod.POST)
	public RespModel delByRoleCode(String roleCode) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数无效");
		UupmSecRoleEntity model = new UupmSecRoleEntity();
		model.setTtCode(getLoginInfo().getTtCode());	// 指定租户
		model.setRoleCode(roleCode);
		int result = this.uupmSecRoleService.delCascadeByRole(model, getLoginInfo());
		if(result==0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	
}
