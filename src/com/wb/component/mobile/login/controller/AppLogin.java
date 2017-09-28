/*
O * 文 件 名:  login.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-4-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.mobile.login.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import org.springframework.web.servlet.support.RequestContext;

import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.component.computer.login.util.SortNameCN;
import com.wb.component.mobile.common.certificate.Certificate;
import com.wb.component.mobile.common.constant.ConstantMobile;
import com.wb.component.mobile.login.service.IAppLoginService;
import com.wb.component.mobile.mainBuss.service.IMainBussService;
import com.wb.framework.commonConstant.Constant;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.framework.commonUtil.encrypt.AES;
import com.wb.framework.commonUtil.encrypt.MD5;
import com.wb.framework.commonUtil.smssSender.SMSCheckCode;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.entity.computer.cusManage.MntCustomInfo;
import com.wb.model.entity.mobile.BizAppDownLoad;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.mobile.LoginInfo;
import com.wb.model.pojo.mobile.MemberLogin;

/**
 * 登陆相关接口
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("monitored")
public class AppLogin extends AjaxAction
{
    /**
     * 手机端用登陆ser
     */
    @Autowired()
    @Qualifier("appLoginService")
    private IAppLoginService appLoginSer;
    
    /**
     * 电脑端用登陆ser
     */
    @Autowired()
    @Qualifier("loginService")
    private ILoginService cpuLoginSer;
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 主要业务ser
     */
    @Autowired
    @Qualifier("mainBussService")
    private IMainBussService mainBussService;
    
    /**
     * 
     * 认证登陆
     * 
     * @param request
     * @param response
     * @param session
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/mointorAuthenticate", method = {RequestMethod.POST, RequestMethod.GET})
    public void authenticate(HttpServletRequest request, HttpServletResponse response, HttpSession session, String param)
    {
        try
        {
            String json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
            MemberLogin memberLogin = JSONValue.parse(json, MemberLogin.class);
            MntMember member = cpuLoginSer.findByTel(memberLogin.getTelphone());
            if (member == null)
            {
                returnAjaxString(failureMsg("用户不存在!"), response);
            }
            MD5 md5 = new MD5();
            if (!member.getPassword().equals(md5.encrypt(memberLogin.getPassword())))
            {
                returnAjaxString(failureMsg("密码错误!"), response);
                return;
            }
            // 生成证书
            Certificate certificate = new Certificate();
            certificate.setMemberID(member.getOrgId());
            certificate.setSessionID(session.getId());
            certificate.setImei(memberLogin.getImei());
            certificate.signture();
            // 存储cookie--开始
            String certificateJson = JSONValue.toJSONString(certificate);
            response.addCookie(new Cookie("certificate", AES.Encrypt(certificateJson, Constant.COMMUNICATONKEY)));
            EnterpriseQueryForm form = new EnterpriseQueryForm();
            form.setLisence(DateUtil.getCurDateTimeForString().substring(0, 7));
            form.setCreator(certificate.getMemberID());
            form.setAuthState(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
            form.setArg1(ConstantMobile.HEAT_DEAD_LINE / 1000);
            form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_OFFLINE")));
            form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_ONLINE")));
            form.setEndDate(DateUtil.getCurMonthLastDate(new Date()));
            // 存储cookie--结束
            JSONObject dataJson = new JSONObject();
            dataJson.put("member", member);
            dataJson.put("version", appLoginSer.getLastVersion());
            dataJson.put("info1", mainBussService.findHomeShowData(form));
            String mId = null;
            if (!member.isAdmin())
            {
                mId = member.getId() + "";
            }
            dataJson.put("info2", customerManageService.getConPayInfo(member.getOrgId() + "", mId, DateUtil.getCurDateTimeForString()));
            String dataString = dataJson.toJSONString();
            returnAjaxString(successData(dataString, "认证成功"), response);
        }
        catch (Exception e)
        {
            returnAjaxString(failureMsg("服务器内部错误!" + e.getMessage()), response);
            NestLogger.showException(e);
        }
    }
    
    /**
     * 
     * 验证手机号是否已经注册
     * 
     * @param request request
     * @param response response
     * @param tel 电话号
     */
    public boolean checkTelphone(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "tel")
    String tel)
    {
        final MntMember result = cpuLoginSer.findByTel(tel);
        if (result != null && result.isAdmin())
        {
            returnAjaxString(failureMsg("该手机号已被注册!"), response);
            return false;
        }
        if (result != null && !result.isAdmin())
        {
            returnAjaxString(failureMsg("代账公司员工禁止创建账号!"), response);
            return false;
        }
        returnAjaxString(successMsg("该号码可用！"), response);
        return true;
    }
    
    /**
     * 
     * 发送验证码
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param tel 电话号
     * @param verifyCode 验证码
     * @param type 验证码模版类型
     */
    @RequestMapping(value = "asyn/mointorMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public void mointorMsg(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam(value = "tel")
    String tel, Integer template)
    {
        final SMSCheckCode checkCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (checkCode != null)
        {
            if (checkCode.getTel().equals(tel) && new Date().getTime() - checkCode.getStamp() < checkCode.getAge() * 60 * 1000)
            {
                returnAjaxString(failureMsg("验证码发送中,无需重复发送!"), response);
                return;
            }
        }
        // 如果不为空且时间间隔大于设定时间,则重新发送并更新session
        final String code = Integer.toString((int)(Math.random() * 9000 + 1000));
        String sendType = "";
        switch (template)
        {
            case 0:
                // 注册模板
                if (!checkTelphone(request, response, tel))
                {
                    return;
                }
                sendType = SMSSender.REGIST;
                break;
            case 1:
                // 密码找回模板
                sendType = SMSSender.PASSWORD;
                break;
            default:
                returnAjaxString(failureMsg("服务器内部错误:代码模版有误!"), response);
                return;
        }
        final Map<String, Object> result = SMSSender.sendMessage(sendType, tel, code);
        if ("000000".equals(result.get("statusCode")))
        {
            final SMSCheckCode smsCheckCode = new SMSCheckCode();
            smsCheckCode.setCode(code);
            smsCheckCode.setTel(tel);
            smsCheckCode.setStamp(new Date().getTime());
            session.setAttribute("smsCode", smsCheckCode);
            returnAjaxString(successMsg("验证码已发送!"), response);
        }
        else
        {
            returnAjaxString(failureMsg("验证码发送失败!错误信息" + result.get("statusMsg")), response);
        }
    }
    
    /**
     * 管理平台注册
     * 
     * @param member 企业信息
     * @param request request
     * @param session session
     * @param verifyCode 验证码
     * @return 界面路径
     */
    @RequestMapping(value = "asyn/mointorRegistCom", method = {RequestMethod.POST, RequestMethod.GET})
    public void mointorRegistCom(MntMember member, HttpServletRequest request, HttpServletResponse response, HttpSession session, String code)
    {
        request.setAttribute("message", "");
        final SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (smsCheckCode == null || !smsCheckCode.getTel().equals(member.getTelphone()))
        {
            returnAjaxString(failureMsg("未获取验证码!"), response);
            return;
        }
        final String checkCode = smsCheckCode.getCode();
        if (!(checkCode.equals(code) && smsCheckCode.getTel().equals(member.getTelphone())))
        {
            returnAjaxString(failureMsg("手机验证码错误!"), response);
            return;
        }
        try
        {
            final MntMember result = cpuLoginSer.findByTel(member.getTelphone());
            if (result != null && result.isAdmin())
            {
                returnAjaxString(failureMsg("该手机号已被注册!"), response);
                return;
            }
            if (result != null && !result.isAdmin())
            {
                returnAjaxString(failureMsg("代账公司员工禁止创建账号!"), response);
                return;
            }
            final String pwd = member.getPassword();
            member.setUserName(member.getUserName().replaceAll(" ", ""));
            final MD5 md5 = new MD5();
            final Date now = new Date();
            member.setStartDate(now);
            member.setStamp(now);
            member.setPassword(md5.encrypt(pwd));
            member.setAcronym(SortNameCN.getAcronym(member.getOrgName()));
            member.setDepartmentId("0");
            BizMember bm = new BizMember();
            bm.setCreateTime(new Date());
            bm.setDepartmentId("0");
            bm.setEmail(member.getEmail());
            bm.setEnable(1);
            bm.setName(member.getUserName());
            bm.setPassword(member.getPassword());
            bm.setTelphone(member.getTelphone());
            cpuLoginSer.insert(member, bm);
            returnAjaxString(successMsg("注册成功!"), response);
            return;
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(successMsg("服务器内部错误:" + e.toString()), response);
            return;
        }
    }
    
    /**
     * 
     * 重置密码
     * 
     * @param response response
     * @param request request
     * @param pwd 新密码
     */
    @RequestMapping(value = "asyn/mointorPasswordReset", method = {RequestMethod.POST, RequestMethod.GET})
    public void mointorPasswordReset(HttpServletResponse response, HttpServletRequest request, HttpSession session, String tel, String password, String code)
    {
        final SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (smsCheckCode == null || !smsCheckCode.getTel().equals(tel))
        {
            returnAjaxString(failureMsg("未获取验证码!"), response);
            return;
        }
        final String checkCode = smsCheckCode.getCode();
        if (!(checkCode.equals(code) && smsCheckCode.getTel().equals(tel)))
        {
            returnAjaxString(failureMsg("手机验证码错误!"), response);
            return;
        }
        final MD5 md5 = new MD5();
        try
        {
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put("password", md5.encrypt(password));
            cpuLoginSer.updateManager(null, map, " and telphone = '" + tel + "'");
            returnAjaxString(successMsg("密码修改成功!"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("服务器内部错误：" + e.getMessage()), response);
        }
    }
    
    @RequestMapping(value = "asyn/downloadOempApk", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadApk4(HttpServletResponse response, HttpServletRequest request)
    {
        OutputStream os = null;
        try
        {
            BizAppDownLoad appVersion = appLoginSer.downLoadApp();
            String fileName = appVersion.getAppName();
            try
            {
                if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
                {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
                }
                else
                {
                    fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
                }
            }
            catch (Exception e)
            {
                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            }
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream");
            response.setContentLength(appVersion.getAppbin().length);
            os.write(appVersion.getAppbin());
            os.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 
     * 登录保存设备id
     * 
     * @param request
     * @param response
     * @param param
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/loginAndSaveDeviceToken", method = {RequestMethod.POST, RequestMethod.GET})
    public void loginAndSaveDeviceToken(HttpServletRequest request, HttpServletResponse response, String param)
    {
        try
        {
            String json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
            LoginInfo loginInfo = JSONValue.parse(json, LoginInfo.class);
            MntCustomInfo mntCustomInfo=customerManageService.getCustomInfo(loginInfo.getCusId());
            if (mntCustomInfo != null&&!"".equals(mntCustomInfo.getId()))
            {
            	Map map=new HashMap();
            	if(null!=loginInfo.getIosDeviceToken()&&!"".equals(loginInfo.getIosDeviceToken())){
            		map.put("iosDeviceToken", loginInfo.getIosDeviceToken());
            		cpuLoginSer.updateCustom(map, " and id = (SELECT mntCustomId FROM biz_organization  WHERE id = " + loginInfo.getCusId()+")");
            		returnAjaxString(successData("成功保存!"), response);
            	}else if(null!=loginInfo.getAndroidDeviceToken()&&!"".equals(loginInfo.getAndroidDeviceToken())){
            		map.put("androidDeviceToken", loginInfo.getAndroidDeviceToken());
            		cpuLoginSer.updateCustom(map, " and id = (SELECT mntCustomId FROM biz_organization  WHERE id = " + loginInfo.getCusId()+")");
            		returnAjaxString(successData("成功保存!"), response);
            	}else{
                	returnAjaxString(failureMsg("deviceToken为空!"), response);
                }
            }else{
            	returnAjaxString(failureMsg("没有查到该公司"), response);
            }
        }
        catch (Exception e)
        {
            returnAjaxString(failureMsg("服务器内部错误!" + e.getMessage()), response);
            NestLogger.showException(e);
        }
    }
}
