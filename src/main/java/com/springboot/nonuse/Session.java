package com.springboot.nonuse;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注入用户参数(@Session("user") User user)
 * @author seven sins
 * @date 2017年5月8日 下午10:56:02
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface Session {
	String value();
}
