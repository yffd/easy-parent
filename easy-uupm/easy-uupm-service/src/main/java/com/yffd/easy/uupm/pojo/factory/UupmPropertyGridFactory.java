package com.yffd.easy.uupm.pojo.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.framework.pojo.vo.PropertyGridVo;
import com.yffd.easy.uupm.entity.a.UupmApplicationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:35:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmPropertyGridFactory {
	
	public List<PropertyGridVo> createPropertyGridForApp(UupmApplicationEntity model) {
		if(null==model) return null;
		List<PropertyGridVo> retList = new ArrayList<PropertyGridVo>();
		String appDomain = model.getAppDomain();
		PropertyGridVo appDomainVO = new PropertyGridVo("appDomain", "域名", appDomain, "appId", "text");
		retList.add(appDomainVO);
		String appPort = model.getAppPort();
		PropertyGridVo appPortVO = new PropertyGridVo("appPort", "端口", appPort, "appId", "text");
		retList.add(appPortVO);
		String appContextPath = model.getAppContextPath();
		PropertyGridVo appContextPathVO = new PropertyGridVo("appContextPath", "应用上下文", appContextPath, "appId", "text");
		retList.add(appContextPathVO);
		return retList;
	}
}

