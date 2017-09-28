package com.wb.component.mobile.pushMessage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMessage;
import com.wb.model.entity.computer.SendMessage;

public interface IPushMessageService {
	public Map<String, Object> addisOver(String telephone);
	public void addIosMessage(String iosDeviceToken,String bizOrgId,String message,String tabname,int type);
	public void addAndroidMessage(String androidDeviceToken,String bizOrgId,String message,String tabname,int type);
	public void updateMessage(String iosDeviceToken,int type);
	public int countBadge(String iosDeviceToken);
	public int countOtherMessage(String iosDeviceToken,int type);
	public boolean isTodayPush(String channel,String iosDeviceToken,String androidDeviceToken);
	public String updateReadMessage(int messageId);
	public int getLastThreeDay();
	public String checkDupArray(String[] strs);
	public boolean checkIfConPayMonthsArr(String bMonths, String eMonths, String payMonths, String payYear);
	public boolean checkIfConPayMonthsEnd(String eMonths, String payMonths, String payYear);
	public List<Map<String,Object>> selectAllCompany(String payYear);
	public void insertMessage(int nowDate,int lastThreeDay,String str,String mntCustomId,String iosDeviceTokenForInsert);
	public int selectMessageNum(String iosDeviceToken);
	public void updateMessagestate(String iosDeviceToken);
	public void cleanMessage();
	/**
	 * @param type
	 * @param channel
	 * @param androidDeviceToken
	 * @param iosDeviceToken
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ArrayList<MntMessage> getAllMessage(String type, String channel, String androidDeviceToken, String iosDeviceToken, int rowBegin,
			int pageSize);
	
	public ArrayList<MntMember> getMntMemberById();
	public ArrayList<MntMember> getSysroleById(Integer id);
	public void insertSysRole(Integer id);
	public ArrayList<MntMember> getSysRole1(Integer id,String name);
	public ArrayList<MntMember> getSysRole2(String name);
	public void insertSysRoleFunction(Integer id,Integer roleId);
	public void insertSysRoleFunction1(Integer roleId,Integer menuId,Integer functionId);
	public void insertSysMemberRole(Integer id,Integer roleId);
	public ArrayList<MntMember> getBizMemberById(Integer id);
	public ArrayList<MntMember> getMntMemberById1();
	public void update_getAllSendMessage(List<SendMessage> list);
	public List<SendMessage> getAllSendMessage();
}
