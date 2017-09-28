/*
 * 文 件 名:  FrameworkManageService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.frameworkManage.service.imp;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.login.util.HttpUtil;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PropertiesReader;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMngandusers;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.entity.computer.frameworkManage.MntDepartmentInfo;
import com.wb.model.pojo.computer.ClerkManagementDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;

/**
 * 组织框架service
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "frameworkManageService")
public class FrameworkManageService extends BaseDao implements IFrameworkManageService
{
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(FrameworkManageService.class);
    
    /**
     *  
     * 重载方法
     * 
     * @param name
     * @param telphone
     * @return
     */
    @Override
    public BizMember findMemberByParams(String name, String telphone)
    {
        final DetachedCriteria criteria = DetachedCriteria.forClass(BizMember.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("telphone", telphone));
        final List<BizMember> list = this.findByCriteria(BizMember.class, criteria);
        if (list.size() > 0)
        {
            return list.get(0);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param memId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public Integer findAssociationByParams(Integer memId, Integer userId, HttpServletRequest request)
    {
        /**
         * 是否处于请求状态
         */
        final DetachedCriteria criteria = DetachedCriteria.forClass(MntMngandusers.class);
        criteria.add(Restrictions.eq("mntMemberId", memId));
        criteria.add(Restrictions.eq("userMemberId", userId));
        criteria.add(Restrictions.or(Restrictions.eq("state", 0), Restrictions.eq("state", 1)));
        final List<MntMngandusers> accList = this.findByCriteria(MntMngandusers.class, criteria);
        /**
         * 是否处于拒绝状态
         */
        final DetachedCriteria criteria1 = DetachedCriteria.forClass(MntMngandusers.class);
        criteria1.add(Restrictions.eq("mntMemberId", memId));
        criteria1.add(Restrictions.eq("userMemberId", userId));
        criteria1.add(Restrictions.eq("state", 2));
        final List<MntMngandusers> cantList = this.findByCriteria(MntMngandusers.class, criteria1);
        /**
         * 已被他人监控
         */
        final DetachedCriteria criteria2 = DetachedCriteria.forClass(MntMngandusers.class);
        criteria2.add(Restrictions.ne("mntMemberId", memId));
        criteria2.add(Restrictions.eq("userMemberId", userId));
        criteria2.add(Restrictions.eq("state", 1));
        final List<MntMngandusers> ortherList = this.findByCriteria(MntMngandusers.class, criteria2);
        if (accList.size() > 0)
        {
            return 1;
        }
        if (ortherList.size() > 0)
        {
            return 2;
        }
        if (cantList.size() > 0)
        {
            return 3;
        }
        else
        {
            return 0;
        }
    }
    
    public void saveMemberIntroduceRoleInit(Integer memId, Integer userId){
    	PropertiesReader reader = PropertiesReader.getInstance();
        String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
    	StringBuilder paramsBuffer = new StringBuilder();
		paramsBuffer.append("mntMemberId=").append(memId).append("&");
		paramsBuffer.append("bizMemberId=").append(userId);
        try {
			//HttpUtil.doFormPost(url+"/auth/sysauth/memberIntroduceRoleInit/put.jspx", paramsBuffer.toString());
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
    }
    /**
     * 重载方法
     * 
     * @param mmu mmu
     */
    @Override
    public void insertClerkAssociation(MntMngandusers mmu)
    {
        this.save(mmu);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param id
     * @return
     */
    @Override
    public MntMngandusers findAssociationById(Integer id)
    {
        return (MntMngandusers)this.findByPrimaryKey(MntMngandusers.class, id);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param mnc
     */
    @Override
    public void insertMsgNotification(MsgNotification mnc)
    {
        this.save(mnc);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param map
     * @param sqlStr
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateClerkState(Map map, String sqlStr)
    {
        this.update(MntMngandusers.class, map, sqlStr);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param map
     * @param sqlStr
     */
    @Override
    public void updateBizMember(Map map, String sqlStr)
    {
        this.update(BizMember.class, map, sqlStr);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param isAdmin 是否是管理员
     * @param orgId 企业ID
     * @param ifHasEmp 是否查询员工
     * @param partId 部门ID
     * @return 企业组织架构树
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findMntFrameWorkInfo(int memberId, boolean isAdmin, String orgId, String ifHasEmp, String partId)
    {
        final StringBuffer sql = new StringBuffer();
        if (isAdmin)
        {
            sql.append(" SELECT 'BY_DEP' AS cmd,t.id AS id,t.idPath AS idPath,t.level AS level,");
            sql.append(" t.partNum AS partNum,t.partName AS partName,t.topId AS pId,");
            sql.append(" t.rootId AS rootId,'true' AS open,t.icon as icon");
            sql.append(" FROM");
            sql.append(" mnt_departmentInfo t WHERE");
            sql.append(" deleteFlag = 0");
            sql.append(" AND t.adminId = 0");
            sql.append(" UNION ALL");
        }
        sql.append(" SELECT 'BY_DEP' AS cmd,t.id AS id,t.idPath AS idPath,t.level AS level,");
        sql.append(" t.partNum AS partNum,t.partName AS partName,t.topId as pId,");
        sql.append(" t.rootId AS rootId,'true' AS open,t.icon AS icon");
        sql.append(" FROM");
        sql.append(" mnt_departmentInfo t WHERE");
        sql.append(" deleteFlag = 0");
        sql.append(" AND t.adminId = ?");
        sql.append(" AND (t.idPath LIKE CONCAT((SELECT idPath FROM mnt_departmentInfo WHERE id = ?), '-%') OR t.idPath = (SELECT idPath FROM mnt_departmentInfo WHERE id = ?))");
        if (ifHasEmp != null && "yes".equals(ifHasEmp))
        {
            sql.append("  UNION ALL");
            sql.append("  SELECT DISTINCT");
            sql.append("      'BY_EMP' AS cmd,bm.id,");
            sql.append("      mdp.idPath AS idPath,");
            sql.append("      mdp.level AS level,");
            sql.append("      mdp.partNum AS partNum,");
            sql.append("      bm.Name AS partName,");
            sql.append("      mdp.id AS pId,");
            sql.append("      mdp.rootId AS rootId,");
            sql.append("      'true' AS open,");
            sql.append("      '/Oemp/plugins/zTree/css/img/diy/5.png' AS icon");
            sql.append("  FROM biz_member bm");
            sql.append("  LEFT JOIN mnt_departmentInfo mdp ON bm.departmentId = mdp.id ");
            sql.append("  INNER JOIN mnt_mngandusers mmu on bm.id = mmu.userMemberId and mmu.mntMemberId = ? and mmu.state = 1 ");
            sql.append("       WHERE bm.departmentId IN");
            sql.append(" (SELECT DISTINCT id FROM mnt_departmentInfo t WHERE t.idPath LIKE CONCAT((select idPath FROM mnt_departmentInfo tt WHERE tt.id = ?), '-%') OR t.idPath = (select idPath FROM mnt_departmentInfo tt WHERE tt.id = ?)) AND bm.departmentId <> '' ");
            if (isAdmin)
            {
                sql.append(" UNION ALL");
                sql.append(" select DISTINCT");
                sql.append(" 'BY_EMP' AS cmd,bm.id,");
                sql.append(" 'x' as idPath,");
                sql.append(" 'x' as level,");
                sql.append(" 'x' as partNum,");
                sql.append(" bm.Name as partName,");
                sql.append(" '0' as pId,");
                sql.append(" '0' as rootId,");
                sql.append(" 'true' as open,");
                sql.append(" '/Oemp/plugins/zTree/css/img/diy/5.png' as icon");
                sql.append(" from biz_member bm");
                sql.append(" inner JOIN mnt_mngandusers mmu on bm.id = mmu.userMemberId and mmu.mntMemberId = ? and (bm.departmentId is null or bm.departmentId = '') and mmu.state = 1 order by partNum,idPath");
                return this.createSqlQuery(sql.toString(), orgId, partId, partId, orgId, partId, partId, orgId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            }
            else
            {
                sql.append(" ORDER BY partNum,idPath");
            }
            return this.createSqlQuery(sql.toString(), orgId, partId, partId, orgId, partId, partId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }
        else
        {
            sql.append(" ORDER BY partNum,idPath");
        }
        return this.createSqlQuery(sql.toString(), orgId, partId, partId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param map
     * @param sqlWhere
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void updateMntMember(Map map, String sqlWhere)
    {
        this.update(MntMember.class, map, sqlWhere);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param tel
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findUserInfo(String tel)
    {
        final String sql = " SELECT * FROM mnt_member WHERE telphone = ? ";
        final Query query = this.getSession().createSQLQuery(sql);
        query.setParameter(0, tel);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>)query.uniqueResult();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param nowId
     * @param toId
     * @param memberIDS
     * @return
     */
    @Override
    public int updateOrgToOrther(int nowId, int toId, String[] memberIDS)
    {
        final StringBuffer sb = new StringBuffer();
        if (memberIDS != null)
        {
            sb.append(" AND userMemberId in ( ");
            for (int i = 0; i < memberIDS.length; i = i + 1)
            {
                sb.append((i == memberIDS.length - 1) ? " ? ) " : " ?, ");
            }
        }
        String sql = " update mnt_mngandusers set mntMemberId = ? where STATE = 1 and mntMemberId = ? " + sb.toString();
        Query query = this.getSession().createSQLQuery(sql);
        query.setInteger(0, toId);
        query.setInteger(1, nowId);
        if (memberIDS != null)
        {
            for (int i = 0; i < memberIDS.length; i = i + 1)
            {
                query.setParameter(2 + i, memberIDS[i]);
            }
        }
        query.executeUpdate();
        sql = " update biz_member set departmentId = 0 and Enable = 0 " + sb.toString().replace("AND", "WHERE").replace("userMemberId", "id");
        query = this.getSession().createSQLQuery(sql);
        if (memberIDS != null)
        {
            for (int i = 0; i < memberIDS.length; i = i + 1)
            {
                query.setParameter(i, Integer.parseInt(memberIDS[i]));
            }
        }
        query.executeUpdate();
        return 0;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param memberId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findControEmpInfo(int memberId)
    {
        final Query query = this.getSession().createSQLQuery(" select bm.id as id,bm.Name as name from mnt_mngandusers mm inner join biz_member bm on mm.userMemberId = bm.ID where mm.mntMemberId = ? and mm.state=1 ");
        query.setInteger(0, memberId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param memberId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findOtherRoleInfo(String tel)
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select bm.id as id,bm.Name as name, '2' as 'userType',mb.orgName from mnt_mngandusers mm inner join biz_member bm on mm.userMemberId = bm.ID JOIN mnt_member mb ON mm.mntMemberId=mb.id  where bm.Telphone=? and mm.state=1 GROUP BY id ");
        sql.append(" UNION ALL ");
        sql.append(" select mt.id as id,mt.userName as name, '1' as 'userType',mt.orgName from mnt_mngandusers mm inner join mnt_member mt on mm.mntMemberId = mt.ID where mt.Telphone=? and mm.state=1 GROUP BY id ");
    	final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setString(0, tel);
        query.setString(1, tel);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ClerkManagementDto> findClerkListByParams(SuperintendentQueryForm form)
    {
        try
        {
            form.setRecords((Long)sqlMapClient.queryForObject("countClerkQueryListForMonitor", form));
            return sqlMapClient.queryForList("findClerkQueryListForMonitor", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
        }
        return null;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param vo
     * @param creater
     * @return
     */
    @Override
    public int addPartInfo(MntDepartmentInfo vo, int creater)
    {
        try
        {
            final Map<String, Object> partNumMap = new HashMap<String, Object>();
            partNumMap.put("partNum", vo.getPartNum());
            partNumMap.put("adminId", creater);
            final int partNumRepeat = this.findByProperties(MntDepartmentInfo.class, partNumMap).size();
            final Map<String, Object> partNameMap = new HashMap<String, Object>();
            partNameMap.put("partName", vo.getPartName());
            partNameMap.put("adminId", creater);
            final int partNameRepeat = this.findByProperties(MntDepartmentInfo.class, partNameMap).size();
            if (partNumRepeat > 0)
            {
                return 3;
            }
            if (partNameRepeat > 0)
            {
                return 4;
            }
            vo.setLevel(vo.getLevel() + 1);
            vo.setAdminId(creater);
            vo.setTopId(Integer.toString(vo.getId()));
            vo.setRootId(Integer.toString(0));
            vo.setIcon("/Oemp/plugins/zTree/css/img/diy/8.png");
            vo.setDeleteFlag(Integer.toString(0));
            vo.setCreateTime(new Date());
            this.save(vo);
            vo.setIdPath(vo.getIdPath() + "-" + vo.getId());
            vo.setUpdateTime(new Date());
            this.update(vo);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
        }
        return 1;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param vo
     * @return
     */
    @Override
    public int deletePartInfo(MntDepartmentInfo vo)
    {
        final String checkPartSql = "select * from mnt_departmentInfo where idPath like '" + vo.getIdPath() + "%'";
        final String checkCusSql = "select * from biz_member where departmentId = '" + vo.getId() + "'";
        final String deleteSql = "delete from mnt_departmentInfo where id=" + vo.getId() + "";
        try
        {
            final Query partQuery = this.getSession().createSQLQuery(checkPartSql);
            final List partList = partQuery.list();
            if (partList.size() > 1)
            {
                return 3;
            }
            final Query cusQuery = this.getSession().createSQLQuery(checkCusSql);
            final List cusList = cusQuery.list();
            if (cusList.size() > 0)
            {
                return 4;
            }
            final Query query = this.getSession().createSQLQuery(deleteSql);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
        }
        return 1;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param vo
     * @return
     */
    @Override
    public int updatePartInfo(MntDepartmentInfo vo, int creater)
    {
        try
        {
            final Map<String, Object> partNumMap = new HashMap<String, Object>();
            partNumMap.put("partNum", vo.getPartNum());
            partNumMap.put("adminId", creater);
            final int partNumRepeat = this.findByProperties(MntDepartmentInfo.class, partNumMap).size();
            final Map<String, Object> partNameMap = new HashMap<String, Object>();
            partNameMap.put("partName", vo.getPartName());
            partNameMap.put("adminId", creater);
            final int partNameRepeat = this.findByProperties(MntDepartmentInfo.class, partNameMap).size();
            final MntDepartmentInfo entity = (MntDepartmentInfo)this.findByPrimaryKey(MntDepartmentInfo.class, vo.getId());
            if (partNumRepeat > 1)
            {
                return 3;
            }
            if (partNameRepeat > 1)
            {
                return 4;
            }
            entity.setPartName(vo.getPartName());
            entity.setPartNum(vo.getPartNum());
            this.update(entity);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
        }
        return 1;
    }
    
    /**
     * 重载方法
     * 
     * @param tel
     * @return
     */
    @Override
    public boolean ifHasBm(String tel)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("telphone", tel);
        List<BizMember> result = this.findByProperties(BizMember.class, map);
        if (result != null && result.size() > 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 重载方法
     * 
     * @param member
     * @return
     */
    @Override
    public int addEmpInfo(BizMember member, MntMngandusers mmu)
    {
        this.save(member);
        mmu.setUserMemberId(member.getId());
        this.save(mmu);
        return 0;
    }
    
    /**
     * 重载方法
     * 
     * @param mmuID
     * @return
     */
    @Override
    public String findEmpIdByMMUID(String mmuId)
    {
        final String sql = " SELECT t.userMemberId FROM mnt_mngandusers t WHERE t.id = ? ";
        final String empId = this.createSqlQuery(sql, mmuId).uniqueResult().toString();
        return empId;
    }
    
    /**
     * 重载方法
     * 
     * @param empId
     * @return
     */
    @Override
    public BizMember findEmpInfoById(String empId)
    {
        return (BizMember)this.findByPrimaryKey(BizMember.class, Integer.parseInt(empId));
    }
    
    /**
     * 重载方法
     * 
     * @param member
     */
    @Override
    public int updateEmpInfo(BizMember member)
    {
        this.update(member);
        return 0;
    }
    
    /**
     * 重载方法
     * 
     * @param orgId
     * @param empId
     */
    @Override
    public int delEmp(String orgId, String empId, String orgName)
    {
        String sql = " UPDATE mnt_mngandusers t SET t.state = 4 WHERE t.mntMemberId = ? AND t.userMemberId = ? AND t.state = 1 ";
        BizMember member = findEmpInfoById(empId);
        member.setDepartmentId(null);
        member.setEnable(1);
        updateEmpInfo(member);
        final String message = "代帐公司：" + orgName + "已经撤销了对该帐号的监控！";
        final String path = "/supervisory/accountingOrgList";
        final String tabname = "账号被监控管理";
        final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), Integer.parseInt(empId), Integer.parseInt(orgId), tabname, 0, member.getTelphone());
        insertMsgNotification(mnc);
        return this.createSqlQuery(sql, orgId, empId).executeUpdate();
    }
    
}
