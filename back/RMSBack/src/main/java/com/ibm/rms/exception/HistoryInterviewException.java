package com.ibm.rms.exception;

import com.ibm.rms.domain.ErrorResultEnum;

public class HistoryInterviewException extends RuntimeException {
	private Integer code;

	public HistoryInterviewException() {
	}

	public HistoryInterviewException(ErrorResultEnum errorResultEnum) {
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
