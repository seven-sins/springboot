package com.springboot.config.conf;

import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.springboot.config.exception.ExceptionHandlerAdvice;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:54:24
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 视图解析器
	 * 
	 * @return
	 */
	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");

		return templateResolver;
	}

	/**
	 * 全局异常处理
	 * 
	 * @return
	 */
	@Bean
	public ExceptionHandlerAdvice handleException() {
		return new ExceptionHandlerAdvice();
	}

	/**
	 * 文件上传
	 * 
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(1000000);

		return commonsMultipartResolver;
	}

	/**
	 * 不拦截静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// addResourceHandler 文件路径
		// addResourceLocations 访问路径
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
	}

	/**
	 * 
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new UserArgumentResolver());
	}

	/**
	 * 为true 路径参数如果带"."， 则后面的值将被忽略 默认为true
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

	/**
	 * 优化tomcat
	 */
	@Value("${server.port}")
	private Integer serverPort;

	@Bean
	public TomcatEmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
		tomcatFactory.setPort(serverPort);
		tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
		return tomcatFactory;
	}

	class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
		@Override
		public void customize(Connector connector) {
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
			// 设置最大连接数
			protocol.setMaxConnections(2000);
			// 设置最大线程数
			protocol.setMaxThreads(2000);
			protocol.setConnectionTimeout(30000);
		}
	}
}
