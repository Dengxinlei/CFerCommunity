package com.yn.cfer.web.protocol;

import com.yn.cfer.web.common.constant.ErrorCode;

public class ResponseMessage<T> {
	private int code = ErrorCode.ERROR_CODE_SUCCESS;
	private String message;
	private T data;
	
//	public ResponseMessage(int code, String message){
//		this.code = code;
//		this.message = message;
//	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
