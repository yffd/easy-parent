package com.yffd.easy.uupm.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yffd.easy.common.core.util.EasyStringCheckUtils;
import com.yffd.easy.framework.pojo.vo.DataGridVo;
import com.yffd.easy.framework.pojo.vo.PropertyGridVo;
import com.yffd.easy.framework.pojo.vo.RespData;
import com.yffd.easy.uupm.entity.UupmApplicationEntity;
import com.yffd.easy.uupm.pojo.factory.UupmPropertyGridFactory;
import com.yffd.easy.uupm.service.UupmApplicationService;
import com.yffd.easy.uupm.web.base.UupmBaseController;

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
	private UupmApplicationService uupmApplicationService;
	@Autowired
	private UupmPropertyGridFactory uupmPropertyGridModelFactory;
	
	@RequestMapping(value="/findAppCfg", method=RequestMethod.POST)
	public RespData findAppCfg(@RequestParam Map<String, Object> paramMap) {
		String appCode = (String) paramMap.get("appCode");
		if(EasyStringCheckUtils.isEmpty(appCode)) return this.errorAjax("参数无效");
		UupmApplicationEntity entity = new UupmApplicationEntity();
		entity.setAppCode(appCode);
		UupmApplicationEntity result = this.uupmApplicationService.findOne(entity, getLoginInfo());
		if(null==result) result = new UupmApplicationEntity();
		List<PropertyGridVo> listResult = this.uupmPropertyGridModelFactory.createPropertyGridForApp(result);
		DataGridVo dataGridVO = this.toDataGrid(listResult);
		return this.successAjax(dataGridVO);
	}
	
	@RequestMapping(value="/saveAppCfg", method=RequestMethod.POST)
	public RespData saveAppCfg(UupmApplicationEntity paramModel) {
		if(null==paramModel || EasyStringCheckUtils.isEmpty(paramModel.getAppCode())) return this.error("参数无效");
		int result = this.uupmApplicationService.saveAppCfg(paramModel, getLoginInfo());
		if(result==0) return this.error("保存配置失败");
		return this.successAjax();
	}
	
}
