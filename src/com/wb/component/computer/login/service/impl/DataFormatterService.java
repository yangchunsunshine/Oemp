package com.wb.component.computer.login.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.login.service.IDataFormatterService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;
import com.wb.model.entity.computer.cusManage.MntCustomInfo;

@Service(value = "dataFormatterService")
public class DataFormatterService extends BaseDao implements IDataFormatterService
{
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(DataFormatterService.class);
    
    /**
     * 
     * 重载方法
     * 
     * @param userId
     */
    @Override
    public void updateOrganizationCostomInfoContact(boolean isAdmin, String userId)
    {
        try
        {
            if (isAdmin)
            {
                userId = this.getUserIds(userId);
            }
            else
            {
                userId = "'" + userId + "'";
            }
            List<Map<String, Object>> list = this.getOrganization_cusIdIsNull(userId);
            for (Map<String, Object> map : list)
            {
                BizOrganization bizEntity = (BizOrganization)this.findByPrimaryKey(BizOrganization.class, Integer.parseInt(map.get("ID").toString()));
                String mntCustomId = this.idCreater(Integer.toString(bizEntity.getId()));
                bizEntity.setMntCustomId(mntCustomId);
                this.update(bizEntity);
                final MntCustomInfo mntEntity = new MntCustomInfo();
                mntEntity.setId(mntCustomId);
                mntEntity.setCreater(Integer.toString(bizEntity.getId()));
                mntEntity.setSaveCreate("on");
                mntEntity.setTaxType("0");
                this.save(mntEntity);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("初始化公司信息失败(补全公司信息):", e, true));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrganization_cusIdIsNull(String userId)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id as ID FROM biz_organization");
        sql.append(" WHERE (mntCustomId = '' or mntCustomId is null)");
        sql.append(" AND ownerId IN (" + userId + ")");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param userId
     * @return
     */
    @Override
    public String idCreater(String userId)
    {
        final String id = userId + System.currentTimeMillis();
        return id;
    }
    
    @Override
    public String getUserIds(String userId)
    {
        StringBuffer userIdStr = new StringBuffer("'userId'");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT biz.ID AS userId");
        sql.append(" FROM mnt_member mnt");
        sql.append(" INNER JOIN mnt_mngandusers gand ON mnt.id = gand.mntMemberId");
        sql.append(" INNER JOIN biz_member biz ON gand.userMemberId = biz.ID");
        sql.append(" WHERE gand.state = 1 AND mnt.id = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setInteger(0, Integer.parseInt(userId));
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> userIds = query.list();
        for (Map<String, Object> map : userIds)
        {
            userIdStr.append(",'" + map.get("userId") + "'");
        }
        return userIdStr.toString();
    }
    
}
