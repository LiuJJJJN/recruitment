package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

/**
 * 简历上传 相关的错误都会抛出此异常
 *
 * @author 车馨心
 * @date 2023-01-06 21:01:49
 */
public class CVUploadException extends RuntimeException {

    private Integer code;

    public CVUploadException() {
    }

    public CVUploadException(ErrorResultEnum errorResultEnum) {
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
