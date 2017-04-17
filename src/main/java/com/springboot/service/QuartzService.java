package com.springboot.service;

@SuppressWarnings("all")
public interface QuartzService {
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron);
}
