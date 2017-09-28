/*
 * 文 件 名:  HessianFactory.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.hessian.factory;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * Hessian 工厂
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HessianFactory
{
    
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(HessianFactory.class);
    
    /**
     * 
     * 获得factory
     * 
     * @return HessianProxyFactory
     * @see [类、类#方法、类#成员]
     */
    public static HessianProxyFactory getFactory()
    {
        return new HessianProxyFactory();
    }
    
    /**
     * 
     * 预处理方法,适用于不设置任何参数的HessianProxyFactory
     * 
     * @param clazz 接口clazz
     * @param url 请求地址(例:192.168.1.1)
     * @param port 端口
     * @param afterStr 请求名(例:/Oemp/demo)
     * @return 接口实例||-1 url 为空||-2请求失败
     * @see [类、类#方法、类#成员]
     */
    public static Object initHession(Class<?> clazz, String url, String port, String afterStr)
    {
        final HessianProxyFactory factory = getFactory();
        try
        {
            return factory.create(clazz, url.toLowerCase() + (port != null && !"".equals(port.trim()) ? ":" + port : "") + afterStr);
        }
        catch (NullPointerException nullPointerEx)
        {
            logger.error("hessian初始化失败: " + nullPointerEx.toString());
            return -1;
        }
        catch (MalformedURLException urlEx)
        {
            logger.error("hessian请求失败: " + urlEx.toString());
            return -2;
        }
    }
}
