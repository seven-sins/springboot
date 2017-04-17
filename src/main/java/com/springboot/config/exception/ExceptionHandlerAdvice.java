package com.springboot.config.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	
	/**
	 * 全局异常处理
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(SevenException.class)
	@ResponseBody
	public Object sevenException(SevenException ex){
		return this.resultData(ex.getCode(), ex.getMessage());
	}
	
	@ExceptionHandler(ValidatorException.class)
	@ResponseBody
	public Object validatorException(ValidatorException ex){
		return this.resultData(ex.getCode(), ex.getData());
	}
	
	/**
	 * 未处理的错误
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exception(Exception ex){
		return this.resultData(-1, "未知错误");
	}
	
	private ModelAndView resultData(int code, Object object){
		ModelAndView mv = new ModelAndView();
		
		Map<String, Object> errMap = new HashMap<String, Object>();
		errMap.put("code", code);
		errMap.put("message", object);
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setAttributesMap(errMap);
		mv.setView(view);
		
		return mv;
	}
	
	@ModelAttribute
	public void addAttributes(Model model){
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		
	}
}
