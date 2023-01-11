package com.ibm.rms.exception;


import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 部门管理相关的错误都会抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-07 01:49:29
 */
public class DeptManageException extends RuntimeException {
    private Integer code;

    public DeptManageException() {
    }

    public DeptManageException(ErrorResultEnum errorResultEnum) {
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
