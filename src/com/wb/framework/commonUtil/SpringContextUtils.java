package com.wb.framework.commonUtil;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * spring容器运行期 获取bean辅助类 可在web运行期 获取任意spring容器中的bean 在应用单例bean来刷新bean中的值常用
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class SpringContextUtils implements ApplicationContextAware
{
    private static ApplicationContext applicationContext = null;
    
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 
     * 获取ApplicationContext
     * 
     * @return ApplicationContext
     * @see [类、类#方法、类#成员]
     */
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * 
     * 根据一个bean的id获取配置文件中相应的bean
     * 
     * @param name beanName
     * @return Object
     * @throws BeansException
     * @see [类、类#方法、类#成员]
     */
    public static Object getBean(String name)
        throws BeansException
    {
        return applicationContext.getBean(name);
    }

    /**
     * 
     * 类似于getBean(String name)只是在参数中提供了需要返回到的类型。
     * 
     * @param name beanName
     * @param requiredType 类型
     * @return Object
     * @throws BeansException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getBean(String name, Class requiredType)
        throws BeansException
    {
        return applicationContext.getBean(name, requiredType);
    }
    
    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义,则返回true
     * 
     * @param name beanName
     * @return boolean 是否存在
     */
    public static boolean containsBean(String name)
    {
        return applicationContext.containsBean(name);
    }
    
    /**
     * 
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到,将会抛出一个异常（NoSuchBeanDefinitionException）
     * 
     * @param name beanName
     * @return 是否是单例
     * @throws NoSuchBeanDefinitionException
     * @see [类、类#方法、类#成员]
     */
    public static boolean isSingleton(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.isSingleton(name);
    }
    
    /**
     * 
     * 根据名称去查看bean类型
     * 
     * @param name beanName
     * @return Class
     * @throws NoSuchBeanDefinitionException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static Class getType(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.getType(name);
    }
    
    /**
     * 
     * 如果给定的bean名字在bean定义中有别名,则返回这些别名
     * 
     * @param name beanName
     * @return String[]
     * @throws NoSuchBeanDefinitionException
     * @see [类、类#方法、类#成员]
     */
    public static String[] getAliases(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.getAliases(name);
    }
    
    public static ServletContext getServletContext()
    {
        WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        return currentWebApplicationContext.getServletContext();
    }
}