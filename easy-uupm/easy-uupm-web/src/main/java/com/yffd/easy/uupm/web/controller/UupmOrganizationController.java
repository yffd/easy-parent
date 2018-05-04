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

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.uupm.entity.UupmOrganizationEntity;
import com.yffd.easy.uupm.service.UupmOrganizationService;
import com.yffd.easy.uupm.web.common.UupmCommonController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年03月30日 11时47分01秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/organization")
public class UupmOrganizationController extends UupmCommonController {

	@Autowired
	private UupmOrganizationService uupmOrganizationService;
	
	@RequestMapping(value="/findTree", method=RequestMethod.POST)
	public RespData findTree(@RequestParam Map<String, Object> paramMap) {
		List<UupmOrganizationEntity> result = this.uupmOrganizationService.findList(null, paramMap);
		if(null!=result && !result.isEmpty()) {
			String jsonTree = this.uupmOrganizationService.getTreeJsonBuilder()
					.buildTreeJson(UupmOrganizationEntity.class, "orgCode", "parentCode", "root", result);
			return this.successAjax(jsonTree);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/findOne", method=RequestMethod.POST)
	public RespData findOne(UupmOrganizationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmOrganizationEntity result = this.uupmOrganizationService.findOne(model);
		return this.successAjax(result);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmOrganizationEntity model) {
		if(null==model) return this.error("参数无效");
		// 存在校验
		UupmOrganizationEntity paramModel = new UupmOrganizationEntity();
		paramModel.setOrgCode(model.getOrgCode());
		UupmOrganizationEntity result = this.uupmOrganizationService.findOne(paramModel);
		if(null!=result) return this.error("数据已存在");
		this.uupmOrganizationService.addOne(model);
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmOrganizationEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) {
			return this.error("参数无效");
		}
		UupmOrganizationEntity paramOld = new UupmOrganizationEntity();
		paramOld.setId(model.getId());
		this.uupmOrganizationService.update(model, paramOld, null);
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
		this.uupmOrganizationService.deleteBy("id", id);
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(ids)) return this.error("参数无效");
		String[] idsArr = ids.split(",");
		List<String> idsList = Arrays.asList(idsArr);
		int result = this.uupmOrganizationService.delete("idList", idsList);
		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
