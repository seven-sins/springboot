package com.springboot.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	
	/**
	 * 全局异常处理
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(SevenException.class)
	public ModelAndView sevenException(SevenException ex){
		ModelAndView mv = new ModelAndView();
		mv.addObject("code", ex.getCode());
		mv.addObject("message", ex.getMessage());
		mv.setViewName("views/common/error");
		
		return mv;
	}
	
	/**
	 * 未处理的错误
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView exception(Exception ex){
		ModelAndView mv = new ModelAndView();
		mv.addObject("code", 1);
		mv.addObject("message", "未知错误");
		mv.setViewName("views/common/error");
		
		return mv;
	}
	
	@ModelAttribute
	public void addAttributes(Model model){
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		
	}
}
