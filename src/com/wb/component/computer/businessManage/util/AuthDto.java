package com.wb.component.computer.businessManage.util;



//前台ajax传输转换类
public class AuthDto{
	/** 接口返回编码 0：成功 ，1：失败*/
	private String returnCode ;	
	/** 接口返回成功或失败的信息*/
	private String returnMSG;
	/** 针对查询类接口返回的结果集 包含结果list集合或带分页参数的list集合*/
	private Object results;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMSG() {
		return returnMSG;
	}
	public void setReturnMSG(String returnMSG) {
		this.returnMSG = returnMSG;
	}
	public Object getResults() {
		return results;
	}
	public void setResults(Object results) {
		this.results = results;
	}



}

