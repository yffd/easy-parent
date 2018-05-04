package com.yffd.easy.framework.web.mvc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.yffd.easy.framework.web.enums.WebCommonEnum;
import com.yffd.easy.framework.web.model.LoginInfo;
import com.yffd.easy.framework.web.model.RespData;
import com.yffd.easy.framework.web.view.ViewModelConverter;

/**
 * @Description  web控制转发器基类.
 * @Date		 2017年9月18日 下午5:43:59 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public abstract class WebController extends ViewModelConverter {
	public static final String KEY_SESSION_LOGIN_INFO = "_LOGIN_INFO";
	
	/**
	 * 获取当前session对象
	 * @Date	2018年4月17日 上午11:27:27 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return session;
	}
	
	/**
	 * 获取当前登录信息
	 * @Date	2018年4月17日 上午11:27:32 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected LoginInfo loginInfo() {
		LoginInfo info = (LoginInfo) this.getSession().getAttribute(KEY_SESSION_LOGIN_INFO);
		return info;
	}
	
	/**
	 * 同步请求：成功
	 * @Date	2017年9月22日 下午2:48:40 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected RespData success() {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_SYNC.getValue());
		entity.setStatus(WebCommonEnum.SUCCESS.getValue());
		entity.setMsg(WebCommonEnum.SUCCESS.getDesc());
		return entity;
	}
	
	/**
	 * 同步请求：成功
	 * @Date	2017年9月22日 下午2:49:14 <br/>
	 * @author  zhangST
	 * @param msg		提示信息
	 * @param objects
	 * @return
	 */
	protected RespData success(String msg, Object...objects) {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_SYNC.getValue());
		entity.setStatus(WebCommonEnum.SUCCESS.getValue());
		entity.setMsg(msg);
		entity.setData(objects);
		return entity;
	}
	
	/**
	 * 异步请求：成功
	 * @Date	2017年9月22日 下午2:50:00 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected RespData successAjax() {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_ASYNC.getValue());
		entity.setStatus(WebCommonEnum.SUCCESS.getValue());
		entity.setMsg(WebCommonEnum.SUCCESS.getDesc());
		return entity;
	}
	
	/**
	 * 异步请求：成功
	 * @Date	2017年9月22日 下午2:50:00 <br/>
	 * @author  zhangST
	 * @param respData		响应数据
	 * @return
	 */
	protected RespData successAjax(Object respData) {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_ASYNC.getValue());
		entity.setStatus(WebCommonEnum.SUCCESS.getValue());
		entity.setMsg(WebCommonEnum.SUCCESS.getDesc());
		entity.setData(respData);
		return entity;
	}
	
	/**
	 * 异步请求：成功
	 * @Date	2017年9月22日 下午2:50:28 <br/>
	 * @author  zhangST
	 * @param msg		提示信息
	 * @param objects
	 * @return
	 */
	protected RespData successAjax(String msg, Object...objects) {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_ASYNC.getValue());
		entity.setStatus(WebCommonEnum.SUCCESS.getValue());
		entity.setMsg(msg);
		entity.setData(objects);
		return entity;
	}
	
	/**
	 * 同步请求：失败
	 * @Date	2017年9月22日 下午2:49:01 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected RespData error() {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_SYNC.getValue());
		entity.setStatus(WebCommonEnum.ERROR.getValue());
		entity.setMsg(WebCommonEnum.ERROR.getDesc());
		return entity;
	}
	
	/**
	 * 同步请求：失败
	 * @Date	2017年9月22日 下午2:49:43 <br/>
	 * @author  zhangST
	 * @param msg		提示信息
	 * @param objects
	 * @return
	 */
	protected RespData error(String msg, Object...objects) {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_SYNC.getValue());
		entity.setStatus(WebCommonEnum.ERROR.getValue());
		entity.setMsg(msg);
		entity.setData(objects);
		return entity;
	}
	
	/**
	 * 异步请求：失败
	 * @Date	2017年9月22日 下午2:50:16 <br/>
	 * @author  zhangST
	 * @return
	 */
	protected RespData errorAjax() {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_ASYNC.getValue());
		entity.setStatus(WebCommonEnum.ERROR.getValue());
		entity.setMsg(WebCommonEnum.ERROR.getDesc());
		return entity;
	}
	
	/**
	 * 异步请求：失败
	 * @Date	2017年9月22日 下午2:50:41 <br/>
	 * @author  zhangST
	 * @param msg		提示信息
	 * @param objects
	 * @return
	 */
	protected RespData errorAjax(String msg, Object...objects) {
		RespData entity = new RespData();
		entity.setType(WebCommonEnum.REQUEST_ASYNC.getValue());
		entity.setStatus(WebCommonEnum.ERROR.getValue());
		entity.setMsg(msg);
		entity.setData(objects);
		return entity;
	}
	
}

