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
import com.yffd.easy.uupm.entity.UupmRoleEntity;
import com.yffd.easy.uupm.service.UupmRoleService;
import com.yffd.easy.uupm.web.base.UupmBaseController;


/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年03月15日 10时02分10秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/role")
public class UupmRoleController extends UupmBaseController {

	@Autowired
	private UupmRoleService uupmRoleService;
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespData listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmRoleEntity paramModel = this.getModelParam(paramMap, UupmRoleEntity.class);
		PageResult<UupmRoleEntity> pageResult = this.uupmRoleService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmRoleEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getRoleCode())) return this.errorAjax("参数无效");
		UupmRoleEntity model = new UupmRoleEntity();
		model.setRoleCode(paramModel.getRoleCode());
		boolean exsist = this.uupmRoleService.exsist(model, getLoginInfo());
		if(exsist) return this.error("编号已存在");
		int result = this.uupmRoleService.save(paramModel, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmRoleEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getRoleCode())) return this.errorAjax("参数无效");
		UupmRoleEntity oldModel = new UupmRoleEntity();
		oldModel.setRoleCode(paramModel.getRoleCode());
		int result = this.uupmRoleService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.errorAjax("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByRoleCode", method=RequestMethod.POST)
	public RespData delByRoleCode(String roleCode) {
		if(EasyStringCheckUtils.isEmpty(roleCode)) return this.errorAjax("参数无效");
		int result = this.uupmRoleService.delByRoleCode(roleCode, getLoginInfo());
		if(result==0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	
}
