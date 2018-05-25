package com.yffd.easy.common.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
	public void copyPropertiesTest() throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("userName", "白牛");
		mapData.put("age", "13");
		mapData.put("sex", false);
//		mapData.put("startTime", "2017-12-14");
		// map2pojo
		DemoPO po = new DemoPO();
		BeanUtils.copyProperties(po, mapData);
		System.out.println("map2pojo:"+po.getUserName());
		// map2map
		Map<String, Object> mapDest = new HashMap<String, Object>();
		BeanUtils.copyProperties(mapDest, mapData);
		System.out.println("map2map:"+mapDest);
		
		// pojo2map
		DemoPO tmp = new DemoPO();
		tmp.setUserName("张三");
		Map<String, Object> mapDest1 = new HashMap<String, Object>();
		BeanUtils.copyProperties(mapDest1, tmp);
		System.out.println("pojo2map:"+mapDest1);
	}
	
	@Test
	public void copy2beanWithSamePropertiesTest() throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("userName", "白牛");
		mapData.put("age", "13");
		mapData.put("sex", false);
		mapData.put("startTime", "2017-12-14");
//		DemoDTO dto = EasyJavaBeanUtils.map2bean(mapData, DemoDTO.class);
//		DemoDTO dto = EasyJavaBeanUtils.copyProperties(mapData, DemoDTO.class);
//		System.out.println(dto);
		
		DemoPO po = EasyJavaBeanUtils.copyProperties(mapData, DemoPO.class);
		System.out.println(po.getUserName());
		System.out.println(po.getStartTime());
		
		
		DemoPO tmp = new DemoPO();
		tmp.setUserName("张三");
		System.out.println(tmp);
		Map<String, Object> map = EasyJavaBeanUtils.copyProperties(tmp, HashMap.class);
		System.out.println(map);
	}
}

