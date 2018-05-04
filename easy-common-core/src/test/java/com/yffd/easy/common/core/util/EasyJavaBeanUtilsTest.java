package com.yffd.easy.common.core.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.yffd.easy.common.core.model.DemoPO;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月15日 下午4:23:17 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyJavaBeanUtilsTest {

	@Test
	public void map2beanTest() throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("userName", "白牛");
		mapData.put("age", "13");
		mapData.put("sex", false);
		mapData.put("startTime", "2017-12-14");
//		DemoDTO dto = EasyJavaBeanUtils.map2bean(mapData, DemoDTO.class);
//		DemoDTO dto = EasyJavaBeanUtils.copyProperties(mapData, DemoDTO.class);
//		System.out.println(dto);
		
		DemoPO po = EasyJavaBeanUtils.copyProperties(mapData, DemoPO.class);
		System.out.println(po.getStartTime());
		
		
	}
}

