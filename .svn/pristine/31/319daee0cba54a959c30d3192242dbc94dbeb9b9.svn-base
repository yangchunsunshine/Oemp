/*
 * 文 件 名:  IEmpWorkDetailService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.empWork.service;

import java.util.List;
import java.util.Map;

import com.wb.model.pojo.computer.ClerkWorkMonitorDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgTaxMonitorDto;
import com.wb.model.pojo.computer.SettleOrgDto;
import com.wb.model.pojo.computer.SuperintendentDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;
import com.wb.model.pojo.computer.TaxAmount;

/**
 * 员工工作量统计ser
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IEmpWorkDetailService
{
    /**
     * 
     * 查询企业结账明细
     * 
     * @param form form
     * @return List<SettleOrgDto>
     * @see [类、类#方法、类#成员]
     */
    List<SettleOrgDto> findSettledOrgList(EnterpriseQueryForm form);
    
    /**
     * 
     * 查询会计明细
     * 
     * @param form form
     * @return List<ClerkWorkMonitorDto>
     * @see [类、类#方法、类#成员]
     */
    List<ClerkWorkMonitorDto> findClerkWorkDetailList(EnterpriseQueryForm form);
    
    /**
     * 员工在线情况查询
     * 
     * @param form form
     * @return List<SuperintendentDto>
     * @see [类、类#方法、类#成员]
     */
    List<SuperintendentDto> findByParams(SuperintendentQueryForm form);
    
    /**
     * 查询报税明细
     * 
     * @param args args
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findTaxDetail(Object... args);
    
    /**
     * 新增报税明细
     * 
     * @param reid reid
     * @param taxAmount taxAmount
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int saveTaxAmount(int reid, List<TaxAmount> taxAmount);
    
    /**
     * 查询企业报税信息
     * 
     * @param form form
     * @return List<OrgTaxMonitorDto>
     * @see [类、类#方法、类#成员]
     */
    List<OrgTaxMonitorDto> findOrgTaxByParamsForMng(EnterpriseQueryForm form);
}