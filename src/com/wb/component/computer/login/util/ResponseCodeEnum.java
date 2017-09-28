package com.wb.component.computer.login.util;

public enum ResponseCodeEnum {
	Network404(404, "找不到目标地址"),
	Network500(500, "服务器内部错误");
	 private final int code;
	 private final String reason;
	 
	 private ResponseCodeEnum(int code,String reason) {
	    this.code = code;
	    this.reason= reason;
	 }
	 
	 public int getCode(){
		 return this.code;
	 }
	 public String getReason(){
		 return this.reason;
	 }
}
