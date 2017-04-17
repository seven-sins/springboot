package com.springboot.service;

public interface RedisService {
	
	public void add(String id, Object obj);

	public Object get(String key);

	public void delete(String key);

	public void put(String key, String hashKey, Object obj);

	public Object get(String key, String hashKey);
}
