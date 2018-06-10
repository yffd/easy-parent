package com.yffd.easy.uupm.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.uupm.web.base.UupmBaseController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年3月7日 下午2:44:55 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/ui")
public class UupmUIController extends UupmBaseController {
	
//	@Autowired
//	private UupmUITreeService uupmUITreeService;
//	@Autowired
//	private UupmOrganizationService uupmOrganizationService;
//	@Autowired
//	private UupmUIFactory uupmComboTreeModelFactory;
//	
//	@RequestMapping(value="/listComboTree", method=RequestMethod.POST)
//	public RespData listComboTree(@RequestParam Map<String, Object> paramMap) {
//		String treeIds = (String) paramMap.get("treeIds");
//		if(EasyStringCheckUtils.isEmpty(treeIds)) return this.error("参数无效");
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String[] treeIdArr = treeIds.split(",");
//		for(String treeId : treeIdArr) {
//			UupmUITreeEntity model = new UupmUITreeEntity();
//			model.setTreeId(treeId);
//			List<UupmUITreeEntity> listResult = this.uupmUITreeService.findList(model, getLoginInfo());
//			if(null==listResult || listResult.size()==0) continue;
//			List<UupmEasyuiTreeVo> treeList = this.uupmComboTreeModelFactory.buildComboTree(listResult, treeId);
//			resultMap.put(treeId, treeList);
//		}
//		return this.successAjax(resultMap);
//	}
//	
//	@RequestMapping(value="/listOrgComboTree", method=RequestMethod.POST)
//	public RespData listOrgComboTree() {
//		UupmOrganizationEntity entity = new UupmOrganizationEntity();
//		List<UupmOrganizationEntity> listResult = this.uupmOrganizationService.findList(entity, getLoginInfo());
//		if(null!=listResult && !listResult.isEmpty()) {
//			List<UupmEasyuiTreeVo> treeList = this.uupmComboTreeModelFactory.buildOrgComboTree(listResult);
//			return this.successAjax(treeList);
//		}
//		return this.successAjax();
//	}
	
}

