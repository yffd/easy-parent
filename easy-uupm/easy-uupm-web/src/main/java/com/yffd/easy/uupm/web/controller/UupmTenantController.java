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
import com.yffd.easy.uupm.entity.UupmTenantEntity;
import com.yffd.easy.uupm.service.UupmTenantService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月9日 上午10:09:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/tenant")
public class UupmTenantController extends UupmBaseController {

	@Autowired
	private UupmTenantService uupmTenantService;
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmTenantEntity paramModel = this.getModelParam(paramMap, UupmTenantEntity.class);
		PageResult<UupmTenantEntity> pageResult = this.uupmTenantService.findPage(paramModel, paramPage);
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmTenantEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTenantCode())) return this.errorAjax("参数无效");
		UupmTenantEntity model = new UupmTenantEntity();
		model.setTenantCode(paramModel.getTenantCode());
		boolean exsist = this.uupmTenantService.exsist(model);
		if(exsist) return this.errorAjax("编号已存在");
		this.initAddProps(paramModel);
		this.uupmTenantService.addTenantWithAccount(paramModel);
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmTenantEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UupmTenantEntity modelOld = new UupmTenantEntity();
		modelOld.setId(paramModel.getId());
		this.initUpdateProps(paramModel);
		int result = this.uupmTenantService.update(paramModel, modelOld);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/del", method=RequestMethod.POST)
	public RespData del(UupmTenantEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UupmTenantEntity model = new UupmTenantEntity();
		model.setId(paramModel.getId());
		int result = this.uupmTenantService.delete(model);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}

