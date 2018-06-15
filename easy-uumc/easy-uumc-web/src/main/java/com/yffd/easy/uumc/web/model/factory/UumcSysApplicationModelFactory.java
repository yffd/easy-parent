package com.yffd.easy.uumc.web.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.framework.web.mvc.model.easyui.PropertyGridModel;
import com.yffd.easy.uumc.pojo.entity.UumcSysApplicationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:35:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UumcSysApplicationModelFactory {
	
	public List<PropertyGridModel> fmtPropertyGrid(UumcSysApplicationEntity model) {
		if(null==model) return null;
		List<PropertyGridModel> retList = new ArrayList<PropertyGridModel>();
		String appDomain = model.getAppDomain();
		PropertyGridModel appDomainVO = new PropertyGridModel("appDomain", "域名", appDomain, "appId", "text");
		retList.add(appDomainVO);
		String appPort = model.getAppPort();
		PropertyGridModel appPortVO = new PropertyGridModel("appPort", "端口", appPort, "appId", "text");
		retList.add(appPortVO);
		String appContextPath = model.getAppContextPath();
		PropertyGridModel appContextPathVO = new PropertyGridModel("appContextPath", "应用上下文", appContextPath, "appId", "text");
		retList.add(appContextPathVO);
		return retList;
	}
}

