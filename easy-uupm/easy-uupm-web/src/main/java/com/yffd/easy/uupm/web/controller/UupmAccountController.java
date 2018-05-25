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
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		String tenantCode = (String) paramMap.get("tenantCode");
		if(EasyStringCheckUtils.isEmpty(tenantCode)) return this.error("参数无效");
		PageParam paramPage = this.getPageParam(paramMap);
		UupmAccountEntity paramModel = this.getModelParam(paramMap, UupmAccountEntity.class);
		paramModel.setTenantCode(tenantCode);
		PageResult<UupmAccountEntity> pageResult = this.uupmAccountService.findAdminAccount(paramModel, paramPage);
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmAccountEntity paramModel) {
		if(null==paramModel 
				|| EasyStringCheckUtils.isEmpty(paramModel.getTenantCode())
				|| EasyStringCheckUtils.isEmpty(paramModel.getAccountId())
				|| EasyStringCheckUtils.isEmpty(paramModel.getAccountPwd())) return this.error("参数无效");
		// 存在校验
		UupmAccountEntity entity = new UupmAccountEntity();
		entity.setTenantCode(paramModel.getTenantCode());
		entity.setAccountId(paramModel.getAccountId());
		boolean exsist = this.uupmAccountService.exsist(entity);
		if(exsist) return this.error("账号已存在");
		// 生成盐串和密码串
//		String salt = PasswordEncrypt.getRandomSalt();
//		String encryptPwd = PasswordEncrypt.getEncryptPassword(paramModel.getAccountId(), paramModel.getAccountPwd(), salt);
//		paramModel.setSalt(salt);
//		paramModel.setAccountPwd(encryptPwd);
		this.initAddProps(paramModel);
		int result = this.uupmAccountService.save(paramModel);
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmAccountEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.error("参数无效");
		UupmAccountEntity entityOld = new UupmAccountEntity();
		entityOld.setId(paramModel.getId());
		this.initUpdateProps(paramModel);
		int result = this.uupmAccountService.update(paramModel, entityOld);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		UupmAccountEntity entity = new UupmAccountEntity();
		entity.setId(id);
		int result = this.uupmAccountService.delete(entity);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
