package com.wb.component.computer.auditSettings.service.imp;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.auditSettings.service.IAuditSettingsService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;

/**
 * 审批设置Service层实现
 * 
 * @author 郑炜
 * @version [版本号, 2016-3-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "auditSettingsService")
public class AuditSettingsService extends BaseDao implements IAuditSettingsService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(AuditSettingsService.class);
    
    @Override
    public PageUtil getAuditSettingsList(String cmd, int queryId, int mngId, String auditorName, int auditType, String auditLevel, PageInfo info)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" audit.id AS auditId,");
        sql.append(" audit.auditorId,");
        sql.append(" bMem.name AS auditorName,");
        sql.append(" dep.partName,");
        sql.append(" audit.auditType,");
        sql.append(" audit.auditLevel,");
        sql.append(" audit.id AS opera");
        sql.append(" FROM mnt_departmentInfo dep");
        sql.append(" INNER JOIN biz_member bMem ON dep.id = bMem.departmentId");
        sql.append(" INNER JOIN mnt_mngandusers mng ON bMem.id = mng.userMemberId AND mng.state = 1");
        sql.append(" INNER JOIN mnt_auditSettings audit ON bMem.id = audit.auditorId AND audit.mngId = '" + mngId + "'");
        sql.append(" WHERE mng.mntMemberId = ?");
        if ("BY_EMP".equals(cmd))
        {
            sql.append(" AND audit.auditorId = '" + queryId + "'");
        }
        else if ("BY_DEP".equals(cmd))
        {
            sql.append(" AND (dep.idPath LIKE CONCAT((SELECT idPath FROM mnt_departmentInfo WHERE id = '" + queryId + "'),'-%')");
            sql.append(" OR dep.idPath = (SELECT idPath FROM mnt_departmentInfo WHERE id = '" + queryId + "'))");
        }
        if (auditType != -1)
        {
            sql.append(" AND audit.auditType = " + auditType + "");
        }
        if (!"-1".equals(auditorName) && auditorName != null)
        {
            sql.append(" AND bMem.name LIKE '%" + auditorName + "%'");
        }
        if (!"-1".equals(auditLevel))
        {
            sql.append(" AND audit.auditLevel = " + auditLevel);
        }
        sql.append(" ORDER BY audit.auditLevel,dep.partName,audit.auditType");
        PageUtil util = this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows(), mngId);
        return util;
    }
    
    @Override
    public int saveAuditor(int mngId, String auditorId, String auditType, String auditLevel)
    {
        try
        {
            boolean isRepeat = this.checkAuditorRepeat(mngId, null, auditorId, auditType, auditLevel, false);
            if (isRepeat)
            {
                return 2;
            }
            final StringBuffer sql = new StringBuffer();
            sql.append(" INSERT INTO mnt_auditSettings");
            sql.append(" (mngId,auditorId,auditType,auditLevel,stamp)");
            sql.append(" values(?,?,?,?,SYSDATE())");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, mngId);
            query.setParameter(1, auditorId);
            query.setParameter(2, auditType);
            query.setParameter(3, auditLevel);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
        return 0;
    }
    
    @Override
    public boolean checkAuditorRepeat(int mngId, String auditId, String auditorId, String auditType, String auditLevel, boolean ifUpdate)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num FROM mnt_auditSettings");
        sql.append(" WHERE auditorId = ?");
        sql.append(" AND mngId = ?");
        sql.append(" AND auditType = ?");
        sql.append(" AND auditLevel = ?");
        if (ifUpdate)
        {
            sql.append(" AND id <> ?");
        }
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, auditorId);
        query.setParameter(1, mngId);
        query.setParameter(2, auditType);
        query.setParameter(3, auditLevel);
        if (ifUpdate)
        {
            query.setParameter(4, auditId);
        }
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String,Object>> list = query.list();
        final int num = Integer.parseInt(list.get(0).get("num").toString());
        if (num > 0)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteAuditor(String auditId)
    {
        try
        {
            final StringBuffer sql = new StringBuffer();
            sql.append(" DELETE FROM mnt_auditSettings");
            sql.append(" WHERE ID = ?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, auditId);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public int updateAuditor(int mngId, String auditId, String auditorId, String auditType, String auditLevel)
    {
        try
        {
            boolean isRepeat = this.checkAuditorRepeat(mngId, auditId, auditorId, auditType, auditLevel, true);
            if (isRepeat)
            {
                return 2;
            }
            final StringBuffer sql = new StringBuffer();
            sql.append(" UPDATE mnt_auditSettings");
            sql.append(" SET auditorId=?,");
            sql.append(" auditType=?,");
            sql.append(" auditLevel=?");
            sql.append(" WHERE id=?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, auditorId);
            query.setParameter(1, auditType);
            query.setParameter(2, auditLevel);
            query.setParameter(3, auditId);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
        return 0;
    }
    
    @Override
    public void createAudit(int mngId, int correlationId, int auditType, int auditFlag, int maxAuditLevel)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT mnt_auditRoute");
        sql.append(" (mngId,correlationId,auditType,auditFlag,maxAuditLevel,stamp)");
        sql.append(" VALUES(?,?,?,?,?,SYSDATE())");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setParameter(1, correlationId);
        query.setParameter(2, auditType);
        query.setParameter(3, auditFlag);
        query.setParameter(4, maxAuditLevel);
        query.executeUpdate();
    }
    
    @Override
    public int getAuditFlag(int auditType, int mngId)
    {
        int auditFlag = 0;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MIN(auditLevel) AS auditFlag");
        sql.append(" FROM mnt_auditSettings");
        sql.append(" WHERE auditType = ? AND mngId = ?");
        sql.append(" GROUP BY auditType");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, auditType);
        query.setParameter(1, mngId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        if (list != null)
        {
            for (Map<String, Object> map : list)
            {
                auditFlag = Integer.parseInt(map.get("auditFlag").toString());
            }
        }
        return auditFlag;
    }
    
    @Override
    public int getMaxAuditLevel(int auditType, int mngId)
    {
        int maxAuditLevel = 0;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT MAX(auditLevel) AS maxAuditLevel");
        sql.append(" FROM mnt_auditSettings");
        sql.append(" WHERE auditType = ? AND mngId= ? ");
        sql.append(" GROUP BY auditType");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, auditType);
        query.setParameter(1, mngId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        if (list != null)
        {
            for (Map<String, Object> map : list)
            {
                maxAuditLevel = Integer.parseInt(map.get("maxAuditLevel").toString());
            }
        }
        return maxAuditLevel;
    }
    
    @Override
    public boolean checkAuditIntegrity(int auditType, int mngId)
    {
        try
        {
            int min = this.getAuditFlag(auditType, mngId);
            if (min != 1)
            {
                return false;
            }
            int max = this.getMaxAuditLevel(auditType, mngId);
            int num = max - min + 1;
            int realNum = 0;
            final StringBuffer sql = new StringBuffer();
            sql.append(" SELECT auditLevel,");
            sql.append(" count(*) AS auditorNum");
            sql.append(" FROM mnt_auditSettings");
            sql.append(" WHERE auditType = ? AND mngId = ?");
            sql.append(" GROUP BY auditLevel");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, auditType);
            query.setParameter(1, mngId);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            final List<Map<String, Object>> list = query.list();
            if (list != null)
            {
                realNum = list.size();
                if (num != realNum)
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public int getAuditorLevel(int userId, int mngId, int auditType)
    {
        int auditLevel = 0;
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT auditLevel");
        sql.append(" FROM mnt_auditSettings");
        sql.append(" WHERE auditorId = ?");
        sql.append(" AND mngId = ?");
        sql.append(" AND auditType = ? order by auditLevel limit 1");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, userId);
        query.setParameter(1, mngId);
        query.setParameter(2, auditType);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        final List<Map<String, Object>> list = query.list();
        if (list != null)
        {
            for (Map<String, Object> map : list)
            {
                auditLevel = Integer.parseInt(map.get("auditLevel").toString());
            }
        }
        return auditLevel;
    }
    
    @Override
    public boolean updatePassAudit(int mngId, int auditType, int routeId, int auditFlag, int auditorLevel)
    {
        try
        {
            int maxAuditLevel = this.getMaxAuditLevel(auditType, mngId);
            if (auditorLevel == 88 || auditorLevel == maxAuditLevel)
            {
                auditFlag = 0;
            }
            else
            {
                auditFlag = auditFlag + 1;
            }
            StringBuffer sql = new StringBuffer();
            sql.append(" UPDATE mnt_auditRoute");
            sql.append(" SET auditFlag = ?");
            sql.append(" WHERE id = ?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, auditFlag);
            query.setParameter(1, routeId);
            query.executeUpdate();
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
    public boolean updateBackAudit(int routeId)
    {
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append(" UPDATE mnt_auditRoute");
            sql.append(" SET auditFlag = 1");
            sql.append(" WHERE id = ?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, routeId);
            query.executeUpdate();
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
    public boolean deleteExp(int expId)
    {
        try
        {
            StringBuffer sql = new StringBuffer();
            sql.append(" UPDATE mnt_expenseDetail");
            sql.append(" SET deleteFlag = 1");
            sql.append(" WHERE id = ?");
            final Query query = this.getSession().createSQLQuery(sql.toString());
            query.setParameter(0, expId);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
}
