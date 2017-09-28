package com.wb.component.mobile.pushMessage.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import util.SendMessageUtil;

import com.wb.component.mobile.pushMessage.service.IPushMessageService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMessage;
import com.wb.model.entity.computer.SendMessage;
import com.wb.model.entity.computer.SendRealationMobile;

@Service("pushMessageService")
public class PushMessageService extends BaseDao implements IPushMessageService {
	private static final ThreadLocal<HttpSession> localSession = new ThreadLocal<HttpSession>();
	private Object map;

	/**
	 * 去掉数组中的重复月份
	 * 
	 * @param strs
	 * @return
	 */
	public String checkDupArray(String[] strs) {
		Set<String> strSet = new HashSet<String>();
		for (String str : strs) {
			strSet.add(str);
		}
		String strsN = "";
		for (String str : strSet) {
			strsN += str + ",";
		}
		return strsN.substring(0, strsN.length() - 1);
	}

	/**
	 * 判断是否有欠费月份
	 * 
	 * @param bMonths
	 *            合同起始月份
	 * @param eMonths
	 *            合同截止月份
	 * @param payMonths
	 *            已缴费月份 逗号分隔格式
	 * @param payYear
	 *            缴费年份
	 * @return
	 */
	public boolean checkIfConPayMonthsArr(String bMonths, String eMonths, String payMonths, String payYear) {
		int fromMonth = Integer.parseInt(bMonths);// 合同起始月份
		int toMonth = Integer.parseInt(eMonths);// 合同结束月份
		List list = new ArrayList();// 应该缴费的月份
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份

		for (int i = fromMonth; i < toMonth + 1; i++) {
			list.add(i);
		}
		String[] alreadyPayM = payMonths.split(",");// 已交缴费的月份
		for (int i = 0; i < alreadyPayM.length; i++) {// 从应该缴费的月份中去掉已经缴费的月份
			Iterator it = list.iterator(); // 应该缴费的月份
			while (it.hasNext()) {
				int shoudPay = (Integer) it.next();
				if (!"".equals(alreadyPayM[i])) {// 查出来的数据有,的情况
					if (Integer.parseInt(alreadyPayM[i]) == shoudPay) {
						it.remove(); // 移除该对象
					}
				}
			}
			list.remove(alreadyPayM[i]);
		}
		if (list.size() > 0) {
			// 如果存在仍未缴费的月份，判断缴费年费是否小于当前系统年份，如果小于当前系统年份都为欠费
			if (Integer.parseInt(payYear) < currYear) {
				return true;
			} else if (Integer.parseInt(payYear) == currYear) {// 如果不小于但前年分则判断未交费月份是否小于当前月份，小于则欠费
				Collections.sort(list);// 仍未缴费的月份排序
				// 判断仍未缴费的月份是否小于当前月份
				int noPayM = (Integer) list.get(0);
				if (noPayM < currMonth) {// 小于则欠费
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否即将到期
	 * 
	 * @param eMonths
	 *            合同截止月份
	 * @param payMonths
	 *            已缴费月份 逗号分隔格式
	 * @param payYear
	 *            缴费年份
	 * @return
	 */
	public boolean checkIfConPayMonthsEnd(String eMonths, String payMonths, String payYear) {
		int toMonth = Integer.parseInt(eMonths);// 合同结束月份
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份
		if (currMonth == toMonth - 1 && Integer.parseInt(payYear) == currYear) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当月最后3天
	 * 
	 * @param days
	 * @return
	 */
	public int getLastThreeDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		List<String> list = new ArrayList<String>();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -3);
		int theDate = calendar.getTime().getDate();
		return theDate;
	}

	/**
	 * 根据手机号判断是否欠费,如果欠费每次登录时推送，最多1天推送1条
	 */
	public Map<String, Object> addisOver(String bizOrgId) {
		Calendar a = Calendar.getInstance();
		String payYear = a.get(Calendar.YEAR) + "";
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" IFNULL(org.ID, '') AS ORGID,");
		sql.append(" IFNULL(cus.ID, '') AS CUSID,");
		sql.append("IFNULL(cus.androidDeviceToken,'') AS ANDROIDDEVICETOKEN,");
		sql.append("IFNULL(cus.iosDeviceToken,'') AS IOSDEVICETOKEN,");
		sql.append(" IFNULL(org. NAME, '') AS ORGNAME,");
		sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS PAYMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accStartTime, '%Y')<'" + payYear
				+ "', '01', IFNULL(DATE_FORMAT(con.accStartTime, '%m'), '')) AS BMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime, '%Y')>'" + payYear
				+ "', 12, IFNULL(DATE_FORMAT(con.accEndTime , '%m'), '')) AS EMONTHS ");
		sql.append(" FROM mnt_customContract con LEFT JOIN mnt_customInfo cus");
		sql.append(" ON DATE_FORMAT(con.accStartTime, '%Y') <= '" + payYear
				+ "' AND DATE_FORMAT(con.accEndTime, '%Y') >= '" + payYear + "'");
		sql.append(" AND con.cusId = cus.id");
		sql.append(" LEFT JOIN  biz_organization org ON ");
		sql.append(" org.mntCustomId = cus.id");
		sql.append(" LEFT JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id");
		sql.append(" LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId");
		sql.append(
				" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.accNo = con.accNo AND exp.deleteFlag = 0 AND exp.id not in ");
		sql.append(" (SELECT ar.correlationId FROM mnt_auditRoute ar WHERE " + "ar.auditFlag <> 0)");
		sql.append(" AND DATE_FORMAT(exp.payDate, '%Y') =  '" + payYear + "'");
		sql.append(" WHERE");
		sql.append(" mng.state = 1 AND org.Enable = 1");
		sql.append(" AND org.Id='" + bizOrgId + "'");
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo");

		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if (list != null) {

			for (Map<String, Object> map : list) {
				String payMonths = map.get("PAYMONTHS").toString();
				if (payMonths != null && !"".equals(payMonths)) {
					payMonths = checkDupArray(payMonths.split(","));// 由于sql语句查出来月份会重复此处去重排序;
					map.put("PAYMONTHS", payMonths);
				}
				String bMonths = map.get("BMONTHS").toString();
				String eMonths = map.get("EMONTHS").toString();
				boolean isArr = checkIfConPayMonthsArr(bMonths, eMonths, payMonths, payYear);// 是否欠费
				if (isArr) {
					return map;
				}
			}
		}
		return null;
	}

