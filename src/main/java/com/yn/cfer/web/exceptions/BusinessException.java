package com.yn.cfer.web.exceptions;

public class BusinessException extends Exception {

    //错误编码
    private int code;

    //错误消息
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public BusinessException(int code, String message) {
        this(code, message, null);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

}
