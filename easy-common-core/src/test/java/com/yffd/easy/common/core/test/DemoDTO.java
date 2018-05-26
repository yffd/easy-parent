package com.yffd.easy.common.core.test;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年12月12日 下午5:38:42 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DemoDTO implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7+
	 */
	private static final long serialVersionUID = 7029018818933599108L;

	private String userName;
	private int age;
	private Boolean sex;
	private String state;
	private String isActive;
	private String startTime;
	private Date endTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String isActive() {
		return isActive;
	}
	public void setIsActive(String active) {
		this.isActive = active;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}

