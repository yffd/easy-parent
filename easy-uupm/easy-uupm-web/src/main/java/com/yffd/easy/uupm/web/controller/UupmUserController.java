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
import com.yffd.easy.framework.web.shiro.login.account.ShiroAccount;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.entity.UupmUserEntity;
import com.yffd.easy.uupm.pojo.vo.UupmUserInfoVo;
import com.yffd.easy.uupm.service.UupmUserService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月05日 17时25分29秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/user")
public class UupmUserController extends UupmBaseController {

	@Autowired
	private UupmUserService uupmUserService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespData listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmUserInfoVo paramModel = this.getModelParam(paramMap, UupmUserInfoVo.class);
		PageResult<UupmUserInfoVo> pageResult = this.uupmUserService.findUserInfoPage(paramModel, paramPage, getLoginInfo());
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmUserEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getUserCode())) return this.errorAjax("参数无效");
		String realUserCode = this.getLoginInfo().getTtCode() + paramModel.getUserCode();	// 重置userCode
		UupmUserEntity model = new UupmUserEntity();	// 存在校验
		model.setUserCode(realUserCode);
		boolean exsist = this.uupmUserService.exsist(model, getLoginInfo());
		if(exsist) return this.errorAjax("编号已存在");
		// 生成用户账号+密码
		String accountId = realUserCode;
		String password = paramModel.getUserCode();
		ShiroAccount shiroAccount = new ShiroAccount(accountId, password);	// 账号加密
		UupmAccountEntity account = new UupmAccountEntity();
		account.setAccountId(accountId);
		account.setAccountPwd(shiroAccount.getEncryptPwd());
		account.setSalt(shiroAccount.getCredentialsSalt());
		
		paramModel.setUserCode(realUserCode);	// 重置userCode
		paramModel.setAccountId(accountId);		// 关联账号ID
		int result = this.uupmUserService.addUserWithAccount(paramModel, account, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmUserEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getUserCode())) return this.errorAjax("参数无效");
		UupmUserEntity oldModel = new UupmUserEntity();
		oldModel.setUserCode(paramModel.getUserCode());
		int result = this.uupmUserService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByUserCode", method=RequestMethod.POST)
	public RespData delByUserCode(String userCode) {
		if(EasyStringCheckUtils.isEmpty(userCode)) return this.errorAjax("参数无效");
		int result = this.uupmUserService.delByUserCode(userCode, getLoginInfo());
		if(result==0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
}
