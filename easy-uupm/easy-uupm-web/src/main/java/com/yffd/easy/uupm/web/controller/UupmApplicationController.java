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
import com.yffd.easy.framework.web.view.vo.DataGridVO;
import com.yffd.easy.framework.web.view.vo.PropertyGridVO;
import com.yffd.easy.uupm.entity.UupmApplicationEntity;
import com.yffd.easy.uupm.service.UupmApplicationService;
import com.yffd.easy.uupm.web.common.UupmCommonController;
import com.yffd.easy.uupm.web.support.UupmModePropertyGridSupport;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月06日 14时09分38秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/application")
public class UupmApplicationController extends UupmCommonController {
	@Autowired
	private UupmApplicationService uupmApplicationService;
	@Autowired
	private UupmModePropertyGridSupport uupmModePropertyGridSupport;
	
	@RequestMapping(value="/findAppCfg", method=RequestMethod.POST)
	public RespData findAppCfg(@RequestParam Map<String, Object> paramMap) {
		String appCode = (String) paramMap.get("appCode");
		if(EasyStringCheckUtils.isEmpty(appCode)) return this.error("参数无效");
		UupmApplicationEntity model = new UupmApplicationEntity();
		model.setAppCode(appCode);
		UupmApplicationEntity result = this.uupmApplicationService.findOne(model );
		if(null==result) result = new UupmApplicationEntity();
		List<PropertyGridVO> listResult = this.uupmModePropertyGridSupport.toAppPropertyGridVo(result);
		DataGridVO dataGridVO = this.toDataGrid(listResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/saveAppCfg", method=RequestMethod.POST)
	public RespData saveAppCfg(UupmApplicationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getAppCode())) return this.error("参数无效");
		int result = this.uupmApplicationService.saveAppCfg(model);
		if(result==0) return this.error("保存配置失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		PageResult<UupmApplicationEntity> pageResult = this.uupmApplicationService.findPage(null, paramMap, paramPage);
		DataGridVO dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/findOne", method=RequestMethod.POST)
	public RespData findOne(UupmApplicationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmApplicationEntity result = this.uupmApplicationService.findOne(model);
		return this.successAjax(result);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmApplicationEntity model) {
		if(null==model) return this.error("参数无效");
		// 存在校验
		UupmApplicationEntity paramModel = new UupmApplicationEntity();
		paramModel.setTenantCode(model.getTenantCode());
		UupmApplicationEntity resultModel = this.uupmApplicationService.findOne(paramModel);
		if(null!=resultModel) return this.error("数据已存在");
		int result = this.uupmApplicationService.addOne(model);
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmApplicationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmApplicationEntity paramOld = new UupmApplicationEntity();
		paramOld.setId(model.getId());
		int result = this.uupmApplicationService.update(model, paramOld, null);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		int result = this.uupmApplicationService.deleteBy("id", id);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(ids)) return this.error("参数无效");
		String[] idsArr = ids.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		int result = this.uupmApplicationService.delete("id", idsList);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
