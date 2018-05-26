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
	
//	@RequestMapping(value="/findPage", method=RequestMethod.POST)
//	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
//		PageParam paramPage = this.getPageParam(paramMap);
//		UupmUserInfoVo paramModel = this.getModelParam(paramMap, UupmUserInfoVo.class);
//		paramModel.setTenantCode(this.getLoginInfo().getTenantCode());	// 租户信息
//		PageResult<UupmUserInfoVo> pageResult = this.uupmUserService.findUserInfoPage(paramModel, paramPage);
//		DataGridVo dataGridVO = this.toDataGrid(pageResult);
//		return this.successAjax(dataGridVO);
//	}
//	
//	@RequestMapping(value="/add", method=RequestMethod.POST)
//	public RespData add(UupmUserEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getUserCode())) return this.errorAjax("参数无效");
//		paramModel.setTenantCode(this.getLoginInfo().getTenantCode());	// 租户信息
//		UupmUserEntity model = new UupmUserEntity();	// 存在校验
//		model.setTenantCode(paramModel.getTenantCode());
//		model.setUserCode(paramModel.getUserCode());
//		boolean exsist = this.uupmUserService.exsist(model);
//		if(exsist) return this.errorAjax("编号已存在");
//		int result = this.uupmUserService.addUserWithAccount(paramModel);
//		if(result==0) return this.errorAjax("添加失败");
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/edit", method=RequestMethod.POST)
//	public RespData edit(UupmUserEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
//		UupmUserEntity modelOld = new UupmUserEntity();
//		modelOld.setId(paramModel.getId());
//		int result = this.uupmUserService.update(paramModel, modelOld);
//		if(result==0) return this.error("更新失败");
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/del", method=RequestMethod.POST)
//	public RespData del(UupmUserEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
//		UupmUserEntity model = new UupmUserEntity();
//		model.setId(paramModel.getId());
//		int result = this.uupmUserService.delete(model);
//		if(result==0) return this.errorAjax("删除失败");
//		return this.successAjax();
//	}
	
}
