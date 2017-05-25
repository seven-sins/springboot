package com.springboot.config.security.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * seven sins
 * 2017年5月25日 下午2:03:45
 */
@JsonSerialize(using = AuthExceptionSerializer.class)
public class AuthException extends OAuth2Exception{

	private static final long serialVersionUID = 1L;

	private Integer code;
	
	public AuthException(String msg) {
		super(msg);
	}

	public AuthException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
