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
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.pojo.entity.UumcUserEntity;
import com.yffd.easy.uumc.service.UumcRelationRoleUserService;
import com.yffd.easy.uumc.service.UumcUserService;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月13日 15时37分22秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/user")
public class UumcUserController extends UumcWebController {

	@Autowired
	private UumcUserService uumcUserService;
	@Autowired
	private UumcRelationRoleUserService uumcRelationRoleUserService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UumcUserEntity paramModel = this.getModelParam(paramMap, UumcUserEntity.class);
		PageResult<UumcUserEntity> pageResult = this.uumcUserService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridModel dataGrid = this.toDataGrid(pageResult);
		return this.successAjax(dataGrid);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcUserEntity user, UumcAccountEntity account) {
		if (null == user || EasyStringCheckUtils.isEmpty(user.getUserCode())) return this.errorAjax("参数无效");
		UumcUserEntity model = new UumcUserEntity();	// 存在校验
		model.setUserCode(user.getUserCode());
		boolean exsist = this.uumcUserService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcUserService.addUserWithAccount(user, account, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcUserEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcUserEntity oldModel = new UumcUserEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcUserService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByUserCode", method=RequestMethod.POST)
	public RespModel delByUserCode(String userCode) {
		if (EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数无效");
		int result = this.uumcUserService.delCascadeByUserCode(userCode);
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/updateStatus", method=RequestMethod.POST)
	public RespModel updateStatus(UumcUserEntity paramModel) {
		if (EasyStringCheckUtils.isEmpty(paramModel.getUserCode()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getUserStatus())) return this.errorAjax("参数无效");
		int result = this.uumcUserService.updateStatus(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	
	@RequestMapping(value="/saveCfg", method=RequestMethod.POST)
	public RespModel saveCfg(HttpServletRequest req) {
		String userCode = req.getParameter("userCode");
		String roleCodes = req.getParameter("roleCodes");
		if (EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数错误");
		List<String> roleCodeList = JSON.parseArray(roleCodes, String.class);
		int result = this.uumcRelationRoleUserService.saveCfg(userCode, roleCodeList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
		
	}
	
	@RequestMapping(value="/listHaveRoleCodes", method=RequestMethod.POST)
	public RespModel listHaveRoleCodes(String userCode) {
		if (EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数错误");
		Set<String> result = this.uumcRelationRoleUserService.findRoleCode(userCode);
		return this.successAjax(result);
		
	}
}
