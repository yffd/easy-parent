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
import com.yffd.easy.uupm.entity.UupmTenantEntity;
import com.yffd.easy.uupm.service.UupmTenantService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

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
public class UupmTenantController extends UupmCommonController {

	@Autowired
	private UupmTenantService uupmTenantService;
	
	@RequestMapping(value="/findPage", method=RequestMethod.POST)
	public RespData findPage(@RequestParam Map<String, Object> paramMap) {
		PageParam pageParam = this.getPageParam(paramMap);
		PageResult<UupmTenantEntity> pageResult = this.uupmTenantService.findPage(null, paramMap, pageParam);
		DataGridVO dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmTenantEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getTenantCode())) return this.error("参数无效");
		UupmTenantEntity paramModel = new UupmTenantEntity();
		paramModel.setTenantCode(model.getTenantCode());
		UupmTenantEntity hasModel = (UupmTenantEntity) this.uupmTenantService.findOne(paramModel);
		if(null!=hasModel) return this.errorAjax("租户编号已存在");
//		this.uupmTenantService.addOne(model);
		this.uupmTenantService.addTenantWithAccount(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmTenantEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) {
			return this.error("参数无效");
		}
		UupmTenantEntity old = new UupmTenantEntity();
		old.setId(model.getId());
		int result = this.uupmTenantService.update(model, old, null);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		int result = this.uupmTenantService.deleteBy("id", id);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(ids)) return this.error("参数无效");
		String[] idsArr = ids.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		int result = this.uupmTenantService.delete("idList", idsList);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
}

