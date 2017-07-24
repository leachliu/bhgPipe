package com.bhg.pipeServer.exception;

import com.bhg.pipeServer.config.ErrorCode;

public class PipeException extends RuntimeException {

	private static final long serialVersionUID = 9142502354382336726L;
	private int errorCode;

	public PipeException(int errorCode) {
		super(ErrorCode.getErrorMessage(errorCode));
		this.errorCode = errorCode;
	}
	
	public PipeException(String msg){
		super(msg);
		this.errorCode  = 1;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
