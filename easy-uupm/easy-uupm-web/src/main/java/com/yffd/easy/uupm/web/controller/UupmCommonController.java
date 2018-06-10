package com.yffd.easy.uupm.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.web.model.RespModel;
import com.yffd.easy.uupm.web.base.UupmBaseController;
import com.yffd.easy.uupm.web.model.UupmComboboxVo;
import com.yffd.easy.uupm.web.model.factory.UupmComboboxVoFactory;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月8日 下午5:29:07 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@RestController
@RequestMapping("/uupm/common")
public class UupmCommonController extends UupmBaseController {

	@Autowired
	private UupmComboboxVoFactory comboboxVoFactory;
	
	@RequestMapping(value="/listComboboxData", method=RequestMethod.POST)
	public RespModel listComboboxData(@RequestParam Map<String, Object> paramMap) {
		String keyCodes = (String) paramMap.get("keyCodes");
		if (EasyStringCheckUtils.isEmpty(keyCodes)) return this.errorAjax("参数无效");
		Map<String, List<UupmComboboxVo>> resultMap = this.comboboxVoFactory.getComboboxData(keyCodes);
		return this.successAjax(resultMap);
	}
}

