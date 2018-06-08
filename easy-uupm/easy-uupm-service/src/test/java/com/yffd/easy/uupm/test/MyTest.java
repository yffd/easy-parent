package com.yffd.easy.uupm.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.uupm.pojo.enums.UupmAppSystemTypeEnum;

public class MyTest {

	@Test
	public void test() {
		String rsCodes = "[q,w]";
		List<String> rsCodeList = JSON.parseArray(rsCodes, String.class);
		System.out.println(rsCodeList);
	}
	
	@Test
	public void test1() {
		Map<String, String> map = UupmAppSystemTypeEnum.getMap();
		System.out.println(map);
		System.out.println(JSON.toJSON(map));
	}
	
}
