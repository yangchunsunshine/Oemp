package com.wb.component.computer.billManage.server;

public interface IOempWithdrawalRecService {

	public String withdrawalRec(String json);
	
	public String memberInfo(String json);
	
	public String orderList(String json);
	
	public String launchWithdrawal(String json);
	
	public String detailWithdrawalRec(String json);
}
