package com.wb.component.computer.billManage.server.impl;

import java.net.MalformedURLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianProxyFactory;
import com.wb.component.computer.billManage.server.IOempSettlementService;
import com.wb.component.computer.billManage.util.PropertyReader;

@Service
@Transactional
public class OempSettleServiceImpl implements IOempSettlementService {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static String url;
	private static IOempSettlementService setService;

	static {
		try {
			String billPath = PropertyReader.getValue(
					"/com/wb/config/config.properties", "billPath");
			url = billPath + "/billAccount.do";
			setService = (IOempSettlementService) factory.create(
					IOempSettlementService.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String memberSettleInfo(String json) {
		try {
			return setService.memberSettleInfo(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String accountOrderList(String json) {
		try {
			return setService.accountOrderList(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
