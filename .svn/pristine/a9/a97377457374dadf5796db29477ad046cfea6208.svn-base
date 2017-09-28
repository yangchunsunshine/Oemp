package com.wb.component.computer.payManage.service;

import java.util.List;
import java.util.Map;

import com.wb.model.entity.computer.MntAlipayInfo;

public interface IPayManagerService
{
    /**
     * 阿里支付参数保存
     * 
     * @param mngId 公司ID
     * @param aliParams 阿里支付参数列表
     * @return boolean
     */
    boolean saveAlipaySetting(int mngId, List<MntAlipayInfo> aliParams);
    
    /**
     * 获取支付宝支付信息
     * 
     * @param mngId 公司ID
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getAlipayInfo(int mngId);
}
