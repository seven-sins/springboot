package com.springboot.config.annotation;

import java.lang.annotation.*;

/**
 * Created by seven sins on 2/9/2017.
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
