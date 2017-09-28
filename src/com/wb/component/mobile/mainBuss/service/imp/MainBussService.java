package com.wb.component.mobile.mainBuss.service.imp;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.mobile.mainBuss.service.IMainBussService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgDetailDto;
import com.wb.model.pojo.computer.TrendDataDto;
import com.wb.model.pojo.mobile.HomeShowForMobile;

/**
 * 
 * 处理大部分业务功能ser(不去细化功能级)
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("mainBussService")
public class MainBussService extends BaseDao implements IMainBussService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(MainBussService.class);
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 
     * 重载方法
     * 
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public HomeShowForMobile findHomeShowData(EnterpriseQueryForm form)
    {
        try
        {
            List<HomeShowForMobile> list = (List<HomeShowForMobile>)sqlMapClient.queryForList("findHomeShowForMobile", form);
            if (list.size() > 0)
            {
                return list.get(0);
            }
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询主要信息:", e, true));
            NestLogger.showException(e);
        }
        return null;
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
    public List<OrgDetailDto> findBizInfoOrgList(EnterpriseQueryForm form)
    {
        try
        {
            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgDetailListForMobile", form));
            return (List<OrgDetailDto>)sqlMapClient.queryForList("findOrgDetailListForMobile", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询公司信息:", e, true));
        }
        return null;
    }
    
    @Override
    public List<Map<String, Object>> getCustomerIncreaseInfo(String mngId, String date)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" org.name AS orgName,");
        sql.append(" org.CreateTime AS incTime,");
        sql.append(" bMem.name AS director,");
        sql.append(" bMem.Telphone AS tel");
        sql.append(" FROM");
        sql.append(" mnt_member mMem");
        sql.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId");
        sql.append(" AND mng.state = 1");
        sql.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
        sql.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID");
        sql.append(" AND org. ENABLE = 1");
        sql.append(" WHERE");
        sql.append(" mMem.id = ?");
        sql.append(" AND DATE_FORMAT(org.CreateTime, '%Y-%m') = DATE_FORMAT(?, '%Y-%m')");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setParameter(1, date);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Override
    public List<Map<String, Object>> mointorShowArrearageInfo(String mngId, String memberId)
    {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT tab.*, (con.monthCost * con.discount / 100) AS money,");
        sql.append(" IF (DATE_FORMAT(con.accStartTime, '%Y') < date_format(SYSDATE(), '%Y'),1,");
        sql.append(" IFNULL(group_concat(date_format(con.accStartTime, '%m') SEPARATOR ','),'')) AS BMONTHS,");
        sql.append(" IF (DATE_FORMAT(con.accEndTime, '%Y') > date_format(SYSDATE(), '%Y'),12,");
        sql.append(" IFNULL(group_concat(date_format(con.accEndTime, '%m') SEPARATOR ','),'')) AS EMONTHS");
        sql.append(" FROM(SELECT org.id,IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS payMonths,");
        sql.append(" IFNULL(cus.ID, '') AS cusId,org. NAME AS orgName,bMem. NAME AS director,bMem.Telphone AS tel");
        sql.append(" FROM mnt_member mMem");
        sql.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId");
        sql.append(" AND mng.state = 1");
        sql.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
        sql.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID");
        sql.append(" AND org.ENABLE = 1");
        sql.append(" INNER JOIN mnt_customInfo cus ON cus.id = org.mntCustomId");
        sql.append(" LEFT JOIN mnt_expenseDetail exp ON exp.orgId = org.ID");
        sql.append(" AND exp.deleteFlag = 0");
        sql.append(" AND date_format(exp.payDate, '%Y') = date_format(SYSDATE(), '%Y')");
        sql.append(" WHERE mMem.id = ?");
        if (memberId != null && !"".equals(memberId))
        {
            sql.append(" AND bMem.id = ?");
        }
        sql.append(" GROUP BY org.id ) tab");
        sql.append(" LEFT JOIN mnt_customContract con ON tab.CUSID = con.cusId");
        sql.append(" AND DATE_FORMAT(con.accStartTime, '%Y') <= date_format(SYSDATE(), '%Y')");
        sql.append(" AND DATE_FORMAT(con.accEndTime, '%Y') >= date_format(SYSDATE(), '%Y')");
        sql.append(" WHERE con.accStartTime IS NOT NULL GROUP BY tab.id");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setInteger(0, Integer.parseInt(mngId));
        if (memberId != null)
        {
            query.setInteger(1, Integer.parseInt(memberId));
        }
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            String payMonths = map.get("payMonths") + "";
            String bMonths = map.get("BMONTHS").toString();
            String eMonths = map.get("EMONTHS").toString();
            if (customerManageService.checkConPayMonths(bMonths, eMonths, payMonths))
            {
                result.add(map);
            }
        }
        return result;
    }
    
    @Override
    public List<Map<String, Object>> mointorShowPrePayInfo(String mngId, String date)
    {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String month = (new Date().getMonth() + 1) + "";
        try
        {
            month = new SimpleDateFormat("MM").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        }
        catch (ParseException e)
        {
            NestLogger.showException(e);
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" org. NAME AS orgName,");
        sql.append(" exp.payAmount AS prePay,");
        sql.append(" bMem. NAME AS director,");
        sql.append(" bMem.Telphone AS tel");
        sql.append(" FROM");
        sql.append(" mnt_member mMem");
        sql.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId");
        sql.append(" AND mng.state = 1");
        sql.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
        sql.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID");
        sql.append(" AND org. ENABLE = 1");
        sql.append(" INNER JOIN mnt_expenseDetail exp ON exp.orgId = org.ID");
        sql.append(" AND exp.deleteFlag = 0");
        sql.append(" WHERE");
        sql.append(" mMem.id = ?");
        sql.append(" AND exp.payMonths LIKE ?");
        sql.append(" AND DATE_FORMAT(?, '%Y') = DATE_FORMAT(exp.payDate, '%Y')");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setParameter(1, "%" + Integer.parseInt(month) + "%");
        query.setParameter(2, date);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            result.add(map);
        }
        return result;
    }
}
