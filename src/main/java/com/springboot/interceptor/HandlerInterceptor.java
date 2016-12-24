package com.springboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 拦截器
 * @author seven sins
 *
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 请求发生前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler){
		long startTime = System.currentTimeMillis();
		req.setAttribute("startTime", startTime);
		
		return true;
	}
	
	/**
	 * 请求完成后执行
	 */
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView){
		
		long startTime = (long) req.getAttribute("startTime");
		req.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println("本次请求处理时间: " + new Long(endTime - startTime) + "ms");
	}
}
