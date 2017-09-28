package com.wb.framework.commonResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.framework.nestLogger.NestLogger;

/**
 * 
 * 通用response输出流工具
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AjaxAction
{
    
    public void returnAjaxString(String text, HttpServletResponse response)
    {
        PrintWriter writer = null;
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.println(text);
        }
        catch (IOException e)
        {
            NestLogger.showException(e);
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }
    
    public void returnAjaxBean(Object bean, HttpServletResponse response)
    {
        PrintWriter writer = null;
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.println(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }
    
    public void returnAjaxBean(Object bean, HttpServletResponse response, String dateFormat)
    {
        PrintWriter writer = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.println(mapper.writeValueAsString(bean));
        }
        catch (JsonProcessingException e)
        {
            NestLogger.showException(e);
        }
        catch (IOException e)
        {
            NestLogger.showException(e);
        }
    }
    
    public String successMsg(String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("message", msg);
        return jsonObject.toJSONString();
    }
    
    public String failureMsg(String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("message", msg);
        return jsonObject.toJSONString();
    }
    
    public String successData(String data)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }
    
    public String successData(String data, String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("message", msg);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }
    
    public String failureData(String data)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }
    
    public String failureData(String data, String msg)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("message", msg);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }
    
    /**
     * 
     * 文件过大拦截(controller必须继承该类,才会对异常进行拦截)
     * 
     * @param request
     * @param response
     * @see [类、类#方法、类#成员]
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void fileTooLarge(HttpServletRequest request, HttpServletResponse response)
    {
        returnAjaxString(failureMsg("上传文件过大!请重新选择再试!"), response);
    }
}
