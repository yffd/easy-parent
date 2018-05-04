package com.yffd.easy.framework.web.model;

import java.io.Serializable;

import com.yffd.easy.common.core.pojo.IPOJO;

/**
 * 
 * @Description  系统响应信息的标准类.
 * @Date		 2017年8月7日 下午2:18:45 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see
 */
public class RespData implements IPOJO, Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = -1521500074035646023L;
	
	/** 响应状态码  */
    private String status;
    /** 请求方式，同步、异步 */
    private String type;
    /** 提示信息 */
    private String msg;
    /** 数据信息 */
    private Object data;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
}