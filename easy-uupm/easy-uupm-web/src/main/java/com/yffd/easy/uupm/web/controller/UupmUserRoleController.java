package com.yffd.easy.uupm.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.a.UupmUserRoleEntity;
import com.yffd.easy.uupm.service.a.UupmUserRoleService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月06日 13时23分41秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/user/role")
public class UupmUserRoleController extends UupmBaseController {

	@Autowired
	private UupmUserRoleService uupmUserRoleService;
	
	//用户角色授权
	@RequestMapping(value="/saveUserRole", method=RequestMethod.POST)
	public RespData saveUserRole(HttpServletRequest req) {
		String userCode = req.getParameter("userCode");
		String roleCodes = req.getParameter("roleCodes");
		if(EasyStringCheckUtils.isEmpty(userCode) || EasyStringCheckUtils.isEmpty(roleCodes)) return this.errorAjax("参数错误");
		List<String> roleCodesList = JSON.parseArray(roleCodes, String.class);
		if(null==roleCodesList || roleCodesList.size()==0) return this.errorAjax("参数错误");
		int result = this.uupmUserRoleService.saveUserRole(userCode, roleCodesList, getLoginInfo());
		if(result==0) return this.errorAjax("授权失败");
		return this.successAjax();
	}
	
	// 查询用户已有角色
	@RequestMapping(value="/findRoleByUserCode", method=RequestMethod.POST)
	public RespData findRoleByUserCode(String userCode) {
		if(EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数错误");
		UupmUserRoleEntity model = new UupmUserRoleEntity();
		model.setUserCode(userCode);
		List<UupmUserRoleEntity> listResult = this.uupmUserRoleService.findList(model, getLoginInfo());
		return this.successAjax(listResult);
	}
	
}
