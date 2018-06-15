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
import com.yffd.easy.uumc.pojo.entity.UumcPermissionEntity;
import com.yffd.easy.uumc.service.UumcPermissionService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月12日 14时01分34秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/permission")
public class UumcPermissionController extends UumcWebController {

	@Autowired
	private UumcPermissionService uumcPermissionService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UumcPermissionEntity paramModel = this.getModelParam(paramMap, UumcPermissionEntity.class);
		PageResult<UumcPermissionEntity> pageResult = this.uumcPermissionService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridModel dataGrid = this.toDataGrid(pageResult);
		return this.successAjax(dataGrid);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcPermissionEntity paramModel) {
		if (null == paramModel) return this.errorAjax("参数无效");
		UumcPermissionEntity model = new UumcPermissionEntity();	// 存在校验
		// TODO
		model.setId(paramModel.getId());
		boolean exsist = this.uumcPermissionService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcPermissionService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcPermissionEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcPermissionEntity oldModel = new UumcPermissionEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcPermissionService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByPmsCode", method=RequestMethod.POST)
	public RespModel delByPmsCode(String pmsCode) {
		if (EasyStringCheckUtils.isEmpty(pmsCode)) return this.errorAjax("参数无效");
		int result = this.uumcPermissionService.delCascadeByPmsCode(pmsCode);
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/savePms", method=RequestMethod.POST)
	public RespModel savePms(HttpServletRequest req) {
		String appCode = req.getParameter("appCode");
		String rsCodes = req.getParameter("rsCodes");
		if (EasyStringCheckUtils.isEmpty(appCode)) return this.errorAjax("参数错误");
		List<String> rsCodeList = JSON.parseArray(rsCodes, String.class);
		int result = this.uumcPermissionService.savePermission(appCode, rsCodeList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
		
	}
	
	@RequestMapping(value="/listHavePmsForRsCodes", method=RequestMethod.POST)
	public RespModel listHavePmsForRsCodes(String appCode) {
		if (EasyStringCheckUtils.isEmpty(appCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uumcPermissionService.findHavePmsForRsCode(appCode);
		return this.successAjax(result);
		
	}
	
}
