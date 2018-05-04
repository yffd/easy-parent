package com.yffd.easy.uupm.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;
import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.framework.web.shiro.password.PasswordEncrypt;
import com.yffd.easy.framework.web.view.vo.DataGridVO;
import com.yffd.easy.uupm.entity.UupmAccountEntity;
import com.yffd.easy.uupm.service.UupmAccountService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

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
public class UupmAccountController extends UupmCommonController {

	@Autowired
	private UupmAccountService uupmAccountService;
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		paramMap.put("accountType", "admin");
		PageResult<UupmAccountEntity> pageResult = this.uupmAccountService.findPage(null, paramMap, paramPage);
		DataGridVO dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/findOne", method=RequestMethod.POST)
	public RespData findOne(UupmAccountEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmAccountEntity result = this.uupmAccountService.findOne(model);
		return this.successAjax(result);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmAccountEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getAccountId())
				|| EasyStringCheckUtils.isEmpty(model.getAccountPwd())) return this.error("参数无效");
		// 存在校验
		UupmAccountEntity paramModel = new UupmAccountEntity();
		paramModel.setTenantCode(model.getTenantCode());
		paramModel.setAccountId(model.getAccountId());
		UupmAccountEntity resultModel = this.uupmAccountService.findOne(paramModel);
		if(null!=resultModel) return this.error("数据已存在");
		// 生成盐串和密码串
		String salt = PasswordEncrypt.getRandomSalt();
		String encryptPwd = PasswordEncrypt.getEncryptPassword(model.getAccountId(), model.getAccountPwd(), salt);
		
		int result = this.uupmAccountService.addAccount(model, encryptPwd, salt);
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmAccountEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmAccountEntity paramOld = new UupmAccountEntity();
		paramOld.setId(model.getId());
		int result = this.uupmAccountService.update(model, paramOld, null);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		int result = this.uupmAccountService.deleteBy("id", id);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(ids)) return this.error("参数无效");
		String[] idsArr = ids.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		int result = this.uupmAccountService.delete("id", idsList);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
