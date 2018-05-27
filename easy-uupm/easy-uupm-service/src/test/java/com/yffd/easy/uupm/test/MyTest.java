package com.yffd.easy.uupm.test;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class MyTest {

	@Test
	public void test() {
		String rsCodes = "[q,w]";
		List<String> rsCodeList = JSON.parseArray(rsCodes, String.class);
		System.out.println(rsCodeList);
	}
}
