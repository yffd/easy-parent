package com.yffd.easy.uupm.web.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.yffd.easy.uupm.pojo.entity.UupmAppSystemEntity;
import com.yffd.easy.uupm.web.model.UupmPropertyGridVo;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月16日 下午3:35:29 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Component
public class UupmPropertyGridVoFactory {
	
	public List<UupmPropertyGridVo> createPropertyGridForApp(UupmAppSystemEntity model) {
		if(null==model) return null;
		List<UupmPropertyGridVo> retList = new ArrayList<UupmPropertyGridVo>();
		String appDomain = model.getAppDomain();
		UupmPropertyGridVo appDomainVO = new UupmPropertyGridVo("appDomain", "域名", appDomain, "appId", "text");
		retList.add(appDomainVO);
		String appPort = model.getAppPort();
		UupmPropertyGridVo appPortVO = new UupmPropertyGridVo("appPort", "端口", appPort, "appId", "text");
		retList.add(appPortVO);
		String appContextPath = model.getAppContextPath();
		UupmPropertyGridVo appContextPathVO = new UupmPropertyGridVo("appContextPath", "应用上下文", appContextPath, "appId", "text");
		retList.add(appContextPathVO);
		return retList;
	}
}

