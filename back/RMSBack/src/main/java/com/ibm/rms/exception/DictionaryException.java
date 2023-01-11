package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 数据字典相关的错误都会抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-03 23:48:56
 */
public class DictionaryException extends RuntimeException{
    private Integer code;

    public DictionaryException() {
    }

    public DictionaryException(ErrorResultEnum errorResultEnum) {
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
