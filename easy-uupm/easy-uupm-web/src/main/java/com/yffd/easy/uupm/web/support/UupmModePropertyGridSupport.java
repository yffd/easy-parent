package com.yffd.easy.uupm.web.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.framework.web.view.vo.PropertyGridVO;
import com.yffd.easy.uupm.entity.UupmApplicationEntity;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年4月12日 下午4:10:54 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmModePropertyGridSupport {

	public List<PropertyGridVO> toAppPropertyGridVo(UupmApplicationEntity model) {
		if(null==model) return null;
		List<PropertyGridVO> retList = new ArrayList<PropertyGridVO>();
		String appDomain = model.getAppDomain();
		PropertyGridVO appDomainVO = new PropertyGridVO("appDomain", "域名", appDomain, "appId", "text");
		retList.add(appDomainVO);
		String appPort = model.getAppPort();
		PropertyGridVO appPortVO = new PropertyGridVO("appPort", "端口", appPort, "appId", "text");
		retList.add(appPortVO);
		String appContextPath = model.getAppContextPath();
		PropertyGridVO appContextPathVO = new PropertyGridVO("appContextPath", "应用上下文", appContextPath, "appId", "text");
		retList.add(appContextPathVO);
		return retList;
	}
}

