package com.springboot.service.assist;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:57
 */
public interface QuartzService {
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<?> jobClass, String cron);
}
