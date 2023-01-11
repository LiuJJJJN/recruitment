package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 简历相关的错误都会抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-18 13:44:16
 */
public class ResumeException extends RuntimeException {
    private Integer code;

    public ResumeException() {
    }

    public ResumeException(ErrorResultEnum errorResultEnum) {
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
