/*
 * 文 件 名:  Login.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-17
 * 跟踪单号:  1.0
 * 修改单号:  1.0
 * 修改内容:  1.0
 */
package com.wb.component.computer.login.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.wb.component.computer.common.constant.ConstantComputer;
import com.wb.component.computer.common.verification.ComputerCheckEnum;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.login.service.IDataFormatterService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.component.computer.login.util.FileIOUtil;
import com.wb.component.computer.login.util.SortNameCN;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.CookieUtil;
import com.wb.framework.commonUtil.PropertiesReader;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.framework.commonUtil.encrypt.MD5;
import com.wb.framework.commonUtil.smssSender.SMSCheckCode;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.HomeShowForComputer;
import com.wb.model.pojo.mobile.login.LoginVo;

/**
 * 管理平台登陆管理类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-17]
 */
@Controller
@RequestMapping("supervisory")
public class Login extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(Login.class);
    
    /**
     * 登陆用SER
     */
    @Autowired
    @Qualifier("loginService")
    private ILoginService loginSer;
    
    /**
     * 新旧数据重新格式化
     */
    @Autowired
    @Qualifier("dataFormatterService")
    private IDataFormatterService dataFormatterService;
    
    /**
     * 组织架构ser
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 
     * 管理平台登陆页面跳转
     * 
     * @param session session
     * @param request request
     * @return 页面信息
     */
    @RequestMapping(value = "forward/gotoLogin", method = RequestMethod.GET)
    public String gotoLogin(HttpSession session, HttpServletRequest request)
    {
        return "login/login";
    }
    
    /**
     * 
     * 管理平台注册功能页面跳转
     * 
     * @param session session
     * @param request request
     * @return 页面信息
     */
    @RequestMapping(value = "forward/gotoMemberRegister", method = RequestMethod.GET)
    public String gotoMemberRegister(HttpSession session, HttpServletRequest request)
    {
        return "login/register";
    }
    
    /**
     * 
     * 忘记密码页面跳转
     * 
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoPwdGetBack", method = RequestMethod.GET)
    public String gotoPwdGetBack(HttpServletRequest request, String tel)
    {
        request.setAttribute("tel", tel);
        return "login/passwordGetBack";
    }
    
    /**
     * 
     * 重置密码界面跳转
     * 
     * @param response response
     * @param request request
     * @param verifyCode 验证码
     * @return 路径
     */
    @RequestMapping(value = "forward/gotoPwdReset")
    public String gotoPwdReset(HttpServletResponse response, HttpServletRequest request, HttpSession session, String verifyCode)
    {
        SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (smsCheckCode != null)
        {
            final String code = smsCheckCode.getCode();
            final String checkCode = request.getParameter("checkCode");
            final String telphone = request.getParameter("telphone");
            if (code.equals(checkCode) && smsCheckCode.getTel().equals(telphone))
            {
                request.setAttribute("telphone", telphone);
                return "login/passwordReset";
            }
            else
            {
                request.setAttribute("message", "验证码错误！");
                return "login/passwordReset";
            }
        }
        else
        {
            request.setAttribute("message", "未获取验证码！");
            return "login/passwordReset";
        }
    }
    
    /**
     * 
     * 跳转主页面
     * 
     * @param session session
     * @param request request
     * @return 页面路径
     */
    @RequestMapping(value = "forward/gotoMainFrame", method = RequestMethod.GET)
    public String gotoMainFrame(HttpSession session, HttpServletRequest request)
    {
//        final MntMember member = (MntMember)session.getAttribute("userInfo");
//        int userId = member.getId();
//        boolean isAdmin = member.isAdmin();
//        dataFormatterService.updateOrganizationCostomInfoContact(isAdmin, Integer.toString(userId));
//        request.setAttribute("userMsg", member.getUserName());
        return "login/main";
    }
    
    /**
     * 
     * 体验账号登陆
     * 
     * @param session
     * @param request
     * @param response
     * @param tel
     * @param pwd
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/demoLogin", method = RequestMethod.GET)
    public String demoLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response, String tel, String pwd)
    {
        final MD5 md5 = new MD5();
        session.setMaxInactiveInterval(60 * 60);
        final MntMember result = loginSer.findByTel(tel);
        final String passWord = result.getPassword();
        if (passWord.equals(md5.encrypt(pwd)))
        {
            PropertiesReader reader = PropertiesReader.getInstance();
            String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
            session.setAttribute("userInfo", result);
            session.setAttribute("beatTimeForMng", ConstantComputer.HEAT_BEAT_VIEW);
            request.setAttribute("homeDate", DateUtil.getPrevTaxDate());
            CookieUtil.addCookie(response, "memberId", result.getId() + "", -1);
            CookieUtil.addCookie(response, "isAdmin", result.isAdmin() + "", -1);
            CookieUtil.addCookie(response, "ACC_ROLER_URL", url, -1);
            return "login/login";
        }
        return "login/login";
    }
    
    /**
     * 
     * 首页页面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoHome", method = RequestMethod.GET)
    public String gotoHome(HttpSession session, HttpServletRequest request)
    {
        request.setAttribute("homeDate", DateUtil.getPrevTaxDate());
        return "login/home";
    }
    
    /**
     * 
     * 在线修改密码页面跳转
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoPasswordModify", method = RequestMethod.GET)
    public String gotoPasswordModify(HttpServletRequest request)
    {
        return "login/passwordModify";
    }
    
    /**
     * 
     * 当前账号修改信息跳转页面
     * 
     * @param request request
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoManagerInfoModify", method = RequestMethod.GET)
    public String gotoManagerInfoModify(HttpServletRequest request, HttpSession session)
    {
        final MntMember userInfo = (MntMember)session.getAttribute("userInfo");
        request.setAttribute("ifCanSaveMore", userInfo.getIfCanSaveMore());
        if (userInfo.isAdmin())
        {
            request.setAttribute("isAdmin", true);
        }
        else
        {
            request.setAttribute("isAdmin", false);
        }
        return "login/memberInfoModify";
    }
    
    /**
     * 去往帮助页面
     * 
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoHelp", method = RequestMethod.GET)
    public String gotoHelp(HttpServletRequest request)
    {
        return "login/help";
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
    @RequestMapping(value = "asyn/register")
    public String register(MntMember member, HttpServletRequest request, HttpSession session, String verifyCode)
    {
        request.setAttribute("message", "");
        final SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (smsCheckCode == null || !smsCheckCode.getTel().equals(member.getTelphone()))
        {
            request.setAttribute("message", "未获取验证码！");
            return "login/register";
        }
        final String code = smsCheckCode.getCode();
        final String checkCode = request.getParameter("checkCode");
        if (!code.equals(checkCode) && smsCheckCode.getTel().equals(member.getTelphone()))
        {
            request.setAttribute("message", "手机验证码错误！");
            return "login/register";
        }
        try
        {
            final MntMember result = loginSer.findByTel(member.getTelphone());
            if (result != null && result.isAdmin())
            {
                request.setAttribute("message", "该手机号已被注册!");
                return "login/register";
            }
            if (result != null && !result.isAdmin())
            {
                request.setAttribute("message", "代账公司员工禁止创建账号!");
                return "login/register";
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
            member.setIfCanSaveMore("0");
            BizMember bm = new BizMember();
            bm.setCreateTime(new Date());
            bm.setDepartmentId("0");
            bm.setEmail(member.getEmail());
            bm.setEnable(1);
            bm.setName(member.getUserName());
            bm.setPassword(member.getPassword());
            bm.setTelphone(member.getTelphone());
            loginSer.insert(member, bm);
            session.setAttribute("registerFlag", "1");
            session.setAttribute("tel",member.getTelphone());
//            session.setAttribute("member_id",member.getId().toString());
            return "redirect:/supervisory/forward/gotoLogin";
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            request.setAttribute("message", "系统出现错误: " + e.getMessage());
            return "login/register";
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
    @RequestMapping(value = "asyn/checkTelphone")
    public void checkTelphone(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "tel")
    String tel)
    {
        final MntMember result = loginSer.findByTel(tel);
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
        returnAjaxString(successMsg("该号码可用！"), response);
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
    @RequestMapping(value = "asyn/getCheckCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam(value = "tel")
    String tel, Integer type)
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
        String template = "";
        switch (type)
        {
            case 1:
                // 注册模板
                template = SMSSender.REGIST;
                break;
            case 2:
                // 密码找回模板
                template = SMSSender.PASSWORD;
                break;
            default:
                break;
        }
        final Map<String, Object> result = SMSSender.sendMessage(template, tel, code);
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
            returnAjaxString(failureMsg("验证码发送失败!错误信息: " + result.get("statusMsg")), response);
        }
    }
    
    /**
     * 
     * 管理平台信息修改
     * 
     * @param response response
     * @param request request
     * @param member 企业信息
     */
    @RequestMapping(value = "asyn/updateMemberInfo", method = RequestMethod.POST)
    @ResponseBody
    public void updateMemberInfo(HttpServletResponse response, HttpServletRequest request, HttpSession session, MntMember member, @RequestParam(required = false)
    MultipartFile bigLogo, @RequestParam(required = false)
    MultipartFile littleLogo)
    {
        MntMember mem = (MntMember)session.getAttribute("userInfo");
        mem = loginSer.findByTel(mem.getTelphone());
        session.setAttribute("userInfo", mem);
        if (member.getUserName() == null || member.getUserName().equals(""))
        {
            returnAjaxString(failureMsg("管理员名称不能为空！"), response);
            return;
        }
        if (member.getOrgName() == null || member.getOrgName().equals(""))
        {
            returnAjaxString(failureMsg("代帐企业名称不能为空！"), response);
            return;
        }
        if (member.getEmail() != null && !member.getEmail().equals("") && !(ComputerCheckEnum.VERIFY.VERIFY_EMAIL_MODE.isVerificated(member.getEmail())))
        {
            returnAjaxString(failureMsg("管理员联系邮箱格式不正确！"), response);
            return;
        }
        if (member.getHotline() != null && !member.getHotline().equals("") && !(ComputerCheckEnum.VERIFY.VERIFY_TELPHONE_MODE.isVerificated(member.getHotline()) || ComputerCheckEnum.VERIFY.VERIFY_SolidTel_MODE.isVerificated(member.getHotline())))
        {
            returnAjaxString(failureMsg("企业咨询电话格式不正确！"), response);
            return;
        }
        if (member.getCardNo() != null && !member.getCardNo().equals("") && !ComputerCheckEnum.VERIFY.VERIFY_CARDNO_MODE.isVerificated(member.getCardNo()))
        {
            returnAjaxString(failureMsg("收款银行卡卡号格式不正确！"), response);
            return;
        }
        try
        {
            final Map<String, String> map = new HashMap<String, String>();
            map.put("userName", member.getUserName());
            map.put("email", member.getEmail());
            map.put("orgName", member.getOrgName());
            map.put("stamp", DateUtil.getCurDateTimeForString());
            map.put("acronym", SortNameCN.getAcronym(member.getOrgName()));
            map.put("hotline", member.getHotline());
            map.put("cardNo", member.getCardNo());
            map.put("cardMaster", member.getCardMaster());
            map.put("orgDepict", member.getOrgDepict());
            map.put("orgCatchArea", member.getOrgCatchArea());
            map.put("orgSortName", member.getOrgSortName());
            
            map.put("payId", member.getPayId());
            map.put("aliPay", member.getAliPay());
            //map.put("aliPayKey", member.getAliPayKey());
            
            PropertiesReader reader = PropertiesReader.getInstance();
            final String logoUrl = reader.getValue("/com/wb/config/application", "MNT_LOGO_URL");
            String bigLogoName = "x";
            String littleLogoName = "x";
            if (bigLogo != null)
            {
                final String imageType = bigLogo.getOriginalFilename().substring(bigLogo.getOriginalFilename().indexOf("."));
                bigLogoName = new Date().getTime() + "_BIG_" + new Random().nextInt(1000) + imageType;
                FileIOUtil.deleteFile(logoUrl, mem.getBigLogoName());
                if (!FileIOUtil.ifImage(bigLogo.getInputStream()))
                {
                    returnAjaxString(failureMsg("公司LOGO(大)非图片类型!"), response);
                    return;
                }
                FileIOUtil.fileIO(logoUrl, bigLogo, bigLogoName);
            }
            if (littleLogo != null)
            {
                final String imageType = littleLogo.getOriginalFilename().substring(littleLogo.getOriginalFilename().indexOf("."));
                littleLogoName = new Date().getTime() + "_LIT_" + new Random().nextInt(1000) + imageType;
                FileIOUtil.deleteFile(logoUrl, mem.getLittleLogoName());
                if (!FileIOUtil.ifImage(littleLogo.getInputStream()))
                {
                    returnAjaxString(failureMsg("公司LOGO(小)非图片类型!"), response);
                    return;
                }
                FileIOUtil.fileIO(logoUrl, littleLogo, littleLogoName);
            }
            map.put("logoUrl", logoUrl);
            if (!"x".equals(bigLogoName))
            {
                map.put("bigLogoName", bigLogoName);
            }
            if (!"x".equals(littleLogoName))
            {
                map.put("littleLogoName", littleLogoName);
            }
            loginSer.updateManager(null, map, " and id = " + mem.getOrgId());
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("个人信息修改失败! 错误：" + e.getMessage()), response);
            return;
        }
        session.setAttribute("userInfo", loginSer.findByTel(mem.getTelphone()));
        returnAjaxString(successMsg("个人信息修改成功!"), response);
    }
    
    /**
     * 
     * 重置密码
     * 
     * @param response response
     * @param request request
     * @param pwd0 pwd0
     * @param pwd1 pwd1
     */
    @RequestMapping(value = "asyn/modifyPwdForMng", method = RequestMethod.POST)
    public void modifyPwdForMng(HttpServletResponse response, HttpServletRequest request, HttpSession session, String pwd0, String pwd1)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final boolean isAdmin = member.isAdmin();
        final MD5 md5 = new MD5();
        if (!member.getPassword().equals(md5.encrypt(pwd0)))
        {
            returnAjaxString(failureMsg("原始密码不正确请重新输入！"), response);
            return;
        }
        if (pwd1 == null || pwd1.equals(""))
        {
            returnAjaxString(failureMsg("新密码不能为空！"), response);
            return;
        }
        try
        {
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put("password", md5.encrypt(pwd1));
            loginSer.updateManager(isAdmin, map, " and id = " + member.getId());
            session.setAttribute("userInfo", loginSer.findByTel(member.getTelphone()));
            returnAjaxString(successMsg("密码修改成功!"), response);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            returnAjaxString(failureMsg("密码修改失败! 错误：" + e.getMessage()), response);
        }
    }
    
    /**
     * 
     * 密码更新
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param password password
     */
    @RequestMapping(value = "asyn/pwdModify")
    public void updatePwd(HttpServletRequest request, HttpServletResponse response, HttpSession session, String password)
    {
        final SMSCheckCode smsCheckCode = (SMSCheckCode)session.getAttribute("smsCode");
        if (smsCheckCode != null)
        {
            final MD5 md5 = new MD5();
            final Map<String, String> map = new HashMap<String, String>();
            map.put("password", md5.encrypt(password));
            loginSer.updateManager(null, map, " and telphone ='" + smsCheckCode.getTel() + "'");
            returnAjaxString(successMsg("密码更新成功！"), response);
        }
        else
        {
            returnAjaxString(successMsg("无法获取认证信息！"), response);
        }
    }
    
    /**
     * 登录
     * 
     * @param session session
     * @param request request
     * @param response response
     * @param pwd pwd
     * @param tel tel
     */
    @RequestMapping(value = "asyn/loginPlatform", method = RequestMethod.POST)
    public void loginPlatform(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "password")
    String pwd, @RequestParam(value = "telphone")
    String tel)
    {
        final MD5 md5 = new MD5();
        session.setMaxInactiveInterval(60 * 60);
        final MntMember result = loginSer.findByTel(tel);
        if (result == null)
        {
            returnAjaxString(failureMsg("账号不存在!"), response);
            return;
        }
        if (result.getEmpInfo() != null && !result.isAdmin() && result.getEmpInfo().getEnable() == 0)
        {
            returnAjaxString(failureMsg("账号已被锁定,请联系您的公司管理员!"), response);
            return;
        }
        final String passWord = result.getPassword();
        final String passWordBiz = result.getEmpInfo().getPassword();
        if (passWord.equals(md5.encrypt(pwd)))
        {
            PropertiesReader reader = PropertiesReader.getInstance();
            String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
            session.setAttribute("userInfo", result);
            session.setAttribute("beatTimeForMng", ConstantComputer.HEAT_BEAT_VIEW);
            CookieUtil.addCookie(response, "memberId", result.getId() + "", -1);
            CookieUtil.addCookie(response, "isAdmin", result.isAdmin() + "", -1);
            CookieUtil.addCookie(response, "ACC_ROLER_URL", url, -1);
            returnAjaxString(successMsg("登陆成功!"), response);
        }else if(passWordBiz.equals(md5.encrypt(pwd))){ //add by zhouww date:20161026 desc:bizMember和MntMember 中都存在 tel 这条数据 mntMember的密码和bizMember密码不一样 正好客户这次用的bizMember的密码登录的
        	PropertiesReader reader = PropertiesReader.getInstance();
            String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
            session.setAttribute("userInfo", result);
            session.setAttribute("beatTimeForMng", ConstantComputer.HEAT_BEAT_VIEW);
            CookieUtil.addCookie(response, "memberId", result.getId() + "", -1);
            CookieUtil.addCookie(response, "isAdmin", false + "", -1);
            CookieUtil.addCookie(response, "ACC_ROLER_URL", url, -1);
            returnAjaxString(successMsg("登陆成功!"), response);
        }
        else
        {
            returnAjaxString(failureMsg("密码错误!"), response);
        }
        return;
    }
    
    
    /**
     * 切换用户登录
     * 
     * @param session session
     * @param request request
     * @param response response
     * @param tel tel
     */
    @RequestMapping(value = "asyn/changLoginPlatform", method = RequestMethod.POST)
    public void changLoginPlatform(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userType")
    String userType, @RequestParam(value = "telphone")
    String tel)
    {
        final MD5 md5 = new MD5();
        session.setMaxInactiveInterval(60 * 60);
        final MntMember result = loginSer.findByTelForChangeRole(tel, userType);
        PropertiesReader reader = PropertiesReader.getInstance();
        String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
        session.setAttribute("userInfo", result);
        session.setAttribute("beatTimeForMng", ConstantComputer.HEAT_BEAT_VIEW);
        CookieUtil.addCookie(response, "memberId", result.getId() + "", -1);
        CookieUtil.addCookie(response, "isAdmin", ("1".equals(userType)?true:false)+ "", -1);
        CookieUtil.addCookie(response, "ACC_ROLER_URL", url, -1);
        returnAjaxString(successMsg("登陆成功!"), response);
    }
    
    
    /**
     * 
     * 主界面显示信息查询
     * 
     * @param request request
     * @param response response
     * @param period period
     * @see [类、类#方法、类#成员]
     */
    /*@RequestMapping(value = "asyn/getHomeShowData")
    public void getHomeShowData(HttpServletRequest request, HttpServletResponse response, HttpSession session, String period)
    {
        HomeShowForComputer dto = new HomeShowForComputer();
        final Pattern pattern = Pattern.compile("^2[0-9]{3}-(0[1-9]|1[0-2])");
        if (pattern.matcher(period).matches())
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            final EnterpriseQueryForm form = new EnterpriseQueryForm();
            form.setLisence(period);
            form.setCreator(member.getOrgId());
            form.setAuthState(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_MANAGE_STATE_ACCEPT")));
            form.setArg1(ConstantComputer.HEAT_DEAD_LINE / 1000);
            form.setArg2(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_OFFLINE")));
            form.setArg3(Integer.parseInt((new RequestContext(request)).getMessage("CLERK_STATE_ONLINE")));
            form.setEndDate(DateUtil.getCurMonthLastDate(new Date()));
            dto = loginSer.findHomeShowData(form);
            String mId = null;
            if (!member.isAdmin())
            {
                mId = member.getId() + "";
            }
            LoginVo vo = customerManageService.getConPayInfo(member.getOrgId() + "", mId, DateUtil.getCurDateTimeForString());
            dto.setOrgNum2(vo.getArrPayNum());
        }
        returnAjaxBean(dto, response);
    }*/
    
    /**
     * 
     * 登出
     * 
     * @param request
     * @return 跳转请求
     */
    @RequestMapping(value = "forward/logout", method = RequestMethod.GET)
    public String Logout(HttpSession session, HttpServletResponse response)
    {
        session.setAttribute("loginState", false);
        session.invalidate();
        return "redirect:/supervisory/forward/gotoLogin";
    }
    
    /**
     * 
     * 查询是否掉线
     * 
     * @param request request
     * @param response response
     * @param session session
     */
    @RequestMapping(value = "asyn/heartbeatForMng", method = RequestMethod.GET)
    public void heartbeatForMng(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("stamp", DateUtil.getCurDateTimeForString());
        loginSer.updateManager(null, map, " and id = " + member.getOrgId());
        if (!member.isAdmin())
        {
            map.clear();
            map.put("lastStamp", DateUtil.getCurDateTimeForString());
            frameworkSer.updateBizMember(map, " and id = " + member.getId());
        }
        else if (member.getEmpInfo() != null && member.isAdmin())
        {
            map.clear();
            map.put("lastStamp", DateUtil.getCurDateTimeForString());
            frameworkSer.updateBizMember(map, " and id = " + member.getEmpInfo().getId());
        }
      //update by zhouww date:20161020 desc:修复bug管理员和员工查询的公告信息一样 改成 只查询自己的公告信息 查询表msg_notification的时候添加约束userId
        final Long msgSize = loginSer.countNotificationById(member.getOrgId(),member.getEmpInfo().getId(), 1);
        returnAjaxString(successMsg(String.valueOf(msgSize)), response);
    }
    
    /**
     * 
     * 预览LOGO
     * 
     * @param response response
     * @param request request
     * @param session session
     * @param size size
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "syn/getCompanyLogo", method = RequestMethod.GET)
    public void getCompanyLogo(HttpServletResponse response, HttpServletRequest request, HttpSession session, String size)
        throws IOException
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        String imagePath = "";
        if ("BIG".equals(size))
        {
            imagePath = member.getLogoUrl() + "/" + member.getBigLogoName();
        }
        if ("LIT".equals(size))
        {
            imagePath = member.getLogoUrl() + "/" + member.getLittleLogoName();
        }
        try
        {
            
            final String imageType = imagePath.substring(imagePath.indexOf(".") + 1);
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ImageIO.write(ImageIO.read(new File(imagePath)), imageType, byteStream);
            response.getOutputStream().write(byteStream.toByteArray());
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            String path = System.getProperty("wb.root") + "/style/image/nopic.jpg";
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ImageIO.write(ImageIO.read(new File(path)), "jpg", byteStream);
            response.getOutputStream().write(byteStream.toByteArray());
        }
    }
}
