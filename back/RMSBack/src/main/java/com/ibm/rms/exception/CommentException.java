package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 评论功能相关的错误都会抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-18 18:15:25
 */
public class CommentException extends RuntimeException {
    private Integer code;

    public CommentException() {
    }

    public CommentException(ErrorResultEnum errorResultEnum) {
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
