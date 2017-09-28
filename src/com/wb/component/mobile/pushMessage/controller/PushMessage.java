package com.wb.component.mobile.pushMessage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.mobile.common.constant.ConstantMobile;
import com.wb.component.mobile.push.PushUtil;
import com.wb.component.mobile.pushMessage.service.IPushMessageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.encrypt.AES;
import com.wb.model.entity.computer.MntMessage;

@Controller
@RequestMapping("monitored")
public class PushMessage extends AjaxAction {

	@Autowired
	@Qualifier("pushMessageService")
	private IPushMessageService pushMessageService;

	/**
	 * 推送接口理财金服android和IOS公用 登录时调用检测是否欠费或即将到期，如果是则推送消息
	 * 
	 * @param telephone
	 */
	@RequestMapping(value = "asyn/isOver", method = { RequestMethod.POST, RequestMethod.GET })
	public void isOver(HttpServletRequest request, HttpServletResponse response, String param) {
//此方法与android端同步上线
//		String json = null;
//		try {
//			json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
//			System.out.println("json:" + json);
//			ObjectMapper obj = new ObjectMapper();
//			JsonNode jsontemp = null;
//			jsontemp = obj.readTree(json);
//			String bizOrgId = jsontemp.findValue("mntCustomId").asText();
//			String channel = jsontemp.findValue("channel").asText();
//			// 检测是否欠费--开始
//			Map<String, Object> map = pushMessageService.addisOver(bizOrgId);
//			// 检测是否欠费--结束
//			// 获取androidDeviceToken或者iosDeviceToken开始
//			String androidDeviceToken = "";
//			String iosDeviceToken = "";
//			if (null != map) {
//				androidDeviceToken = map.get("ANDROIDDEVICETOKEN").toString();
//				iosDeviceToken = map.get("IOSDEVICETOKEN").toString();
//			}
//			// 获取androidDeviceToken或者iosDeviceToken结束
//			
//			//上面有检测是否欠费方法，如果需要详细信息则启用下面的查询欠费月份
////			//查询欠费月份--开始
////			String bMonths = map.get("BMONTHS").toString();
////			String eMonths = map.get("EMONTHS").toString();
////			String payMonths = map.get("PAYMONTHS").toString();
////			
////			
////			int fromMonth = Integer.parseInt(bMonths);// 合同起始月份
////			int toMonth = Integer.parseInt(eMonths);// 合同结束月份
////			List list = new ArrayList();// 应该缴费的月份
////			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
////			String today = df.format(new Date());// new Date()为获取当前系统年月
////			int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
////			int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份
////			for (int i = fromMonth; i < toMonth + 1; i++) {
////				list.add(i);
////			}
////			String[] alreadyPayM = payMonths.split(",");// 已交缴费的月份
////			for (int i = 0; i < alreadyPayM.length; i++) {// 从应该缴费的月份中去掉已经缴费的月份
////				Iterator it = list.iterator(); // 应该缴费的月份
////				while (it.hasNext()) {
////					int shoudPay = (Integer) it.next();
////					if (!"".equals(alreadyPayM[i])) {// 查出来的数据有,的情况
////						if (Integer.parseInt(alreadyPayM[i]) == shoudPay) {
////							it.remove(); // 移除该对象
////						}
////					}
////				}
////				list.remove(alreadyPayM[i]);
////			}
////			if (list.size() > 0) {
////				// 如果存在仍未缴费的月份，判断缴费年费是否小于当前系统年份，如果小于当前系统年份都为欠费
//////				if (Integer.parseInt(payYear) < currYear) {
//////					return true;
//////				} else if (Integer.parseInt(payYear) == currYear) {// 如果不小于但前年分则判断未交费月份是否小于当前月份，小于则欠费
//////					Collections.sort(list);// 仍未缴费的月份排序
//////					// 判断仍未缴费的月份是否小于当前月份
//////					int noPayM = (Integer) list.get(0);
//////					if (noPayM < currMonth) {// 小于则欠费
//////						return true;
//////					}
//////				}
////			}
//////			return false;
//			//查询欠费月份--结束
//			// 配置listArgs开始
//			ArrayList<Map> listArgs = new ArrayList<Map>();
//			Map temp = new HashMap();
//			temp.put("type", "1");
//			listArgs.add(temp);
//			// 配置listArgs结束
//			// 检查今日是否已推送开始
//			boolean isTodayPush = pushMessageService.isTodayPush(channel, iosDeviceToken, androidDeviceToken);
//			// 检查今日是否已推送结束
//			if (!isTodayPush) {
//				if ("android".equals(channel)) {
//					if (androidDeviceToken != null && !androidDeviceToken.equals("")) {
//						pushMessageService.addAndroidMessage(androidDeviceToken, bizOrgId,"您的代理记账费用已到期，请及时进行充值","催收",1);
//						PushUtil.sendAndroidUnicast(androidDeviceToken, "通知栏提示文字", "理财金服", "您的代理记账费用已到期，请及时进行充值",
//								listArgs);
//						returnAjaxString(successData("true"), response);
//					}
//				} else if ("ios".equals(channel)) {
//					if (iosDeviceToken != null && !iosDeviceToken.equals("")) {
//						// 插入消息 开始
//						pushMessageService.addIosMessage(iosDeviceToken, bizOrgId,"您的代理记账费用已到期，请及时进行充值","催收",1);
//						// 插入结束
//						// 查询消息总数badge开始
//						int badge = pushMessageService.countBadge(iosDeviceToken);
//						// 查询消息总数badge结束
//						PushUtil.sendIOSUnicast(iosDeviceToken, "您的代理记账费用已到期，请及时进行充值", listArgs, badge);
//
//						returnAjaxString(successData("true"), response);
//					}
//				}
//				
//			}
//		} catch (Exception e1) {
//			returnAjaxString(failureData("false"), response);
//			e1.printStackTrace();
//		}
//		returnAjaxString(successData("今天已经推送过了"), response);
	}

