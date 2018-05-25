package com.yffd.easy.uupm.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.UupmMenuEntity;
import com.yffd.easy.uupm.pojo.factory.UupmMenuInfoFactory;
import com.yffd.easy.uupm.pojo.vo.UupmMenuInfoVo;
import com.yffd.easy.uupm.pojo.vo.UupmTreeMenuVo;
import com.yffd.easy.uupm.service.UupmMenuService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月08日 16时49分21秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/menu")
public class UupmMenuController extends UupmBaseController {
	
	@Autowired
	private UupmMenuInfoFactory uupmMenuInfoModelFactory;
	@Autowired
	private UupmMenuService uupmMenuService;
	
	@RequestMapping(value="/findMenuTree", method=RequestMethod.POST)
	public RespData findMenuTree() {
		// TODO 租户编号
		String tenantCode = "admin";
		String parentCode = null;//"root";
		List<UupmMenuInfoVo> result = this.uupmMenuService.findMenuList(tenantCode, parentCode);
		if(null!=result && !result.isEmpty()) {
			List<UupmTreeMenuVo> treeList = this.uupmMenuInfoModelFactory.buildMultiTree(result);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/findOne", method=RequestMethod.POST)
	public RespData findOne(UupmMenuEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmMenuEntity result = this.uupmMenuService.findOne(model);
		return this.successAjax(result);
	}
	
	@RequestMapping(value="/saveMenuForAdmin", method=RequestMethod.POST)
	public RespData saveMenuForAdmin() {
//		String tenantCode = "admin";
//		this.uupmMenuService.addMenuForAdmin(tenantCode);
		return this.successAjax();
	}
	
	@RequestMapping(value="/saveMenuForOther", method=RequestMethod.POST)
	public RespData saveMenuForOther() {
//		String tenantCode = "nuoyuan";
//		this.uupmMenuService.addMenuForOther(tenantCode);
		return this.successAjax();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmMenuEntity model) {
		if(null==model) return this.error("参数无效");
		// 存在校验
		UupmMenuEntity paramModel = new UupmMenuEntity();
		// TODO 租户编号
		paramModel.setTenantCode("admin");
		paramModel.setMenuCode(model.getMenuCode());
		UupmMenuEntity resultModel = this.uupmMenuService.findOne(paramModel);
		if(null!=resultModel) return this.error("数据已存在");
		int result = this.uupmMenuService.save(model);
		if(result==0) return this.error("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public RespData edit(UupmMenuEntity model) {
		if(null==model || EasyStringCheckUtils.isEmpty(model.getId())) return this.error("参数无效");
		UupmMenuEntity modelOld = new UupmMenuEntity();
		modelOld.setId(model.getId());
		int result = this.uupmMenuService.update(model, modelOld);
		if(result==0) return this.error("更新失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delById", method=RequestMethod.POST)
	public RespData delById(String id) {
		if(EasyStringCheckUtils.isEmpty(id)) return this.errorAjax("参数无效");
//		int result = this.uupmMenuService.deleteById(id);
//		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delBatch", method=RequestMethod.POST)
	public RespData delBatch(HttpServletRequest req) {
		String idStr = req.getParameter("ids");
		if(EasyStringCheckUtils.isEmpty(idStr)) return this.error("参数无效");
//		int result = this.uupmMenuService.deleteByIds(idStr);
//		if(result==0) return this.error("删除失败");
		return this.successAjax();
	}
	
}
