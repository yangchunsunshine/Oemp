package com.wb.component.computer.payManage.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.payManage.service.IPayManagerService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.model.entity.computer.MntAlipayInfo;
import com.wb.model.entity.computer.MntMember;

@Controller
@RequestMapping(value = "supervisory")
public class PayManager extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(PayManager.class);
    
    /**
     * 支付管理业务层
     */
    @Autowired
    @Qualifier("payManagerService")
    private IPayManagerService payManagerService;
    
    /**
     * 阿里支付参数保存
     * 
     * @param session session
     * @param aliParams 阿里支付参数列表
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/saveAlipaySetting", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveAlipaySetting(HttpSession session, @RequestBody
    List<MntAlipayInfo> aliParams)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final boolean code = payManagerService.saveAlipaySetting(mngId, aliParams);
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取支付宝支付信息
     * 
     * @param session session
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value = "asyn/getAlipayInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getAlipayInfo(HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        List<Map<String, Object>> aliPayInfo = payManagerService.getAlipayInfo(mngId);
        return aliPayInfo;
    }
    
    /**
     * 页面跳转 阿里参数设置页面
     * 
     * @return String
     */
    @RequestMapping(value = "forward/gotoAlipaySetting", method = RequestMethod.GET)
    public String gotoAlipaySetting()
    {
        return "pay/alipaySetting";
    }
}
