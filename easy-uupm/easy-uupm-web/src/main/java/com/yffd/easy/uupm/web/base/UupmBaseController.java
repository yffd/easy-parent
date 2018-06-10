package com.yffd.easy.uupm.web.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.yffd.easy.framework.pojo.login.LoginInfo;
import com.yffd.easy.framework.web.mvc.WebEasyuiController;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年2月9日 上午10:17:46 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UupmBaseController extends WebEasyuiController {
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@InitBinder
    public void intDate(WebDataBinder dataBinder) {
		dateFormat.setLenient(false);  
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }
	
	@Override
	protected LoginInfo getLoginInfo() {
		LoginInfo info = new LoginInfo();
		info.setUserCode("sys");
		info.setTtCode("dft");
		return info;
	}

//	public void initAddProps(CommonEntity entity) {
//		entity.setVersion(0);
//		entity.setDelFlag("0");
//		entity.setCreateTime(new Date());
//		entity.setCreateBy(this.getLoginInfo().getUserCode());
//	}
//	
//	public void initUpdateProps(CommonEntity entity) {
//		entity.setUpdateTime(new Date());
//		entity.setUpdateBy(this.getLoginInfo().getUserCode());
//	}
//	
//	public void initAddProps(UupmBasePojo entity) {
//		entity.setVersion(0);
//		entity.setDelFlag("0");
//		entity.setCreateTime(new Date());
//		entity.setCreateBy(this.getLoginInfo().getUserCode());
//		entity.setTenantCode(entity.getTenantCode());
//	}
//	
//	public void initUpdateProps(UupmBasePojo entity) {
//		entity.setUpdateTime(new Date());
//		entity.setUpdateBy(this.getLoginInfo().getUserCode());
//		entity.setTenantCode(entity.getTenantCode());
//	}
//	
//	public void initQueryProps(UupmBasePojo entity) {
//		entity.setTenantCode(entity.getTenantCode());
//	}
	
}

