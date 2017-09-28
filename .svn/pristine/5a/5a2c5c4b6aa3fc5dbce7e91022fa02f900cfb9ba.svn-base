package com.wb.framework.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wb.model.entity.computer.MntMember;

public class OempComputerInterceptor implements HandlerInterceptor
{
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(OempComputerInterceptor.class);

    public String[] allowUrls;
    
    public void setAllowUrls(String[] allowUrls)
    {
        this.allowUrls = allowUrls;
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
        throws Exception
    {
        if (ex != null)
        {
            logger.error("错误请求：" + request.getRequestURI() + ",异常信息：" + ex.toString());
        }
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
        throws Exception
    {

    }

    /**
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<String> key = parameterMap.keySet().iterator();
        while (key.hasNext())
        {
            String[] strs = parameterMap.get(key.next());
            for (String str : strs)
            {
                String dict = "like |drop table |and |or |exec |insert |select |delete |xp_cmdshell |update |count |chr |mid |netlocalgroup administrators |master |truncate |char |declare |script|alert|jquery|${";
                String[] checkStr = dict.split("\\|");
                for (int i = 0; i < checkStr.length; i++)
                {
                    try
                    {
                        if (str.toLowerCase().indexOf(checkStr[i]) != -1)
                        {
                            request.getSession().invalidate();
                            return false;
                        }
                    }
                    catch (Exception e)
                    {
                        request.getSession().invalidate();
                        return false;
                    }
                }
            }
        }
        MntMember obj = (MntMember)request.getSession().getAttribute("userInfo");
        if (obj != null)
        {
            return true;
        }
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if (null != allowUrls && allowUrls.length >= 1)
        {
            for (String url : allowUrls)
            {
                if (requestUrl.contains(url))
                {
                    return true;
                }
            }
        }
        response.sendRedirect(request.getContextPath());
        return false;
    }
}
