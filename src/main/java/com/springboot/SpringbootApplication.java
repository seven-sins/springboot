package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication: Spring Boot核心注解
 * 该注解组合了 @Configuration、@EnableAutoConfiguration、@ComponentsScan
 * 可以使用@Configuration、@EnableAutoConfiguration、@ComponentsScan三个注解替换
 * @author seven sins
 *
 */
@SpringBootApplication 
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
