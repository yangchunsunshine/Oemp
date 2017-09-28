package com.wb.component.computer.sendMessageManager.service.imp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import util.PageModel;
import util.SendMessageUtil;

import com.wb.component.computer.sendMessageManager.service.ISendMessageService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.model.entity.computer.SendMessage;
import com.wb.model.entity.computer.SendRealationMobile;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;

@Service
public class SendMessageService extends BaseDao implements ISendMessageService {

	@Override
	public PageUtil getAllOrganization(Integer id, PageInfo info) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  DISTINCT IFNULL(org.ID,");
		sql.append(" '') AS ORGID,");
		sql.append(" IFNULL(cus.ID,");
		sql.append(" '') AS CUSID,");
		sql.append(" IFNULL(org. NAME,");
		sql.append(" '') AS ORGNAME,");
		sql.append(" cus.androidDeviceToken,");
		sql.append("  cus.iosDeviceToken ,cus.mobile");
		sql.append("   FROM mnt_customContract con LEFT JOIN ");
		sql.append("   mnt_customInfo cus ");
		sql.append("   ON con.cusId = cus.id ");
		sql.append("   LEFT JOIN biz_organization org   ON  org.mntCustomId = cus.id  ");
		sql.append("   LEFT JOIN biz_member mem ON org.ownerId = mem.ID  ");
		sql.append("    LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id  ");
		sql.append("    LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId ");
		sql.append("     WHERE mng.state = 1 AND org.Enable = 1  and mng.mntMemberId="+id);
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}

