package com.wb.framework.nestLogger;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpSession;

import com.wb.model.entity.computer.MntMember;

/**
 * 
 * 日志生成工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NestLogger
{
    /**
     * 开发者模式
     */
    public static final boolean DEVELOP_MODEL = true;
    
    /**
     * 
     * 异常处理方法
     * 
     * @param e
     * @see [类、类#方法、类#成员]
     */
    public static void showException(Exception e)
    {
        if (DEVELOP_MODEL)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 日志生成
     * 
     * @param session 会话
     * @param whatWork 事件描述
     * @param e 异常对象
     * @param ifGetTraceInfo 是否打印详细信息
     * @return 日志信息
     */
    public static String buildLog(HttpSession session, String whatWork, Exception e, boolean ifGetTraceInfo)
    {
        final MntMember userInfo = (MntMember)session.getAttribute("userInfo");
        if (userInfo == null)
        {
            return "Logger error:获取不到当前企业信息!";
        }
        return "Logger Info : 当前操作企业名称: " + userInfo.getOrgName() + " , 企业ID: " + userInfo.getOrgId() + " , 日志详情: " + whatWork + (e == null ? "" : " , 当前异常信息为: " + (ifGetTraceInfo ? getTrace(e) : e.toString()));
    }
    
    /**
     * 日志生成
     * 
     * @param whatWork 事件描述
     * @param e 异常对象
     * @param ifGetTraceInfo 是否打印详细信息
     * @return 日志信息
     */
    public static String buildLog(String whatWork, Exception e, boolean ifGetTraceInfo)
    {
        return "Logger Info : 日志详情: " + whatWork + (e == null ? "" : " , 当前异常信息为: " + (ifGetTraceInfo ? getTrace(e) : e.toString()));
    }
    
    /**
     * 打印详细异常信息
     * 
     * @param t 异常
     * @return string信息
     */
    private static String getTrace(Throwable t)
    {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
