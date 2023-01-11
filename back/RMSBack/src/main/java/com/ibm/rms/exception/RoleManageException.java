package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 角色管理操作出现错误时抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:18:04
 */
public class RoleManageException extends RuntimeException {

    private Integer code;

    public RoleManageException() {
    }

    public RoleManageException(ErrorResultEnum errorResultEnum) {
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
