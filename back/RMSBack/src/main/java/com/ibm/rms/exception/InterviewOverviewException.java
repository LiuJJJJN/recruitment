package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 面试一览异常
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:49:44
 */
public class InterviewOverviewException extends RuntimeException{

    private Integer code;

    public InterviewOverviewException() {
    }

    public InterviewOverviewException(ErrorResultEnum errorResultEnum) {
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
