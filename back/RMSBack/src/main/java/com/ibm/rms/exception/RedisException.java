package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * Redis 操作出现错误时抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-11-15 23:58:27
 */
public class RedisException extends RuntimeException{

    private Integer code;

    public RedisException() {
    }

    public RedisException(ErrorResultEnum errorResultEnum) {
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
