//package com.wb.component.computer.businessManage.util;
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.minidev.json.JSONObject;
//import net.minidev.json.JSONStyle;
//import net.minidev.json.JSONValue;
//
//import org.apache.log4j.Logger;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import org.springframework.web.servlet.support.RequestContext;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class AjaxAction {
//	private static final Logger logger = Logger.getLogger(AjaxAction.class);
//
//	public void returnAjaxString(String text,HttpServletResponse response){
//		PrintWriter writer = null;
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8"); 
//			writer = response.getWriter();
//			writer.println(text);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		}finally{
//			if(writer!=null){
//				writer.close();
//			}
//		}
//	}
//	
//	
//	public void returnAjaxBean(Object bean,HttpServletResponse response){
//		PrintWriter writer = null;
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8"); 
//			writer =  response.getWriter();
//			writer.println(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}finally{
//			if(writer != null){
//				writer.close();
//			}
//		}
//	}
//	
//	public void returnAjaxBean(Object bean,HttpServletResponse response,String dateFormat){
//		PrintWriter writer = null;
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(new SimpleDateFormat(dateFormat));  
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8"); 
//			writer =  response.getWriter();
//			writer.println(mapper.writeValueAsString(bean));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		}
//	}
//	
//	
//	public String successMsg(String msg){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", true);
//		jsonObject.put("message", msg);
//		return jsonObject.toJSONString() ;
//	}
//	
//	public String failureMsg(String msg){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", false);
//		jsonObject.put("message", msg);
//		return jsonObject.toJSONString() ;
//	}
//	public String successData(String data){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", true);
//		jsonObject.put("data", data);
//		return jsonObject.toJSONString();
//	}
//	
//	public String successData(String data,String msg){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", true);
//		jsonObject.put("message", msg);
//		jsonObject.put("data", data);
//		return jsonObject.toJSONString();
//	}
//	public String failureData(String data){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", false);
//		jsonObject.put("data", data);
//		return jsonObject.toJSONString();
//	}
//	public String failureData(String data,String msg){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", false);
//		jsonObject.put("message", msg);
//		jsonObject.put("data", data);
//		return jsonObject.toJSONString();
//	}
//	
//	/*
//	* @Title: fileTooLarge
//	* @Description: 上传文件太大拦截
//	* @param @param response
//	* @author 王磊
//	* @return void json
//	*/
//	@ExceptionHandler(MaxUploadSizeExceededException.class)
//	public void fileTooLarge(HttpServletRequest request,HttpServletResponse response){
//		returnAjaxString(failureMsg((new RequestContext(request)).getMessage("UPLOADED_IMAGE_TOO_LARGER_TIP")), response);
//	}
//
//	//跨域ajax增加callBack()外包
//	public void returnAjaxBeanCallBack(Object bean,HttpServletResponse response){
//		PrintWriter writer = null;
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8"); 
//			writer =  response.getWriter();
////			writer.println(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));
//			writer.println("callBack("+(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS))+")");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		}finally{
//			if(writer != null){
//				writer.close();
//			}
//		}
//	}
//	//跨域ajax增加callBack()外包
//	public void returnAjaxBeanCallBack2(Object bean,HttpServletResponse response,String callBack){
//		PrintWriter writer = null;
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8"); 
//			writer =  response.getWriter();
////			writer.println(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));
//			//增加一个json字符串的变量，将其中的转义字符"\/"替换为"/"：解决数据库中的路径格式转义问题。
//			String jsonStr="";
//			if(null==callBack||"".equals(callBack)){
//				jsonStr=(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));	
//			}else{
//				jsonStr=callBack+"("+(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS))+")";	
//			}
//			
//			jsonStr=jsonStr.replaceAll("\\\\/","/");
//			writer.println(jsonStr);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		}finally{
//			if(writer != null){
//				writer.close();
//			}
//		}
//	}
//
//}
