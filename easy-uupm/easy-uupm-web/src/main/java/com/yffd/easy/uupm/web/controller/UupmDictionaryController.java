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
@RequestMapping("/uupm/dictionary")
public class UupmDictionaryController extends UupmBaseController {

//	@Autowired
//	private UupmDictionaryService uupmDictionaryService;
//	@Autowired
//	private UupmDictionaryFactory uupmDictionaryModelFactory;
//	
//	@RequestMapping(value="/listCategory", method=RequestMethod.POST)
//	public RespData listCategory(@RequestParam Map<String, Object> paramMap) {
//		PageParam paramPage = this.getPageParam(paramMap);
//		UupmDictionaryEntity entity = new UupmDictionaryEntity();
//		entity.setTenantCode(this.getLoginInfo().getTenantCode());
//		entity.setParentCode("root");
//		PageResult<UupmDictionaryEntity> pageResult = this.uupmDictionaryService.findPage(entity, paramPage);
//		DataGridVo dataGridVO = this.toDataGrid(pageResult);
//		return this.successAjax(dataGridVO);
//		
//	}
//	
//	@RequestMapping(value="/listCategoryTree", method=RequestMethod.POST)
//	public RespData listCategoryTree(@RequestParam Map<String, Object> paramMap) {
//		String category = (String) paramMap.get("category");
//		if(EasyStringCheckUtils.isEmpty(category)) return this.successAjax();
//		UupmDictionaryEntity entity = new UupmDictionaryEntity();
//		entity.setTenantCode(this.getLoginInfo().getTenantCode());
//		entity.setCategory(category);
//		List<UupmDictionaryEntity> listResult = this.uupmDictionaryService.findList(entity);
//		if(null!=listResult && !listResult.isEmpty()) {
//			List<UupmTreeDictVo> treeList = this.uupmDictionaryModelFactory.buildMultiTree(listResult);
//			return this.successAjax(treeList);
//		}
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/addCategory", method=RequestMethod.POST)
//	public RespData addCategory(UupmDictionaryEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getKeyCode())) return this.errorAjax("参数无效");
//		UupmDictionaryEntity entity = new UupmDictionaryEntity();	// 存在判断
//		entity.setTenantCode(this.getLoginInfo().getTenantCode());
//		entity.setKeyCode(paramModel.getKeyCode());
//		boolean exsist = this.uupmDictionaryService.exsist(entity);
//		if(exsist) return this.errorAjax("编号已存在");
//		paramModel.setCategory(paramModel.getKeyCode());
//		this.initAddProps(paramModel);
//		this.uupmDictionaryService.save(paramModel);
//		return this.successAjax();
//	}
//	
//	@RequestMapping(value="/add", method=RequestMethod.POST)
//	public RespData add(UupmDictionaryEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getKeyCode())
//				|| EasyStringCheckUtils.isEmpty(paramModel.getParentCode())
//				|| EasyStringCheckUtils.isEmpty(paramModel.getCategory())) return this.errorAjax("参数无效");
//		UupmDictionaryEntity entity = new UupmDictionaryEntity();	// 存在判断
//		entity.setTenantCode(this.getLoginInfo().getTenantCode());
//		entity.setKeyCode(paramModel.getKeyCode());
//		boolean exsist = this.uupmDictionaryService.exsist(entity);
//		if(exsist) return this.errorAjax("编号已存在");	
//		this.initUpdateProps(paramModel);
//		int num = this.uupmDictionaryService.save(paramModel);
//		if(num>0) {
//			return this.successAjax();
//		} else {
//			return this.errorAjax("添加失败");
//		}
//	}
//	
//	@RequestMapping(value="/update", method=RequestMethod.POST)
//	public RespData update(UupmDictionaryEntity paramModel) {
//		if(EasyStringCheckUtils.isEmpty(paramModel.getKeyCode())) return this.errorAjax("参数无效");
//		UupmDictionaryEntity entityOld = new UupmDictionaryEntity();
//		entityOld.setTenantCode(this.getLoginInfo().getTenantCode());
//		entityOld.setKeyCode(paramModel.getKeyCode());
//		this.initUpdateProps(paramModel);
//		int num = this.uupmDictionaryService.update(paramModel, entityOld);
//		if(num>0) {
//			return this.successAjax();
//		} else {
//			return this.errorAjax("修改失败");
//		}
//	}
//	
//	@RequestMapping(value="/delByIds", method=RequestMethod.POST)
//	public RespData delByIds(@RequestParam Map<String, Object> paramMap) {
//		String ids = (String) paramMap.get("ids");
//		if(EasyStringCheckUtils.isEmpty(ids)) return this.errorAjax("参数无效");
//		int num = this.uupmDictionaryService.deleteByIds(ids);
//		if(num>0) {
//			return this.successAjax();
//		} else {
//			return this.errorAjax("删除失败");
//		}
//	}
	
}
