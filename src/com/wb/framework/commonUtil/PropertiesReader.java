package com.wb.framework.commonUtil;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * PropertiesReader
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PropertiesReader
{
    private static final PropertiesReader instance = new PropertiesReader();
    
    private PropertiesReader()
    {
        
    }
    
    public static PropertiesReader getInstance()
    {
        return instance;
    }
    
    /**
     * 获取配置文件中的值
     * 
     * @param file_name properties文件路径,不需要带后缀,只需要文件名即可
     * @param key 对应要获取key的值
     * @return value
     * @throws MissingResourceException
     * 
     */
    public String getValue(String file_name, String key)
        throws MissingResourceException
    {
        final ResourceBundle res = ResourceBundle.getBundle(file_name);
        String value = "";
        try
        {
            value = res.getString(key);
        }
        catch (MissingResourceException e)
        {
            throw e;
        }
        return value;
    }
    
}
