package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 当登录时没有查询对应账户时抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-11-14 23:48:16
 */
public class AuthenticationException extends RuntimeException{

    private Integer code;

    public AuthenticationException() {

    }

    public AuthenticationException(ErrorResultEnum errorResultEnum) {
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
