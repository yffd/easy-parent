package com.yffd.easy.demo.shiro.custom.token;

import java.io.Serializable;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年6月5日 下午5:22:32 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class LoginStatus implements Serializable {
	private static final long serialVersionUID = 8072352529023820914L;
	private String status;
	private String msg;
	
	public LoginStatus() {
	}
	
	public LoginStatus(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static LoginStatus get(StatusStyle style) {
		return new LoginStatus(style.getCode(), style.getMsg());
	}
	
	public static LoginStatus SUCCESS() {
		return LoginStatus.get(StatusStyle.SUCCESS);
	}
	
	public static LoginStatus FAILED() {
		return LoginStatus.get(StatusStyle.FAILED);
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public enum StatusStyle {
		SUCCESS("200", "成功"), 
		FAILED("302", "禁止访问");
		
		private String code;
		private String msg;
		
		private StatusStyle(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
}

