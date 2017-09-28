package com.wb.component.mobile.pushMessage.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wb.component.mobile.pushMessage.service.IPushMessageService;
import com.wb.component.mobile.push.PushUtil;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.SendMessage;
@Service("job")
public class Job {
	
	@Autowired
	@Qualifier("pushMessageService")
	private IPushMessageService pushMessageService;
	/*
	 * wangyanlong
	 */
	public void initPower() {
		ArrayList<MntMember> mntMemberList1=pushMessageService.getSysRole2("查看者");
		if(mntMemberList1!=null&&mntMemberList1.size()>0&&!"".equals(mntMemberList1.get(0).getId().toString())){
			for(int i=0;i<mntMemberList1.size();i++){
				pushMessageService.insertSysRoleFunction1(mntMemberList1.get(i).getId(),153,198);
				pushMessageService.insertSysRoleFunction1(mntMemberList1.get(i).getId(),154,199);
			}
		}else{
			System.out.println("该用户角色已经存在。。。。。。");
		}
		
//		ArrayList<MntMember> mntMemberList=pushMessageService.getMntMemberById1();
//		Integer id=0; 
//		for(int i=0;i<mntMemberList.size();i++){
////			ArrayList<MntMember> mntMemberList8=pushMessageService.getSysroleById(mntMemberList.get(i).getId());
////			if(mntMemberList8!=null&&mntMemberList8.size()>0&&!"".equals(mntMemberList8.get(0).getId().toString())){
////				System.out.println("该用户角色已经存在。。。。。。");
////			}else{
//				pushMessageService.insertSysRole(mntMemberList.get(i).getId());
//				//查看者
//				ArrayList<MntMember> mntMemberList1=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "查看者");
//				for(int j=0;j<mntMemberList1.size();j++){
//					id=mntMemberList1.get(j).getId();
//					pushMessageService.insertSysRoleFunction(mntMemberList1.get(j).getId(), -1);
//				}
//				//工商主管
//				ArrayList<MntMember> mntMemberList2=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "工商主管");
//				for(int j=0;j<mntMemberList2.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList2.get(j).getId(), -2);
//				}
//				//工商外勤
//				ArrayList<MntMember> mntMemberList3=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "工商外勤");
//				for(int j=0;j<mntMemberList3.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList3.get(j).getId(), -3);
//				}
//				
//				//银行主管
//				ArrayList<MntMember> mntMemberList4=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "银行主管");
//				for(int j=0;j<mntMemberList4.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList4.get(j).getId(), -4);
//				}
//				
//				//银行外勤
//				ArrayList<MntMember> mntMemberList5=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "银行外勤");
//				for(int j=0;j<mntMemberList5.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList5.get(j).getId(), -5);
//				}
//				
//				//税务主管
//				ArrayList<MntMember> mntMemberList6=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "税务主管");
//				for(int j=0;j<mntMemberList6.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList6.get(j).getId(), -6);
//				}
//				
//				//税务外勤
//				ArrayList<MntMember> mntMemberList7=pushMessageService.getSysRole1(mntMemberList.get(i).getId(), "税务外勤");
//				for(int j=0;j<mntMemberList7.size();j++){
//					pushMessageService.insertSysRoleFunction(mntMemberList7.get(j).getId(), -7);
//				}
//				ArrayList<MntMember> bizMember=pushMessageService.getBizMemberById(mntMemberList.get(i).getId());
//				if(bizMember!=null&&bizMember.size()>0&&!"".equals(bizMember.get(0).getId().toString())){
//					for(int j=0;j<bizMember.size();j++){
//						pushMessageService.insertSysMemberRole(bizMember.get(j).getId(), id);
//					}
//				}
////				pushMessageService.insertSysMemberRole(mntMemberList.get(i).getId(), id);
//			}
			
//		}
		System.out.println("初始化权限完成。。。。。。");
	}
	
