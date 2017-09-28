package com.wb.component.computer.processManage.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.processManage.service.IProcessManageService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntProcessInfoTmp;
import com.wb.model.entity.computer.processManage.MntNodeInfo;
import com.wb.model.entity.computer.processManage.MntOrgProcessHistory;
import com.wb.model.entity.computer.processManage.MntOrgProcessInfo;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

/**
 * 流程管理Service层实现
 * 
 * @author 郑炜
 * @version [版本号, 2016-5-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "processManageService")
public class ProcessManageService extends BaseDao implements IProcessManageService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(ProcessManageService.class);
    
    @Override
    public PageUtil getProcessList(int mngId, String processName, String canUse, PageInfo info)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" pro.id AS proId,pro.mngId AS mngId,");
        sql.append(" pro.processName AS proName,mMem.orgName AS mngName,");
        sql.append(" pro.canUse AS canUse,pro.id AS opera");
        sql.append(" FROM mnt_processInfo pro");
        sql.append(" INNER JOIN mnt_member mMem");
        sql.append(" WHERE pro.mngId = mMem.id");
        sql.append(" AND pro.isDelete = 0 AND pro.mngId = ?");
        if (processName != null && !"".equals(processName))
        {
            sql.append(" AND pro.processName LIKE '%" + processName + "%'");
        }
        if (canUse != null && !"".equals(canUse))
        {
            sql.append(" AND pro.canUse = '" + canUse + "'");
        }
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows(), mngId);
    }
    
    /**
     * 查询所有业务模板
     */
    @Override
	public PageUtil getModelList(int mngId, String canUse, PageInfo info) {
		// TODO Auto-generated method stub
    	final StringBuffer sql = new StringBuffer();
        if("0".equals(canUse)){
        	sql.append(" select * from mnt_processInfoTmp where adminid="+mngId);	
        }else{
        	sql.append(" select * from mnt_processInfoTmp p where ");
        	sql.append(" p.processType='"+canUse+"'"+" and adminid="+mngId);
        }
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}
   
    @Override
    public int saveProcess(int mngId, MntProcessInfo processInfo)
    {
        try
        {
            boolean isRepeat = this.checkProcessNameRepeat(mngId, processInfo.getProcessName());
            if (isRepeat)
            {
                return 2;
            }
            processInfo.setMngId(mngId);
            processInfo.setCanUse(0);
            processInfo.setIsDelete(0);
            processInfo.setStamp(new Date());
            this.save(processInfo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
        return 0;
    }
    
    /***
     * 给相应的业务模板增加节点
     */
    @Override
	public int saveNodes(MntNodeInfo mntNodeInfo,Integer adminId) {
		// TODO Auto-generated method stub
    	try {
    		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
			Pattern p=Pattern.compile(regEx);
			for(char c:mntNodeInfo.getNodeName().toCharArray()){
				Matcher m= p.matcher(String.valueOf(c));
				if(m.matches()){
					return 11;	
				}
			}
    		 int num=this.checkOrHelp(mntNodeInfo,adminId);
     		if(num>0){
     			return 10;
     		}
    		 boolean isRepeat = this.checkNodeNameRepeat(mntNodeInfo,adminId);
    		 int maxNum=this.getmax(mntNodeInfo.getProcessTmpId(),adminId);
    		 
    		 if(isRepeat){
    			 return 2; 
    		 }
    		 if(maxNum==0){
    			 mntNodeInfo.setVersion(0);
        		 mntNodeInfo.setStamp(new Date());
        		 mntNodeInfo.setAdminId(adminId);
        		 mntNodeInfo.setOrderSeq(maxNum+1);
        		 mntNodeInfo.setOrhelp(0);
        		 mntNodeInfo.setBeforeNodeId("-1");
        		 mntNodeInfo.setAfterNodeId("-2");
        		 this.save(mntNodeInfo); 
    		 }
    		 List<MntNodeInfo> list=this.getPrevNodeId(adminId,mntNodeInfo,maxNum);
    		 if(list.size()>0){
    			//上一个对象
    			MntNodeInfo info=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,list.get(0).getId());
    			//下一个对象
    			MntNodeInfo info2=new MntNodeInfo();
    			info2.setVersion(0);
    			info2.setStamp(new Date());
    			info2.setAdminId(adminId);
    			info2.setNodeName(mntNodeInfo.getNodeName());
    			info2.setOrderSeq(maxNum+1);
    			info2.setOrhelp(0);
    			info2.setBeforeNodeId(String.valueOf(info.getId()));
    			info2.setAfterNodeId("-2");
    			info2.setProcessTmpId(mntNodeInfo.getProcessTmpId());
        		this.save(info2);
        		info.setAfterNodeId(String.valueOf(info2.getId()));
        		this.update(info);
    		 }
    		 
		} catch (Exception e) {
			    e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            return 1;
		}
		return 0;
	}

    private List<MntNodeInfo> getPrevNodeId(Integer adminId, MntNodeInfo mntNodeInfo,int max) {
		// TODO Auto-generated method stub
    	StringBuffer sql = new StringBuffer();
        sql.append("SELECT * from mnt_nodeInfo where processTmpId=?");
        sql.append(" and  adminId=?");
        sql.append("  and orderseq=?");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, mntNodeInfo.getProcessTmpId());
        query.setParameter(1, adminId);
        query.setParameter(2, max);
		return query.list();
	}

    private List<MntNodeInfo> getProcessNodeId(Integer adminId, MntNodeInfo mntNodeInfo,int max) {
		// TODO Auto-generated method stub
    	StringBuffer sql = new StringBuffer();
        sql.append("SELECT * from mnt_nodeInfo where processId=?");
        sql.append(" and  adminId=?");
        sql.append("  and orderseq=?");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, mntNodeInfo.getProcessId());
        query.setParameter(1, adminId);
        query.setParameter(2, max);
		return query.list();
	}

    /***
     * 给相应的业务模板增加节点
     */
    @Override
	public int saveProcessNodes(MntNodeInfo mntNodeInfo,Integer adminId) {
		// TODO Auto-generated method stub
    	try {
    		 int num=this.checkProcessOrHelp(mntNodeInfo,adminId);
      		if(num>0){
      			return 10;
      		}
    		
    		boolean isRepeat = this.checkProcessNodeNameRepeat(mntNodeInfo, adminId);
    		int maxNum=this.getProcessNodemax(mntNodeInfo.getProcessId(),adminId);
    		 
    		 if(isRepeat){
    			 return 2; 
    		 }
    		 if(maxNum==0){
    			 mntNodeInfo.setVersion(0);
        		 mntNodeInfo.setStamp(new Date());
        		 mntNodeInfo.setAdminId(adminId);
        		 mntNodeInfo.setOrderSeq(maxNum+1);
        		 mntNodeInfo.setOrhelp(0);
        		 mntNodeInfo.setBeforeNodeId("-1");
        		 mntNodeInfo.setAfterNodeId("-2");
        		 this.save(mntNodeInfo); 
    		 }
    		 List<MntNodeInfo> list=this.getProcessNodeId(adminId,mntNodeInfo,maxNum);
    		 if(list.size()>0){
    			//上一个对象
    			MntNodeInfo info=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,list.get(0).getId());
    			//下一个对象
    			MntNodeInfo info2=new MntNodeInfo();
    			info2.setVersion(0);
    			info2.setStamp(new Date());
    			info2.setAdminId(adminId);
    			info2.setNodeName(mntNodeInfo.getNodeName());
    			info2.setOrderSeq(maxNum+1);
    			info2.setOrhelp(0);
    			info2.setBeforeNodeId(String.valueOf(info.getId()));
    			info2.setAfterNodeId("-2");
    			info2.setProcessId(mntNodeInfo.getProcessId());
        		this.save(info2);
        		info.setAfterNodeId(String.valueOf(info2.getId()));
        		this.update(info);
    		 }
		} catch (Exception e) {
			    e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            return 1;
		}
		return 0;
	}
    
    private int checkOrHelp(MntNodeInfo mntNodeInfo, Integer adminId) {
		// TODO Auto-generated method stub
    	int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE  processTmpId = ? and adminId=? and orhelp=1 ");
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mntNodeInfo.getProcessTmpId());
        query.setParameter(1, adminId);
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
    
    private int checkOrHelpByOrgAndContract(MntNodeInfo mntNodeInfo, Integer adminId) {
		// TODO Auto-generated method stub
    	int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE  processId = ? and adminId=? and orhelp=1 ");
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mntNodeInfo.getProcessId());
        query.setParameter(1, adminId);
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
    private int checkProcessOrHelp(MntNodeInfo mntNodeInfo, Integer adminId) {
		// TODO Auto-generated method stub
    	int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE  processId = ? and adminId=? and orhelp=1 ");
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mntNodeInfo.getProcessTmpId());
        query.setParameter(1, adminId);
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
	private int getmax(Integer processTmpId, Integer adminId) {

    	int num=0;
    	StringBuffer sql = new StringBuffer();
        sql.append(" SELECT max(orderSeq) as num FROM mnt_nodeInfo mn WHERE mn.processTmpId ="+processTmpId);
        sql.append(" and mn.adminId="+adminId);
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
    private int getProcessNodemax(Integer processId, Integer adminId) {
    	int num=0;
    	StringBuffer sql = new StringBuffer();
        sql.append(" SELECT max(orderSeq) as num FROM mnt_nodeInfo mn WHERE mn.processId ="+processId);
        sql.append(" and mn.adminId="+adminId);
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
    
	private boolean checkNodeNameRepeat(MntNodeInfo mntNodeInfo,Integer adminId){
    	 int num = 0;
         StringBuffer sql = new StringBuffer();
         sql.append(" SELECT count(*) AS num");
         sql.append(" FROM mnt_nodeInfo");
         sql.append(" WHERE nodeName= ? and processTmpId = ? and adminId=? and processId is null ");
         Query query = this.getSession().createSQLQuery(sql.toString());
         query.setParameter(0, mntNodeInfo.getNodeName());
         query.setParameter(1, mntNodeInfo.getProcessTmpId());
         query.setParameter(2, adminId);
         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
         List<Map<String, Object>> list = query.list();
         for (Map<String, Object> map : list)
         {
        	 if(map.get("num")==null){
        		 break;
        	 }
             num = Integer.parseInt(map.get("num").toString());
         }
         if (num > 0)
         {
             return true;
         }
    	return false;
    }
    
	private boolean checkProcessNodeNameRepeat(MntNodeInfo mntNodeInfo,Integer adminId){
   	 int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE nodeName= ? and processId = ? and adminId = ?");
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mntNodeInfo.getNodeName());
        query.setParameter(1, mntNodeInfo.getProcessId());
        query.setParameter(2, adminId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
        if (num > 0)
        {
            return true;
        }
   	return false;
   }
    
    @Override
    public boolean checkProcessNameRepeat(int mngId, String processName)
    {
        int num = 0;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_processInfo");
        sql.append(" WHERE mngId=? AND processName = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setParameter(1, processName);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
        if (num > 0)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteProcess(MntProcessInfo processInfo)
    {
        try
        {
            this.deleteByPrimaryKey(MntProcessInfo.class, processInfo.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean saveNode(List<MntNodeInfo> nodes)
    {
        try
        {
            int processId = -1;
            for (MntNodeInfo node : nodes)
            {
                processId = node.getProcessId();
                node.setStamp(new Date());
                this.save(node);
            }
            int version = this.getNodeVersion(processId) + 1;
            for (int i = 0; i < nodes.size(); i++)
            {
                if (i == 0 && nodes.size() != 1)
                {
                    int beforeNodeId = -1;
                    int afterNodeId = nodes.get(i + 1).getId();
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setVersion(version);
                    this.update(nodes.get(i));
                }
                else if (i == (nodes.size() - 1) && nodes.size() != 1)
                {
                    int beforeNodeId = nodes.get(i - 1).getId();
                    int afterNodeId = -2;
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setVersion(version);
                    this.update(nodes.get(i));
                }
                else if (i == 0 && i == (nodes.size() - 1))
                {
                    int beforeNodeId = -1;
                    int afterNodeId = -2;
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setVersion(version);
                    this.update(nodes.get(i));
                }
                else
                {
                    int beforeNodeId = nodes.get(i - 1).getId();
                    int afterNodeId = nodes.get(i + 1).getId();
                    nodes.get(i).setBeforeNodeId(beforeNodeId+"");
                    nodes.get(i).setAfterNodeId(afterNodeId+"");
                    nodes.get(i).setVersion(version);
                    this.update(nodes.get(i));
                }
            }
            MntProcessInfo process = (MntProcessInfo)this.findByPrimaryKey(MntProcessInfo.class, processId);
            process.setCanUse(1);
            this.update(process);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean saveNodeAfter(List<MntNodeInfo> nodes,Integer adminId)
    {	
    	
    	try
        {
//            int processId = -1;
//            for (MntNodeInfo node : nodes)
//            {
//                processId = node.getProcessId();
//                node.setStamp(new Date());
//                this.save(node);
//            }
//            int version = this.getNodeVersion(processId) + 1;
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
                    nodes.get(i).setAdminId(adminId);
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
                    nodes.get(i).setAdminId(adminId);
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
                    nodes.get(i).setAdminId(adminId);
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
                    nodes.get(i).setAdminId(adminId);
                    this.update(nodes.get(i));
                }
            }
//            MntProcessInfo process = (MntProcessInfo)this.findByPrimaryKey(MntProcessInfo.class, processId);
//            process.setCanUse(1);
//            this.update(process);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    	
    	return true;

    }
    
    @Override
    public int getNodeVersion(int processId)
    {
        int version = -1;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT IFNULL(MAX(version),-1) AS version");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE processId = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, processId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            version = Integer.parseInt(map.get("version").toString());
        }
        return version;
    }
    
    @Override
    public List<Map<String, Object>> getUpdateNodes(int processId)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT nodeName,roleId,sr.roleName");
        sql.append(" FROM mnt_nodeInfo mn");
        sql.append(" LEFT JOIN sys_role sr ON mn.roleId = sr.id");
        sql.append(" WHERE mn.processId = ?");
        sql.append(" AND mn.version = (");
        sql.append(" SELECT MAX(temp.version)");
        sql.append(" FROM mnt_nodeInfo temp");
        sql.append(" WHERE temp.processId = ?)");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, processId);
        query.setParameter(1, processId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Override
    public PageUtil getServiceList(int orgId, PageInfo info)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT pro.id AS proId,");
        sql.append(" pro.processName AS proName,");
        sql.append(" IFNULL(orgPro.id,'close') AS orgProId,");
        sql.append(" IFNULL(orgPro.flag,'close') AS flag");
        sql.append(" FROM mnt_processInfo pro");
        sql.append(" INNER JOIN mnt_member mMem ON mMem.id = pro.mngId");
        sql.append(" INNER JOIN mnt_mngandusers mmu ON mmu.mntMemberId = mMem.id AND mmu.state = 1");
        sql.append(" INNER JOIN biz_member bMem ON bMem.ID = mmu.userMemberId");
        sql.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID AND org.enable = 1 AND org.ID = ?");
        sql.append(" LEFT JOIN mnt_orgProcessInfo orgPro ON orgPro.processId = pro.id AND orgPro.orgId = org.ID AND orgPro.flag = 1");
        sql.append(" WHERE pro.canUse = 1 AND pro.isDelete = 0");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows(), orgId);
    }
    
    @Override
    public boolean saveService(MntOrgProcessInfo orgProcessInfo)
    {
        try
        {
            int version = this.getNodeVersion(orgProcessInfo.getProcessId());
            orgProcessInfo.setVersion(version);
            orgProcessInfo.setFlag(1);
            orgProcessInfo.setStamp(new Date());
            this.save(orgProcessInfo);
            int processId = orgProcessInfo.getProcessId();
            int orgId = orgProcessInfo.getOrgId();
            int orgProcessId = orgProcessInfo.getId();
            int currentNodeId = this.getInitNodeId(processId, version);
            MntOrgProcessHistory orgProcessHistory = new MntOrgProcessHistory();
            orgProcessHistory.setOrgId(orgId);
            orgProcessHistory.setOrgProcessId(orgProcessId);
            orgProcessHistory.setCurrentNodeId(currentNodeId);
            orgProcessHistory.setFlag(0);
            orgProcessHistory.setOrderSeq(0);
            orgProcessHistory.setBeginStamp(new Date());
            this.save(orgProcessHistory);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public Integer getInitNodeId(int processId, int version)
    {
        Integer initNodeId = null;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id AS nodeId");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE processId = ?");
        sql.append(" AND version = ?");
        sql.append(" AND beforeNodeId = -1");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, processId);
        query.setParameter(1, version);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            initNodeId = Integer.parseInt(map.get("nodeId").toString());
        }
        return initNodeId;
    }
    
    @Override
    public boolean deleteService(int id)
    {
        try
        {
        	this.deleteByProperty(MntNodeInfo.class, "processId", id+"");
            this.deleteByPrimaryKey(MntProcessInfo.class, id);
            
//            this.deleteByProperty(MntOrgProcessHistory.class, "orgProcessId", Integer.toString(id));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public Map<String, Object> getMainServiceInfo(boolean isAdmin, int memberId, List<Map<String, Object>> roleIdList, int mngId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" orgProHis.id AS ophId,");
        sql.append(" DATE_FORMAT(orgProHis.beginStamp, '%Y-%m-%d') AS stamp,");
        sql.append(" org. NAME AS orgName,");
        sql.append(" node.nodeName");
        sql.append(" FROM mnt_processInfo pro");
        sql.append(" INNER JOIN mnt_nodeInfo node ON node.processId = pro.id");
        sql.append(" INNER JOIN mnt_orgProcessInfo orgPro ON orgPro.processId = pro.id");
        sql.append(" INNER JOIN mnt_orgProcessHistory orgProHis ON orgProHis.orgProcessId = orgPro.id");
        sql.append(" AND orgProHis.currentNodeId = node.id");
        sql.append(" AND orgProHis.flag = 0");
        sql.append(" INNER JOIN biz_organization org ON org.id = orgPro.orgId");
        sql.append(" WHERE currentNodeId<>-2 AND pro.mngId = ?");
        if (!isAdmin)
        {
            String roleId = "'roleId'";
            for (Map<String, Object> map : roleIdList)
            {
                roleId = roleId + "," + map.get("roleID");
            }
            sql.append(" AND (node.roleId IN (" + roleId + ")");
            sql.append(" OR orgProHis.memberId = " + memberId + ")");
        }
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setInteger(0, mngId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        int serTotal = list.size();
        result.put("serList", list);
        result.put("serTotal", serTotal);
        return result;
    }
    
    @Override
    public boolean updateService(int ophId, int creatorId, String creatorName)
    {
        try
        {
            MntOrgProcessHistory oldOph = (MntOrgProcessHistory)this.findByPrimaryKey(MntOrgProcessHistory.class, ophId);
            Map<String, Object> nodeInfo = this.getNextNodeInfo(oldOph.getCurrentNodeId());
            if (Integer.parseInt(nodeInfo.get("nodeId").toString()) == -2)
            {
                oldOph.setFlag(1);
                oldOph.setEndStamp(new Date());
                oldOph.setCreatorId(creatorId);
                oldOph.setCreatorName(creatorName);
                this.update(oldOph);
                int orgProId = oldOph.getOrgProcessId();
                MntOrgProcessInfo orgProcessInfo = (MntOrgProcessInfo)this.findByPrimaryKey(MntOrgProcessInfo.class, orgProId);
                orgProcessInfo.setFlag(0);
                this.update(orgProcessInfo);
                return true;
            }
            oldOph.setFlag(1);
            oldOph.setEndStamp(new Date());
            oldOph.setCreatorId(creatorId);
            oldOph.setCreatorName(creatorName);
            this.update(oldOph);
            MntOrgProcessHistory newOph = new MntOrgProcessHistory();
            newOph.setOrgId(oldOph.getOrgId());
            newOph.setCurrentNodeId(Integer.parseInt(nodeInfo.get("nodeId").toString()));
            newOph.setOrderSeq(Integer.parseInt(nodeInfo.get("orderSeq").toString()));
            newOph.setOrgProcessId(oldOph.getOrgProcessId());
            newOph.setFlag(0);
            newOph.setBeginStamp(new Date());
            this.save(newOph);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public Map<String, Object> getNextNodeInfo(int cNodeId)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT afterNodeId AS nodeId");
        sql.append(" FROM mnt_nodeInfo");
        sql.append(" WHERE id = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, cNodeId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<String, Object> map = (Map<String, Object>)query.uniqueResult();
        int aNodeId = Integer.parseInt(map.get("nodeId").toString());
        if (aNodeId == -2)
        {
            return map;
        }
        final StringBuffer sqlNext = new StringBuffer();
        sqlNext.append(" SELECT id AS nodeId,orderSeq");
        sqlNext.append(" FROM mnt_nodeInfo");
        sqlNext.append(" WHERE id = ?");
        final Query queryNext = this.getSession().createSQLQuery(sqlNext.toString());
        queryNext.setParameter(0, aNodeId);
        queryNext.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<String, Object> mapNext = (Map<String, Object>)queryNext.uniqueResult();
        mapNext.put("nodeId", aNodeId);
        return mapNext;
    }
    
    @Override
    public PageUtil getMoreServiceInfo(boolean isAdmin, int memberId, List<Map<String, Object>> roleIdList, int mngId, String orgName, String stamp, String flag, PageInfo info)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" orgProHis.id AS ophId,");
        sql.append(" DATE_FORMAT(orgProHis.beginStamp, '%Y-%m-%d') AS stamp,");
        sql.append(" org.name AS orgName,");
        sql.append(" pro.processName AS proName,");
        sql.append(" node.nodeName,");
        sql.append(" orgProHis.flag,orgProHis.id AS opera");
        sql.append(" FROM mnt_processInfo pro");
        sql.append(" INNER JOIN mnt_nodeInfo node ON node.processId = pro.id");
        sql.append(" INNER JOIN mnt_orgProcessInfo orgPro ON orgPro.processId = pro.id");
        sql.append(" INNER JOIN mnt_orgProcessHistory orgProHis ON orgProHis.orgProcessId = orgPro.id");
        sql.append(" AND orgProHis.currentNodeId = node.id");
        sql.append(" INNER JOIN biz_organization org ON org.id = orgPro.orgId");
        sql.append(" WHERE pro.mngId = ?");
        if (!isAdmin)
        {
            String roleId = "'roleId'";
            for (Map<String, Object> map : roleIdList)
            {
                roleId = roleId + "," + map.get("roleID");
            }
            sql.append(" AND (node.roleId IN (" + roleId + ")");
            sql.append(" OR orgProHis.memberId = " + memberId + ")");
        }
        if (orgName != null && !"".equals(orgName))
        {
            sql.append(" AND org.name LIKE '%" + orgName + "%'");
        }
        if (stamp != null && !"".equals(stamp))
        {
            sql.append(" AND DATE_FORMAT(orgProHis.beginStamp, '%Y-%m-%d')='" + stamp + "'");
        }
        if (flag != null && !"".equals(flag))
        {
            sql.append(" AND orgProHis.flag ='" + flag + "'");
        }
        sql.append(" ORDER BY pro.id,node.id");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows(), mngId);
    }
    
    @Override
    public List<Map<String, Object>> getOrgProNodeList(int orgProId)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" oph.id AS ophId,");
        sql.append(" oph.orgProcessId AS opId,");
        sql.append(" oph.orgId,");
        sql.append(" oph.currentNodeId AS cNodeId,");
        sql.append(" node.nodeName,");
        sql.append(" oph.orderSeq,");
        sql.append(" oph.flag,");
        sql.append(" DATE_FORMAT(oph.beginStamp, '%Y-%m-%d') AS bStamp,");
        sql.append(" DATE_FORMAT(oph.endStamp, '%Y-%m-%d') AS eStamp,");
        sql.append(" IFNULL(fb.id, 'no') AS fbId,");
        sql.append(" IFNULL(fb.score, '') AS score,");
        sql.append(" IFNULL(fb.detail, '') AS detail");
        sql.append(" FROM mnt_orgProcessHistory oph");
        sql.append(" INNER JOIN mnt_nodeInfo node ON node.id = oph.currentNodeId");
        sql.append(" LEFT JOIN mnt_feedBack fb ON fb.ophId = oph.id");
        sql.append(" WHERE oph.orgProcessId = ?");
        sql.append(" ORDER BY oph.orderSeq");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, orgProId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Override
    public boolean updateServiceMember(String ophId, String memberId)
    {
        try
        {
            MntOrgProcessHistory orgProcessHistory = (MntOrgProcessHistory)this.findByPrimaryKey(MntOrgProcessHistory.class, Integer.parseInt(ophId));
            orgProcessHistory.setMemberId(Integer.parseInt(memberId));
            this.update(orgProcessHistory);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean deleteProcessSoft(MntProcessInfo processInfo)
    {
        try
        {
            MntProcessInfo process = (MntProcessInfo)this.findByPrimaryKey(MntProcessInfo.class, processInfo.getId());
            process.setIsDelete(1);
            this.update(process);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean createDefaultProcess(int mngId, String processName, List<Map<String, Object>> nodeInfo)
    {
        try
        {
            int orderSeq = 0;
            MntProcessInfo processInfo = new MntProcessInfo();
            processInfo.setProcessName(processName);
            this.saveProcess(mngId, processInfo);
            List<MntNodeInfo> nodes = new ArrayList<MntNodeInfo>();
            for (Map<String, Object> map : nodeInfo)
            {
                MntNodeInfo node = new MntNodeInfo();
                node.setNodeName(map.get("nodeName").toString());
                node.setRoleId(Integer.parseInt(map.get("roleId").toString()));
                node.setProcessId(processInfo.getId());
                node.setOrderSeq(orderSeq * 1000);
                nodes.add(node);
                orderSeq++;
            }
            this.saveNode(nodes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    /***
     * 查看该模板下的流程
     */
	@Override
	public PageUtil getModelNodeList(String processTmpId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
        	sql.append(" select * from mnt_nodeInfo p where ");	
        	sql.append(" p.processTmpId='"+processTmpId+"' and processId is null order by p.orderSeq ");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}

	@Override
	public void addNodeInfo(String id) {
		// TODO Auto-generated method stub
		MntNodeInfo nodeInfo=new MntNodeInfo();
		//nodeInfo.set
		
	}

	@Override
	public List<Map<String, Object>> getUpdateNodesAfter(int processTmpId) {
		 final StringBuffer sql = new StringBuffer();
	        sql.append(" SELECT nodeName,processTmpId,id ,orhelp");
	        sql.append(" FROM mnt_nodeInfo mn");
	        sql.append(" WHERE mn.processTmpId = ? and mn.processId is null order by orderSeq ");
	        final Query query = this.getSession().createSQLQuery(sql.toString());
	        query.setParameter(0, processTmpId);
	        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	        return query.list();
	}
	@Override
	public List<Map<String, Object>> getProcessNodesAfter(int processId) {
		 final StringBuffer sql = new StringBuffer();
	        sql.append(" SELECT nodeName,processId,id ,orhelp");
	        sql.append(" FROM mnt_nodeInfo mn");
	        sql.append(" WHERE mn.processId = ? order by orderSeq ");
	        final Query query = this.getSession().createSQLQuery(sql.toString());
	        query.setParameter(0, processId);
	        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	        return query.list();
	}
	
	@Override
	public int deleteNode(Integer id,Integer adminId) {
		 try
	        {
			 MntNodeInfo mntNodeInfo=(MntNodeInfo) this.getSession().get(MntNodeInfo.class, id);
			 int num = 0 ;
			 if(mntNodeInfo.getProcessId()==null||"".equals(mntNodeInfo.getProcessId())){
				 num=this.checkOrHelp(mntNodeInfo,adminId);
			 }else{
				 num=this.checkOrHelpByOrgAndContract(mntNodeInfo,adminId);
			 }
			 
	     	 if(num>0){
	     			return 1;
	     	}
	            final StringBuffer sql = new StringBuffer();
	            sql.append(" DELETE FROM mnt_nodeInfo");
	            sql.append(" WHERE ID = ?");
	            final Query query = this.getSession().createSQLQuery(sql.toString());
	            query.setParameter(0, id);
	            query.executeUpdate();
	            return 3;
	        }
	        catch (Exception e)
	        {	e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            return 2;
	        }
		
	}

	@Override
	public List<Map<String,Object>> getProcessTemp(Integer processTmpId) {
		// TODO Auto-generated method stub
		
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from mnt_processInfoTmp where ");
            sql.append(" ID = ?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, processTmpId);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		    return query.list();
	}

	@Override
	public int updateProcessInfoTemp(MntProcessInfoTmp mntProcessInfoTmp,Integer adminId) {
		try {
			String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
			Pattern p=Pattern.compile(regEx);
			for(char c:mntProcessInfoTmp.getProcessName().toCharArray()){
				Matcher m= p.matcher(String.valueOf(c));
				if(m.matches()){
					return 4;
				}
			}
			boolean flag=checkRepectBusinessTempForUpdate(adminId,"",mntProcessInfoTmp.getProcessName(),mntProcessInfoTmp.getId());
			if(flag){
				return 1;
			}
			StringBuffer sql = new StringBuffer(); 
			sql.append(" update mnt_processInfoTmp set processName=? where id=? ");
			final Query query = this.getSession().createSQLQuery(sql.toString());
	        query.setParameter(0, mntProcessInfoTmp.getProcessName());
	        query.setParameter(1, mntProcessInfoTmp.getId());
	        query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
		}
		return 3;
	}

	@Override
	public PageUtil getHelpNode(int mngId, Integer processTmpId,
			Integer nodeId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT n.* FROM mnt_nodeInfo n WHERE  n.processTmpId ="+processTmpId+" AND n.orhelp=0  ");
        sql.append("  AND n.orderSeq>(SELECT orderSeq FROM mnt_nodeInfo WHERE id ="+nodeId);
        sql.append(" )  ORDER BY orderseq");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}
	@Override
	public PageUtil getHelpNodeInfo(int mngId, Integer processId,
			Integer nodeId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT n.* FROM mnt_nodeInfo n WHERE  n.processId ="+processId+" AND n.orhelp=0  ");
        sql.append("  AND n.orderSeq>(SELECT orderSeq FROM mnt_nodeInfo WHERE id ="+nodeId);
        sql.append(" )  ORDER BY orderseq");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}
	@Override
	public int savegetTogether(String array,Integer processTmpId,Integer nodeId) {
		
		try {
			String[] arr=array.split(",");
			
			
			int[] arrr =new int[arr.length];
			List<Integer> li=new ArrayList<Integer>();
			MntNodeInfo mnnn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,nodeId);
			li.add(mnnn.getOrderSeq());
			for(int i=0;i<arr.length;i++){
				if(!"".equals(arr[i])){
					arrr[i]=Integer.parseInt(arr[i]);
				}
				
			}
			 Arrays.sort(arrr);
			 for(int i:arrr){
				 MntNodeInfo mnnnn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,i);
				 if(mnnnn!=null){
					 li.add(mnnnn.getOrderSeq()); 
				 }
				
			 }
			for(int i=0;i<li.size()-1;i++){
					if ((li.get(i)+1)!=li.get(i+1)){
						//System.out.println((li.get(i)+1)!=li.get(i+1));
						return 1;
					}
			}
			 
			
			//List<Integer> ids=new ArrayList<Integer>();
			List<MntNodeInfo> list=new ArrayList<MntNodeInfo>();
			// 起始节点 谁跟谁协同（3(点三)和4协同）
			MntNodeInfo mn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,nodeId);
			//ids.add(mn.getId());
			String nodeIds="";
			list.add(mn);
			for(String id:arr){
				if(!"".equals(id)){
					MntNodeInfo mntNodeInfo=(MntNodeInfo) this.getSession().get(MntNodeInfo.class, Integer.valueOf(id));
					list.add(mntNodeInfo);
				}
				
				//ids.add(Integer.valueOf(id));
			}
			//取第一个beforeNodeId值
			String beforeNodeId=list.get(0).getBeforeNodeId();
			//取最后一个afterNodeId值
			String afterNodeId=list.get(list.size()-1).getAfterNodeId();
			
			if("-1".equals(beforeNodeId)&&"-2".equals(afterNodeId)){
				String total=array+","+String.valueOf(nodeId);
				for(String s:total.split(",")){
					if(!"".equals(s)){
						MntNodeInfo infoTmp=(MntNodeInfo) this.getSession().get(MntNodeInfo.class, Integer.valueOf(s));
						infoTmp.setAfterNodeId("-2");
						infoTmp.setBeforeNodeId("-1");
						infoTmp.setOrhelp(1);
						this.update(infoTmp);
					}
					
				}
				return 3;
			}
			
			
			MntNodeInfo mntNodeInfo2=null;
			MntNodeInfo mntNodeInfo3=null;
			String[] arrs=null;
			if(beforeNodeId.indexOf(",")>-1){
				 arrs=beforeNodeId.split(","); 
				 mntNodeInfo2=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(arrs[0]));
			}else{
				mntNodeInfo2=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(beforeNodeId));
			}if(afterNodeId.indexOf(",")>-1){
				 arrs=afterNodeId.split(","); 
				 mntNodeInfo3=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(arrs[0]));
			}else{
				mntNodeInfo3=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(afterNodeId));
			}
			list.get(0).setAfterNodeId(afterNodeId);
			list.get(0).setOrhelp(1);
			if(beforeNodeId.equals("-1")){
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId("-1");
					list.get(i+1).setAfterNodeId(afterNodeId);
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
					nodeIds=list.get(i).getId()+","+nodeIds; 
				}
				String nodeIDS=array+","+mn.getId().toString();
				this.update(list.get(0));
				//操作下一个节点
				//List<MntNodeInfo> listNodeInfo=this.getNextNode(processTmpId,nodeId,nodeIDS);
				//if(listNodeInfo.size()>0){
				if(afterNodeId.indexOf(",")>-1){
					for(String s:afterNodeId.split(",")){
						 MntNodeInfo mntNodeInfo6=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(s));
						 mntNodeInfo6.setBeforeNodeId(nodeIDS);
						 this.update(mntNodeInfo6);
					}
				}else{
					mntNodeInfo3.setBeforeNodeId(nodeIDS);
					this.update(mntNodeInfo3);
				}
//				
				//}
				
				
			}
			if(afterNodeId.equals("-2") && mntNodeInfo2.getBeforeNodeId().equals("-1")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId("-2");
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
				//设置上一个节点的afterNodeId
				if(beforeNodeId.indexOf(",")>-1){
					for(String s:beforeNodeId.split(",")){
						 MntNodeInfo mntNodeInfo6=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(s));
						 mntNodeInfo6.setAfterNodeId(nodeIDS);
						 this.update(mntNodeInfo6);
					}
				}
//				
			}
			
			if(!beforeNodeId.equals("-1")&&afterNodeId.equals("-2")&& !mntNodeInfo2.getBeforeNodeId().equals("-1")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId("-2");
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
				//设置上一个节点的afterNodeId
				List<MntNodeInfo> listNodeInfo=this.getPrevNode(processTmpId,nodeId,nodeIDS);
				if(listNodeInfo.size()>0){
					mntNodeInfo2.setAfterNodeId(nodeIDS);
					this.update(mntNodeInfo2);
				}
			}
			
			if(!beforeNodeId.equals("-1")&&!afterNodeId.equals("-2")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId(list.get(list.size()-1).getAfterNodeId());
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
//				mntNodeInfo2.setAfterNodeId(nodeIDS);
				
//				update aa set after=nodeIDS where before=mntNodeInfo2.getBeforeNodeId()
				Map map=new HashMap();
				map.put("afterNodeId", nodeIDS);
				this.update(MntNodeInfo.class, map, " and beforeNodeId='"+mntNodeInfo2.getBeforeNodeId()+"'"+" and processTmpId="+processTmpId);
				//设置上一个节点的afterNodeId
//				List<MntNodeInfo> listNodeInfo=this.getPrevNode(processTmpId,nodeId,nodeIDS);
//				if(listNodeInfo.size()>0){
//					listNodeInfo.get(0).setBeforeNodeId();
//					this.update(mntNodeInfo2);
				//}
//				List<MntNodeInfo> listNodeInfo1=this.getNextNode(processTmpId,nodeId,nodeIDS);
//				if(listNodeInfo1.size()>0){
//					mntNodeInfo3.setBeforeNodeId(nodeIDS);
//					this.update(mntNodeInfo3);
//					mntNodeInfo3.getAfterNodeId();
//					
				Map map1=new HashMap();
				map1.put("beforeNodeId", nodeIDS);
				this.update(MntNodeInfo.class, map1, " and afterNodeId='"+mntNodeInfo3.getAfterNodeId()+"'"+" and processTmpId="+processTmpId);
					
				//}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return 2;
		}

        return 3;
	}

	@Override
	public int saveProcessTogether(String array,Integer processId,Integer nodeId) {
		
		try {
			String[] arr=array.split(",");
			
			
			int[] arrr =new int[arr.length];
			List<Integer> li=new ArrayList<Integer>();
			MntNodeInfo mnnn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,nodeId);
			li.add(mnnn.getOrderSeq());
			for(int i=0;i<arr.length;i++){
				if(!"".equals(arr[i])){
					arrr[i]=Integer.parseInt(arr[i]);
				}
				
			}
			 Arrays.sort(arrr);
			 for(int i:arrr){
				 MntNodeInfo mnnnn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,i);
				 if(mnnnn!=null){
					 li.add(mnnnn.getOrderSeq()); 
				 }
				
			 }
			for(int i=0;i<li.size()-1;i++){
					if ((li.get(i)+1)!=li.get(i+1)){
						//System.out.println((li.get(i)+1)!=li.get(i+1));
						return 1;
					}
			}
			List<MntNodeInfo> list=new ArrayList<MntNodeInfo>();
			// 起始节点 谁跟谁协同（3(点三)和4协同）
			MntNodeInfo mn=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,nodeId);
			String nodeIds="";
			list.add(mn);
			for(String id:arr){
				if(!"".equals(id)){
					MntNodeInfo mntNodeInfo=(MntNodeInfo) this.getSession().get(MntNodeInfo.class, Integer.valueOf(id));
					list.add(mntNodeInfo);
				}
			}
			//取第一个beforeNodeId值
			String beforeNodeId=list.get(0).getBeforeNodeId();
			//取最后一个afterNodeId值
			String afterNodeId=list.get(list.size()-1).getAfterNodeId();
			
			if("-1".equals(beforeNodeId)&&"-2".equals(afterNodeId)){
				String total=array+","+String.valueOf(nodeId);
				for(String s:total.split(",")){
					if(!"".equals(s)){
						MntNodeInfo infoTmp=(MntNodeInfo) this.getSession().get(MntNodeInfo.class, Integer.valueOf(s));
						infoTmp.setAfterNodeId("-2");
						infoTmp.setBeforeNodeId("-1");
						infoTmp.setOrhelp(1);
						this.update(infoTmp);
					}
					
				}
				return 3;
			}
			
			
			MntNodeInfo mntNodeInfo2=null;
			MntNodeInfo mntNodeInfo3=null;
			String[] arrs=null;
			if(beforeNodeId.indexOf(",")>-1){
				 arrs=beforeNodeId.split(","); 
				 mntNodeInfo2=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(arrs[0]));
			}else{
				mntNodeInfo2=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(beforeNodeId));
			}if(afterNodeId.indexOf(",")>-1){
				 arrs=afterNodeId.split(","); 
				 mntNodeInfo3=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(arrs[0]));
			}else{
				mntNodeInfo3=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(afterNodeId));
			}
			list.get(0).setAfterNodeId(afterNodeId);
			list.get(0).setOrhelp(1);
			if(beforeNodeId.equals("-1")){
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId("-1");
					list.get(i+1).setAfterNodeId(afterNodeId);
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
					nodeIds=list.get(i).getId()+","+nodeIds; 
				}
				String nodeIDS=array+","+mn.getId().toString();
				this.update(list.get(0));
				//操作下一个节点
				if(afterNodeId.indexOf(",")>-1){
					for(String s:afterNodeId.split(",")){
						 MntNodeInfo mntNodeInfo6=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(s));
						 mntNodeInfo6.setBeforeNodeId(nodeIDS);
						 this.update(mntNodeInfo6);
					}
				}else{
					mntNodeInfo3.setBeforeNodeId(nodeIDS);
					this.update(mntNodeInfo3);
				}
			}
			if(afterNodeId.equals("-2") && mntNodeInfo2.getBeforeNodeId().equals("-1")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId("-2");
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
				//设置上一个节点的afterNodeId
				if(beforeNodeId.indexOf(",")>-1){
					for(String s:beforeNodeId.split(",")){
						 MntNodeInfo mntNodeInfo6=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,Integer.valueOf(s));
						 mntNodeInfo6.setAfterNodeId(nodeIDS);
						 this.update(mntNodeInfo6);
					}
				}
