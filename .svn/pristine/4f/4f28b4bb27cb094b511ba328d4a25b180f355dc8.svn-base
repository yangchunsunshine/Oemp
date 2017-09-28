package com.wb.framework.commonUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * cookie操作工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CookieUtil
{
    
    /**
     * 设置cookie
     * 
     * @param response 响应对象
     * @param name cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期 以秒为单位
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value, int maxAge)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }
    
    /**
     * 根据key获取cookie
     * 
     * @param request
     * @param name cookie名字
     * @return Cookie Cookie对象
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name)
    {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name))
        {
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 将cookie封装到Map里面
     * 
     * @param request 请求对象
     * @return Map<String, Cookie> key为cookie的name
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
    {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies)
        {
            for (Cookie cookie : cookies)
            {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    
}
