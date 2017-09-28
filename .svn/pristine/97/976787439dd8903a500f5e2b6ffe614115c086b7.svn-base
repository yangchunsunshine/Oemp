package com.wb.component.computer.billManage.server.impl;

import java.net.MalformedURLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianProxyFactory;
import com.wb.component.computer.billManage.server.IOempWithdrawalRecService;
import com.wb.component.computer.billManage.util.PropertyReader;

@Service
@Transactional
public class OempWithdrawalRecServiceImpl implements IOempWithdrawalRecService{
	
	private static HessianProxyFactory factory = new HessianProxyFactory();
    private static String url;
    private static IOempWithdrawalRecService recService;

    static{
    	try {
    		  String billPath = PropertyReader.getValue("/com/wb/config/config.properties", "billPath");
    		  url = billPath+"/bill.do";
    		  recService =(IOempWithdrawalRecService)factory.create(IOempWithdrawalRecService.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }

	@Override
	public String withdrawalRec(String json) {
	    try {
	    	return recService.withdrawalRec(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String memberInfo(String json) {
		try {
	    	return recService.memberInfo(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String orderList(String json) {
		try {
	    	return recService.orderList(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String launchWithdrawal(String json) {
		try {
	    	return recService.launchWithdrawal(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String detailWithdrawalRec(String json) {
		try {
	    	return recService.detailWithdrawalRec(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
