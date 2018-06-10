package com.yffd.easy.uupm.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年04月10日 17时19分46秒 <br/>
 * @author		 ZhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/ui/tree")
public class UupmUITreeController extends UupmBaseController {

//	@Autowired
//	private UupmUITreeService uupmUITreeService;
//	@Autowired
//	private UupmUITreeFactory uupmUITreeFactory;
//	
//	@RequestMapping(value="/listRoot", method=RequestMethod.POST)
//	public RespData listRoot(@RequestParam Map<String, Object> paramMap) {
//		PageParam paramPage = this.getPageParam(paramMap);
//		UupmUITreeEntity model = new UupmUITreeEntity();
//		model.setParentCode("root");
//		PageResult<UupmUITreeEntity> pageResult = this.uupmUITreeService.findPage(model, paramPage, getLoginInfo());
//		DataGridVo dataGridVO = this.toDataGrid(pageResult);
//		return this.successAjax(dataGridVO);
//		
//	}
//	
//	@RequestMapping(value="/listTree", method=RequestMethod.POST)
//	public RespData listTree(@RequestParam Map<String, Object> paramMap) {
//		String treeId = (String) paramMap.get("treeId");
//		if(EasyStringCheckUtils.isEmpty(treeId)) return this.successAjax();
//		UupmUITreeEntity model = new UupmUITreeEntity();
//		model.setTreeId(treeId);
//		List<UupmUITreeEntity> listResult = this.uupmUITreeService.findList(model, getLoginInfo());
//		if(null!=listResult && !listResult.isEmpty()) {
//			List<UupmUIComboTreeVo> treeList = this.uupmUITreeFactory.buildMultiTree(listResult, treeId);
//			return this.successAjax(treeList);
//		}
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/addRoot", method=RequestMethod.POST)
//	public RespData addRoot(UupmUITreeEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getDataCode())) return this.errorAjax("参数无效");
//		UupmUITreeEntity model = new UupmUITreeEntity();	// 存在判断
//		model.setDataCode(paramModel.getDataCode());
//		boolean exsist = this.uupmUITreeService.exsist(model, getLoginInfo());
//		if(exsist) return this.errorAjax("编号已存在");
//		paramModel.setTreeId(paramModel.getDataCode());	// 设treeId=dataCode
//		paramModel.setParentCode("root");
//		int num = this.uupmUITreeService.save(paramModel, getLoginInfo());
//		if(num>0) {
//			return this.successAjax();
//		} else {
//			return this.errorAjax("添加失败");
//		}
//	}
//	
//	@RequestMapping(value="/add", method=RequestMethod.POST)
//	public RespData add(UupmUITreeEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
//				|| EasyStringCheckUtils.isEmpty(paramModel.getDataCode())
//				|| EasyStringCheckUtils.isEmpty(paramModel.getParentCode())) return this.errorAjax("参数无效");
//		UupmUITreeEntity model = new UupmUITreeEntity();	// 存在判断
//		model.setDataCode(paramModel.getDataCode());		// 编号全局唯一
//		boolean exsist = this.uupmUITreeService.exsist(model, getLoginInfo());
//		if(exsist) return this.errorAjax("编号已存在");	
//		int result = this.uupmUITreeService.save(paramModel, getLoginInfo());
//		if(result==0) return this.errorAjax("添加失败");
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/update", method=RequestMethod.POST)
//	public RespData update(UupmUITreeEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getTreeId()) 
//				|| EasyStringCheckUtils.isEmpty(paramModel.getDataCode())) return this.errorAjax("参数无效");
//		UupmUITreeEntity oldModel = new UupmUITreeEntity();
//		oldModel.setTreeId(paramModel.getTreeId());
//		oldModel.setDataCode(paramModel.getDataCode());
//		int result = this.uupmUITreeService.update(paramModel, oldModel, getLoginInfo());
//		if(result==0) return this.errorAjax("修改失败");
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/delTree", method=RequestMethod.POST)
//	public RespData delTree(@RequestParam Map<String, Object> paramMap) {
//		String treeId = (String) paramMap.get("treeId");
//		if(EasyStringCheckUtils.isEmpty(treeId)) return this.errorAjax("参数无效");
//		UupmUITreeEntity model = new UupmUITreeEntity();
//		model.setTreeId(treeId);
//		int result = this.uupmUITreeService.delete(model, getLoginInfo());
//		if(result==0) return this.errorAjax("删除失败");
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
//	public RespData delByIds(@RequestParam Map<String, Object> paramMap) {
//		String ids = (String) paramMap.get("ids");
//		if(EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
//		int result = this.uupmUITreeService.deleteByIds(ids);
//		if(result==0) return this.errorAjax("删除失败");
//		return this.successAjax();
//	}
	
}
