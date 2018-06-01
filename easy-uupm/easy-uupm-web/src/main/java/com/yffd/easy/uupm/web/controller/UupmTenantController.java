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
	
	@RequestMapping(value="/listPage", method=RequestMethod.POST)
	public RespData listPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmTenantEntity paramModel = this.getModelParam(paramMap, UupmTenantEntity.class);
		PageResult<UupmTenantEntity> pageResult = this.uupmTenantService.findPage(paramModel, paramPage, getLoginInfo());
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmTenantEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTtName())
				|| EasyStringCheckUtils.isEmpty(paramModel.getPinyin())
				|| EasyStringCheckUtils.isEmpty(paramModel.getShortPinyin())) return this.errorAjax("参数无效");
		paramModel.setTtCode(paramModel.getShortPinyin());	// 设置 ttCode = shortPinyin
		UupmTenantEntity model = new UupmTenantEntity();
		model.setTtCode(paramModel.getTtCode());
		boolean exsist = this.uupmTenantService.exsist(model, getLoginInfo());
		if(exsist) return this.errorAjax("编号已存在");
		int result = this.uupmTenantService.addTenantWithAccount(paramModel, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmTenantEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getId())) return this.errorAjax("参数无效");
		UupmTenantEntity oldModel = new UupmTenantEntity();
		oldModel.setId(paramModel.getId());
		int result = this.uupmTenantService.update(paramModel, oldModel, getLoginInfo());
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByTtCode", method=RequestMethod.POST)
	public RespData delByTtCode(String ttCode) {
		if(EasyStringCheckUtils.isEmpty(ttCode)) return this.errorAjax("参数无效");
		int result = this.uupmTenantService.delByTtCode(ttCode, getLoginInfo());
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}

