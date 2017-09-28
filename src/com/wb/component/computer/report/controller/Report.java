/*
 * 文 件 名:  Report.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.report.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.report.service.IReportService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.pojo.computer.ClerkFeeStatisticDto;
import com.wb.model.pojo.computer.ClerkTaxStatisticDto;
import com.wb.model.pojo.computer.ClerkVchStatisticDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgFeeStatisticDto;
import com.wb.model.pojo.computer.OrgTaxStatisticDto;
import com.wb.model.pojo.computer.OrgVchStatisticDto;
import com.wb.model.pojo.computer.PageProxyDto;
import com.wb.model.pojo.computer.TrendData;
import com.wb.model.pojo.computer.TrendDataDto;

/**
 * 报表统计service
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "supervisory")
public class Report extends AjaxAction
{
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(Report.class);
    
    /**
     * 服务ser
     */
    @Autowired
    @Qualifier("reportService")
    private IReportService reportSer;
    
    /**
     * 
     * 报表跳转
     * 
     * @param request request
     * @param session session
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoOrgTrend", method = RequestMethod.GET)
    public String gotoOrgTrend(HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("sidxDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("sordDate", DateUtil.getPrevTaxDate());
        return "report/orgChart";
    }
    
    /**
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoClerkVchList", method = RequestMethod.GET)
    public String gotoClerkVchList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/clerkVchList";
    }
    
    /**
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoClerkTaxList", method = RequestMethod.GET)
    public String gotoClerkTaxList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/clerkTaxList";
    }
    
    /**
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoClerkFeeList", method = RequestMethod.GET)
    public String gotoClerkFeeList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/clerkFeeList";
    }
    
    /**
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoOrgVchList", method = RequestMethod.GET)
    public String gotoOrgVchList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/orgVchList";
    }
    
    /**
     * 
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoOrgTaxList", method = RequestMethod.GET)
    public String gotoOrgTaxList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/orgTaxList";
    }
    
    /**
     * 跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoOrgFeeList", method = RequestMethod.GET)
    public String gotoOrgFeeList(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        return "report/orgFeeList";
    }
    
    /**
     * 
     * 按会计查询凭证量 区间统计
     * 
     * @param request request
     * @param response response
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getClerkVchListForReport")
    public void getClerkVchListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<ClerkVchStatisticDto> list = new ArrayList<ClerkVchStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findClerkVchData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 按会计统计税金区间总额
     * 
     * @param request request
     * @param response response
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getClerkTaxListForReport")
    public void getClerkTaxListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<ClerkTaxStatisticDto> list = new ArrayList<ClerkTaxStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo3(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findClerkTaxData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd");
    }
    
    /**
     * 
     * 获取会计下公司记账费用
     * 
     * @param request request
     * @param response response
     * @param form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getClerkFeeListForReport")
    public void getClerkFeeListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<ClerkFeeStatisticDto> list = new ArrayList<ClerkFeeStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findClerkFeeData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd");
    }
    
    /**
     * 按会计查询凭证量 区间统计
     * 
     * @param request request
     * @param response response
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOrgVchListForReport")
    public void getOrgVchListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<OrgVchStatisticDto> list = new ArrayList<OrgVchStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findOrgVchData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 按企业统计税金区间总额
     * 
     * @param request request
     * @param response response
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOrgTaxListForReport")
    public void getOrgTaxListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<OrgTaxStatisticDto> list = new ArrayList<OrgTaxStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo3(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findOrgTaxData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd");
    }
    
    /**
     * 按公司维度查询记账费用
     * 
     * @param request request
     * @param response response
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOrgFeeListForReport")
    public void getOrgFeeListForReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, EnterpriseQueryForm form)
    {
        List<OrgFeeStatisticDto> list = new ArrayList<OrgFeeStatisticDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getOnDate()).matches() && pattern.matcher(form.getOffDate()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getOnDate()));
            form.setEndDate(DateUtil.getFirstDateForCompareTo2(form.getOffDate()));
            list = reportSer.findOrgFeeData(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd");
    }
    
    /* ------------------------主界面chart用service开始--------------- */
    
    /**
     * 
     * 主界面统计chart图标用
     * 
     * @param response response
     * @param request request
     * @param session session
     * @param form form
     * @see [类、类#方法、类#成员]
     */
    /*@RequestMapping(value = "asyn/getOrgRunningInfoList")
    public void getOrgRunningInfoList(HttpServletResponse response, HttpServletRequest request, HttpSession session, EnterpriseQueryForm form)
    {
        final TrendData data = new TrendData();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getSidx()).matches() && pattern.matcher(form.getSord()).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            form.setCreator(member.getOrgId());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getSidx()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(form.getSord()));
            List<TrendDataDto> list = new ArrayList<TrendDataDto>();
            data.setStrArray(DateUtil.getMonthArray(form.getSidx(), form.getSord()));
            if (form.getAuthState().equals(0) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForTax(form);
            }
            else if (form.getAuthState().equals(1) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForVch(form);
            }
            else if (form.getAuthState().equals(2) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForFee(form);
            }
            recoverTrendData(data, list);
        }
        returnAjaxBean(data, response);
    }*/
    
    /**
     * 获取提成明细
     * 
     * @param memberId 成员ID
     * @return Map<String, Object>
     */
    @RequestMapping("asyn/getCommissionDetail")
    @ResponseBody
    public List<Map<String, Object>> getCommissionDetail(String sdId, String edId, String memberId)
    {
        final List<Map<String, Object>> result = reportSer.getCommissionDetail(sdId, edId, Integer.parseInt(memberId));
        return result;
    }
    
    /**
     * 主界面报表
     * 
     * @param data data
     * @param list list
     * @see [类、类#方法、类#成员]
     */
    public void recoverTrendData(TrendData data, List<TrendDataDto> list)
    {
        data.setIntArray(new ArrayList<Object>());
        if (data.getStrArray().size() == 0)
        {
            return;
        }
        for (String item : data.getStrArray())
        {
            if (list == null || list.size() == 0)
            {
                data.getIntArray().add(0);
            }
            else
            {
                Object flag = 0;
                for (TrendDataDto dto : list)
                {
                    if (dto.getPeriod().equals(item))
                    {
                        flag = dto.getCount();
                        break;
                    }
                }
                data.getIntArray().add(flag);
            }
        }
    }
    
    /**
     * 页面跳转 提成明细页面
     * 
     * @param memberId 成员ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoCommissionDetail", method = RequestMethod.GET)
    public String gotoMoreFollows(HttpServletRequest request, String sdId, String edId, String memberId)
    {
        request.setAttribute("sdId", sdId);
        request.setAttribute("edId", edId);
        request.setAttribute("memberId", memberId);
        return "report/commissionDetail";
    }
}
