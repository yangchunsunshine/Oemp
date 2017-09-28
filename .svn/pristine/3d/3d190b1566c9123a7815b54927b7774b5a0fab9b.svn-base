package com.wb.framework.commonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.wb.model.entity.computer.accTableEntity.BizMember;

public class DateFormatUtil
{
    public static Object nullFilter(Object obj){
        try
        {
            Class clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            for(Method method : methods){
                String methodName = method.getName();
                if("get".equals(methodName.substring(0, 3))&&!"getClass".equals(methodName)){
                    Object mObj = method.invoke(methodName, null);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static void main(String[] args){
        BizMember mem = new BizMember();
        DateFormatUtil.nullFilter(mem);
    }
}
