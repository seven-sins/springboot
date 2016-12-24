package com.springboot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.springboot.exception.ExceptionHandlerAdvice;
import com.springboot.interceptor.HandlerInterceptor;

/**
 * 
 * @author seven sins
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	/**
	 * 视图解析器
	 * @return
	 */
	@Bean
	public TemplateResolver templateResolver(){
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		
		return templateResolver;
	}
	
	/**
	 * 全局异常处理
	 * @return
	 */
	@Bean
	public ExceptionHandlerAdvice handleException(){
		return new ExceptionHandlerAdvice();
	}
	
	/**
	 * 文件上传
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(1000000);
		
		return commonsMultipartResolver;
	}
	
	/**
	 * 不拦截静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		// addResourceHandler 文件路径
		// addResourceLocations 访问路径
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
	}
	
	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new HandlerInterceptor());
	}
	
	/**
	 * 为true 路径参数如果带"."， 则后面的值将被忽略
	 * 默认为true
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer){
		configurer.setUseSuffixPatternMatch(false);
	}
}
