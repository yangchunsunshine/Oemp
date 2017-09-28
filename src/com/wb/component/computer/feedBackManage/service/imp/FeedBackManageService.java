package com.wb.component.computer.feedBackManage.service.imp;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.feedBackManage.service.IFeedBackManageService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.processManage.MntFeedBack;

/**
 * 客户反馈Service层
 * 
 * @author 郑炜
 * @version [版本号, 2016-5-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "feedBackManageService")
public class FeedBackManageService extends BaseDao implements IFeedBackManageService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(FeedBackManageService.class);
    
    @Override
    public boolean saveFeedBack(MntFeedBack feedBack, int mngId)
    {
        try
        {
            feedBack.setMngId(mngId);
            feedBack.setStamp(new Date());
            this.save(feedBack);
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
    public PageUtil getFeedBackList(boolean isAdmin, int userId, int mngId, String queryName, String queryCreatorName, String queryScore, PageInfo info)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" fb.id AS fbId,org.id AS orgId,org.name AS orgName,");
        sql.append(" node.nodeName,fb.score,fb.detail,oph.creatorName");
        sql.append(" FROM biz_organization org");
        sql.append(" INNER JOIN accountingagency acc ON acc.userId = org.ownerId");
        sql.append(" INNER JOIN mnt_feedBack fb ON fb.orgId = org.ID");
        sql.append(" INNER JOIN mnt_orgProcessHistory oph ON oph.id = fb.ophId");
        sql.append(" INNER JOIN mnt_nodeInfo node ON node.id = oph.currentNodeId");
        sql.append(" WHERE org.enable = 1");
        sql.append(" AND acc.mngId = " + mngId);
        if (!isAdmin)
        {
            sql.append(" AND acc.userId = " + userId);
        }
        if(queryName!=null&&!"".equals(queryName)){
            sql.append(" AND (org.name LIKE '%"+queryName+"%' OR");
            sql.append(" node.nodeName LIKE '%"+queryName+"%')");
        }
        if(queryCreatorName!=null&&!"".equals(queryCreatorName)){
            sql.append(" AND oph.creatorName LIKE '%"+queryCreatorName+"%'");
        }
        if(queryScore!=null&&!"".equals(queryScore)&&!"0".equals(queryScore)){
            sql.append(" AND fb.score LIKE '%"+queryScore+"%'");
        }
        sql.append(" ORDER BY org.ID,node.orderSeq");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
    }
    
    @Override
    public String getNearOrgProId(String orgId, String mngId)
    {
        String orgProId = "";
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT orgPro.id AS orgProId");
        sql.append(" FROM mnt_processInfo pro");
        sql.append(" INNER JOIN mnt_orgProcessInfo orgPro ON orgPro.processId = pro.id");
        sql.append(" WHERE pro.mngId = ?");
        sql.append(" AND orgPro.orgId = ?");
        sql.append(" ORDER BY orgPro.stamp DESC");
        sql.append(" LIMIT 0,1");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setParameter(1, orgId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<String, Object> map = (Map<String, Object>)query.uniqueResult();
        if (map != null)
        {
            orgProId = map.get("orgProId").toString();
        }
        return orgProId;
    }

    @Override
    public String getMngIdByOrgId(String orgId)
    {
        String mngId = "";
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" mMem.id AS mngId");
        sql.append(" FROM biz_organization org");
        sql.append(" INNER JOIN biz_member bMem ON org.Creator = bMem.ID");
        sql.append(" INNER JOIN mnt_mngandusers mmu ON bMem.ID = mmu.userMemberId AND mmu.state = 1");
        sql.append(" INNER JOIN mnt_member mMem ON mmu.mntMemberId = mMem.id");
        sql.append(" WHERE org.ID = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, orgId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<String, Object> map = (Map<String, Object>)query.uniqueResult();
        if(map!=null){
            mngId = map.get("mngId").toString();
        }
        return mngId;
    }
}