	/**
	 * 获取消息总数
	 */
	public int countBadge(String iosDeviceToken) {

		// 插入结束,查询消息总数badge开始
		final StringBuffer selectsql = new StringBuffer();
		selectsql.append(" select count(1) from mnt_message where 1=1 and ");
		selectsql.append("iosdevicetoken='" + iosDeviceToken + "' and ");
		selectsql.append("isread='0' ");

		final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
		BigInteger badge = (BigInteger) selectString.uniqueResult();
		// 查询消息总数badge结束
		return badge.intValue();
	}

	/**
	 * 增加消息
	 */
	public void addIosMessage(String iosDeviceToken, String bizOrgId,String message,String tabname,int type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		final StringBuffer insertsql = new StringBuffer();
		//TODO:增加详细内容
		insertsql.append(
				" INSERT INTO mnt_message (message,isread,stamp,mngid,tabname,channeltype,iosdevicetoken,type) values('");
		insertsql.append(message+"','");
		insertsql.append("0','");
		insertsql.append(str + "','");
		insertsql.append(bizOrgId + "','");
		insertsql.append(tabname+"','");
		insertsql.append("ios','");
		insertsql.append(iosDeviceToken + "','");
		insertsql.append(type + "')");
		final Query insertString = this.getSession().createSQLQuery(insertsql.toString());
		insertString.executeUpdate();
	}

