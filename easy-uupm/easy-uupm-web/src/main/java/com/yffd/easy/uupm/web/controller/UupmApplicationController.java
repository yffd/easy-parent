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
import com.yffd.easy.framework.web.model.easyui.EasyuiDataGridModel;
import com.yffd.easy.uupm.pojo.entity.UupmAppSystemEntity;
import com.yffd.easy.uupm.service.UupmAppSystemService;
import com.yffd.easy.uupm.web.base.UupmBaseController;
import com.yffd.easy.uupm.web.model.UupmPropertyGridVo;
import com.yffd.easy.uupm.web.model.factory.UupmPropertyGridVoFactory;

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
public class UupmApplicationController extends UupmBaseController {
	@Autowired
	private UupmAppSystemService uupmAppSystemService;
	@Autowired
	private UupmPropertyGridVoFactory uupmPropertyGridModelFactory;
	
	@RequestMapping(value="/findAppCfg", method=RequestMethod.POST)
	public RespModel findAppCfg(@RequestParam Map<String, Object> paramMap) {
		String appCode = (String) paramMap.get("appCode");
		if(EasyStringCheckUtils.isEmpty(appCode)) return this.errorAjax("参数无效");
		UupmAppSystemEntity entity = new UupmAppSystemEntity();
		entity.setAppCode(appCode);
		UupmAppSystemEntity result = this.uupmAppSystemService.findOne(entity, getLoginInfo());
		if(null==result) result = new UupmAppSystemEntity();
		List<UupmPropertyGridVo> listResult = this.uupmPropertyGridModelFactory.createPropertyGridForApp(result);
		EasyuiDataGridModel dataGridVO = this.toDataGrid(listResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/saveAppCfg", method=RequestMethod.POST)
	public RespModel saveAppCfg(UupmAppSystemEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAppCode())) return this.error("参数无效");
		int result = this.uupmAppSystemService.saveAppCfg(paramModel, getLoginInfo());
		if(result==0) return this.error("保存配置失败");
		return this.successAjax();
	}
	
}
