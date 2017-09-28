package com.wb.framework.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 日志注解
 * 
 * @author 郝洋
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation
{
    /**
     * 
     * 模块名称
     * 
     * @return String String
     */
    String operateModelNm();
    
    /**
     * 
     * 操作描述
     * 
     * @return String String
     * @see [类、类#方法、类#成员]
     */
    String operateFuncNm();
}