	/**
	 * 更新消息
	 */
	public void updateMessage(String iosDeviceToken, int type) {

		// 所有未读信息改为已读信息--开始
		final StringBuffer updatesql = new StringBuffer();
		updatesql.append("update mnt_message set isread='1' WHERE channeltype='ios' and type='"+type+"'");
		updatesql.append(" and iosdevicetoken='" + iosDeviceToken + "'");
		final Query updateString = this.getSession().createSQLQuery(updatesql.toString());
		updateString.executeUpdate();
		// 所有未读信息改为已读信息--结束
	}

	/**
	 * 定时任务清理所有已读信息
	 */
	public void cleanMessage() {
		final StringBuffer updatesql = new StringBuffer();
		updatesql.append("DELETE FROM mnt_message WHERE channeltype='1' and isread='1'");
		final Query updateString = this.getSession().createSQLQuery(updatesql.toString());
		updateString.executeUpdate();

	}

	/**
	 * 今日是否已推送过
	 */
	@Override
	public boolean isTodayPush(String channel, String iosDeviceToken, String androidDeviceToken) {
		// 插入结束,查询消息总数badge开始
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		if ("ios".equals(channel)) {
			if (null != iosDeviceToken) {
				final StringBuffer selectsql = new StringBuffer();
				selectsql.append(" select count(1) from mnt_message where 1=1 and ");
				selectsql.append("iosdevicetoken='" + iosDeviceToken + "' and ");
				selectsql.append(" DATE_FORMAT(stamp,'%Y-%m-%d')='" + str + "'");

				final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
				BigInteger badge = (BigInteger) selectString.uniqueResult();
				// 查询今日消息总数badge结束
				if (badge.intValue() > 1) {
					return true;
				} else {
					return false;
				}
			}

		} else if ("android".equals(channel)) {
			if (null != androidDeviceToken) {
				final StringBuffer selectsql = new StringBuffer();
				selectsql.append(" select count(1) from mnt_message where 1=1 and ");
				selectsql.append("androiddevicetoken='" + androidDeviceToken + "' and ");
				selectsql.append(" DATE_FORMAT(stamp,'%Y-%m-%d')='" + str + "'");

				final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
				BigInteger badge = (BigInteger) selectString.uniqueResult();
				// 查询消息总数badge结束
				if (badge.intValue() > 1) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MntMessage> getAllMessage(String type, String channel, String androidDeviceToken,
			String iosDeviceToken, int rowBegin, int pageSize) {
		if ("android".equals(channel)) {

			if (androidDeviceToken != null && !androidDeviceToken.equals("") && !"0".equals(androidDeviceToken)) {
				// 插入结束,查询消息总数badge开始
				final StringBuffer selectsql = new StringBuffer();
				selectsql.append(" select * from mnt_message where 1=1 and ");
				selectsql.append("androiddevicetoken='" + androidDeviceToken + "' and type='" + type + "' and channeltype='"+channel+"'");
				selectsql.append(" Limit " + rowBegin + "," + pageSize);
				final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
				selectString.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				ArrayList<Map<String, Object>> androidAllMessageMap = (ArrayList<Map<String, Object>>) selectString
						.list();
				ArrayList<MntMessage> androidAllMessage = new ArrayList<MntMessage>();
				for (Map<String, Object> maptemp : androidAllMessageMap) {
					MntMessage androidMessage = new MntMessage();
					androidMessage.setId(Integer.parseInt(maptemp.get("id").toString()));
					androidMessage.setAndroidDeviceToken(androidDeviceToken);
					androidMessage.setChanneltype("1");
					androidMessage.setIosDeviceToken(iosDeviceToken);
					androidMessage.setIsread(Integer.parseInt(maptemp.get("isread").toString()));
					androidMessage.setMessage(maptemp.get("message").toString());
					androidMessage.setMngid(Integer.parseInt(maptemp.get("mngId").toString()));
					androidMessage.setStamp(maptemp.get("stamp").toString());
					androidMessage.setTabname(maptemp.get("tabname").toString());
					androidMessage.setType(Integer.parseInt(maptemp.get("type").toString()));
					androidAllMessage.add(androidMessage);
				}

				// 查询消息总数badge结束
				return androidAllMessage;
			}
		} else if ("ios".equals(channel)) {
			if (iosDeviceToken != null && !iosDeviceToken.equals("")) {
				// 插入结束,查询消息总数badge开始
				final StringBuffer selectsql = new StringBuffer();
				selectsql.append(" select * from mnt_message where 1=1 and ");
				selectsql.append("iosdevicetoken='" + iosDeviceToken + "' and type='" + type + "' and channeltype='"+channel+"'");
				selectsql.append(" Limit " + rowBegin + "," + pageSize);
				final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
				selectString.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

				ArrayList<Map<String, Object>> iosAllMessageMap = (ArrayList<Map<String, Object>>) selectString.list();
				// 查询消息总数badge结束

				ArrayList<MntMessage> iosAllMessage = new ArrayList<MntMessage>();
				for (Map<String, Object> maptemp : iosAllMessageMap) {
					MntMessage iosMessage = new MntMessage();
					iosMessage.setId(Integer.parseInt(maptemp.get("id").toString()));
					iosMessage.setAndroidDeviceToken(androidDeviceToken);
					iosMessage.setChanneltype(maptemp.get("channeltype").toString());
					iosMessage.setIosDeviceToken(iosDeviceToken);
					iosMessage.setIsread(Integer.parseInt(maptemp.get("isread").toString()));
					iosMessage.setMessage(maptemp.get("message").toString());
					iosMessage.setMngid(Integer.parseInt(maptemp.get("mngId").toString()));
					iosMessage.setStamp(maptemp.get("stamp").toString());
					iosMessage.setTabname(maptemp.get("tabname").toString());
					iosMessage.setType(Integer.parseInt(maptemp.get("type").toString()));
					iosAllMessage.add(iosMessage);
				}
				System.out.println("iosAllMessage"+iosAllMessage.toString());
				return iosAllMessage;
			}
		}
		return null;
	}

	public String updateReadMessage(int messageId) {
		// 未读信息改为已读信息--开始
		final StringBuffer updatesql = new StringBuffer();
		updatesql.append("update mnt_message set isread='1' WHERE id='");
		updatesql.append(+messageId+"'");
		final Query updateString = this.getSession().createSQLQuery(updatesql.toString());
		updateString.executeUpdate();
		// 未读信息改为已读信息--结束
		return "阅读消息成功";

	}

	/**
	 * 增加android消息
	 */
	 @Override
	public void addAndroidMessage(String androidDeviceToken, String bizOrgId,String message,String tabname,int type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		final StringBuffer insertsql = new StringBuffer();
		//TODO:增加详细内容
		insertsql.append(
				" INSERT INTO mnt_message (message,isread,stamp,mngid,tabname,channeltype,androiddevicetoken,type) values('");
		insertsql.append(message+"','");
		insertsql.append("0','");
		insertsql.append(str + "','");
		insertsql.append(bizOrgId + "','");
		insertsql.append(tabname+"','");
		insertsql.append("android','");
		insertsql.append(androidDeviceToken + "','");
		insertsql.append(type + "')");
		final Query insertString = this.getSession().createSQLQuery(insertsql.toString());
		insertString.executeUpdate();
		
	}
	
	@Override
	public ArrayList<MntMember> getMntMemberById() {
		final StringBuffer selectsql = new StringBuffer();
		selectsql.append(" SELECT * FROM mnt_member");
		final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
		selectString.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap = (ArrayList<Map<String, Object>>) selectString
				.list();
		ArrayList<MntMember> mntMemberList = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList.add(mntMember);
		}
		return mntMemberList;
	}

	@Override
	public ArrayList<MntMember> getSysroleById(Integer id) {
		final StringBuffer selectsql8 = new StringBuffer();
		selectsql8.append(" SELECT id FROM sys_role WHERE refCompanyId="+id);
		final Query selectString8 = this.getSession().createSQLQuery(selectsql8.toString());
		selectString8.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap8 = (ArrayList<Map<String, Object>>) selectString8
				.list();
		ArrayList<MntMember> mntMemberList8 = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap8) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList8.add(mntMember);
		}
		return mntMemberList8;
	}
	