	/**
	 * 当月月末前三天自动推送即将到期信息
	 * @author sunxu
	 */
	public void pushM() {
		Calendar a = Calendar.getInstance();
		System.out.println("批量推送任务进行中。。。");
		String payYear = a.get(Calendar.YEAR) + "";
		int nowDate = a.get(Calendar.DATE);
		int lastThreeDay = pushMessageService.getLastThreeDay();
		
		//查询所有小微企业--开始
		List<Map<String, Object>> list = pushMessageService.selectAllCompany(payYear);
		//查询所有小微企业--结束
		if (list != null) {

			for (Map<String, Object> map : list) {
				String payMonths = map.get("PAYMONTHS").toString();
				if (payMonths != null && !"".equals(payMonths)) {
					payMonths = pushMessageService.checkDupArray(payMonths.split(","));// 由于sql语句查出来月份会重复此处去重排序;
					map.put("PAYMONTHS", payMonths);
				}
				String bMonths = map.get("BMONTHS").toString();
				String eMonths = map.get("EMONTHS").toString();
				boolean isArr = pushMessageService.checkIfConPayMonthsArr(bMonths, eMonths, payMonths, payYear);// 是否欠费
				boolean isEnd = pushMessageService.checkIfConPayMonthsEnd(eMonths, payMonths, payYear);// 是否即将到期
				if (!isArr) {
					if (isEnd) {
						String androidDeviceToken = map.get("ANDROIDDEVICETOKEN").toString();
						String iosDeviceToken = map.get("IOSDEVICETOKEN").toString();
//当前日期大于最后3天
						if (nowDate >= lastThreeDay) {
							if (androidDeviceToken != null && !androidDeviceToken.equals("")) {
								ArrayList<Map> androidListArgs = new ArrayList<Map>();
								Map temp = new HashMap();
								temp.put("type", "1");
								androidListArgs.add(temp);

								try {
									PushUtil.sendAndroidUnicast(androidDeviceToken, "testticket", "理财金服",
											"您的代理记账费用还有" + (3 - (nowDate - lastThreeDay)) + "天到期，请及时进行充值",
											androidListArgs);
								} catch (Exception e) {
									System.out.println("发送android推送信息失败");
									e.printStackTrace();
								}
							}

							if (iosDeviceToken != null && !iosDeviceToken.equals("")) {
								ArrayList<Map> iosListArgs = new ArrayList<Map>();
								Map iosTemp = new HashMap();
								iosTemp.put("type", "1");
								iosListArgs.add(iosTemp);
								// badge是消息总数 开始
								Date date = new Date();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String str = sdf.format(date);
								String mntCustomId = map.get("MNTCUSTOMID").toString();
								String iosDeviceTokenForInsert = map.get("IOSDEVICETOKEN").toString();
								pushMessageService.insertMessage(nowDate, lastThreeDay, str, mntCustomId, iosDeviceTokenForInsert);
								// 插入结束,查询消息总数badge开始
								int badge = pushMessageService.selectMessageNum(iosDeviceTokenForInsert);
								
								// 查询消息总数badge结束

								try {
									PushUtil.sendIOSUnicast(iosDeviceToken,
											"您的代理记账费用还有" + (3 - (nowDate - lastThreeDay)) + "天到期，请及时进行充值", iosListArgs,
											badge);
								} catch (Exception e) {
									System.out.println("发送ios推送信息失败");
									e.printStackTrace();
								}
								// 所有未读信息改为已读信息--开始
								pushMessageService.updateMessagestate(iosDeviceTokenForInsert);
								// 所有未读信息改为已读信息--结束
							}
						}
					}
				}
			}
		}
	}
	
	/** 
	* @Title: sendMessage 
	* @Description: 定时发短信
	* @param 
	* @date 2016年10月19日 下午5:46:48 
	* @author hechunyang 
	* @return void 返回类型 
	* @throws
	 */
	public void sendMessage(){
		List<SendMessage> list=pushMessageService.getAllSendMessage();
		pushMessageService.update_getAllSendMessage(list);
	
	}
	
	public void cleanMessage(){
		pushMessageService.cleanMessage();
	}
}
