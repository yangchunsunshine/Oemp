package com.wb.component.computer.login.exception;

import com.wb.component.computer.login.util.ResponseCodeEnum;


public abstract class ServerBaseException extends RuntimeException {

	private static final long serialVersionUID = 4809541693589411650L;
	
	protected int code;
	//just for internal trace
	String reason;

	public ServerBaseException(ResponseCodeEnum resCode,String verboseMsg){
		this.code= resCode.getCode();
		this.reason = resCode.getReason() + verboseMsg;
	}
	
	public ServerBaseException (ResponseCodeEnum resCode,String verboseMsg, Exception ex){
		super(ex);
		this.code= resCode.getCode();
		this.reason = resCode.getReason() + verboseMsg;
	}
	
	protected ServerBaseException (int code,String reason){
		this.code= code;
		this.reason = reason;
	}

	public int getCode() {
		return code;
	}

	public String getMessage(){
		return reason;
	}

	public String getReason(){
		return this.reason;
	}
	
	@Override
	public String toString(){
		return String.format("ServerBaseException - %s [code= %s, message = %s, reason =%s]", this.getClass().getName(),  this.code, this.getMessage(), this.getReason());

	}
}