	@Override
	public void insertSysRole(Integer id) {
		final StringBuffer insertsql = new StringBuffer();
		//TODO:增加详细内容
		insertsql.append(" INSERT  INTO sys_role (id,roleName,refCompanyId,roleType,roleCode,createTime,updateTime) "
				+ "SELECT NULL,roleName,"+id+",roleType,roleCode,SYSDATE(),SYSDATE() FROM sys_role WHERE id<=-1 AND id>=-7");
		final Query insertString = this.getSession().createSQLQuery(insertsql.toString());
		insertString.executeUpdate();
	}

	@Override
	public ArrayList<MntMember> getSysRole1(Integer id, String name) {
		final StringBuffer selectsql1 = new StringBuffer();
		selectsql1.append(" SELECT id FROM sys_role WHERE refCompanyId="+id+" AND roleName='"+name+"';");
		final Query selectString1 = this.getSession().createSQLQuery(selectsql1.toString());
		selectString1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap1 = (ArrayList<Map<String, Object>>) selectString1
				.list();
		ArrayList<MntMember> mntMemberList1 = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap1) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList1.add(mntMember);
		}
		return mntMemberList1;
	}
	@Override
	public ArrayList<MntMember> getSysRole2(String name) {
		final StringBuffer selectsql1 = new StringBuffer();
		selectsql1.append(" SELECT id FROM sys_role WHERE  roleName='"+name+"';");
		final Query selectString1 = this.getSession().createSQLQuery(selectsql1.toString());
		selectString1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap1 = (ArrayList<Map<String, Object>>) selectString1
				.list();
		ArrayList<MntMember> mntMemberList1 = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap1) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList1.add(mntMember);
		}
		return mntMemberList1;
	}
	@Override
	public void insertSysRoleFunction(Integer id, Integer roleId) {
		final StringBuffer insertsql1 = new StringBuffer();
		//TODO:增加详细内容
		insertsql1.append(" INSERT  INTO sys_role_function (id,roleId,menuId,functionId,createTime,updateTime) "
				+ "SELECT NULL,"+id+",menuId,functionId,SYSDATE(),SYSDATE() FROM sys_role_function WHERE roleId="+roleId);
		final Query insertString1 = this.getSession().createSQLQuery(insertsql1.toString());
		insertString1.executeUpdate();
	}
	@Override
	public void insertSysRoleFunction1(Integer roleId,Integer menuId,Integer functionId) {
		final StringBuffer insertsql1 = new StringBuffer();
		//TODO:增加详细内容
		insertsql1.append(" INSERT  INTO sys_role_function (id,roleId,menuId,functionId,createTime,updateTime) values"
				+ "(NULL,"+roleId+","+menuId+","+functionId+",SYSDATE(),SYSDATE())");
		final Query insertString1 = this.getSession().createSQLQuery(insertsql1.toString());
		insertString1.executeUpdate();
	}
	@Override
	public void insertSysMemberRole(Integer id, Integer roleId) {
		final StringBuffer insertsql2 = new StringBuffer();
		//TODO:增加详细内容
		insertsql2.append(" INSERT  INTO sys_member_role (id,memberID,roleID,createTime,updateTime) VALUES "
				+ "(NULL,"+id+","+roleId+",SYSDATE(),SYSDATE())");
		final Query insertString2 = this.getSession().createSQLQuery(insertsql2.toString());
		insertString2.executeUpdate();
	}
	
	public List<Map<String,Object>> selectAllCompany(String payYear){
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" IFNULL(org.ID, '') AS ORGID,");
		sql.append(" IFNULL(cus.ID, '') AS CUSID,");
		sql.append(" IFNULL(org.mntCustomId, '') AS MNTCUSTOMID,");
		sql.append("IFNULL(cus.androidDeviceToken,'') AS ANDROIDDEVICETOKEN,");
		sql.append("IFNULL(cus.iosDeviceToken,'') AS IOSDEVICETOKEN,");
		sql.append(" IFNULL(org. NAME, '') AS ORGNAME,");
		sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS PAYMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accStartTime, '%Y')<'" + payYear
				+ "', '01', IFNULL(DATE_FORMAT(con.accStartTime, '%m'), '')) AS BMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime, '%Y')>'" + payYear
				+ "', 12, IFNULL(DATE_FORMAT(con.accEndTime , '%m'), '')) AS EMONTHS ");
		sql.append(" FROM mnt_customContract con LEFT JOIN mnt_customInfo cus");
		sql.append(" ON DATE_FORMAT(con.accStartTime, '%Y') <= '" + payYear
				+ "' AND DATE_FORMAT(con.accEndTime, '%Y') >= '" + payYear + "'");
		sql.append(" AND con.cusId = cus.id");
		sql.append(" LEFT JOIN  biz_organization org ON ");
		sql.append(" org.mntCustomId = cus.id");
		sql.append(" LEFT JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id");
		sql.append(" LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId");
		sql.append(
				" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.accNo = con.accNo AND exp.deleteFlag = 0 AND exp.id not in ");
		sql.append(" (SELECT ar.correlationId FROM mnt_auditRoute ar WHERE " + "ar.auditFlag <> 0)");
		sql.append(" AND DATE_FORMAT(exp.payDate, '%Y') =  '" + payYear + "'");
		sql.append(" WHERE");
		sql.append(" mng.state = 1 AND org.Enable = 1");
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo");

		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public void insertMessage(int nowDate,int lastThreeDay,String str,String mntCustomId,String iosDeviceTokenForInsert){
		final StringBuffer insertsql = new StringBuffer();
		insertsql.append(
				" INSERT INTO mnt_message (message,isread,stamp,mngid,tabname,channeltype,iosdevicetoken) values(");
		insertsql.append("'您的代理记账费用还有" + (3 - (nowDate - lastThreeDay)) + "天到期，请及时进行充值','");
		insertsql.append("0','");
		insertsql.append(str + "','");
		insertsql.append(mntCustomId + "','");
		insertsql.append("催收','");
		insertsql.append("1','");
		insertsql.append(iosDeviceTokenForInsert + "','");
		final Query insertString = this.getSession().createSQLQuery(insertsql.toString());
		insertString.executeUpdate();
	}
	public int selectMessageNum(String iosDeviceToken){
		final StringBuffer selectsql = new StringBuffer();
		selectsql.append(" select count(1) from mnt_msgssage, where 1=1 and");
		selectsql.append("iosdevicetoken=" + iosDeviceToken);
		final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
		return (Integer) selectString.uniqueResult();
	}
	
	public void updateMessagestate(String iosDeviceToken){
		final StringBuffer updatesql = new StringBuffer();
		updatesql.append("update mnt_message set isread='0' WHERE ");
		updatesql.append("iosdevicetoken=" + iosDeviceToken);
		final Query updateString = this.getSession().createSQLQuery(updatesql.toString());
		updateString.executeUpdate();
	}

	@Override
	public ArrayList<MntMember> getBizMemberById(Integer id) {
		final StringBuffer selectsql1 = new StringBuffer();
		selectsql1.append(" SELECT bm.id FROM mnt_mngandusers mmd,biz_member  bm WHERE mmd.mntMemberId="+id
				+" AND bm.id=mmd.userMemberId AND ( mmd.state = 0 OR mmd.state = 1 OR mmd.state = 2 )");
		final Query selectString1 = this.getSession().createSQLQuery(selectsql1.toString());
		selectString1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap1 = (ArrayList<Map<String, Object>>) selectString1
				.list();
		ArrayList<MntMember> mntMemberList1 = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap1) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList1.add(mntMember);
		}
		return mntMemberList1;
	}

	/* (non-Javadoc)
	 * 根据消息类型返回各类型消息总数
	 * @see com.wb.component.mobile.pushMessage.service.IPushMessageService#countOtherMessage(java.lang.String, int)
	 */
	@Override
	public int countOtherMessage(String iosDeviceToken, int type) {
		final StringBuffer selectsql = new StringBuffer();
		selectsql.append(" select count(1) from mnt_message where");
		selectsql.append(" iosDeviceToken='" + iosDeviceToken+"' and ");
		selectsql.append(" type='" + type+"' and isread='0'");
		final Query selectString = this.getSession().createSQLQuery(selectsql.toString());
		 BigInteger a = (BigInteger) selectString.uniqueResult();
		 return a.intValue();
	}

	@Override
	public ArrayList<MntMember> getMntMemberById1() {
		final StringBuffer selectsql1 = new StringBuffer();
		selectsql1.append(" SELECT id FROM mnt_member WHERE id NOT IN(SELECT refcompanyid FROM sys_role WHERE refcompanyid IN(SELECT id FROM mnt_member) AND roleName='查看者')");
		final Query selectString1 = this.getSession().createSQLQuery(selectsql1.toString());
		selectString1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Object>> mntMemberMap1 = (ArrayList<Map<String, Object>>) selectString1
				.list();
		ArrayList<MntMember> mntMemberList1 = new ArrayList<MntMember>();
		for (Map<String, Object> maptemp : mntMemberMap1) {
			MntMember mntMember = new MntMember();
			mntMember.setId(Integer.parseInt(maptemp.get("id").toString()));
			mntMemberList1.add(mntMember);
		}
		return mntMemberList1;
	}
	
	public static void main(String[] args) {
		String mobile = "2,1,";
		String mobiles=mobile.substring(0, mobile.length()-1);
		System.out.println(mobiles);
	}

	public void update_getAllSendMessage(List<SendMessage> list) {
	    Date dateTime=new Date();
	    String mobile=null;
	    for(SendMessage sendMessage:list){
	    	List<SendRealationMobile> listSendRealationMobile=this.getListSendRealtion(sendMessage.getId());
	    	
	    	for(SendRealationMobile realationMobile:listSendRealationMobile){
	    		mobile=(realationMobile.getMobile()+","+mobile);
	    		
	    	}
	    	if(listSendRealationMobile.size()>0){
	    		String mobiles=mobile.substring(0, mobile.length()-1);
		    	if(dateTime.getTime()>=sendMessage.getSendtime().getTime()){
		    		String user="王艳福";
		    		String pass="weibao2004";
		    		String result=SendMessageUtil.SendMsg(user, pass, sendMessage.getContent(),mobiles);
		    		String  codeAndMessage[]=result.split("\\|");
		    		// 0是失败的情况
		    		if(codeAndMessage[0].equals("0")){
		    			sendMessage.setStatus(1);
		    			sendMessage.setReason(codeAndMessage[1]);
		    			this.getSession().update(sendMessage);
		    		}else if(codeAndMessage[0].equals("1")){//成功的情况
		    			sendMessage.setStatus(2);
		    			sendMessage.setReason("");
		    			sendMessage.setNum(Integer.valueOf(codeAndMessage[1]));
		    			this.getSession().update(sendMessage);
		    		}
		    	}

	    	}
	    	
	    	
	    }
	}
	
	private List<SendRealationMobile> getListSendRealtion(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from mnt_sendrealationmoblie where sendId= "+id);
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(SendRealationMobile.class);
	    List<SendRealationMobile> list=query.list();
	    return list;
	}

	public static HttpSession currentSession(HttpServletRequest request) {    
	    HttpSession session = localSession.get();  
	    if (session == null) {  
	        localSession.set(request.getSession(true));  
	    }  
	    return localSession.get();  
	  
	}

	@Override
	public List<SendMessage> getAllSendMessage() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from mnt_sendmessage where status<>2 ");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(SendMessage.class);
	    List<SendMessage> list=query.list();
	    return list;
	}  
}
