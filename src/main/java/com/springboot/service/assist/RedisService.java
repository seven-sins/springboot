package com.springboot.service.assist;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:02:04
 */
public interface RedisService {
	
	public void add(String id, Object obj);

	public Object get(String key);

	public void delete(String key);

	public void put(String key, String hashKey, Object obj);

	public Object get(String key, String hashKey);
}
