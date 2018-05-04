package com.yffd.easy.framework.web.mvc.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yffd.easy.framework.web.enums.WebCommonEnum;
import com.yffd.easy.framework.web.model.RespData;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年11月16日 下午3:22:02 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class SpringMvcExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(SpringMvcExceptionHandler.class);
	
	private static final String _MSG = "Sorry!!! System error, Please contact with administrator!";
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//判断ajax请求
		if(request.getHeader("x-requested-with")!=null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
			RespData entity = new RespData();
			entity.setType(WebCommonEnum.REQUEST_SYNC.getValue());
			entity.setStatus(WebCommonEnum.ERROR.getValue());
			entity.setMsg("系统异常");
			entity.setData(ex.getClass().getName());
			String msg = JSON.toJSONString(entity);
			writeMsg(msg, response);
			return null;
		}else {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("msg", _MSG);
			return new ModelAndView("msg/error", model); 
		}
	}

	//打印异步请求的异常信息
	private void writeMsg(String msg, HttpServletResponse response){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(msg);
		} catch (IOException e) {
			logger.error("[sys]--返回异步异常信息失败！");
		} finally {
			out.flush();
			out.close();
		}
	}

}

