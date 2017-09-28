package com.wb.component.mobile.mainBuss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.empWork.service.IEmpWorkDetailService;
import com.wb.component.computer.report.service.IReportService;
import com.wb.component.mobile.common.certificate.Certificate;
import com.wb.component.mobile.common.constant.ConstantMobile;
import com.wb.component.mobile.mainBuss.service.IMainBussService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.model.pojo.computer.ClerkWorkMonitorDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgDetailDto;
import com.wb.model.pojo.computer.OrgTaxMonitorDto;
import com.wb.model.pojo.computer.PageProxyDto;
import com.wb.model.pojo.computer.SettleOrgDto;
import com.wb.model.pojo.computer.TrendData;
import com.wb.model.pojo.computer.TrendDataDto;
import com.wb.model.pojo.mobile.BusinessInfoForm;
import com.wb.model.pojo.mobile.ClerkWorkInfoForm;
import com.wb.model.pojo.mobile.HomeShowForMobile;
import com.wb.model.pojo.mobile.PeriodInfoForm;
import com.wb.model.pojo.mobile.login.LoginVo;

/**
 * 
 * 手机端接口
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]_测试新的切换功能
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("monitored")
public class MainBussiness extends AjaxAction
{
    /**
     * 主要业务ser
     */
    @Autowired
    @Qualifier("mainBussService")
    private IMainBussService mainBussService;
    
    /**
     * 员工工作明细ser
     */
    @Autowired
    @Qualifier("empWorkDetailService")
    private IEmpWorkDetailService empSer;
    
    /**
     * 服务ser
     */
    @Autowired
    @Qualifier("reportService")
    private IReportService reportSer;
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 
     * 代帐公司基本业务信息一览
     * 
     * @param request
     * @param response
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorGetSumInfoData")
    public void mointorGetSumInfoData(HttpServletRequest request, HttpServletResponse response, String param)
    {
        Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(param).matches())
        {
            Certificate certificate = (Certificate)request.getAttribute("certificate");
            EnterpriseQueryForm form = new EnterpriseQueryForm();
            form.setLisence(param);
            form.setCreator(certificate.getMemberID());
            form.setAuthState(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
            form.setArg1(ConstantMobile.HEAT_DEAD_LINE / 1000);
            form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_OFFLINE")));
            form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_ONLINE")));
            form.setEndDate(DateUtil.getCurMonthLastDate(new Date()));
            HomeShowForMobile dto = mainBussService.findHomeShowData(form);
            LoginVo conPayInfo = customerManageService.getConPayInfo(certificate.getMemberID().toString(), null, param);
            JSONObject dataJson = new JSONObject();
            dataJson.put("info1", dto);
            dataJson.put("info2", conPayInfo);
            String dataString = dataJson.toJSONString();
            returnAjaxString(successData(dataString), response);
        }
    }
    
    /**
     * 
     * 获取员工工作情况
     * 
     * @param response
     * @param request
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorGetClerkForMobile")
    public void mointorGetClerkForMobile(HttpServletResponse response, HttpServletRequest request, String param)
    {
        ClerkWorkInfoForm arg = JSONValue.parse(param, ClerkWorkInfoForm.class);
        EnterpriseQueryForm form = new EnterpriseQueryForm();
        List<ClerkWorkMonitorDto> list = new ArrayList<ClerkWorkMonitorDto>();
        Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(arg.getPeriod()).matches())
        {
            Certificate certificate = (Certificate)request.getAttribute("certificate");
            form.setCreator(certificate.getMemberID());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(arg.getPeriod()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(arg.getPeriod()));
            form.setLisence(arg.getPeriod());
            form.setFbUserName(arg.getClerkName());
            form.setSettleProcess(arg.getSettleProcess());
            form.setTaxProcess(arg.getTaxProcess());
            form.setSidx("CONVERT(clerkName USING GBK)");
            form.setSord("asc");
            form.setPage(arg.getPage());
            form.setRows(arg.getRows());
            list = empSer.findClerkWorkDetailList(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 获取企业信息
     * 
     * @param response
     * @param request
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorGetOrgBizInfoList")
    public void mointorGetOrgBizInfoList(HttpServletResponse response, HttpServletRequest request, String param)
    {
        BusinessInfoForm arg = JSONValue.parse(param, BusinessInfoForm.class);
        EnterpriseQueryForm form = new EnterpriseQueryForm();
        List<OrgDetailDto> list = new ArrayList<OrgDetailDto>();
        Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(arg.getPeriod()).matches())
        {
            Certificate certificate = (Certificate)request.getAttribute("certificate");
            form.setCreator(certificate.getMemberID());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(arg.getPeriod()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(arg.getPeriod()));
            form.setLisence(arg.getPeriod());
            form.setOrgName(arg.getOrgName());
            form.setFbUserName(arg.getClerkName());
            form.setAuthState(arg.getIsSettled());
            form.setIsAllSelect(arg.getOrgTaxState());
            form.setIsDeleted(arg.getIsMsgState());
            form.setPage(arg.getPage());
            form.setRows(arg.getRows());
            list = mainBussService.findBizInfoOrgList(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 按税金 凭证 代账费统计报表
     * 
     * @param response
     * @param request
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorGetPeriodInfoList")
    public void getPeriodInfoList(HttpServletResponse response, HttpServletRequest request, String param)
    {
        PeriodInfoForm arg = JSONValue.parse(param, PeriodInfoForm.class);
        EnterpriseQueryForm form = new EnterpriseQueryForm();
        TrendData data = new TrendData();
        Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(arg.getStartPeriod()).matches() && pattern.matcher(arg.getEndPeriod()).matches())
        {
            Certificate certificate = (Certificate)request.getAttribute("certificate");
            form.setCreator(certificate.getMemberID());
            form.setSidx(arg.getStartPeriod());
            form.setSord(arg.getEndPeriod());
            form.setAuthState(arg.getSelectEle());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(arg.getStartPeriod()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(arg.getEndPeriod()));
            List<TrendDataDto> list = new ArrayList<TrendDataDto>();
            data.setStrArray(DateUtil.getMonthArray(arg.getStartPeriod(), arg.getEndPeriod()));
            if (arg.getSelectEle().equals(0) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForTax(form);
            }
            else if (arg.getSelectEle().equals(1) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForVch(form);
            }
            else if (arg.getSelectEle().equals(2) && data.getStrArray().size() > 0)
            {
                list = reportSer.findIntegerArrayForFee(form);
            }
            recoverTrendData(data, list);
        }
        returnAjaxBean(data, response);
    }
    
    /**
     * 
     * 生成数据
     * 
     * @param data
     * @param list
     * @see [类、类#方法、类#成员]
     */
    public void recoverTrendData(TrendData data, List<TrendDataDto> list)
    {
        data.setIntArray(new ArrayList<Object>());
        if (data.getStrArray().size() == 0)
        {
            return;
        }
        else
        {
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
    }
    
    @RequestMapping("asyn/mointorShowTaxComInfo")
    public void showTaxComInfo(HttpServletResponse response, HttpServletRequest request, EnterpriseQueryForm form)
    {
        form.setSidx("orgTaxState");
        form.setSord("desc");
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        form.setCreator(certificate.getMemberID());
        final List<OrgTaxMonitorDto> list = empSer.findOrgTaxByParamsForMng(form);
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    @RequestMapping("asyn/mointorShowSettleComInfo")
    public void showSettleComInfo(HttpServletResponse response, HttpServletRequest request, HttpSession session, EnterpriseQueryForm form)
    {
        List<SettleOrgDto> list = new ArrayList<SettleOrgDto>();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(form.getLisence()).matches())
        {
            Certificate certificate = (Certificate)request.getAttribute("certificate");
            form.setCreator(certificate.getMemberID());
            form.setStartDate(DateUtil.getFirstDateForCompareTo(form.getLisence()));
            form.setEndDate(DateUtil.getLastDateForCompareTo(form.getLisence()));
            list = empSer.findSettledOrgList(form);
        }
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list, form.getTrendData()), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 获取新增客户信息
     * 
     * @param response response
     * @param mngId 所属代账公司ID
     */
    @RequestMapping(value = "asyn/mointorShowCustomerIncreaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public void mointorShowCustomerIncreaseInfo(HttpServletResponse response, String mngId)
    {
        List<Map<String, Object>> result = mainBussService.getCustomerIncreaseInfo(mngId, DateUtil.getCurDateTimeForString());
        returnAjaxBean(result, response);
    }
    
    /**
     * 获取欠费信息
     * 
     * @param response response
     * @param mngId 所属代账公司ID
     * @param memberId biz_memberID
     */
    @RequestMapping(value = "asyn/mointorShowArrearageInfo", method = RequestMethod.POST)
    @ResponseBody
    public void mointorShowArrearageInfo(HttpServletResponse response, String mngId, String memberId)
    {
        List<Map<String, Object>> result = mainBussService.mointorShowArrearageInfo(mngId, memberId);
        returnAjaxBean(result, response);
    }
    
    /**
     * 获取预交费信息
     * 
     * @param response response
     * @param mngId 所属代账公司ID
     */
    @RequestMapping(value = "asyn/mointorShowPrePayInfo", method = RequestMethod.POST)
    @ResponseBody
    public void mointorShowPrePayInfo(HttpServletResponse response, String mngId)
    {
        List<Map<String, Object>> result = mainBussService.mointorShowPrePayInfo(mngId, DateUtil.getCurDateTimeForString());
        returnAjaxBean(result, response);
    }
}
