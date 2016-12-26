package com.springboot.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 必须在入口类使用注解@EnableTask
 * Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。
 * 可以利用@Order注解来规定所有CommandLineRunner实例的运行顺序。
 * @author seven sins
 */
@Component
@Order(value=1)
public class TaskCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=============服务启动执行加载数据=============");
	}
	
	@BeforeTask
	public void methodA(TaskExecution taskExecution) {
		System.out.println("=============beforeTask=============");
	}

	@AfterTask
	public void methodB(TaskExecution taskExecution) {
		System.out.println("=============afterTask=============");
	}

	@FailedTask
	public void methodC(TaskExecution taskExecution, Throwable throwable) {
		System.out.println("=============failedTask=============");
	}
}