	@Override
	public int getNumCount(Integer id) {
		int num=0;
		StringBuffer sql=new  StringBuffer();
		sql.append("SELECT COUNT(*) as num FROM ( ");
		sql.append("SELECT  DISTINCT IFNULL(org.ID,");
		sql.append(" '') AS ORGID,");
		sql.append(" IFNULL(cus.ID,");
		sql.append(" '') AS CUSID,");
		sql.append(" IFNULL(org. NAME,");
		sql.append(" '') AS ORGNAME,");
		sql.append(" cus.androidDeviceToken,");
		sql.append("  cus.iosDeviceToken ,cus.mobile");
		sql.append("   FROM mnt_customContract con LEFT JOIN ");
		sql.append("   mnt_customInfo cus ");
		sql.append("   ON con.cusId = cus.id ");
		sql.append("   LEFT JOIN biz_organization org   ON  org.mntCustomId = cus.id  ");
		sql.append("   LEFT JOIN biz_member mem ON org.ownerId = mem.ID  ");
		sql.append("    LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id  ");
		sql.append("    LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId ");
		sql.append("     WHERE mng.state = 1 AND org.Enable = 1  and mng.mntMemberId="+id);
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo ) x ");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
       	 if(map.get("num")==null){
       		 break;
       	 }
            num = Integer.parseInt(map.get("num").toString());
        }
		return num;
	}

	@Override
	public PageUtil getAllOrganizationByChose(Integer id, PageInfo info,
			String orgName) {
		if(orgName.trim().equals("")){
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT  DISTINCT IFNULL(org.ID,");
			sql.append(" '') AS ORGID,");
			sql.append(" IFNULL(cus.ID,");
			sql.append(" '') AS CUSID,");
			sql.append(" IFNULL(org. NAME,");
			sql.append(" '') AS ORGNAME,");
			sql.append(" cus.androidDeviceToken,");
			sql.append("  cus.iosDeviceToken ,cus.mobile");
			sql.append("   FROM mnt_customContract con LEFT JOIN ");
			sql.append("   mnt_customInfo cus ");
			sql.append("   ON con.cusId = cus.id ");
			sql.append("   LEFT JOIN biz_organization org   ON  org.mntCustomId = cus.id  ");
			sql.append("   LEFT JOIN biz_member mem ON org.ownerId = mem.ID  ");
			sql.append("    LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id  ");
			sql.append("    LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId ");
			sql.append("     WHERE mng.state = 1 AND org.Enable = 1  and mng.mntMemberId="+id);
			sql.append(" GROUP BY org.id,org.seqCode,con.accNo");
			return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
		}
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from(");
		sql.append(" SELECT  DISTINCT IFNULL(org.ID,");
		sql.append(" '') AS ORGID,");
		sql.append(" IFNULL(cus.ID,");
		sql.append(" '') AS CUSID,");
		sql.append(" IFNULL(org. NAME,");
		sql.append(" '') AS ORGNAME,");
		sql.append(" cus.androidDeviceToken,");
		sql.append("  cus.iosDeviceToken ,cus.mobile");
		sql.append("   FROM mnt_customContract con LEFT JOIN ");
		sql.append("   mnt_customInfo cus ");
		sql.append("   ON con.cusId = cus.id ");
		sql.append("   LEFT JOIN biz_organization org   ON  org.mntCustomId = cus.id  ");
		sql.append("   LEFT JOIN biz_member mem ON org.ownerId = mem.ID  ");
		sql.append("    LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id  ");
		sql.append("    LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId ");
		sql.append("     WHERE mng.state = 1 AND org.Enable = 1  and mng.mntMemberId="+id);
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo )x where x.ORGNAME like '%"+orgName+"%'");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}

	@Override
	public int updateSendMessage(Integer orgId, String topname) {
		List<SendMessage> list=this.getSendMessage(orgId);
		SendMessage message=list.get(0);
		if(message.getOrchange()==1){
			return 1;
		}
		message.setTopname(topname);
		message.setOrchange(1);
		this.getSession().update(message);
		return 2;
	}

	private List<SendMessage> getSendMessage(Integer orgId) {
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT * from mnt_sendmessage where member_id=?");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(SendMessage.class);
        query.setParameter(0, orgId);
		return query.list();
	}

	@Override
	public String add_sendRightNow(String mytext, String array,Integer id,String orgName) {
		
		String[] strs=array.split(",");
		String mobiles="";
		String[] arrMobile= new String[strs.length];
		String[] arrOrgId= new String[strs.length];
		int j=0;
		for(int i=0;i<strs.length;i++){
			String s[]=strs[i].split("\\|");
			arrMobile[j]=s[0];
			arrOrgId[j]=s[1];
			j++;
			if(i==strs.length-1){
				mobiles=mobiles+s[0];
			}else{
				mobiles=mobiles+s[0]+",";
			}
		}

		String user="王艳福";
		String pass="weibao2004";
		String result=SendMessageUtil.SendMsg(user, pass, mytext, mobiles);
		String  codeAndMessage[]=result.split("\\|");
		// 0是失败的情况
		if(codeAndMessage[0].equals("0")){
			return codeAndMessage[1];
		}
		if(codeAndMessage[0].equals("1")){
			SendMessage message=new SendMessage();
			message.setContent(mytext);
			message.setCratetime(new Date());
			message.setMemberId(id);
			message.setOrchange(0);
			message.setSendtime(new Date());
			message.setTopname("理财金服");
			message.setStatus(2);
//			message.setTelephone(array);
			message.setNum(Integer.valueOf(codeAndMessage[1]));
			message.setSmsstatus(0);// 0是立即发送
			this.getSession().save(message);
			if(arrMobile.length>0){
				for(int i=0;i<arrMobile.length;i++){
					BizOrganization bizOrganization=(BizOrganization)this.getSession().get(BizOrganization.class, Integer.valueOf(arrOrgId[i]));
					SendRealationMobile sendMobile=new SendRealationMobile();
					sendMobile.setSendId(message.getId());
					sendMobile.setMobile(arrMobile[i]);
					sendMobile.setOrgId(Integer.valueOf(arrOrgId[i]));
					sendMobile.setOrgName(bizOrganization.getName());
					sendMobile.setCreateDate(new Date());
					this.save(sendMobile);
				}
			}
		}				    
		return "success";
	}

	@Override
	public int savewaitSendMessage(String mytext, String array, String dateTime,Integer id) {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sendtime=null;
		try {
			sendtime = df.parse(dateTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}		
		
		
		String[] strs=array.split(",");
		String mobiles="";
		String[] arrMobile= new String[strs.length];
		String[] arrOrgId= new String[strs.length];
		int j=0;
		for(int i=0;i<strs.length;i++){
			String s[]=strs[i].split("\\|");
			arrMobile[j]=s[0];
			arrOrgId[j]=s[1];
			j++;
			if(i==strs.length-1){
				mobiles=mobiles+s[0];
			}else{
				mobiles=mobiles+s[0]+",";
				}
			}
				SendMessage message=new SendMessage();
				message.setContent(mytext);
				message.setCratetime(new Date());
				message.setMemberId(id);
				message.setOrchange(0);
				message.setSendtime(sendtime);
				message.setTopname("理财金服");
				message.setStatus(0);// 未发送
				//message.setTelephone(array);
				//message.setNum(num);// 发送短信数量
				message.setSmsstatus(1);// 定时发送
				this.getSession().save(message);
				if(arrMobile.length>0){
					for(int i=0;i<arrMobile.length;i++){
						BizOrganization bizOrganization=(BizOrganization)this.getSession().get(BizOrganization.class, Integer.valueOf(arrOrgId[i]));
						SendRealationMobile sendMobile=new SendRealationMobile();
						sendMobile.setSendId(message.getId());
						sendMobile.setMobile(arrMobile[i]);
						sendMobile.setOrgId(Integer.valueOf(arrOrgId[i]));
						sendMobile.setOrgName(bizOrganization.getName());
						sendMobile.setCreateDate(new Date());
						this.save(sendMobile);
					}
				}
			
		return 1;
	}

	@Override
	public List<SendMessage> getAllMessage(Integer id) {
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT * from mnt_sendmessage where member_id=?");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(SendMessage.class);
        query.setParameter(0, id);
        return query.list();
	}

	@Override
	public PageUtil getFenyeResult(Integer id, PageInfo info) {
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT * from mnt_sendmessage where member_id="+id);
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}

	@Override
	public PageModel findList(int pageNo, int pageSize,Integer id) {
		//一共有多少页
		int totalPage=0;
		//总记录数
		int TotalNum=getTotalNum(id);
		if(TotalNum%pageSize==0){
		 totalPage=TotalNum/pageSize;
		}else {
			totalPage=TotalNum/pageSize+1;
			}
		 if(pageNo>totalPage){
			pageNo=totalPage;
			 	}
			if(pageNo<1){
			pageNo=1;
				}
		int begin=(pageNo-1)*pageSize;
        Query query = this.getSession().createQuery("From SendMessage where member_id="+id+" order by sendtime desc");
        query.setFirstResult(begin);
        query.setMaxResults(pageSize);
        PageModel pageModel=new PageModel();
		pageModel.setList(query.list());
		pageModel.setTotalRecord(TotalNum);	
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageNo);
		return pageModel;
	}

	private int getTotalNum(Integer id) {
		int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" from mnt_sendmessage where member_id="+id);
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
        if (num > 0)
        {
            return num;
        }
        return num;
	}

	@Override
	public SendMessage findMessageById(Integer id) {
		SendMessage sendMessage = (com.wb.model.entity.computer.SendMessage) this.getSession().get(SendMessage.class, id);
		List<SendRealationMobile> list=this.getListSendRealtion(sendMessage.getId());
		Map<String,String> map=new HashMap<String,String>();
		for(SendRealationMobile realationMobile:list){
			map.put(realationMobile.getMobile(), realationMobile.getOrgName());
		}
		return sendMessage;
		 
	}
	private List<SendRealationMobile> getListSendRealtion(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from mnt_sendrealationmoblie where sendId= "+id);
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(SendRealationMobile.class);
	    List<SendRealationMobile> list=query.list();
	    return list;
	}

	@Override
	public int updateMessage(SendMessage sendMessage,Integer id) {
		SendMessage message=(SendMessage) this.getSession().get(SendMessage.class, sendMessage.getId());
		message.setContent(sendMessage.getContent());
		this.getSession().update(message);
		return 1;
	}

	@Override
	public Map<String, String> getkeys(Integer id) {
		// TODO Auto-generated method stub
		List<SendRealationMobile> list=this.getListSendRealtion(id);
		Map<String, String> map=new HashMap<String,String>();
		for(SendRealationMobile realationMobile:list){
			if(realationMobile.getMobile()!=null&&!"null".equals(realationMobile.getMobile())){
				map.put(realationMobile.getOrgName(),realationMobile.getMobile());
			}
			
		}
		return map;
	}
	
	public void saveTuiSong(String content,String tel,Integer id){
		SendMessage message=new SendMessage();
		message.setContent(content);
		message.setCratetime(new Date());
		message.setMemberId(id);
		message.setOrchange(0);
		message.setSendtime(new Date());
		message.setTopname("");
		message.setStatus(2);
		message.setNum(5);
		message.setSmsstatus(0);// 0是立即发送
		message.setType(1);
		this.getSession().save(message);
				SendRealationMobile sendMobile=new SendRealationMobile();
				sendMobile.setSendId(message.getId());
				sendMobile.setMobile(tel);
				sendMobile.setOrgId(null);
				sendMobile.setOrgName(null);
				sendMobile.setCreateDate(new Date());
				this.getSession().save(sendMobile);
		
	}

}
