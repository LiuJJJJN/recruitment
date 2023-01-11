package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 用户管理操作出现错误时抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-06 17:02:07
 */
public class UserManageException extends RuntimeException {

    private Integer code;

    public UserManageException() {
    }

    public UserManageException(ErrorResultEnum errorResultEnum) {
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
