package com.wb.model.pojo.mobile;

public class LoginInfo {
    private String cusId;
    private String iosDeviceToken;
    private String androidDeviceToken;

	public String getCusId() {
		return cusId;
	}
	public void setCusId(String cusId) {
		this.cusId = cusId;
	}
	public String getIosDeviceToken() {
		return iosDeviceToken;
	}
	public void setIosDeviceToken(String iosDeviceToken) {
		this.iosDeviceToken = iosDeviceToken;
	}
	public String getAndroidDeviceToken() {
		return androidDeviceToken;
	}
	public void setAndroidDeviceToken(String androidDeviceToken) {
		this.androidDeviceToken = androidDeviceToken;
	}

}
