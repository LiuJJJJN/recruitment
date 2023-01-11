package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 简历 Review 相关的错误都会抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-18 13:33:36
 */
public class CVReviewException extends RuntimeException {

    private Integer code;

    public CVReviewException() {
    }

    public CVReviewException(ErrorResultEnum errorResultEnum) {
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
