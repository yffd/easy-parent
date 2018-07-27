package com.yffd.easy.demo.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月26日 下午1:51:15 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Controller
public class IndexController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String toindex() {
		return "redirect:index";
	}
	
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(ModelMap map) {
		return "index";
	}

	@RequestMapping(value="error")
	public void error() {
		throw new RuntimeException("哎呀出错了");
	}

	@RequestMapping(value="/list")
	public String list(ModelMap map) {
		return "list";
	}

	@RequestMapping(value="/detail/{id}")
	public String detail(@PathVariable(value = "id") Integer id, ModelMap map) {
		return "detail";
	}

	@RequestMapping(value="toAdd")
	public String toAdd() {
		return "add";
	}

	@RequestMapping(value="/save")
	public String save(ModelMap map) {
		return "redirect:/index";
	}
	
}

