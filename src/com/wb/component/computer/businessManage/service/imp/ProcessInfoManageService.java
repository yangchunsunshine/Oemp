package com.wb.component.computer.businessManage.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.businessManage.service.IProcessInfoManageService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.Cooperation;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.Mnt_resumes;
import com.wb.model.entity.computer.processManage.MntNodeInfo;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

/**
 * 流程管理Service层实现
 * 
 * @author 郑炜
 * @version [版本号, 2016-5-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "processInfoManageService")
public class ProcessInfoManageService extends BaseDao implements
		IProcessInfoManageService {
	/**
	 * 日志服务
	 */
	private static final Logger log = Logger
			.getLogger(ProcessInfoManageService.class);

	/*
	 * 获取流程列表 (non-Javadoc)
	 * 
	 * @see
	 * com.wb.component.computer.businessManage.service.IProcessInfoManageService
	 * #getProcessList(int, java.lang.String, java.lang.String,
	 * java.lang.String, com.wb.framework.commonUtil.PageUtil.PageInfo)
	 */
	@Override
	public PageUtil getProcessList(int mngId, String processName,
			String cusName, String conType,String contractType ,boolean isAdmin,int adminId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		if(isAdmin){ //管理员查询
			sql.append(" select a.* from (");
			sql.append(" SELECT");
			sql.append(" pro.id AS proId,pro.mngId AS orgId,");
			sql.append(" pro.processName AS processName,");
			sql.append(" pro.processType AS contractType,");
			sql.append(" pro.canUse AS canUse,pro.id AS opera ,");
			sql.append(" contract.name AS orgName ,") ;
			sql.append(" 'yes' as isAdmin ");
			
			sql.append(" FROM mnt_processInfo pro");
			sql.append(" LEFT JOIN biz_organization contract on pro.mngId = contract.id"); // 关联合同
			//sql.append(" LEFT JOIN mnt_member mMem on pro.mngId = mMem.id"); //关联所属公司
			sql.append(" WHERE ");
			sql.append(" pro.isDelete = 0 ");
			sql.append(" AND pro.adminId ='"+adminId+"'");
			if (processName != null && !"".equals(processName)) {
				sql.append(" AND pro.processName LIKE '%" + processName + "%'");
			}
			if (cusName != null && !"".equals(cusName)) {
				sql.append(" AND contract.name LIKE '%" + cusName + "%'");//客户名称
			}

			if (conType != null && !"".equals(conType) && !"-1".equals(conType)) {
				sql.append(" AND pro.processType ='" + conType + "'");
			}
			if (contractType != null && !"".equals(contractType) && !"-1".equals(contractType)) {
				if("1".equals(contractType)){
					sql.append(" AND pro.cusContractId <> 0 ");
				}else{
					sql.append(" AND pro.cusContractId = 0");
				}
				
			}
			
			sql.append("union ");

			sql.append(" SELECT");
			sql.append(" pro.id AS proId,pro.mngId AS orgId,");
			sql.append(" pro.processName AS processName,");
			sql.append(" pro.processType AS contractType,");
			sql.append(" pro.canUse AS canUse,pro.id AS opera ,");
			sql.append(" contract.name AS orgName,") ;
			sql.append(" 'no' as isAdmin ");
			sql.append(" FROM mnt_processInfo pro");
			sql.append(" INNER JOIN biz_organization contract"); // 关联公司
			sql.append(" WHERE pro.mngId = contract.id");
			sql.append(" AND pro.isDelete = 0 ");
			sql.append(" AND pro.id in(SELECT processId FROM mnt_nodeInfo node WHERE node.adminId = '"+adminId+"'  GROUP BY processId)");
			if (processName != null && !"".equals(processName)) {
				sql.append(" AND pro.processName LIKE '%" + processName + "%'");
			}
			if (cusName != null && !"".equals(cusName)) {
				sql.append(" AND contract.name LIKE '%" + cusName + "%'");//客户名称
			}

			if (conType != null && !"".equals(conType) && !"-1".equals(conType)) {
				sql.append(" AND pro.processType ='" + conType + "'");
			}
			if (contractType != null && !"".equals(contractType) && !"-1".equals(contractType)) {
				if("1".equals(contractType)){
					sql.append(" AND pro.cusContractId <> 0 ");
				}else{
					sql.append(" AND pro.cusContractId = 0");
				}
				
			}
			sql.append(" )a ");
			sql.append(" GROUP BY a.proId  ");
			sql.append(" order by a.proId desc ");
			return this.findPageBySqlQuery(sql.toString(), info.getPage(),
					info.getRows());
		}else{//非管理员查询
			//根据用户id查询所有环节 的流程数据
			sql.append(" SELECT");
			sql.append(" pro.id AS proId,pro.mngId AS orgId,");
			sql.append(" pro.processName AS processName,");
			sql.append(" pro.processType AS contractType,");
			sql.append(" pro.canUse AS canUse,pro.id AS opera ,");
			sql.append(" contract.name AS orgName") ;
			sql.append(" FROM mnt_processInfo pro");
			sql.append(" INNER JOIN biz_organization contract"); // 关联公司
			sql.append(" WHERE pro.mngId = contract.id");
			sql.append(" AND pro.isDelete = 0 ");
			sql.append(" AND pro.id in(SELECT processId FROM mnt_nodeInfo node WHERE node.adminId = '"+adminId+"'  GROUP BY processId)");
			if (processName != null && !"".equals(processName)) {
				sql.append(" AND pro.processName LIKE '%" + processName + "%'");
			}
			if (cusName != null && !"".equals(cusName)) {
				sql.append(" AND contract.name LIKE '%" + cusName + "%'");//客户名称
			}

			if (conType != null && !"".equals(conType) && !"-1".equals(conType)) {
				sql.append(" AND pro.processType ='" + conType + "'");
			}
			if (contractType != null && !"".equals(contractType) && !"-1".equals(contractType)) {
				if("1".equals(contractType)){
					sql.append(" AND pro.cusContractId <> 0 ");
				}else{
					sql.append(" AND pro.cusContractId = 0");
				}
				
			}
			
			sql.append(" order by pro.id desc ");
			return this.findPageBySqlQuery(sql.toString(), info.getPage(),
					info.getRows());
		}
		
	}

	/**
	 * 获取环节列表
	 */
	@Override
	public PageUtil getNodeList(String processId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select node.id as nodeId,node.processId as processIds,node.nodeName,node.nodeStatus,node.orderSeq ,node.beforeNodeId,node.afterNodeId,node.orhelp as orHelp ,node.adminId,member.Name from mnt_nodeInfo node,biz_member member ");
		sql.append(" where node.processId = '" + processId + "' ");
		sql.append(" and node.adminId = member.id");
		sql.append(" ORDER BY node.orderSeq ");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	@Override
	public PageUtil getProcessListByOrgId (int orgId, PageInfo info){
		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM mnt_processInfo WHERE cusContractId = 0 and mngId="+orgId+" order by stamp");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	
	/*
	 * 更新流程环节状态
	 */

	@Override
	public int updateNode(String processId, String nodeId, String status) {
		try {

			final StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_nodeInfo ");
			sql.append(" SET nodeStatus=?");
			sql.append(" WHERE processId=?");
			sql.append(" and id=?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setParameter(0, status);
			query.setParameter(1, processId);
			query.setParameter(2, nodeId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	

	// 根据流程id和环节id查询协同人员和下个处理人
	@Override
	public PageUtil getDealNodeMan(String processId,String NodeId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select node.beforeNodeId as beforeNodeId,node.afterNodeId as afterNodeId from mnt_nodeInfo node");
		sql.append("  where node.processId = '" + processId + "' and id ='"+NodeId+"'");
		System.out.println("查询环节sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public PageUtil getBeforeMan(String processId,String nodeId, String beforeNodeId,
			PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select node.adminId ,member.Name,member.Telphone ,mng.mntMemberId from mnt_nodeInfo node left join biz_member member on node.adminId = member.id ");
		sql.append(" LEFT JOIN mnt_mngandusers mng on mng.userMemberId = member.id");
		sql.append(" where  node.processId = '" + processId + "' and beforeNodeId ='"+beforeNodeId+"'");
		sql.append(" and node.id !='"+nodeId+"' group by node.adminId");
		System.out.println("查询协同人员sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public PageUtil getAfterMan(String processId, String afterNodeId,
			PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select node.adminId ,member.Name,member.Telphone  ,mng.mntMemberId from mnt_nodeInfo node left join biz_member member on node.adminId = member.id ");
		sql.append(" LEFT JOIN mnt_mngandusers mng on mng.userMemberId = member.id ");
		sql.append(" where node.adminId = member.id and node.processId = '" + processId + "' and node.id ='"+afterNodeId+"' group by node.adminId");
		System.out.println("查询下个执行人sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	
	//获取部门下的环节列表
	@Override
	public PageUtil getDepartmentNodeList(String processId,
			String departmentId,boolean isAdmin,int adminId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select node.id as nodeId,node.processId as processIds,node.nodeName,node.nodeStatus,node.orderSeq , ");
		sql.append(" node.adminId ,");
		sql.append(" member.Name, ");
		sql.append(" member.departmentId ");
		sql.append(" from ");
		sql.append(" mnt_nodeInfo node ");
		sql.append(" ,biz_member member ");
		sql.append(" where node.nodeStatus=0 ");
		sql.append(" and node.adminId = member.id ");
		sql.append(" and node.processId = '" + processId + "'");
		//sql.append(" and member.departmentId = '" + departmentId + "'");
		isAdmin = false ;
		if(isAdmin){ //管理员可以指派所有环节		
			
		}else{ //非管理员只能指派自己的环节
			sql.append(" and node.adminId = '"+adminId+"'");
		}
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	
	@Override
	public PageUtil getDepartmentUserList(String departmentId,String name , String tel,boolean isAdmin,int adminId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		
		sql.append(" select id,Name,Telphone,qq ,Email,sex ");
		sql.append(" from ");
		sql.append(" biz_member member ");
		sql.append(" where member.Enable=1 ");//必须是有效用户
		sql.append(" and member.departmentId = '" + departmentId + "'");
		if(name!=null&&!"".equals(name)){
			sql.append(" and member.name = '" + name + "'");
		}
		if(tel!=null&&!"".equals(tel)){
			sql.append(" and member.telphone = '" + tel + "'");
		}
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	//重新指派环节
	@Override
	public int updateReAppoint(String processId, String nodeId, String id) {
		try {

			final StringBuffer sql = new StringBuffer();
			
			sql.append(" UPDATE mnt_nodeInfo ");
			sql.append(" SET adminId=?");
			sql.append(" WHERE processId=?");
			sql.append(" and id=?");
			
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setParameter(0, id);
			query.setParameter(1, processId);
			query.setParameter(2, nodeId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	@Override
	public PageUtil getMemberInfo(String id, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select member.id ,member.Name,member.Telphone ,mng.mntMemberId from biz_member member  LEFT JOIN mnt_mngandusers mng on mng.userMemberId = member.id ");
		sql.append("  where mng.state=1 and  member.id  = '" + id + "'");
		System.out.println("查询biz_member 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	
	@Override
	public PageUtil getMemberInfoByTel(String tel, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select member.id ,member.Name,member.Telphone from biz_member member");
		sql.append("  where  member.Telphone  = '" + tel + "'");
		System.out.println("查询biz_member 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public PageUtil getContractInfo(String contractId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select id,(select id from biz_organization where mntCustomId=cusId) cusId,(select seqCode from biz_organization where mntCustomId=cusId) seqCode,cusName,contractType from mnt_customContract where ");
		sql.append(" id='"+contractId+"'");
		System.out.println("查询mnt_customContract 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public PageUtil getProcessInfoTmp(String processId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select processName,processType from mnt_processInfoTmp where ");
		sql.append(" id='"+processId+"'");
		System.out.println("查询mnt_processInfoTmp 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	@Override
	public void saveProcessInfo(MntProcessInfo processInfo) {
		this.save(processInfo);
	}
	@Override
	public void updateProcessInfo(MntProcessInfo processInfo) {
		this.update(processInfo);
	}
	
	
	
	@Override
	public PageUtil getProcessInfo(String processName,String contractId,String processType,String adminId,PageInfo info){
		final StringBuffer sql = new StringBuffer();
		sql.append(" select id from mnt_processInfo where ");
		sql.append(" processName='"+processName+"'");
		sql.append(" and cusContractId='"+contractId+"'");
		sql.append(" and processType='"+processType+"'");
		sql.append(" and adminId='"+adminId+"'");
		sql.append(" order by stamp desc ");
		System.out.println("查询mnt_processInfoTmp 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}
	
	
	@Override
	public int addProcessInfo(String processIdTemp,String mngId,String cusContractId,String adminId) {
		try {
			if("".equals(mngId)){
				mngId = null ;
			}
			StringBuffer sql=new StringBuffer();
	        sql.append("INSERT  INTO `mnt_processInfo`(`id`,`processName`,`mngId`,`canUse`,`stamp`,`isDelete`,`cusContractId`,`processType`,`adminId`)");
	        sql.append(" SELECT NULL,processName,"+mngId+",0,now(),0,"+cusContractId+",processType,"+adminId+"  FROM mnt_processInfoTmp WHERE adminId IS NOT NULL and id='"+processIdTemp+"' ");
	        Query query = this.getSession().createSQLQuery(sql.toString()); 
	        query.executeUpdate();
	        
	     
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}
	
	
	@Override
	public int addNodeInfo(String processIdTemp,String mngId,String processId,String adminId) {
		try {
			if("".equals(mngId)){
				mngId = null ;
			}
			
	        StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append("INSERT  INTO `mnt_nodeInfo`(`id`,`processId`,`beforeNodeId`,`afterNodeId`,`nodeName`,`roleId`,`orderSeq`,`stamp`,`version`,`departmentId`,`adminId`,`nodeStatus`,`processTmpId`,`orhelp`,nodeTmpStatus)");
	        sqlStr.append(" SELECT NULL,"+processId+",`beforeNodeId`,`afterNodeId`,`nodeName`,`roleId`,`orderSeq`,`stamp`,`version`,`departmentId`,"+adminId+",`nodeStatus`,`processTmpId`,`orhelp`,0");
	        sqlStr.append(" FROM mnt_nodeInfo WHERE   processTmpId='"+processIdTemp+"' and adminId = '"+adminId+"' and nodeTmpStatus is null ");
	        Query query1 = this.getSession().createSQLQuery(sqlStr.toString()); 
	        query1.executeUpdate();
	        List<MntNodeInfo> nodes=this.getNodeByProcessId(processId,adminId);
	        for (int i = 0; i < nodes.size(); i++)
            {	//第一个（多个）
                if (i == 0 && nodes.size() != 1)
                {
                    int beforeNodeId = -1;
                    int afterNodeId = nodes.get(i + 1).getId();
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setOrderSeq(i+1);
                    nodes.get(i).setStamp(new Date());
                    nodes.get(i).setOrhelp(0);
                    nodes.get(i).setAdminId(Integer.valueOf(adminId));
                    this.update(nodes.get(i));
                }
                //最后一个
                else if (i == (nodes.size() - 1) && nodes.size() != 1)
                {
                    int beforeNodeId = nodes.get(i - 1).getId();
                    int afterNodeId = -2;
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setOrderSeq(i+1);
                    nodes.get(i).setStamp(new Date());
                    nodes.get(i).setOrhelp(0);
                    nodes.get(i).setAdminId(Integer.valueOf(adminId));
                    this.update(nodes.get(i));
                }
                // 就一条数据
                else if (i == 0 && i == (nodes.size() - 1))
                {
                    int beforeNodeId = -1;
                    int afterNodeId = -2;
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setOrderSeq(i+1);
                    nodes.get(i).setStamp(new Date());
                    nodes.get(i).setOrhelp(0);
                    nodes.get(i).setAdminId(Integer.valueOf(adminId));
                    this.update(nodes.get(i));
                }
                else
                {
                    int beforeNodeId = nodes.get(i - 1).getId();
                    int afterNodeId = nodes.get(i + 1).getId();
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setStamp(new Date());
                    nodes.get(i).setOrderSeq(i+1);
                    nodes.get(i).setOrhelp(0);
                    nodes.get(i).setAdminId(Integer.valueOf(adminId));
                    this.update(nodes.get(i));
                }
            }
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	private List<MntNodeInfo> getNodeByProcessId(String processId, String adminId) {
		// TODO Auto-generated method stub
        StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("SELECT * from mnt_nodeInfo n where n.processId =?");
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, processId);
        return query.list();
		
	}

	@Override
	public PageUtil getProcessInfoById(String processId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" select processName,processType from mnt_processInfo where ");
		sql.append(" id='"+processId+"'");
		System.out.println("查询mnt_processInfo 的sql："+sql.toString());
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());	}

	@Override
	public int deleteNode(String processId) {
		try {
			
	       
	        StringBuffer sqlStr2=new StringBuffer();
	        sqlStr2.append(" delete from mnt_nodeInfo where processId = '"+processId+"'");
	        Query query2 = this.getSession().createSQLQuery(sqlStr2.toString()); 
	        query2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}
	
	@Override
	public int deleteProcess(String processId) {
		try {
			
	        StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append(" delete from mnt_processInfo where id = '"+processId+"'");
	        Query query1 = this.getSession().createSQLQuery(sqlStr.toString()); 
	        query1.executeUpdate();
	        
	       
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}
	
	@Override
	public PageUtil getProcessListByContractId (int contractId, PageInfo info){
		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM mnt_processInfo WHERE cusContractId="+contractId+" order by stamp");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public boolean checkRepectBusinessTemp(Integer id, String processName,String orgId,String contractId,String isOrgAndContract) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from mnt_processInfo where adminId=" + id);
		sql.append(" and processName = ?");
		if("org".equals(isOrgAndContract)){ //公司
			sql.append(" and mngId='"+orgId+"' and cusContractId ='0'");
		}else{ //合同下的
			sql.append(" and mngId='"+orgId+"' and cusContractId ='"+contractId+"'");
		}
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, processName);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list) {
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0) {
			return true;
		}
		return false;
	}


	@Override
	public void saveCooeration(Cooperation cooperation) {
		// TODO Auto-generated method stub
		try {
			this.getSession().save(cooperation);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void saveResume(Mnt_resumes resumes) {
		// TODO Auto-generated method stub
		this.getSession().save(resumes);	
	}


	@Override
	public int addInvestment(String invest_name, String invest_comp,
			String invest_post, String invest_phone, String invest_email,
			String invest_desc) {
		try {

			final StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO `mnt_investment` (id,name,comp,post,phone,email,`desc`) VALUES (NULL,'"+invest_name+"','"+invest_comp+"','"+invest_post+"','"+invest_phone+"','"+invest_email+"','"+invest_desc+"')");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	@Override
	public PageUtil getInvstList(String invest_name, String invest_comp,
			String invest_phone, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT i.id,i.`name`,i.comp,i.post,i.phone,i.email,i.`desc` FROM mnt_investment i where 1=1 ");
		if(invest_name!=null && !"".equals(invest_name)){
			sql.append(" and name='"+invest_name+"'");
		}
		if(invest_comp!=null && !"".equals(invest_comp)){
			sql.append(" and comp='"+invest_comp+"'");
		}
		if(invest_phone!=null && !"".equals(invest_phone)){
			sql.append(" and phone='"+invest_phone+"'");
		}
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public int updateInvst(String id, String invest_name, String invest_comp,
			String invest_post, String invest_phone, String invest_email,
			String invest_desc) {
		try {
			
		       
	        StringBuffer sqlStr2=new StringBuffer();
	        sqlStr2.append(" update mnt_investment set name='"+invest_name+"' ,comp='"+invest_comp+"',post='"+invest_post+"' ,phone='"+invest_phone+"',email='"+invest_email+"',desc='"+invest_desc+"' where id='"+id+"'");
	        Query query2 = this.getSession().createSQLQuery(sqlStr2.toString()); 
	        query2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	
	@Override
	public int deleteInvst(String id) {
		try {
			
	        StringBuffer sqlStr2=new StringBuffer();
	        sqlStr2.append(" delete from mnt_investment  where id='"+id+"'");
	        Query query2 = this.getSession().createSQLQuery(sqlStr2.toString()); 
	        query2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}
	
}
