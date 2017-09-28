package com.wb.component.computer.customerManage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wb.component.computer.customerManage.service.ICustomerManageService;

@Component
public class CustomJob {
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
	  /**，缴费提醒
     * @param response
     * @param orgId 公司id
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void createCustomPayRemind()
    {
    	
       customerManageService.createCustomPayRemind();
    
    }
}
