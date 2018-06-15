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
import com.yffd.easy.uumc.pojo.entity.UumcAccountEntity;
import com.yffd.easy.uumc.pojo.entity.UumcUserEntity;
import com.yffd.easy.uumc.service.UumcAccountService;

import com.yffd.easy.uumc.web.controller.UumcWebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年06月15日 16时55分49秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/account")
public class UumcAccountController extends UumcWebController {

	@Autowired
	private UumcAccountService uumcAccountService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespModel listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UumcAccountEntity paramModel = this.getModelParam(paramMap, UumcAccountEntity.class);
		PageResult<UumcAccountEntity> pageResult = this.uumcAccountService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridModel dataGrid = this.toDataGrid(pageResult);
		return this.successAjax(dataGrid);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespModel add(UumcAccountEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAcntId())) return this.errorAjax("参数无效");
		UumcAccountEntity model = new UumcAccountEntity();	// 存在校验
		model.setAcntId(paramModel.getAcntId());
		boolean exsist = this.uumcAccountService.exsist(model, getLoginInfo());
		if (exsist) return this.errorAjax("编号已存在");
		int result = this.uumcAccountService.save(paramModel, getLoginInfo());
		if (result == 0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespModel update(UumcAccountEntity paramModel) {
		if (null == paramModel || EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UumcAccountEntity oldModel = new UumcAccountEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uumcAccountService.update(paramModel, oldModel, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespModel delById(String id) {
		if (EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		UumcAccountEntity model = new UumcAccountEntity();
		model.setId(id);
		int result = this.uumcAccountService.delete(model, getLoginInfo());
		if (result == 0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	
	@RequestMapping(value="/updateStatus", method=RequestMethod.POST)
	public RespModel updateStatus(UumcAccountEntity paramModel) {
		if (EasyStringCheckUtils.isEmpty(paramModel.getAcntId()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getAcntStatus())) return this.errorAjax("参数无效");
		int result = this.uumcAccountService.updateStatus(paramModel.getAcntId(), paramModel.getAcntStatus(), getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/resetPwd", method=RequestMethod.POST)
	public RespModel resetPwd(String acntId) {
		if (EasyStringCheckUtils.isEmpty(acntId)) return this.errorAjax("参数无效");
		int result = this.uumcAccountService.resetUserAccount(acntId, getLoginInfo());
		if (result == 0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
}
