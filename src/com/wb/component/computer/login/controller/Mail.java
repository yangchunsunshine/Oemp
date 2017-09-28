/*
 * 文 件 名:  Mail.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wb.component.computer.login.service.IMailService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageProxy;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.pojo.computer.PageProxyDto;

/**
 * 站内信
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class Mail extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(Mail.class);

    /**
     * 站内信ser
     */
    @Autowired
    @Qualifier("mailService")
    private IMailService mailSer;
    
    /**
     * 
     * 站内信页面跳转
     * 
     * @param request request
     * @return String request
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoLookMail", method = RequestMethod.GET)
    public String gotoLookMail(HttpServletRequest request)
    {
        return "login/mail";
    }
    
    /**
     * 查询企业信息
     * 
     * @param response response
     * @param request response
     * @param session session
     * @param page page
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getMessageListForMng", method = RequestMethod.POST)
    public void getMessageListForMng(HttpServletResponse response, HttpServletRequest request, HttpSession session, int page)
    {
        final PageProxy pageProxy = new PageProxy();
        pageProxy.setCurrentPage(page);
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        //add by zhouww date:20161020 desc:修复bug管理员和员工查询的公告信息一样 改成 只查询自己的公告信息 查询表msg_notification的时候添加约束userId
        Integer adminId = member.getEmpInfo().getId();
        final List<MsgNotification> list = mailSer.findNotificationForMng(member.getOrgId(),adminId, pageProxy);
        returnAjaxBean(new PageProxyDto(pageProxy.getCurrentPage(), pageProxy.getAllRecord(), pageProxy.getRecordOfPage(), list), response, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 
     * 设置全部为已读(总账)
     * 
     * @param response response
     * @param request request
     * @param session session
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "asyn/setAllReadForMng", method = RequestMethod.POST)
    public void setAllReadForMng(HttpServletResponse response, HttpServletRequest request, HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        try
        {
            final Map map = new HashMap();
            map.put("isread", String.valueOf(1));
            mailSer.updateMsgNotification(map, " and mngId = " + member.getOrgId() + " and isread = 0 and direction = 1 ");
            returnAjaxString(successMsg("已全部标记为已读!"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("全部标记为已读失败! 错误：" + e.getMessage()), response);
        }
    }
    
    /**
     * 
     * 设置全部为已读
     * 
     * @param response response
     * @param request request
     * @param session session
     * @param ids ids
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "asyn/setcurReadForMng", method = RequestMethod.POST)
    public void setcurReadForMng(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestParam(value = "ids") String ids)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        try
        {
            final Map map = new HashMap();
            map.put("isread", String.valueOf(1));
            mailSer.updateMsgNotification(map, " and mngId = " + member.getOrgId() + " and id in ( " + ids + " )  and isread = 0 and direction = 1 ");
            returnAjaxString(successMsg("已全部标记为已读!"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("全部标记为已读失败! 错误：" + e.getMessage()), response);
        }
    }
    
    /**
     * 站内信
     * 
     * @param request request
     * @param url url
     * @param id id
     * @param condition condition
     * @return 站内信关联的功能菜单路径
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "forward/messageDispatherForMng", method = RequestMethod.GET)
    public String messageDispatherForMng(HttpServletRequest request, @RequestParam(value = "path") String url, @RequestParam(value = "msgId") Integer id, @RequestParam(value = "condition") String condition)
    {
        final Map map = new HashMap();
        map.put("isread", String.valueOf(1));
        mailSer.updateMsgNotification(map, " and id = " + id);
        request.setAttribute("condition", condition == null ? "" : condition);
        return url;
    }
    
    @RequestMapping(value = "forward/gotoUpdateLog", method = RequestMethod.GET)
    public String gotoMoreFollows()
    {
        return "login/updateLog";
    }
}
