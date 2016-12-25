package com.springboot.base;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	
	//@Value("${author.name}")
	//private String author;
	//@Autowired
	//private Admin admin;
	
	protected Map<String, Object> resultMap(int code, Object object){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", object);
		
		return map;
	}
}
