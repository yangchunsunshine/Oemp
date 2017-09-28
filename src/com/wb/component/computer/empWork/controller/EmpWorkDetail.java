/*
 * 文 件 名:  EmpWorkDetail.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.empWork.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.wb.component.computer.common.constant.ConstantComputer;
import com.wb.component.computer.empWork.service.IEmpWorkDetailService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.MobileUtil;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.pojo.computer.ClerkWorkMonitorDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgTaxMonitorDto;
import com.wb.model.pojo.computer.PageProxyDto;
import com.wb.model.pojo.computer.SettleOrgDto;
import com.wb.model.pojo.computer.SuperintendentDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;

/**
 * 代帐企业员工工作明细
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class EmpWorkDetail extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(EmpWorkDetail.class);
    
    /**
     * 员工工作明细ser
     */
    @Autowired
    @Qualifier("empWorkDetailService")
    private IEmpWorkDetailService empSer;
    
    /**
     * 会计详情
     * 
     * @param request request
     * @param telphone telphone
     * @param period period
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoClerkDetail", method = RequestMethod.GET)
    public String gotoClerkDetail(HttpServletRequest request, @RequestParam(value = "telphone", required = false) String telphone, @RequestParam(value = "period", required = false) String period)
    {
        if (MobileUtil.isMobile(telphone))
        {
            request.setAttribute("clerkDetailTelphone", telphone);
        }
        else
        {
            request.setAttribute("clerkDetailTelphone", "");
        }
        request.setAttribute("clerkDetailDate", DateUtil.getPrevTaxDate());
        return "empWorkDetail/clerkDetail";
    }
    
    /**
     * 
     * 结账详情界面
     * 
     * @param request request
     * @param telphone telphone
     * @param period period
     * @return 结账详情界面
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoSettleDetail", method = RequestMethod.GET)
    public String gotoSettleDetail(HttpServletRequest request, @RequestParam(value = "telphone", required = false) String telphone, @RequestParam(value = "period", required = false) String period)
    {
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (period != null && pattern.matcher(period).matches())
        {
            request.setAttribute("settleDate", period);
        }
        else
        {
            request.setAttribute("settleDate", DateUtil.getPrevTaxDate());
        }
        if (MobileUtil.isMobile(telphone))
        {
            request.setAttribute("settleTelphone", telphone);
        }
        else
        {
            request.setAttribute("settleTelphone", "");
        }
        return "empWorkDetail/settleDetail";
    }
    
    /**
     * 
     * 报税详情界面跳转
     * 
     * @param request request
     * @param telphone telphone
     * @param period period
     * @return 结账详情界面
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoTaxDetail", method = RequestMethod.GET)
    public String gotoTaxDetail(HttpServletRequest request, @RequestParam(value = "telphone") String telphone, @RequestParam(value = "period") String period)
    {
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(period).matches())
        {
            request.setAttribute("taxDetailDate", period);
        }
        else
        {
            request.setAttribute("taxDetailDate", DateUtil.getPrevTaxDate());
        }
        if (MobileUtil.isMobile(telphone))
        {
            request.setAttribute("taxDetailTelphone", telphone);
        }
        else
        {
            request.setAttribute("taxDetailTelphone", "");
        }
        return "empWorkDetail/taxDetail";
    }
    
    /**
     * 
     * 员工在线状态页面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoOnlineState", method = RequestMethod.GET)
    public String gotoOnlineState(HttpSession session, HttpServletRequest request)
    {
        request.setAttribute("dateFrom", DateUtil.getCurYearAndMonthToString());
        return "empWorkDetail/onlineState";
    }
    
    /**
     * 获取员工在线信息列表
     * 
     * @param request request
     * @param response request
     * @param session session
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOnlineEmpList")
    public void getOnlineEmpList(HttpServletRequest request, HttpServletResponse response, HttpSession session, SuperintendentQueryForm form)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        form.setMngId(member.getOrgId());
        form.setIsAccept(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
        form.setArg1(ConstantComputer.HEAT_DEAD_LINE / 1000);
        form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_OFFLINE")));
        form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_ONLINE")));
        final List<SuperintendentDto> list = empSer.findByParams(form);
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 查询会计工作明细统计
     * 
     * @param response response
     * @param request request
     * @param form request
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getClerkWorkDetailList")
    public void getClerkWorkDetailList(HttpServletResponse response, HttpServletRequest request, HttpSession session, EnterpriseQueryForm form)
    {
        List<ClerkWorkMonitorDto> list = new ArrayList<ClerkWorkMonitorDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getLisence()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getLisence()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(form.getLisence()));
            list = empSer.findClerkWorkDetailList(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 结账详情
     * 
     * @param response response
     * @param request request
     * @param session session
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getSetttledOrgList")
    public void getSetttledOrgList(HttpServletResponse response, HttpServletRequest request, HttpSession session, EnterpriseQueryForm form)
    {
        List<SettleOrgDto> list = new ArrayList<SettleOrgDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getLisence()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getLisence()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(form.getLisence()));
            list = empSer.findSettledOrgList(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 查询报税信息列表
     * 
     * @param request
     * @param response
     * @param session
     * @param form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOrgTaxList")
    public void getOrgTaxList(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        form.setCreator(member.getOrgId());
        final List<OrgTaxMonitorDto> list = empSer.findOrgTaxByParamsForMng(form);
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 查询报税明细
     * 
     * @param response response
     * @param request request
     * @param reid reid
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getRecordHistory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getRecordHistory(HttpServletResponse response, HttpServletRequest request, String reid)
    {
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        final List<Map<String, Object>> resultList = empSer.findTaxDetail(reid, reid);
        resultMap.put("result", resultList);
        resultMap.put("showNum", resultList.size());
        return resultMap;
    }
    
}
