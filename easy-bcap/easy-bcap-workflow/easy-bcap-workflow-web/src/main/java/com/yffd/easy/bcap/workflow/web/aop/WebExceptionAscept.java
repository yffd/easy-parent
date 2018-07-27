package com.yffd.easy.bcap.workflow.web.aop;

import java.io.IOException;

import javax.servlet.ServletException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月26日 下午1:43:51 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
//@Aspect
//@Component
//@EnableAspectJAutoProxy
public class WebExceptionAscept {
	private Logger LOG = LoggerFactory.getLogger(WebExceptionAscept.class);
	
	@AfterThrowing(value = "execution(public * com.yffd.easy.bcap.workflow.controller..*.*(..))", throwing = "ex")
	public ModelAndView aroundAdvice(Exception ex) throws ServletException, IOException {
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		ServletRequestAttributes r = (ServletRequestAttributes) requestAttributes;
//		HttpServletRequest request = r.getRequest();
//		HttpServletResponse response = r.getResponse();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		//第一如果是 RuntimeException
		if (ex instanceof RuntimeException) {
			LOG.error("抛出运行时异常{}", ex.getMessage());
			modelAndView.addObject("errorMsg", ex.getMessage());
			return modelAndView;
		}
		modelAndView.addObject("errorMsg", "未知异常");
		return modelAndView;
	}
}

