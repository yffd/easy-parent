package com.yffd.easy.uupm.web.controller;

import java.util.List;
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
import com.yffd.easy.uupm.entity.UupmResourceEntity;
import com.yffd.easy.uupm.pojo.factory.UupmResourceFactory;
import com.yffd.easy.uupm.pojo.vo.easyui.UupmUIResTreeVo;
import com.yffd.easy.uupm.service.UupmResourceService;
import com.yffd.easy.uupm.web.base.UupmBaseController;
/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月03日 14时09分26秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/resource")
public class UupmResourceController extends UupmBaseController {

	@Autowired
	private UupmResourceService uupmResourceService;
	@Autowired
	private UupmResourceFactory uupmResourceModelFactory;
	
	@RequestMapping(value="/listRoot", method=RequestMethod.POST)
	public RespData listRoot(@RequestParam Map<String, Object> paramMap) {
		PageParam paramPage = this.getPageParam(paramMap);
		UupmResourceEntity entity = new UupmResourceEntity();
		entity.setParentCode("root");
		PageResult<UupmResourceEntity> pageResult = this.uupmResourceService.findPage(entity, paramPage, getLoginInfo());
		DataGridVo dataGridVO = this.toDataGrid(pageResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/listTree", method=RequestMethod.POST)
	public RespData listTree(@RequestParam Map<String, Object> paramMap) {
		String treeId = (String) paramMap.get("treeId");
		if(EasyStringCheckUtils.isEmpty(treeId)) return this.successAjax();
		UupmResourceEntity entity = new UupmResourceEntity();
		entity.setTreeId(treeId);
		List<UupmResourceEntity> listResult = this.uupmResourceService.findList(entity, getLoginInfo());
		if(null!=listResult && !listResult.isEmpty()) {
			List<UupmUIResTreeVo> treeList = this.uupmResourceModelFactory.buildMultiTree(listResult, treeId);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/listAllTree", method=RequestMethod.POST)
	public RespData listAllTree() {
		List<UupmResourceEntity> listResult = this.uupmResourceService.findAll();
		if(null!=listResult && !listResult.isEmpty()) {
			List<UupmUIResTreeVo> treeList = this.uupmResourceModelFactory.buildMultiTree(listResult);
			return this.successAjax(treeList);
		}
		return this.successAjax();
	}
	
	@RequestMapping(value="/addRoot", method=RequestMethod.POST)
	public RespData addRoot(UupmResourceEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getRsCode())) return this.errorAjax("参数无效");
		UupmResourceEntity entity = new UupmResourceEntity();	// 存在判断
		entity.setRsCode(paramModel.getRsCode());
		boolean exsist = this.uupmResourceService.exsist(entity, getLoginInfo());
		if(exsist) return this.errorAjax("编号已存在");
		paramModel.setTreeId(paramModel.getRsCode());	// 设treeId=rsCode
		paramModel.setParentCode("root");
		int result = this.uupmResourceService.save(paramModel, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public RespData add(UupmResourceEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getRsCode())
				|| EasyStringCheckUtils.isEmpty(paramModel.getParentCode())) return this.errorAjax("参数无效");
		UupmResourceEntity entity = new UupmResourceEntity();	// 存在判断
		entity.setRsCode(paramModel.getRsCode());	// 编号全局唯一
		boolean exsist = this.uupmResourceService.exsist(entity, getLoginInfo());
		if(exsist) return this.error("编号已存在");
		int result = this.uupmResourceService.save(paramModel, getLoginInfo());
		if(result==0) return this.errorAjax("添加失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RespData update(UupmResourceEntity paramModel) {
		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
				|| EasyStringCheckUtils.isEmpty(paramModel.getRsCode())) return this.errorAjax("参数无效");
		UupmResourceEntity entityOld = new UupmResourceEntity();
		entityOld.setTreeId(paramModel.getTreeId());
		entityOld.setRsCode(paramModel.getRsCode());
		int result = this.uupmResourceService.update(paramModel, entityOld, getLoginInfo());
		if(result==0) return this.errorAjax("修改失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delTree", method=RequestMethod.POST)
	public RespData delTree(@RequestParam Map<String, Object> paramMap) {
		String treeId = (String) paramMap.get("treeId");
		if(EasyStringCheckUtils.isEmpty(treeId)) return this.errorAjax("参数无效");
		UupmResourceEntity entity = new UupmResourceEntity();
		entity.setTreeId(treeId);
		int result = this.uupmResourceService.delete(entity, getLoginInfo());
		if(result==0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
	public RespData delByIds(String ids) {
		if(EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
		int result = this.uupmResourceService.deleteByIds(ids);
		if(result==0) return this.errorAjax("删除失败");
		return this.successAjax();
	}
	
}