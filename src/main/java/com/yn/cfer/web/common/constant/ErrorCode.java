package com.yn.cfer.web.common.constant;

/**
 * DXL
 * 响应码
 */
public class ErrorCode {
    /**
     * 正常响应
     */
    public static final int ERROR_CODE_SUCCESS = 0;
    /**
     * 失败
     */
    public static final int ERROR_CODE_FAILURE = 1;
    /**
     * 用户不存在
     */
    public static final int ERROR_CODE_USER_IS_NOT_EXISTS = 100;
    /**
     * 用户类型不正确
     */
    public static final int ERROR_CODE_USER_TYPE_ERROR = 101;
    /**
     * 缺少参数
     */
    public static final int ERROR_CODE_MISS_PARAM = 3;
    /**
     * 会员不存在
     */
    public static final int ERROR_CODE_MEMBER_IS_NOT_EXISTS = 4;
    /**
     * 缺少token参数
     */
    public static final int ERROR_CODE_MISS_TOKEN = 5;
    /**
     * 登录token已失效
     */
    public static final int ERROR_CODE_TOKEN_IS_EXPIRED = 1001;
    
    
}
