package com.wb.framework.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONValue;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wb.component.mobile.common.certificate.Certificate;
import com.wb.framework.commonConstant.Constant;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.CookieUtil;
import com.wb.framework.commonUtil.encrypt.AES;
import com.wb.framework.nestLogger.NestLogger;

public class OempMobileInterceptor extends AjaxAction implements HandlerInterceptor
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
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        if (ex != null)
        {
            logger.error("错误请求：" + request.getRequestURI() + ",异常信息：" + ex.toString());
        }
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
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
                            CookieUtil.addCookie(response, "certificate", null, -1);
                            request.getSession().invalidate();
                            return false;
                        }
                    }
                    catch (Exception e)
                    {
                        CookieUtil.addCookie(response, "certificate", null, -1);
                        request.getSession().invalidate();
                        return false;
                    }
                }
            }
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
        Cookie cookie = CookieUtil.getCookieByName(request, "certificate");
        if (cookie != null)
        {
            String certificate = cookie.getValue();
            try
            {
                Certificate cert = JSONValue.parse(AES.Decrypt(certificate, Constant.COMMUNICATONKEY), Certificate.class);
                if (cert.checkSignture())
                {
                    request.setAttribute("certificate", cert);
                    return true;
                }
                else
                {
                    returnAjaxString(failureMsg("证书签名信息不符!"), response);
                    return false;
                }
            }
            catch (Exception e)
            {
                returnAjaxString(failureMsg("证书解析失败!"), response);
                NestLogger.showException(e);
                return false;
            }
        }
        else
        {
            returnAjaxString(failureMsg("未进行认证!"), response);
            return false;
        }
    }
}
