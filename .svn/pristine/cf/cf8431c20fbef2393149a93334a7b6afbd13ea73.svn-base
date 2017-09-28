/*
 * 文 件 名:  SqlBuild.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-2-22
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.framework.commonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * SQL拼接工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-2-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SqlBuildUtil
{
    /**
     * 
     * 根据传入的实体,拼接where条件
     * 
     * @param clazz 实体
     * @param ifHasWhere 如果是true的话 那么返回带where的查询语句,没有则返回 and开头的
     * @return String sql
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String addWhere(Object obj, boolean ifHasWhere)
    {
        StringBuffer sql = new StringBuffer(ifHasWhere ? " WHERE " : " AND ");
        Class clazz = obj.getClass();
        Field[] filds = clazz.getDeclaredFields();
        boolean ifFirst = true;
        for (Field fild : filds)
        {
            String fieldName = fild.getName();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try
            {
                Method method = clazz.getMethod("get" + fieldName);
                Object value = method.invoke(obj);
                if (value != null)
                {
                    if (ifFirst)
                    {
                        sql.append(fieldName.toUpperCase() + " = ? ");
                        ifFirst = false;
                    }
                    else
                    {
                        sql.append(" AND " + fieldName.toUpperCase() + " = ? ");
                    }
                    
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (ifFirst == true)
        {
            return "";
        }
        return sql.toString();
    }
    
    /**
     * 
     * 将map拼成sql中where后的查询条件
     * 
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static String mapToWhere(Map map)
    {
        String str = "";
        if (map != null)
        {
            Iterator it = map.entrySet().iterator();
            while (it != null && it.hasNext())
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                str += " AND " + key + " " + value;
            }
        }
        return str;
    }
    
    /**
     * 
     * 将map拼成hql中where后的查询条件
     * 
     * @param map
     * @param table 表别名
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static String mapToWhereWithTableAlais(Map map, String table)
    {
        String str = "";
        if (map != null)
        {
            Iterator it = map.entrySet().iterator();
            while (it != null && it.hasNext())
            {
                Map.Entry entry = (Map.Entry)it.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                str += " and " + table + "." + key + " " + value;
            }
        }
        return str;
    }
    
    /**
     * 
     * 组合参数
     * 
     * @param obj
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object[] buildCondition(Object obj)
    {
        Class clazz = obj.getClass();
        Field[] filds = clazz.getDeclaredFields();
        List<Object> list = new ArrayList<Object>();
        for (Field fild : filds)
        {
            String fieldName = fild.getName();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try
            {
                Method method = clazz.getMethod("get" + fieldName);
                Object value = method.invoke(obj);
                if (value != null)
                {
                    list.add(value);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Object[] objs = new Object[list.size()];
        for (int i = 0; i < list.size(); i = i + 1)
        {
            objs[i] = list.get(i);
        }
        return objs;
    }
}
