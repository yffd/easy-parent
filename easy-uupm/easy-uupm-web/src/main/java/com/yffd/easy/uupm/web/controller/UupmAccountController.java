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
import com.yffd.easy.framework.pojo.vo.DataGridVo;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.framework.web.shiro.password.PasswordEncrypt;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
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
	public RespData listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmAccountEntity paramModel = this.getModelParam(paramMap, UupmAccountEntity.class);
		PageResult<UupmAccountEntity> pageResult = this.uupmAccountService.findTenantAccount(paramModel, paramPage, getLoginInfo());
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/addTenantAccount", method=RequestMethod.POST)
	public RespData addTenantAccount(UupmAccountEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getAccountId())
				|| EasyStringCheckUtils.isEmpty(paramModel.getAccountPwd())) return this.error("参数无效");
		UupmAccountEntity model = new UupmAccountEntity();	// 存在校验
		model.setAccountId(paramModel.getAccountId());
		boolean exsist = this.uupmAccountService.exsist(model, getLoginInfo());
		if(exsist) return this.error("账号已存在");
		// 生成盐串和密码串
//		String salt = PasswordEncrypt.getRandomSalt();
//		String encryptPwd = PasswordEncrypt.getEncryptPassword(paramModel.getAccountId(), paramModel.getAccountPwd(), salt);
//		paramModel.setSalt(salt);
//		paramModel.setAccountPwd(encryptPwd);
		int result = this.uupmAccountService.addTenantAccount(paramModel, getLoginInfo());
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmAccountEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAccountId())) return this.error("参数无效");
		UupmAccountEntity oldModel = new UupmAccountEntity();
		oldModel.setAccountId(paramModel.getAccountId());
		int result = this.uupmAccountService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		UupmAccountEntity model = new UupmAccountEntity();
		model.setId(id);
		int result = this.uupmAccountService.delete(model, getLoginInfo());
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
