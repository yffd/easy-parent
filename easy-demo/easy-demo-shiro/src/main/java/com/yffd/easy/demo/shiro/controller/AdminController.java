package com.yffd.easy.demo.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
public class AdminController {
	
	// 登录成功的页面
    @RequestMapping(value = "/admin/home")
    public String adminHomePage(){
        return "admin/home";
    }

    // 只有角色为admin的才能访问
    @RequiresRoles("admin")
    @RequestMapping(value = "/admin/role")
    public String adminWithRole(){
        return "admin/withRole";
    }

    // 只用同时具有user:view和user:create权限才能访问
    @RequiresPermissions(value={"user:view","user:create"}, logical= Logical.AND)
    @RequestMapping(value = "/admin/auth")
    public String adminWithAuth(){
        return "admin/withAuth";
    }
}

