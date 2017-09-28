package com.wb.framework.interceptor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.nestLogger.NestLogger;

/**
 * 日志服务
 */
public class LogService
{
    private static final Logger logger = Logger.getLogger(LogService.class);
    
    public void logAll(JoinPoint joinPoint)
    {
        try
        {
            String temp = joinPoint.getStaticPart().toShortString();
            String longTemp = joinPoint.getStaticPart().toLongString();
            joinPoint.getStaticPart().toString();
            String classType = joinPoint.getTarget().getClass().getName();
            String methodName = temp.substring(10, temp.length() - 1);
            Class<?> className = Class.forName(classType);
            // 日志动作
            @SuppressWarnings("rawtypes")
            Class[] args = new Class[joinPoint.getArgs().length];
            String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1, longTemp.length() - 2)).split(",");
            for (int i = 0; i < args.length; i++)
            {
                if (sArgs[i].endsWith("String[]"))
                {
                    args[i] = Array.newInstance(Class.forName("java.lang.String"), 1).getClass();
                }
                else if (sArgs[i].endsWith("Long[]"))
                {
                    args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1).getClass();
                }
                else if (sArgs[i].indexOf(".") == -1)
                {
                    if (sArgs[i].equals("int"))
                    {
                        args[i] = int.class;
                    }
                    else if (sArgs[i].equals("char"))
                    {
                        args[i] = char.class;
                    }
                    else if (sArgs[i].equals("float"))
                    {
                        args[i] = float.class;
                    }
                    else if (sArgs[i].equals("long"))
                    {
                        args[i] = long.class;
                    }
                }
                else
                {
                    args[i] = Class.forName(sArgs[i]);
                }
            }
            Method method = className.getMethod(methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);
            
            // 如果该方法写了注解才做操作
            if (method.isAnnotationPresent(LogAnnotation.class))
            {
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                // String operateModelNm = logAnnotation.operateModelNm();
                // String operateFuncNm = logAnnotation.operateFuncNm();
                StringBuilder sb = new StringBuilder();
                sb.append("method:" + joinPoint.getSignature().getName());
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                Iterator<java.util.Map.Entry<String, String[]>> iter = request.getParameterMap().entrySet().iterator();
                boolean isFirst = true;
                sb.append("#paras:");
                while (iter.hasNext())
                {
                    java.util.Map.Entry<String, String[]> entry = iter.next();
                    if (isFirst)
                    {
                        isFirst = false;
                    }
                    else
                    {
                        sb.append("#");
                    }
                    sb.append(entry.getKey() + "=");
                    Object[] allValue = entry.getValue();
                    for (int i = 0; i < allValue.length; i++)
                    {
                        if (i != 0)
                        {
                            sb.append(",");
                        }
                        sb.append(allValue[i].toString());
                    }
                }

            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            logger.error(NestLogger.buildLog("日志记录异常", e, true));
        }
    }
    
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
