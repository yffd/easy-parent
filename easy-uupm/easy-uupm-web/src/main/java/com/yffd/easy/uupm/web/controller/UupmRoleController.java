package com.yffd.easy.uupm.web.controller;

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
import com.yffd.easy.framework.web.view.vo.DataGridVO;
import com.yffd.easy.uupm.entity.UupmRoleEntity;
import com.yffd.easy.uupm.service.UupmRoleService;
import com.yffd.easy.uupm.web.common.UupmCommonController;


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
public class UupmRoleController extends UupmCommonController {

	@Autowired
	private UupmRoleService uupmRoleService;
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		PageParam pageParam = this.getPageParam(paramMap);
		PageResult<UupmRoleEntity> pageResult = this.uupmRoleService.findPage(null, paramMap, pageParam);
		DataGridVO dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/findOne", method=RequestMethod.POST)
	public RespData findOne(UupmRoleEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmRoleEntity result = this.uupmRoleService.findOne(model);
		return this.successAjax(result);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmRoleEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getRoleCode())) return this.error("参数无效");
		UupmRoleEntity paramModel = new UupmRoleEntity();
		paramModel.setRoleCode(model.getRoleCode());
		UupmRoleEntity resultModel = this.uupmRoleService.findOne(paramModel);
		if(null!=resultModel) return this.error("编号已存在");
		int result = this.uupmRoleService.save(model);
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmRoleEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmRoleEntity old = new UupmRoleEntity();
		old.setId(model.getId());
		int result = this.uupmRoleService.update(model, old, null);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		int result = this.uupmRoleService.deleteByPrimaryId(id);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(ids)) return this.error("参数无效");
		String[] idsArr = ids.split(",");
		int result = this.uupmRoleService.deleteByPrimaryIds(idsArr);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
