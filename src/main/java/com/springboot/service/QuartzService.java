package com.springboot.service;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:57
 */
@SuppressWarnings("all")
public interface QuartzService {
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron);
}
