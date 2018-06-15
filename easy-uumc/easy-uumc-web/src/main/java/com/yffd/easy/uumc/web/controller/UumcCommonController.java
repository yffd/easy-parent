package com.yffd.easy.uumc.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.mvc.model.RespModel;
import com.yffd.easy.framework.web.mvc.model.easyui.ComboboxModel;
import com.yffd.easy.uumc.web.model.CombotreeModel;
import com.yffd.easy.uumc.web.model.factory.UumcComboboxModelFactory;
import com.yffd.easy.uumc.web.model.factory.UumcCombotreeModelFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:29:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uumc/common")
public class UumcCommonController extends UumcWebController {

	@Autowired
	private UumcComboboxModelFactory comboboxModelFactory;
	@Autowired
	private UumcCombotreeModelFactory combotreeModelFactory;
	
	@RequestMapping(value="/listComboboxData", method=RequestMethod.POST)
	public RespModel listComboboxData(@RequestParam Map<String, Object> paramMap) {
		String keyCodes = (String) paramMap.get("keyCodes");
		if (EasyStringCheckUtils.isEmpty(keyCodes)) return this.errorAjax("参数无效");
		Map<String, List<ComboboxModel>> resultMap = this.comboboxModelFactory.getComboboxData(keyCodes);
		return this.successAjax(resultMap);
	}
	
	@RequestMapping(value="/listCombotreeData", method=RequestMethod.POST)
	public RespModel listCombotreeData(@RequestParam Map<String, Object> paramMap) {
		String keyCodes = (String) paramMap.get("keyCodes");
		if (EasyStringCheckUtils.isEmpty(keyCodes)) return this.errorAjax("参数无效");
		Map<String, List<CombotreeModel>> resultMap = this.combotreeModelFactory.getCombotreeData(keyCodes);
		return this.successAjax(resultMap);
	}
}

