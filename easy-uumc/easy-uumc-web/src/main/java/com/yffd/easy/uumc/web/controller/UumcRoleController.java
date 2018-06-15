package com.yffd.easy.uumc.web.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.page.PageParam;
import com.yffd.easy.framework.pojo.page.PageResult;
import com.yffd.easy.framework.web.mvc.model.RespModel;
import com.yffd.easy.framework.web.mvc.model.easyui.DataGridModel;
import com.yffd.easy.uumc.pojo.entity.UumcRoleEntity;
import com.yffd.easy.uumc.service.UumcRelationRolePmsService;
import com.yffd.easy.uumc.service.UumcRoleService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月13日 10时23分39秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/role")
public class UumcRoleController extends UumcWebController {

	@Autowired
	private UumcRoleService uumcRoleService;
	@Autowired
	private UumcRelationRolePmsService uumcRelationRolePmsService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UumcRoleEntity paramModel = this.getModelParam(paramMap, UumcRoleEntity.class);
		PageResult<UumcRoleEntity> pageResult = this.uumcRoleService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridModel dataGrid = this.toDataGrid(pageResult);
		return this.successAjax(dataGrid);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcRoleEntity paramModel) {
		if (null == paramModel) return this.errorAjax("参数无效");
		UumcRoleEntity model = new UumcRoleEntity();	// 存在校验
		model.setRoleCode(paramModel.getRoleCode());
		boolean exsist = this.uumcRoleService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcRoleService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcRoleEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcRoleEntity oldModel = new UumcRoleEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcRoleService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByRoleCode", method=RequestMethod.POST)
	public RespModel delByRoleCode(String roleCode) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数无效");
		int result = this.uumcRoleService.delCascadeByRoleCode(roleCode);
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/saveCfg", method=RequestMethod.POST)
	public RespModel saveCfg(HttpServletRequest req) {
		String roleCode = req.getParameter("roleCode");
		String pmsCodes = req.getParameter("pmsCodes");
		if (EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
		List<String> pmsCodeList = JSON.parseArray(pmsCodes, String.class);
		int result = this.uumcRelationRolePmsService.saveCfg(roleCode, pmsCodeList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
		
	}
	
	@RequestMapping(value="/listHavePmsCodes", method=RequestMethod.POST)
	public RespModel listHavePmsCodes(String roleCode) {
		if (EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uumcRelationRolePmsService.findPmsCode(roleCode);
		return this.successAjax(result);
		
	}
	
}
