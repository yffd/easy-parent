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
import com.yffd.easy.framework.web.shiro.login.account.ShiroAccount;
import com.yffd.easy.uupm.pojo.entity.UupmAccountEntity;
import com.yffd.easy.uupm.service.UupmAccountService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月06日 13时41分04秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/account")
public class UupmAccountController extends UupmBaseController {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmAccountEntity paramModel = this.getModelParam(paramMap, UupmAccountEntity.class);
		PageResult<UupmAccountEntity> pageResult = this.uupmAccountService.findTenantAccount(paramModel, paramPage, getLoginInfo());
		EasyuiDataGridModel dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/addTenantAccount", method=RequestMethod.POST)
	public RespModel addTenantAccount(UupmAccountEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getAcntId())
				|| EasyStringCheckUtils.isEmpty(paramModel.getAcntPwd())) return this.error("参数无效");
		UupmAccountEntity model = new UupmAccountEntity();	// 存在校验
		model.setAcntId(paramModel.getAcntId());
		boolean exsist = this.uupmAccountService.exsist(model, getLoginInfo());
		if(exsist) return this.error("账号已存在");
		String accountId = paramModel.getAcntId();
		String password = paramModel.getAcntPwd();
		ShiroAccount shiroAccount = new ShiroAccount(accountId, password);
		
		paramModel.setAcntPwd(shiroAccount.getEncryptPwd());
		paramModel.setSalt(shiroAccount.getCredentialsSalt());
		int result = this.uupmAccountService.addTenantAccount(paramModel, getLoginInfo());
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UupmAccountEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAcntId())) return this.error("参数无效");
		UupmAccountEntity oldModel = new UupmAccountEntity();
		oldModel.setAcntId(paramModel.getAcntId());
		int result = this.uupmAccountService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespModel delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		UupmAccountEntity model = new UupmAccountEntity();
		model.setId(id);
		int result = this.uupmAccountService.delete(model, getLoginInfo());
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
