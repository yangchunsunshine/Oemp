/*
 * 文 件 名:  powerAspect.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Aspect
public class powerAspect
{
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(powerAspect.class);
    
    /** 
     * <一句话功能简述>
     * <功能详细描述>
     * @param joinPoint joinPoint
     * @see [类、类#方法、类#成员]
     */
    @Before("execution(* com.wb.component.computer.customerManage.controller..*(..))")
    public void before(JoinPoint joinPoint)
    {
        if (log.isInfoEnabled())
        {
            log.info("before " + joinPoint);
        }
    }
    
    /** 
     * <一句话功能简述>
     * <功能详细描述>
     * @param joinPoint joinPoint
     * @see [类、类#方法、类#成员]
     */
    @After("execution(* com.wb.component.computer.*.controller.*.goto*(..))")
    public void after(JoinPoint joinPoint)
    {
        if (log.isInfoEnabled())
        {
            log.info("after " + joinPoint);
        }
    }
}
