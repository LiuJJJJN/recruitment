package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 权限管理出现错误时抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-07 21:32:51
 */
public class PermissionManageException extends RuntimeException {

    private Integer code;

    public PermissionManageException() {
    }

    public PermissionManageException(ErrorResultEnum errorResultEnum) {
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
