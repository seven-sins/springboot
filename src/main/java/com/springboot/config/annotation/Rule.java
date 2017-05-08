package com.springboot.config.annotation;

import java.lang.annotation.*;

/**
 * Bean验证规则
 * @author seven sins
 * @date 2017年5月8日 下午10:55:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface Rule {
    boolean nullable() default false;
    int max() default 0;
    int min() default 0;
    String regex() default "";
    String desc() default "";
}
