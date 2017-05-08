package com.springboot.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.*;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:57:36
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)   
public @interface Valid {

}
