/*
 * 文 件 名:  MyOempAcccHessian.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-6-27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.hessian.server.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wb.component.computer.common.hessian.server.OempAccHessian;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.pojo.computer.CustomPayVo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  郝洋
 * @version  [版本号, 2016-6-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyOempAccHessian implements OempAccHessian
{
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 重载方法
     * 
     * @param bookId
     * @return
     */
    @Override
    public MntMember findMngRecommendByOrgId(int orgID)
    {
        MntMember member = customerManageService.getMemberInfoByOrgID(orgID);
        if (member != null)
        {
            member.setPassword("权限不足!");
            member.setEmail("权限不足!");
            member.setEmpInfo(null);
            member.setEndTime("权限不足!");
            member.setHotline("权限不足!");
            member.setIsCanBeModify(0);
            member.setLogoUrl("权限不足!");
            member.setSelectMethod(0);
            member.setUserName("权限不足!");
        }
        return member;
    }
    
    /**
     * 重载方法
     * 
     * @param orgId
     * @param mngId
     * @return
     */
    @Override
    public Map<String, Boolean> getPayInfo(String orgId, String mngId)
    {
        return customerManageService.getPayYearExpInfo(orgId, mngId);
    }
    
    /**
     * 重载方法
     * 
     * @param orgId
     * @param userId
     * @param vo
     * @return
     */
    @Override
    public boolean customPay(int orgId, int userId, CustomPayVo vo)
    {
        return customerManageService.saveCustomPay(orgId, userId, vo);
    }
    
    /**
     * 获取指定年份缴费月明细
     * 
     * @param orgId orgId
     * @param mngId mngId
     * @param year year
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getMonthPayInfo(String orgId, String mngId, String year)
    {
        return customerManageService.getPayYearMonthExpInfo(mngId, orgId, year);
    }
    
}
