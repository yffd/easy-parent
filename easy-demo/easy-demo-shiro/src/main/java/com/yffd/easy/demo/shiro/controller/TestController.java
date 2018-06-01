package com.yffd.easy.demo.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年5月29日 下午5:10:38 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
	
    @RequestMapping(value = "/test")
    public String test(){
        return "admin/home";
    }

    public static void main(String[] args) {
    	System.out.println(System.getProperty("java.io.tmpdir"));
	}
}

