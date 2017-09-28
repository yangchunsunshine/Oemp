package com.wb.component.computer.auditSettings.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.auditSettings.service.IAuditSettingsService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;

/**
 * 审批设置Controller
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class AuditSettings extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(AuditSettings.class);
    
    /**
     * 审批设置Service层实例
     */
    @Autowired
    @Qualifier("auditSettingsService")
    private IAuditSettingsService auditSettingsService;
    
    /**
     * 获取审批人员列表
     * 
     * @param session session
     * @param init 是否为初始化页面
     * @param cmd 查询方式（BY_EMP,BY_DEP）
     * @param queryId 查询ID
     * @param auditorName 审批人姓名
     * @param auditType 审批流类型
     * @param info 分页信息
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getAuditSettingsList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAuditSettingsList(HttpSession session, boolean init, String cmd, String queryId, String auditorName, String auditType, String auditLevel, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        if (!member.isAdmin() && init)
        {
            queryId = member.getDepartmentId().toString();
        }
        final PageUtil util = auditSettingsService.getAuditSettingsList(cmd, Integer.parseInt(queryId), orgId, auditorName, Integer.parseInt(auditType), auditLevel, info);
        return util.initResult();
    }
    
    /**
     * 添加审批人员
     * 
     * @param auditorId 审批人ID
     * @param auditorType 审批流类别
     * @param auditorLevel 审批级别
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/saveAuditor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveAuditor(HttpSession session, String auditorId, String auditType, String auditLevel)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        Map<String, Object> result = new HashMap<String, Object>();
        final int code = auditSettingsService.saveAuditor(orgId, auditorId, auditType, auditLevel);
        result.put("code", code);
        return result;
    }
    
    /**
     * 删除审批人员信息
     * 
     * @param auditId 审批ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/deleteAuditor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteAuditor(String auditId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = auditSettingsService.deleteAuditor(auditId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 更新审批人员信息
     * 
     * @param auditId 审批ID
     * @param auditorId 审批人ID
     * @param auditType 审批流类型
     * @param auditLevel 审批级别
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/updateAuditor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAuditor(HttpSession session, String auditId, String auditorId, String auditType, String auditLevel)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final int code = auditSettingsService.updateAuditor(mngId, auditId, auditorId, auditType, auditLevel);
        result.put("code", code);
        return result;
    }
    
    /**
     * 验证审批流程完整性
     * 
     * @param auditType 审批流类型
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/checkAuditIntegrity", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkAuditIntegrity(HttpSession session, String auditType)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final boolean code = auditSettingsService.checkAuditIntegrity(Integer.parseInt(auditType), mngId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 审批通过
     * 
     * @param routeId 审批ID
     * @param auditFlag 审批流程节点
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/passAudit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> passAudit(HttpSession session, String auditType, String routeId, String auditFlag, String auditorLevel)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final boolean code = auditSettingsService.updatePassAudit(mngId, Integer.parseInt(auditType), Integer.parseInt(routeId), Integer.parseInt(auditFlag), Integer.parseInt(auditorLevel));
        result.put("code", code);
        return result;
    }
    
    /**
     * 取消审批
     * 
     * @param routeId 审批ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/backAudit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> backAudit(String routeId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = auditSettingsService.updateBackAudit(Integer.parseInt(routeId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 取消缴费
     * 
     * @param expId 缴费ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/delExp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delExp(String expId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = auditSettingsService.deleteExp(Integer.parseInt(expId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 页面跳转 审批设置页面
     * 
     * @param request
     * @return String
     */
    @RequestMapping(value = "forward/gotoAuditSettings", method = RequestMethod.GET)
    public String gotoAuditSettings(HttpServletRequest request)
    {
        return "auditSettings/auditSettings";
    }
}
