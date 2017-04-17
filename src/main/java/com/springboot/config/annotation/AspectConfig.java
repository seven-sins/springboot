package com.springboot.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.springboot.config.exception.SevenException;
import com.springboot.config.exception.ValidatorException;

@Aspect
@Component
@Order(1)
public class AspectConfig {

	@Pointcut("execution(* com.springboot.controller..*.*(..))")
	public void init() {
	}

	@Before("init()")
	public void beforeAdvice(JoinPoint joinPoint) {
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		Annotation[][] annotations = method.getParameterAnnotations();
		Object[] objects = joinPoint.getArgs();
		int i = 0;
		for (Annotation[] annotation : annotations) {
			Object object = objects[i++];
			for (Annotation obj : annotation) {
				if (obj instanceof Valid) {
					new Validator(object);
				}
			}
		}
	}
	
	@AfterThrowing(pointcut = "init()", throwing = "e")  
    public void afterThrowing(JoinPoint point, Throwable e) {  
		/**
    	 * 主动抛出的异常
    	 */
        if(e instanceof ValidatorException || e instanceof SevenException){
        	
        }else{ // 未捕获异常
        	throw new SevenException(-1, "未捕获异常: " + e.getLocalizedMessage());
        }
    }
}
