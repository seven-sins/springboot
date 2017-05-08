package com.springboot.config.exception;

import com.alibaba.fastjson.JSONArray;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:58:18
 */
public class ValidatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;
	private JSONArray message;
	
	public ValidatorException(){
		super();
	}
	
	public ValidatorException(Integer code, JSONArray result) {
		super();
		this.code = code;
		this.message = result;
	}

	public Integer getCode() {
		return code;
	}

	public JSONArray getData(){
		return this.message;
	}
}
