package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 如果在查询用户对应的权限列表时出现错误(数据库权限设置错误), 抛出此异常
 *
 * @author 刘嘉宁
 * @date 2022-11-15 23:57:41
 */
public class PermissionException extends RuntimeException {

    private Integer code;

    public PermissionException() {
    }

    public PermissionException(ErrorResultEnum errorResultEnum) {
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
