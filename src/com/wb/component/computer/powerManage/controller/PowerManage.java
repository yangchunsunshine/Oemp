/*
 * 文 件 名:  PowerManage.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.powerManage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 在线状态管理界面
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class PowerManage
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(PowerManage.class);
    
    /**
     * 
     * 权限管理界面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoPowerManage", method = RequestMethod.GET)
    public String gotoAccountingClerkList(HttpSession session, HttpServletRequest request)
    {
        return "powerManage/powerManage";
    }
}