//				
			}
			
			if(!beforeNodeId.equals("-1")&&afterNodeId.equals("-2")&& !mntNodeInfo2.getBeforeNodeId().equals("-1")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId("-2");
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
				//设置上一个节点的afterNodeId
				List<MntNodeInfo> listNodeInfo=this.getProcessNode(processId,nodeId,nodeIDS);
				if(listNodeInfo.size()>0){
					mntNodeInfo2.setAfterNodeId(nodeIDS);
					this.update(mntNodeInfo2);
				}
			}
			
			if(!beforeNodeId.equals("-1")&&!afterNodeId.equals("-2")){
				String nodeIDS=array+","+mn.getId().toString();
				//最后那个的beforeNodeId改变
				list.get(list.size()-1).setBeforeNodeId(list.get(0).getBeforeNodeId());
				list.get(list.size()-1).setOrhelp(1);
				this.update(list.get(list.size()-1));
				//设置协同的
				for(int i=0;i<list.size()-1;i++){
					list.get(i+1).setBeforeNodeId(list.get(0).getBeforeNodeId());
					list.get(i+1).setAfterNodeId(list.get(list.size()-1).getAfterNodeId());
					list.get(i+1).setOrhelp(1);
					this.update(list.get(i+1));
				}
				
				Map map=new HashMap();
				map.put("afterNodeId", nodeIDS);
				this.update(MntNodeInfo.class, map, " and beforeNodeId='"+mntNodeInfo2.getBeforeNodeId()+"'"+" and processId="+processId);
				Map map1=new HashMap();
				map1.put("beforeNodeId", nodeIDS);
				this.update(MntNodeInfo.class, map1, " and afterNodeId='"+mntNodeInfo3.getAfterNodeId()+"'"+" and processId="+processId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return 2;
		}

        return 3;
	}
	private List<MntNodeInfo> getPrevNode(Integer processTmpId, Integer nodeId,
			String nodeIDS) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT n.* FROM mnt_nodeInfo n WHERE  n.processTmpId =? AND n.orhelp=0 ");
        //sql.append("  AND n.orderSeq>(SELECT orderSeq FROM mnt_nodeInfo WHERE id =?");
        sql.append(" and id not in(?) ORDER BY orderseq desc");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, processTmpId);
        //query.setParameter(1, nodeId);
        query.setParameter(1, nodeIDS);
        //query.setResultTransformer(Transformers.TO_LIST);
	    return query.list();
		
	}
	private List<MntNodeInfo> getProcessNode(Integer processId, Integer nodeId,
			String nodeIDS) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT n.* FROM mnt_nodeInfo n WHERE  n.processId =? AND n.orhelp=0 ");
        //sql.append("  AND n.orderSeq>(SELECT orderSeq FROM mnt_nodeInfo WHERE id =?");
        sql.append(" and id not in(?) ORDER BY orderseq desc");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, processId);
        //query.setParameter(1, nodeId);
        query.setParameter(1, nodeIDS);
        //query.setResultTransformer(Transformers.TO_LIST);
	    return query.list();
		
	}
	private List<MntNodeInfo> getNextNode(Integer processTmpId, Integer nodeId,String nodeIds ) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT n.* FROM mnt_nodeInfo n WHERE  n.processTmpId =? AND n.orhelp=0 ");
        sql.append(" and id not in(?) ORDER BY orderseq asc");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, processTmpId);
        //query.setParameter(1, nodeId);
        query.setParameter(1, nodeIds);
        //query.setResultTransformer(Transformers.TO_LIST);
	    return query.list();
		
	}

	@Override
	public int addUserTempAndNode(String array,int adminId) {
		// 导入之前先判断一下这个人是否还有模板
		try {
			boolean flag = this.findTempByAdminId(adminId);
			if (flag) {
				return 1;
			} else {
				//先查出所有的模板
				StringBuffer sql1 = new StringBuffer();
				sql1.append("select * from mnt_processInfoTmp where adminId is null");
				Query query3 = this.getSession().createSQLQuery(sql1.toString()).addEntity(MntProcessInfoTmp.class);
				List<MntProcessInfoTmp> lis=query3.list();
				List<MntNodeInfo> listNodes=new ArrayList<MntNodeInfo>();
				for(MntProcessInfoTmp infoTmp:lis){
					MntProcessInfoTmp n=new MntProcessInfoTmp();
					n.setAdminId(String.valueOf(adminId));
					n.setCreateDate(new Date());
					n.setProcessName(infoTmp.getProcessName());
					n.setProcessType(infoTmp.getProcessType());
					n.setState(0);
					this.getSession().save(n);
					
					
					
					StringBuffer sql2 = new StringBuffer();
					sql2.append("select * from mnt_nodeInfo where processTmpId="+infoTmp.getId()+" and adminId is null and nodeTmpStatus=1 ");
					Query query4 = this.getSession().createSQLQuery(sql2.toString()).addEntity(MntNodeInfo.class);
					List<MntNodeInfo> li=query4.list();
					boolean asFlag=false;
					int maxNum=1;
					for(MntNodeInfo info:li){
						MntNodeInfo mntNodeInfo=new MntNodeInfo();
						 //int maxNum=this.getmax(info.getProcessTmpId(),adminId);
						 if(asFlag==false){
			    			 mntNodeInfo.setVersion(0);
			        		 mntNodeInfo.setStamp(new Date());
			        		 mntNodeInfo.setAdminId(adminId);
			        		 mntNodeInfo.setOrderSeq(maxNum++);
			        		 mntNodeInfo.setOrhelp(0);
			        		 mntNodeInfo.setBeforeNodeId("-1");
			        		 mntNodeInfo.setAfterNodeId("-2");
			        		 mntNodeInfo.setProcessTmpId(n.getId());
			        		 mntNodeInfo.setNodeName(info.getNodeName());
			        		 listNodes.add(mntNodeInfo);
			        		 this.save(mntNodeInfo);
			        		 asFlag=true;
			        		 continue;
			    		 }
						 //List<MntNodeInfo> list=this.getPrevNodeId(adminId,info,maxNum);
						 if(listNodes.size()>0){
				    			//上一个对象
				    			MntNodeInfo info5=(MntNodeInfo) this.getSession().get(MntNodeInfo.class,listNodes.get(listNodes.size()-1).getId());
				    			//下一个对象
				    			MntNodeInfo info2=new MntNodeInfo();
				    			info2.setVersion(0);
				    			info2.setStamp(new Date());
				    			info2.setAdminId(adminId);
				    			info2.setNodeName(info.getNodeName());
				    			info2.setOrderSeq(maxNum++);
				    			info2.setOrhelp(0);
				    			info2.setBeforeNodeId(String.valueOf(listNodes.get(listNodes.size()-1).getId()));
				    			info2.setAfterNodeId("-2");
				    			info2.setProcessTmpId(n.getId());
				    			listNodes.add(info2);
				        		this.save(info2);
				        		info5.setAfterNodeId(String.valueOf(info2.getId()));
				        		this.update(info5);
				    		 }
						
					}
				}
				
				
//				
//				// if("".equals(array)||null==array){
//				StringBuffer sql = new StringBuffer();
//				sql.append("INSERT  INTO `mnt_processInfoTmp`(`id`,`processName`,`processType`,`createDate`,`state`,`adminId`)");
//				sql.append(" SELECT NULL,`processName`,`processType`,now(),`state`,"
//						+ adminId
//						+ " FROM mnt_processInfoTmp WHERE adminId IS NULL");
//				Query query = this.getSession().createSQLQuery(sql.toString());
//				query.executeUpdate();
//				
//				StringBuffer sqlStr = new StringBuffer();
//				sqlStr.append("INSERT  INTO `mnt_nodeInfo`(`id`,`processId`,`beforeNodeId`,`afterNodeId`,`nodeName`,`roleId`,`orderSeq`,`stamp`,`version`,`departmentId`,`adminId`,`nodeStatus`,`processTmpId`,`orhelp`,nodeTmpStatus)");
//				sqlStr.append(" SELECT NULL,`processId`,`beforeNodeId`,`afterNodeId`,`nodeName`,`roleId`,`orderSeq`,`stamp`,`version`,`departmentId`,`adminId`,`nodeStatus`,`processTmpId`,`orhelp`,0");
//				sqlStr.append(" FROM mnt_nodeInfo WHERE nodeTmpStatus=1");
//				Query query1 = this.getSession().createSQLQuery(
//						sqlStr.toString());
//				query1.executeUpdate();
				// }
				// else{
				// //查询当前用户的剩下的模板
				// Set<String> set=new HashSet<String>();
				// String[] strs=array.split(",");
				// for(String str:strs){
				// if(!"".equals(str)){
				// set.add(str);
				// }
				// }
				// //查询系统模板的信息
				// List<MntProcessInfoTmp>
				// allProcessInfoTmps=this.getAllTempProcess();
				// List<String> alltemps=new ArrayList<String>();
				// for(MntProcessInfoTmp mntProcessInfoTmp:allProcessInfoTmps){
				// alltemps.add(mntProcessInfoTmp.getProcessType());
				// }
				// // 循环系统模板信息（大的） 往set中增加小的
				// for(String str:alltemps){
				// //true 执行insert语句
				// if(set.add(str)){
				// this.insertDeletTemp(str,adminId);
				// }//false 更新操作
				// else{
				// this.updateTemp(str,adminId);
				// }
				// }
				// }
				return 2;
			}

		} catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
	           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 3;
		}
	}

	private boolean findTempByAdminId(int adminId) {
		int num = 0;
        final StringBuffer sql = new StringBuffer();
        sql.append(" select count(*) as num from mnt_processInfoTmp ");
        sql.append(" where adminId=? ");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, adminId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
        if (num > 0)
        {
            return true;
        }
		return false;
	}

	private void updateTemp(String str, int adminId) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
        sql.append("update mnt_processInfoTmp set createDate=now() where adminId="+adminId );
        sql.append( " and processType='"+str+"'");
        Query query = this.getSession().createSQLQuery(sql.toString()); 
        query.executeUpdate();
		
	}

	private void insertDeletTemp(String str,Integer adminId) {
		StringBuffer sql=new StringBuffer();
        sql.append("INSERT  INTO `mnt_processInfoTmp`(`id`,`processName`,`processType`,`createDate`,`state`,`adminId`)");
        sql.append(" SELECT NULL,`processName`,`processType`,now(),`state`,"+adminId+" FROM mnt_processInfoTmp WHERE adminId IS NULL and processType='"+str+"'");
        Query query = this.getSession().createSQLQuery(sql.toString()); 
        query.executeUpdate();
		// TODO Auto-generated method stub
	}

	private List<MntProcessInfoTmp> getAllTempProcess() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT n.* FROM mnt_processInfoTmp n WHERE  n.adminId is null ");
		Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntProcessInfoTmp.class);
		//query.setParameter(0, processTempId);
		return query.list();
		
	}

	@Override
	public boolean deleteNodetemp(Integer id,Integer adminId) {
		// TODO Auto-generated method stub
		try {
			MntProcessInfoTmp mntProcessInfoTmp=(MntProcessInfoTmp) this.getSession().get(MntProcessInfoTmp.class, id);
			if(mntProcessInfoTmp!=null){
				this.getSession().delete(mntProcessInfoTmp);
			}
			// 删除这个人模板下的节点
			List<MntNodeInfo> listnode=this.getAllNodeByAdmin(adminId,mntProcessInfoTmp.getId());
			for(MntNodeInfo info :listnode){
				this.getSession().delete(info);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
		}
		
		return true;
	}
	
	private List<MntNodeInfo> getAllNodeByAdmin(Integer adminId, Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from mnt_nodeInfo where adminId=?");
        sql.append(" and processTmpId=?");
        Query query = this.getSession().createSQLQuery(sql.toString()).addEntity(MntNodeInfo.class);
        query.setParameter(0, adminId);
        query.setParameter(1, id);
	    return query.list();
	}

	@Override
	public int saveBusiness(int adminId, String canUse, String buinessName) {
		// TODO Auto-generated method stub
		try {
			String buinessName1=buinessName.trim();
			String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
			Pattern p=Pattern.compile(regEx);
			for(char c:buinessName1.toCharArray()){
				Matcher m= p.matcher(String.valueOf(c));
				if(m.matches()){
					return 4;	
				}
			}
			
			boolean flag=this.checkRepectBusinessTemp(adminId, canUse,buinessName1);
			if(flag){
				return 3;
			}
			MntProcessInfoTmp mntProcessInfoTmp=new MntProcessInfoTmp();
			mntProcessInfoTmp.setCreateDate(new Date());
			mntProcessInfoTmp.setProcessName(buinessName1);
			mntProcessInfoTmp.setProcessType(canUse);
			mntProcessInfoTmp.setState(0);
			mntProcessInfoTmp.setAdminId(String.valueOf(adminId));
			this.save(mntProcessInfoTmp);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return 2;
            
		}
	}

	private boolean checkRepectBusinessTemp(int adminId, String canUse,
			String buinessName) {
		// TODO Auto-generated method stub
		int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" from mnt_processInfoTmp where adminId="+adminId);
        sql.append(" and processName = ?");
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, buinessName);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
        if (num > 0)
        {
            return true;
        }
        return false;
	}
    
	private boolean checkRepectBusinessTempForUpdate(int adminId, String canUse,
			String buinessName,int id) {
		// TODO Auto-generated method stub
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from mnt_processInfoTmp where adminId="+adminId);
		sql.append(" and processName = ? and id <> ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, buinessName);
		query.setParameter(1, id);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}
	

	@Override
	public boolean updateProcessInfo(Integer orgId,Integer processId){
		Map map=new HashMap();
		map.put("canUse", "1");
		StringBuffer buff=new StringBuffer();
		buff.append(" and mngId="+orgId);
		buff.append(" and id="+processId);
		int i=this.update(MntProcessInfo.class, map, buff.toString());
		if(i>=1){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public MntProcessInfo getProcessInfo(Integer id) {
		// TODO Auto-generated method stub
		return (MntProcessInfo)this.findByPrimaryKey(MntProcessInfo.class, id);
	}
	
	@Override
	public boolean updateProcessInfoByContract(Integer contractId,Integer processId){
		Map map=new HashMap();
		map.put("canUse", "1");
		StringBuffer buff=new StringBuffer();
		buff.append(" and cusContractId="+contractId);
		buff.append(" and id="+processId);
		int i=this.update(MntProcessInfo.class, map, buff.toString());
		if(i>=1){
			return true;
		}else{
			return false;
		}
	}
	
}
