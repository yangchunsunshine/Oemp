/*
 * 文 件 名:  IReportService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.report.service;

import java.util.List;
import java.util.Map;

import com.wb.model.pojo.computer.ClerkFeeStatisticDto;
import com.wb.model.pojo.computer.ClerkTaxStatisticDto;
import com.wb.model.pojo.computer.ClerkVchStatisticDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgFeeStatisticDto;
import com.wb.model.pojo.computer.OrgTaxStatisticDto;
import com.wb.model.pojo.computer.OrgVchStatisticDto;
import com.wb.model.pojo.computer.TrendDataDto;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IReportService
{
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ClerkVchStatisticDto> findClerkVchData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ClerkTaxStatisticDto> findClerkTaxData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ClerkFeeStatisticDto> findClerkFeeData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<OrgVchStatisticDto> findOrgVchData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<OrgTaxStatisticDto> findOrgTaxData(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<OrgFeeStatisticDto> findOrgFeeData(EnterpriseQueryForm form);
    
    /* ------------------------主界面chart用service开始--------------- */
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TrendDataDto> findIntegerArrayForTax(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TrendDataDto> findIntegerArrayForVch(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询报表
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TrendDataDto> findIntegerArrayForFee(EnterpriseQueryForm form);
    
    /**
     * 获取提成明细
     * 
     * @param memberId 成员ID
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> getCommissionDetail(String sdId, String edId, int memberId);
    
    /**
     * 获取客户合同中提成
     * 
     * @param cusId 客户ID
     * @return Map<String,String>
     */
    Map<String, String> getConComCost(String cusId);
    
    /**
     * 获取所在时间段的缴费时间
     * 
     * @param orgId 客户ID
     * @return Map<String,String>
     */
    List<String> getPayMonthInQueryTime(String sdId, String edId, String orgId);
}
