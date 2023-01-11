package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

public class WorkBenchException extends RuntimeException {
	private Integer code;

	public WorkBenchException() {
	}

	public WorkBenchException(ErrorResultEnum errorResultEnum) {
		super(errorResultEnum.getMsg());
		this.code =errorResultEnum.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	 
}
