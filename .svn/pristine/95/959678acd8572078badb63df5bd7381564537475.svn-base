package com.wb.component.mobile.mainBuss.service;

import java.util.List;
import java.util.Map;

import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgDetailDto;
import com.wb.model.pojo.mobile.HomeShowForMobile;

/**
 * 
 * 处理大部分业务功能ser(不去细化功能级)
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IMainBussService
{
    /**
     * 
     * 查询主要显示信息
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    HomeShowForMobile findHomeShowData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询企业相关信息
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<OrgDetailDto> findBizInfoOrgList(EnterpriseQueryForm form);
    
    /**
     * 获取新增客户信息
     * 
     * @param mngId 所属代账公司ID
     * @param date 系统当前时间
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getCustomerIncreaseInfo(String mngId, String date);
    
    /**
     * 获取欠费信息
     * 
     * @param mngId 所属代账公司ID
     * @param memberId biz_memberId
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> mointorShowArrearageInfo(String mngId, String memberId);
    
    /**
     * 获取预交费信息
     * 
     * @param mngId 所属代账公司ID
     * @param date 系统当前时间
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> mointorShowPrePayInfo(String mngId, String date);
    
}
