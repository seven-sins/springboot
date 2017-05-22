package com.springboot.service.assist.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.springboot.service.assist.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void add(String id, Object obj) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(id);
		boundOps.set(obj);
		// ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
		// valueops.set(id, obj);
	}

	@Override
	public Object get(String key) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(key);
		return boundOps.get();
		// ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
		// return valueops.get(key);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void put(String key, String hashKey, Object obj) {
		redisTemplate.opsForHash().put(key, hashKey, obj);
	}

	public Object get(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
}
