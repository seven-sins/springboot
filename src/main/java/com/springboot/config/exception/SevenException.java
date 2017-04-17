package com.springboot.config.exception;

public class SevenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String message;
	
	public SevenException() {
		super();
	}
	
	public SevenException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
