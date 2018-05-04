package com.yffd.easy.uupm.web.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.yffd.easy.framework.web.model.LoginInfo;
import com.yffd.easy.framework.web.mvc.WebController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月9日 上午10:17:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmCommonController extends WebController {
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@InitBinder
    public void intDate(WebDataBinder dataBinder) {
		dateFormat.setLenient(false);  
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }
	
	public LoginInfo getLoginInfo() {
		return new LoginInfo();
	}
}

