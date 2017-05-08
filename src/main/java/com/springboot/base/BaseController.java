package com.springboot.base;

import java.util.HashMap;
import java.util.Map;

import com.springboot.config.annotation.Validator;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:03:02
 */
public class BaseController {
	
	protected Map<String, Object> resultMap(int code, Object object){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", object);
		
		return map;
	}
	
	protected Map<String, Object> resultMap(int code, Object object, int total){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", object);
		map.put("total", total);
		
		return map;
	}
	
	protected Map<String, Object> resultMsg(int code, String object){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", object);
		
		return map;
	}
	
	protected void valid(Object object){
		new Validator(object);
	}
}
