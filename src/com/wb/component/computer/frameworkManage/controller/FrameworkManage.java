/*
 * 文 件 名:  FrameworkManage.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.frameworkManage.controller;

import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.wb.component.computer.common.verification.ComputerCheckEnum;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.StrUtils;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.framework.commonUtil.encrypt.MD5;
import com.wb.framework.commonUtil.smssSender.SMSCheckCode;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMngandusers;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.entity.computer.frameworkManage.MntDepartmentInfo;
import com.wb.model.pojo.computer.ClerkManagementDto;
import com.wb.model.pojo.computer.PageProxyDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;

/**
 * 企业组织架构管理功能
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "supervisory")
public class FrameworkManage extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(FrameworkManage.class);
    
    /**
     * 组织架构ser
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;
    
    /**
     * 登陆ser
     */
    @Autowired
    @Qualifier("loginService")
    private ILoginService loginSer;
    
    /**
     * 
     * 组织架构页面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoFrameworkManage", method = RequestMethod.GET)
    public String gotoAccountingClerkList(HttpSession session, HttpServletRequest request)
    {
        return "frameworkManage/frameworkManage";
    }
    
    /**
     * 
     * 添加员工页面
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoAddEmp", method = RequestMethod.GET)
    public String gotoAddEmp(HttpSession session, HttpServletRequest request)
    {
        return "frameworkManage/empCreate";
    }
    
    /**
     * 
     * 编辑员工信息
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoEditEmp", method = RequestMethod.GET)
    public String gotoEditEmp(HttpSession session, HttpServletRequest request, Model model, String mmuId)
    {
        final String empId = frameworkSer.findEmpIdByMMUID(mmuId);
        final BizMember member = frameworkSer.findEmpInfoById(empId);
        model.addAttribute("empInfo", member);
        return "frameworkManage/empEdit";
    }
    
    /**
     * 登陆限制页面跳转
     * 
     * @param request request
     * @param session session
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoEmpLoginLimited", method = RequestMethod.GET)
    public String gotoLoginLimited(HttpServletRequest request, HttpSession session)
    {
        final MntMember mem = (MntMember)session.getAttribute("userInfo");
        final MntMember member = loginSer.findByTel(mem.getTelphone());
        request.setAttribute("selectMethod", member.getSelectMethod() == null ? "0" : member.getSelectMethod());
        if (member.getSelectMethod() != null && member.getSelectMethod().equals(1))
        {
            request.setAttribute("startTime", member.getStartTime() == null ? "00:00" : member.getStartTime());
            request.setAttribute("endTime", member.getEndTime() == null ? "23:59" : member.getEndTime());
        }
        else
        {
            request.setAttribute("startTime", "00:00");
            request.setAttribute("endTime", "23:59");
        }
        request.setAttribute("isCanBeModify", member.getIsCanBeModify() == null ? "0" : member.getIsCanBeModify());
        return "frameworkManage/empLoginLimited";
    }
    
    /**
     * 
     * 获取管理企业列表
     * 
     * @param request request
     * @param response request
     * @param session session
     * @param form session
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getClerkMngList")
    public void getClerkMngList(HttpServletRequest request, HttpServletResponse response, HttpSession session, SuperintendentQueryForm form)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        if (StrUtils.isEmpty(form.getPartId()))
        {
            form.setPartId(member.getDepartmentId());
        }
        form.setMngId(member.getOrgId());
        form.setArg1(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING")));
        form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
        form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_REFUSED")));
        final List<ClerkManagementDto> list = frameworkSer.findClerkListByParams(form);
        returnAjaxBean(new PageProxyDto(form.getPage(), form.getRecords(), form.getRows(), list), response, "yyyy-MM-dd HH:mm");
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
    @RequestMapping(value = "asyn/findMntFrameWork", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> findFrameWork(HttpSession session, HttpServletRequest request, @RequestParam(required = false)
    String ifHasEmp)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creater = member.getOrgId();
        final String partId = member.getDepartmentId();
        final int memberId = member.getId();
        List<Map<String, Object>> result = frameworkSer.findMntFrameWorkInfo(memberId, member.isAdmin(), Integer.toString(creater), ifHasEmp, partId);
        return result;
    }
    
    /**
     * 
     * 查询企业监控信息列表
     * 
     * @param session session
     * @param response response
     * @param request response
     * @param id id
     * @param state state
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/queryDealingForClerk", method = RequestMethod.POST)
    public void queryDealingForClerk(HttpSession session, HttpServletResponse response, HttpServletRequest request, Integer id, Integer state)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        try
        {
            final MntMngandusers mmu = frameworkSer.findAssociationById(id);
            if (mmu == null)
            {
                returnAjaxString(failureMsg("此条申请已过期,请刷新数据再试！"), response);
                return;
            }
            final Map<String, Object> map = new HashMap<String, Object>();
            if (state.equals(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING"))))
            {
                map.put("state", (new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_GIVEUP"));
                map.put("stamp", DateUtil.getCurDateTimeForString());
                frameworkSer.updateClerkState(map, " and id = " + id);
                final String message = "代帐公司：" + member.getOrgName() + "撤销了对该帐号监控请求！";
                final String path = "/supervisory/accountingOrgList";
                final String tabname = "账号被监控管理";
                final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, member.getTelphone());
                frameworkSer.insertMsgNotification(mnc);
            }
            if (state.equals(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_REFUSED"))))
            {
                map.put("state", (new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_BLACKMENU"));
                map.put("stamp", DateUtil.getCurDateTimeForString());
                frameworkSer.updateClerkState(map, " and id = " + id);
                final String message = "代帐公司：" + member.getOrgName() + "删除了一条被拒绝的监控请求！";
                final String path = "/supervisory/accountingOrgList";
                final String tabname = "账号被监控管理";
                final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, member.getTelphone());
                frameworkSer.insertMsgNotification(mnc);
            }
            returnAjaxString(successMsg("操作成功!"), response);
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
     * @param qname qname
     * @param qtel qtel
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
        final MntMember mem = (MntMember)session.getAttribute("userInfo");
        final Integer flag = frameworkSer.findAssociationByParams(mem.getOrgId(), member.getId(), request);
        if (flag == 1)
        {
            returnAjaxString(failureMsg("已申请此员工加入公司!"), response);
            return;
        }
        if (flag == 2)
        {
            returnAjaxString(failureMsg("该员工已被其他人监控!"), response);
            return;
        }
        if (flag == 3)
        {
            returnAjaxString(failureMsg("该员工已拒绝您的监控请求!"), response);
            return;
        }
        try
        {
            final MntMngandusers mmu = new MntMngandusers(null, mem.getOrgId(), member.getId());
            mmu.setStamp(new Date());
            mmu.setState(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_DEALING")));
            frameworkSer.insertClerkAssociation(mmu);
            final String message = "代帐公司：" + mem.getOrgName() + "_邀请你加入该公司_" + qPartName + ",点击本条消息处理该请求！";
            final String tabname = "账号被监控管理";
            final String path = "/supervisory/accountingOrgList";
            final MsgNotification mnc = new MsgNotification(null, message, path, 0, mmu.getStamp(), mmu.getUserMemberId(), mmu.getMntMemberId(), tabname, 0, mem.getTelphone());
            mnc.setDepartmentId(qPartId);
            frameworkSer.insertMsgNotification(mnc);
            //引入员工初始化权限
            frameworkSer.saveMemberIntroduceRoleInit(mem.getOrgId(), member.getId());
            returnAjaxString(successMsg("已发送请求消息！"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("操作失败" + " : ") + e.getMessage(), response);
        }
    }
    
    /**
     * 修改登陆限制
     * 
     * @param response
     * @param request
     * @param startTime
     * @param endTime
     * @param icbm
     * @param sMethod
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/modifyLimitInfo", method = RequestMethod.POST)
    public void modifyLimitInfo(HttpServletResponse response, HttpServletRequest request, HttpSession session, String startTime, String endTime, Integer icbm, Integer sMethod)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        try
        {
            if (icbm.equals(0) || icbm.equals(1))
            {
                if (sMethod.equals(1) && !(ComputerCheckEnum.VERIFY.VERIFY_TIME_MODE.isVerificated(startTime) && ComputerCheckEnum.VERIFY.VERIFY_TIME_MODE.isVerificated(endTime)))
                {
                    returnAjaxString(failureMsg("时间格式不正确,请重试！"), response);
                    return;
                }
                final Map<String, String> map = new HashMap<String, String>();
                map.put("startTime", startTime);
                map.put("endTime", endTime);
                map.put("isCanBeModify", icbm + "");
                map.put("selectMethod", sMethod + "");
                frameworkSer.updateMntMember(map, " and id = " + member.getOrgId());
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
     * 会计密码修改
     * 
     * @param session session
     * @param response response
     * @param request request
     * @param pwdId pwdId
     * @param pwd pwd
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/clerkPwdModify", method = RequestMethod.POST)
    public void clerkPwdModify(HttpSession session, HttpServletResponse response, HttpServletRequest request, Integer pwdId, String pwd)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        try
        {
            final MntMngandusers mmu = frameworkSer.findAssociationById(pwdId);
            if (mmu != null && mmu.getState().equals(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT"))) && member.getOrgId().equals(mmu.getMntMemberId()))
            {
                if (ComputerCheckEnum.VERIFY.VERIFY_PASSWORD_MODE.isVerificated(pwd))
                {
                    final MD5 md5 = new MD5();
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("password", md5.encrypt(pwd));
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
     * 查询需要交接的账号信息
     * 
     * @param tel tel
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserInfo(String tel)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final Map<String, Object> userInfo = frameworkSer.findUserInfo(tel);
        if (userInfo == null)
        {
            result.put("success", false);
            result.put("message", "未找到到用户信息.");
            return result;
        }
        result.put("success", true);
        result.put("data", userInfo);
        return result;
    }
    
    /**
     * 
     * 交接请求
     * 
     * @param session session
     * @param toId 接受老板ID
     * @param ids 交接的人员ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/rawOrgToOther", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> rawOrgToOther(HttpSession session, int toId, String ids)
    {
        String[] _ids = null;
        if (ids != null && !"".equals(ids))
        {
            _ids = ids.split("\\@\\!\\$\\@");
        }
        final MntMember memberVo = (MntMember)session.getAttribute("userInfo");
        final int nowId = memberVo.getOrgId();
        int flag = frameworkSer.updateOrgToOrther(nowId, toId, _ids);
        final Map<String, Object> result = new HashMap<String, Object>();
        if (flag == 0)
        {
            result.put("msg", "监控人员交接成功!");
        }
        else
        {
            result.put("msg", "监控人员交接失败!");
        }
        return result;
    }
    
    /**
     * 
     * 查询被当前账号监控的人员
     * 
     * @param session session
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/findControEmpInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findControEmpInfo(HttpSession session)
    {
        final MntMember memberVo = (MntMember)session.getAttribute("userInfo");
        final int memberId = memberVo.getOrgId();
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("result", frameworkSer.findControEmpInfo(memberId));
        return result;
    }
    
    
    
    /**
     * 
     * 查询当前登录用户的其他角色信息
     * 
     * @param session session
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/findOtherRoleInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findOtherRoleInfo(HttpSession session,String tel)
    {
        final MntMember memberVo = (MntMember)session.getAttribute("userInfo");
        final int memberId = memberVo.getOrgId();
        List<Map<String, Object>> list = frameworkSer.findOtherRoleInfo(tel);
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("result", list);
        return result;
    }
    
    /**
     * 添加部门
     * 
     * @param session session
     * @param vo ztree实体类
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/addPartInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPartInfo(HttpSession session, MntDepartmentInfo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creater = member.getOrgId();
        final int code = frameworkSer.addPartInfo(vo, creater);
        result.put("code", code);
        return result;
    }
    
    /**
     * 删除部门
     * 
     * @param session session
     * @param vo ztree实体类
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/deletePartInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deletePartInfo(HttpSession session, MntDepartmentInfo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final int code = frameworkSer.deletePartInfo(vo);
        result.put("code", code);
        return result;
    }
    
    /**
     * 更新部门
     * 
     * @param session session
     * @param vo ztree实体类
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/updatePartInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePartInfo(HttpSession session, MntDepartmentInfo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creater = member.getOrgId();
        final int code = frameworkSer.updatePartInfo(vo, creater);
        result.put("code", code);
        return result;
    }
    
    /**
     * 
     * 添加员工
     * 
     * @param session
     * @param vo
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/addEmpInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEmpInfo(HttpSession session, BizMember vo)
    {
        MntMember userInfo = (MntMember)session.getAttribute("userInfo");
        final MD5 md5 = new MD5();
        vo.setPassword(md5.encrypt("123456"));
        vo.setLastStamp(new Date());
        vo.setCreateTime(new Date());
        vo.setEnable(1);
        final Map<String, Object> result = new HashMap<String, Object>();
        boolean ifHasBm = frameworkSer.ifHasBm(vo.getTelphone());
        if (ifHasBm)
        {
            result.put("success", false);
            result.put("msg", "该账号已被注册!<br/>请使用: [引入已注册员工]功能!");
            return result;
        }
        MntMngandusers mmu = new MntMngandusers();
        mmu.setMntMemberId(userInfo.getOrgId());
        mmu.setStamp(new Date());
        mmu.setState(1);
        int flag = frameworkSer.addEmpInfo(vo, mmu);
        if (flag == 0)
        {
            result.put("success", true);
            result.put("msg", "账号注册成功!");
            result.put("memberId", vo.getId());
        }
        else
        {
            result.put("success", true);
            result.put("msg", "账号注册失败!");
        }
        return result;
    }
    
    /**
     * 
     * 编辑员工信息
     * 
     * @param session
     * @param vo
     * @param cancleFlag 停用账号标记,实体中没有,则单独传
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/editEmpInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editEmpInfo(HttpSession session, BizMember vo, String cancleFlag)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        BizMember member = frameworkSer.findEmpInfoById(vo.getId().toString());
        member.setName(vo.getName());
        member.setSex(vo.getSex());
        member.setCardNo(vo.getCardNo());
        member.setEmail(vo.getEmail());
        member.setQq(vo.getQq());
        member.setDepartmentId(vo.getDepartmentId());
        member.setDemo(vo.getDemo());
        member.setAddr(vo.getAddr());
        int enable = 1;
        if ("on".equals(cancleFlag))
        {
            enable = 0;
        }
        member.setEnable(enable);
        int flag = frameworkSer.updateEmpInfo(member);
        if (flag == 0)
        {
            result.put("success", true);
            result.put("msg", "员工信息修改成功!");
        }
        else
        {
            result.put("success", true);
            result.put("msg", "员工信息修改失败!");
        }
        
        return result;
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
    @RequestMapping(value = "asyn/delEmpInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delEmpInfo(HttpServletRequest request, HttpSession session, String empId)
    {
        MntMember userInfo = (MntMember)session.getAttribute("userInfo");
        final Map<String, Object> result = new HashMap<String, Object>();
        int delNum = frameworkSer.delEmp(userInfo.getOrgId().toString(), empId, userInfo.getOrgName());
        if (delNum > 0)
        {
            result.put("success", true);
            result.put("msg", "删除成功!");
            return result;
        }
        result.put("success", false);
        result.put("msg", "删除失败,该员工已被删除!");
        return result;
    }
    
    @RequestMapping(value = "asyn/checkAddEmpCode", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkCode(HttpServletRequest request, HttpSession session, String telphone, String checkCode)
    {
        final SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        final Map<String, Object> result = new HashMap<String, Object>();
        if (smsCheckCode == null || !smsCheckCode.getTel().equals(telphone))
        {
            result.put("success", false);
            result.put("type", 0000);
            result.put("message", "未获取验证码！");
            return result;
        }
        final String code = smsCheckCode.getCode();
        if (code.equals(checkCode) && smsCheckCode.getTel().equals(telphone))
        {
            result.put("success", true);
            return result;
        }
        else
        {
            result.put("success", false);
            result.put("type", 0001);
            result.put("message", "验证码错误！");
            return result;
        }
    }
}
