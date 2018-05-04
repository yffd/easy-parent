package com.yffd.easy.framework.common.pojo.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.yffd.easy.common.core.model.DemoDTO;
import com.yffd.easy.common.core.model.DemoPO;
import com.yffd.easy.common.core.page.PageParam;
import com.yffd.easy.common.core.page.PageResult;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 下午5:41:11 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class EasyModelConverterTest {
	private CommonPojoFactory converter = new CommonPojoFactory();
	
	@Test
	public void model2modelTest() {
		DemoPO po1 = new DemoPO();
		po1.setUserName("小黑");
		po1.setAge(11);
		po1.setState("1");
		po1.setStartTime(new Date());
		po1.setEndTime("2017-12-18 14:00:59");
		DemoDTO dto1 = converter.model2model(po1, DemoDTO.class);
		System.out.println(dto1.getUserName() + "::" + dto1.getAge() + "::" + dto1.getStartTime() + "::" + dto1.getEndTime());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		List<DemoPO> listResult = new ArrayList<DemoPO>();
		for (int i=0; i<5; i++) {
			DemoPO po = new DemoPO();
			po.setUserName("小黑_" + i);
			po.setAge(11 + i);
			listResult.add(po);
		}
		
		List<DemoDTO> dtoList = converter.model2model(listResult, DemoDTO.class);
		Assert.assertNotNull(dtoList);
		for (DemoDTO dto : dtoList) {
			System.out.println(dto.getUserName() + "::" + dto.getAge() + "::" + dto.getSex());
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		PageParam pageParam = new PageParam(1, 10);
		PageResult<DemoPO> pageResult = new PageResult<DemoPO>();
		pageResult.setRecordList(listResult);
		pageResult.setPageParam(pageParam);
		
		PageResult<DemoDTO> _pageResult = converter.model2model(pageResult, DemoDTO.class);
		for(DemoDTO dto : _pageResult.getRecordList()) {
			System.out.println(dto.getUserName() + "::" + dto.getAge() + "::" + dto.getSex());
		}
		
	}
	
	@Test
	public void map2modelTest() {
		Map<String, Object> mapResult1 = new HashMap<String, Object>();
		mapResult1.put("USER_NAME", "白牛");
		mapResult1.put("AGE", 12);
		DemoDTO dto1 = converter.map2model(mapResult1, DemoDTO.class, true);
		System.out.println(dto1.getUserName() + "::" + dto1.getAge());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		for (int i=0; i<5; i++) {
			Map<String, Object> mapResult = new HashMap<String, Object>();
			mapResult.put("userName", "白牛_" + i);
			mapResult.put("AGE_", 12 + i);
			listResult.add(mapResult);
		}
		
		List<DemoDTO> dtoList = converter.map2model(listResult, DemoDTO.class, true);
		Assert.assertNotNull(dtoList);
		for (DemoDTO dto : dtoList) {
			System.out.println(dto.getUserName() + "::" + dto.getAge() + "::" + dto.getSex());
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		PageParam pageParam = new PageParam(1, 10);
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		pageResult.setRecordList(listResult);
		pageResult.setPageParam(pageParam);
		
		PageResult<DemoDTO> _pageResult = converter.map2model(pageResult, DemoDTO.class, true);
		for(DemoDTO dto : _pageResult.getRecordList()) {
			System.out.println(dto.getUserName() + "::" + dto.getAge() + "::" + dto.getSex());
		}
		
	}
	
	@Test
	public void map2mapTest() {
		Map<String, Object> mapResult1 = new HashMap<String, Object>();
		mapResult1.put("userName", "白牛");
		mapResult1.put("age", 12);
		// 属性到表字段
		Map<String, Object> map = this.converter.map2map(mapResult1, null);
		System.out.println(map);
		
		Map<String, Object> mapResult2 = new HashMap<String, Object>();
		mapResult2.put("USER_NAME", "白牛");
		mapResult2.put("_AGE_", 12);
		// 表字段 到 属性
		Map<String, Object> map2 = this.converter.map2map(mapResult2, true);
		System.out.println(map2);
		
	}
	
	@Test
	public void model2mapTest() {
		DemoPO po1 = new DemoPO();
		po1.setUserName("小黑");
		po1.setAge(11);
		Map<String, Object> map2 = this.converter.model2map(po1, false);
		System.out.println(map2);
	}
	
	@Test
	public void model2model_2Test() {
		DemoPO po1 = new DemoPO();
		po1.setUserName("小黑");
		po1.setAge(11);
		po1.setState("1");
		po1.setSex("0");
		po1.setIsActive(true);
		po1.setStartTime(new Date());
		po1.setEndTime("2017-12-14");
		DemoDTO dto1 = converter.model2model(po1, DemoDTO.class);
		System.out.println(dto1.getUserName() + "::" + dto1.getAge() + "::" + dto1.getState() + "::" + dto1.getSex() + "::" + dto1.isActive());
		System.out.println(dto1.getStartTime());
		System.out.println(dto1.getEndTime());
	}
	
	@Test
	public void map2model_2Test() {
		Map<String, Object> mapResult1 = new HashMap<String, Object>();
		mapResult1.put("USER_NAME", "白牛");
		mapResult1.put("AGE", "12");
		mapResult1.put("startTime", new Date());
		mapResult1.put("endTime", "2017-12-14");
//		mapResult1.put("endTime", new Date());
		DemoDTO dto1 = converter.map2model(mapResult1, DemoDTO.class, true);
		System.out.println(dto1.getUserName() + "::" + dto1.getAge());
		System.out.println(dto1.getStartTime());
		System.out.println(dto1.getEndTime());
	}
	
	@Test
	public void test() {
		System.out.println(Map.class.isAssignableFrom(HashMap.class));
		System.out.println(Collection.class.isAssignableFrom(List.class));
	}
}

