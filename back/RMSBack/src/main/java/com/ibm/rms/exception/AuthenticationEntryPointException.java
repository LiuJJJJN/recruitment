package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 此类为认证时的异常抛出实现类
 * 不过抛出这个异常的时机只在 filter 层面, 不会被异常捕获器捕获
 * 所以此异常实现类没有被使用
 */
public class AuthenticationEntryPointException extends RuntimeException {

    private Integer code;

    public AuthenticationEntryPointException() {

    }

    public AuthenticationEntryPointException(ErrorResultEnum errorResultEnum) {
        super(errorResultEnum.getMsg());
        this.code = errorResultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
