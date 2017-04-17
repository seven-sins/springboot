package com.springboot.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)   
public @interface Valid {

}
