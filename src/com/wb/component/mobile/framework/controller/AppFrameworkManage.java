/*
 * 文 件 名:  FrameworkManage.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-4-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.mobile.framework.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.component.mobile.common.certificate.Certificate;
import com.wb.component.mobile.common.verification.MobileCheckEnum;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.framework.commonUtil.encrypt.MD5;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMngandusers;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.pojo.computer.ClerkManagementDto;
import com.wb.model.pojo.computer.PageProxyDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;
import com.wb.model.pojo.mobile.ClerkInfoForm;
import com.wb.model.pojo.mobile.MntLimitedInfoForm;
import com.wb.model.pojo.mobile.UserInfoForm;

/**
 * 组织架构controller
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("monitored")
public class AppFrameworkManage extends AjaxAction
{
    /**
     * 电脑端用登陆ser
     */
    @Autowired()
    @Qualifier("loginService")
    private ILoginService cpuLoginSer;
    
    /**
     * 电脑端组织架构ser
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;

    @RequestMapping(value = "asyn/delPersionById", method = RequestMethod.POST)
    public void delPersionById(HttpServletResponse response, HttpServletRequest request, String param)
    {
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        MntMember mem = cpuLoginSer.findById(certificate.getMemberID());
        try
        {
            JSONValue.toJSONString(param);
            ObjectMapper obj = new ObjectMapper();
            JsonNode node = obj.readTree(param.getBytes());
            int id = node.findValue("id").asInt();
            int state = node.findValue("state").asInt();
            MntMngandusers mmu = frameworkSer.findAssociationById(id);
            if (mmu != null)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                if (state == Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")))
                {
                    map.put("state", (new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DROPED"));
                    map.put("stamp", DateUtil.getCurDateTimeForString());
                    frameworkSer.updateClerkState(map, " and id = " + id);
                    String message = "代帐公司：" + mem.getOrgName() + "已经撤销了对您负责企业的监控！";
                    String path = "/supervisory/accountingOrgList";
                    String tabname = "账号被监控管理";
                    MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, mem.getTelphone());
                    frameworkSer.insertMsgNotification(mnc);
                }
                if (state == Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING")))
                {
                    map.put("state", (new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_GIVEUP"));
                    map.put("stamp", DateUtil.getCurDateTimeForString());
                    frameworkSer.updateClerkState(map, " and id = " + id);
                    String message = "代帐公司：" + mem.getOrgName() + "撤销了申请监控您企业的请求！";
                    String path = "/supervisory/accountingOrgList";
                    String tabname = "账号被监控管理";
                    MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, mem.getTelphone());
                    frameworkSer.insertMsgNotification(mnc);
                }
                if (state == Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_REFUSED")))
                {
                    map.put("state", (new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_BLACKMENU"));
                    map.put("stamp", DateUtil.getCurDateTimeForString());
                    frameworkSer.updateClerkState(map, " and id = " + id);
                    String message = "代帐公司：" + mem.getOrgName() + "删除了一条被您拒绝的监控您企业的请求！";
                    String path = "/supervisory/accountingOrgList";
                    String tabname = "账号被监控管理";
                    MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, mem.getTelphone());
                    frameworkSer.insertMsgNotification(mnc);
                }
                returnAjaxString(successMsg("操作成功!"), response);
            }
            else
            {
                returnAjaxString(failureMsg("此条申请已过期,请刷新数据再试！"), response);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("操作失败" + " : ") + e.getMessage(), response);
        }
    }
    
    /**
     * 
     * 添加员工(监控)
     * 
     * @param session session
     * @param response response
     * @param request request
     * @param qname 员工名称
     * @param qtel 电话号
     * @param qPartId 部门ID
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/addClerkFormonitor", method = RequestMethod.POST)
    public void addClerkFormonitor(HttpSession session, HttpServletResponse response, HttpServletRequest request, String qname, String qtel, String qPartName, String qPartId)
    {
        final BizMember member = frameworkSer.findMemberByParams(qname, qtel);
        if (member == null)
        {
            returnAjaxString(failureMsg("此员工不存在,请查证后再试！"), response);
            return;
        }
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        MntMember mem = cpuLoginSer.findById(certificate.getMemberID());
        final Integer flag = frameworkSer.findAssociationByParams(mem.getOrgId(), member.getId(), request);
        if (flag == 1)
        {
            returnAjaxString(failureMsg("已申请此员工加入公司或该员工已拒绝申请,请查证后再试！"), response);
            return;
        }
        if (flag == 2)
        {
            returnAjaxString(failureMsg("该员工已被其他人监控,请查证后再试！"), response);
            return;
        }
        try
        {
            final MntMngandusers mmu = new MntMngandusers(null, mem.getOrgId(), member.getId());
            mmu.setStamp(new Date());
            mmu.setState(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING")));
            frameworkSer.insertClerkAssociation(mmu);
            final String message = "代帐公司：" + mem.getOrgName() + "邀请你加入该公司" + qPartName + ",点击本条消息处理该请求！";
            final String tabname = "账号被监控管理";
            final String path = "/supervisory/accountingOrgList";
            final MsgNotification mnc = new MsgNotification(null, message, path, 0, mmu.getStamp(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, mem.getTelphone());
            mnc.setDepartmentId(qPartId);
            frameworkSer.insertMsgNotification(mnc);
            returnAjaxString(successMsg("已发送请求消息！"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("操作失败" + " : ") + e.getMessage(), response);
        }
    }
    
    /**
     * 
     * 组织架构树查询
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/findMntFrameWork", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> findFrameWork(HttpSession session, HttpServletRequest request, @RequestParam(required = false)String ifHasEmp)
    {
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        MntMember member = cpuLoginSer.findById(certificate.getMemberID());
        final int creater = member.getOrgId();
        final String partId = member.getDepartmentId();
        final int memberId = member.getId();
        List<Map<String, Object>> result = frameworkSer.findMntFrameWorkInfo(memberId, member.isAdmin(), Integer.toString(creater), ifHasEmp, partId);
        return result;
    }
    
    /**
     * 
     * 获取管理员工信息
     * 
     * @param request
     * @param response
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorGetClerkListForMobile")
    public void mointorGetClerkListForMobile(HttpServletRequest request, HttpServletResponse response, String param)
    {
        ClerkInfoForm arg = JSONValue.parse(param, ClerkInfoForm.class);
        SuperintendentQueryForm form = new SuperintendentQueryForm();
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        form.setMngId(certificate.getMemberID());
        form.setPartId(arg.getPartId());
        form.setArg1(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING")));
        form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
        form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_REFUSED")));
        form.setClerkName(arg.getClerkName());
        form.setClerkTel(arg.getClerkTel());
        form.setClerkState(arg.getClerkState());
        form.setPage(arg.getPage());
        form.setRows(arg.getRows());
        List<ClerkManagementDto> list = frameworkSer.findClerkListByParams(form);
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list), response, "yyyy-MM-dd HH:mm");
    }
    
    /**
     * 
     * 删除员工信息,解绑监控权限
     * 
     * @param request
     * @param session
     * @param empId
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorDelPersionById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mointorDelPersionById(HttpServletRequest request, HttpSession session, String empId)
    {
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        MntMember userInfo = cpuLoginSer.findById(certificate.getMemberID());
        final Map<String, Object> result = new HashMap<String, Object>();
        int delNum = frameworkSer.delEmp(userInfo.getOrgId().toString(), empId, userInfo.getOrgName());
        if (delNum > 0)
        {
            result.put("success", true);
            result.put("message", "删除成功!");
            return result;
        }
        result.put("success", false);
        result.put("message", "删除失败,该员工已被删除!");
        return result;
    }
    
    /**
     * @Title: clerkPwdModifyForMobile
     * @Description: 修改员工密码
     * @param @param session
     * @param @param response
     * @param @param request
     * @param @param id
     * @param @param state
     * @author 王磊
     * @return void 返回类型
     * @throws
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "asyn/mointorClerkPwdModifyForMobile", method = RequestMethod.POST)
    public void mointorClerkPwdModifyForMobile(HttpSession session, HttpServletResponse response, HttpServletRequest request, String param)
    {
        UserInfoForm arg = JSONValue.parse(param, UserInfoForm.class);
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        try
        {
            MntMngandusers mmu = frameworkSer.findAssociationById(arg.getAsoId());
            if (mmu != null && mmu.getState().equals(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT"))) && certificate.getMemberID().equals(mmu.getMntMemberId()))
            {
                if (MobileCheckEnum.VERIFY.VERIFY_PASSWORD_MODE.isVerificated(arg.getArg1()))
                {
                    MD5 md5 = new MD5();
                    Map map = new HashMap();
                    map.put("password", md5.encrypt(arg.getArg1()));
                    frameworkSer.updateBizMember(map, " and id = " + mmu.getUserMemberId());
                    returnAjaxString(successMsg("操作成功!"), response);
                }
                else
                {
                    returnAjaxString(failureMsg("密码格式不正确,应为大小写字母或数字或下划线,请重试！"), response);
                }
            }
            else
            {
                returnAjaxString(failureMsg("此条记录已过期,不符合修改条件,请刷新数据再试！"), response);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("操作失败" + " : ") + e.getMessage(), response);
        }
    }

    /**
     * 
     * 登陆权限设置
     * 
     * @param response
     * @param request
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "asyn/mointorModifyLimitInfoForMobile")
    public void mointorModifyLimitInfoForMobile(HttpServletResponse response, HttpServletRequest request, String param)
    {
        MntLimitedInfoForm arg = JSONValue.parse(param, MntLimitedInfoForm.class);
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        try
        {
            if (arg.getIcbm().equals(0) || arg.getIcbm().equals(1))
            {
                if (arg.getSMethod().equals(1) && !(MobileCheckEnum.VERIFY.VERIFY_TIME_MODE.isVerificated(arg.getStartTime()) && MobileCheckEnum.VERIFY.VERIFY_TIME_MODE.isVerificated(arg.getEndTime())))
                {
                    returnAjaxString(failureMsg("时间格式不正确,请重试！"), response);
                    return;
                }
                Map map = new HashMap();
                map.put("startTime", arg.getStartTime());
                map.put("endTime", arg.getEndTime());
                map.put("isCanBeModify", arg.getIcbm() + "");
                map.put("selectMethod", arg.getSMethod() + "");
                frameworkSer.updateMntMember(map, " and id = " + certificate.getMemberID());
                returnAjaxString(successMsg("操作成功!"), response);
            }
            else
            {
                returnAjaxString(failureMsg("操作有误,请刷新后再试！"), response);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("修改失败! 错误：" + e.getMessage()), response);
        }
    }
    
    /**
     * 
     * 登陆权限查看
     * 
     * @param request
     * @param response
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorLoginLimitedForMobile")
    public void mointorLoginLimitedForMobile(HttpServletRequest request, HttpServletResponse response)
    {
        Certificate certificate = (Certificate)request.getAttribute("certificate");
        MntMember member = cpuLoginSer.findById(certificate.getMemberID());
        MntLimitedInfoForm limitedForm = new MntLimitedInfoForm();
        limitedForm.setSMethod((member.getSelectMethod() == null ? 0 : member.getSelectMethod()));
        if (member.getSelectMethod() != null && member.getSelectMethod().equals(1))
        {
            limitedForm.setStartTime(member.getStartTime() == null ? "00:00" : member.getStartTime());
            limitedForm.setEndTime(member.getEndTime() == null ? "23:59" : member.getEndTime());
        }
        else
        {
            limitedForm.setStartTime("00:00");
            limitedForm.setEndTime("23:59");
        }
        limitedForm.setIcbm(member.getIsCanBeModify() == null ? 0 : member.getIsCanBeModify());
        JSONObject dataJson = new JSONObject();
        dataJson.put("limitedInfo", limitedForm);
        returnAjaxString(successMsg(dataJson.toJSONString()), response);
    }
}