	/**
	 * 将某一类型的消息全部致为已读，并返回其他剩余类型消息各自的消息总数
	 * @author 孙旭
	 * @mobile 15810770758
	 * @param request
	 * @param response
	 * @param param
	 * @return 
	 */
	@RequestMapping(value = "asyn/updateMessage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<Integer, Integer> updateMessage(HttpServletRequest request, HttpServletResponse response, String param) {
		String json = null;
		Map<Integer ,Integer> tempMap=new HashMap();
		try {
			json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
			System.out.println("json:" + json);
			// 获取iosDeviceToken--开始
			ObjectMapper obj = new ObjectMapper();
			JsonNode jsontemp = null;
			jsontemp = obj.readTree(json);
			String iosDeviceToken = jsontemp.findValue("iosDeviceToken").asText();
			// 获取iosDeviceToken--结束
			//获取消息类型--开始
			int type=jsontemp.findValue("type").asInt();
			//获取消息类型--结束
			pushMessageService.updateMessage(iosDeviceToken, type);
			//返回其他类型消息各自的总数
			List<Integer> list=new ArrayList<Integer>();
			//数据库有6种类型
			for(int i=1;i<=6;i++){
				list.add(i);
			}
			list.remove(type-1);
			for(int typeOther:list){
				//查询其他类型消息各自总数开始
				int totalnum=pushMessageService.countOtherMessage(iosDeviceToken, typeOther);
				//查询其他类型消息各自总数结束
				tempMap.put(typeOther, totalnum);
			}
		} catch (Exception e1) {
			returnAjaxString(failureData("false"), response);
			e1.printStackTrace();
		}
		return tempMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "asyn/getAllMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAllMessage(HttpServletRequest request, HttpServletResponse response, String param) {
		String json = null;
		try {
			json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
			System.out.println("json:" + json);
			// 获取iosDeviceToken--开始
			ObjectMapper obj = new ObjectMapper();
			JsonNode jsontemp = null;
			jsontemp = obj.readTree(json);
			String type=jsontemp.findValue("type").asText();
			String channel = jsontemp.findValue("channeltype").asText();
			String iosDeviceToken = jsontemp.findValue("iosDeviceToken").asText();
			String androidDeviceToken = jsontemp.findValue("androidDeviceToken").asText();
			int pageNo=jsontemp.findValue("pageNo").asInt();
			int pageSize=jsontemp.findValue("pageSize").asInt();
			int rowBegin=(pageNo-1)*pageSize;
			//获取全部信息--开始
			ArrayList<MntMessage> resultList=pushMessageService.getAllMessage(type,channel, androidDeviceToken, iosDeviceToken,rowBegin,pageSize);
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<resultList.size();i++){
				System.out.println("id:"+resultList.get(i).getId());
				MntMessage mnttemp=(MntMessage)resultList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("id", mnttemp.getId());
                jo.put("title", mnttemp.getMessage());
                String stamp=mnttemp.getStamp().replace(".0", "");
                jo.put("stamp", stamp);
                jo.put("isread", mnttemp.getIsread());
                jsonArray.put(jo);
			}
			System.out.println(jsonArray.toString());
			//获取全部信息--结束
			returnAjaxString(jsonArray.toString(), response);
		} catch (Exception e1) {
			returnAjaxString(failureData("false"), response);
			e1.printStackTrace();
		}
	}
	@RequestMapping(value = "asyn/readMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void readMessage(HttpServletRequest request, HttpServletResponse response, String param) {
		String json = null;
		try {
			json = AES.Decrypt(param, ConstantMobile.PACKETKEY);
			System.out.println("json:" + json);
			// 获取iosDeviceToken--开始
			ObjectMapper obj = new ObjectMapper();
			JsonNode jsontemp = null;
			jsontemp = obj.readTree(json);
			int messageId = jsontemp.findValue("messageId").asInt();
			String issuccessful=pushMessageService.updateReadMessage(messageId);
			returnAjaxString(successData(issuccessful), response);
		} catch (Exception e1) {
			returnAjaxString(failureData("false"), response);
			e1.printStackTrace();
		}
	}
}
