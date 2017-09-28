/*
 * 文 件 名:  EmpWorkDetailService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.empWork.service.imp;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.empWork.service.IEmpWorkDetailService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.pojo.computer.ClerkWorkMonitorDto;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.OrgTaxMonitorDto;
import com.wb.model.pojo.computer.SettleOrgDto;
import com.wb.model.pojo.computer.SuperintendentDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;
import com.wb.model.pojo.computer.TaxAmount;
import com.wb.model.pojo.computer.TrendDataDto;

/**
 * 会计工作明细ser实现类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "empWorkDetailService")
public class EmpWorkDetailService extends BaseDao implements IEmpWorkDetailService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(EmpWorkDetailService.class);
    
    /**
     * 
     * 重载方法
     * 
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SettleOrgDto> findSettledOrgList(EnterpriseQueryForm form)
    {
        try
        {
            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countSettleOrgList", form));
            return sqlMapClient.queryForList("findSettleOrgList", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询结账信息:", e, true));
        }
        return null;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param form form
     * @return List<ClerkWorkMonitorDto>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ClerkWorkMonitorDto> findClerkWorkDetailList(EnterpriseQueryForm form)
    {
        try
        {
            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgTaskDetialForClerk", form));
            return sqlMapClient.queryForList("findOrgTaskDetialForClerk", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询会计工作详情:", e, true));
        }
        return null;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param form form
     * @return List<SuperintendentDto>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SuperintendentDto> findByParams(SuperintendentQueryForm form)
    {
        try
        {
            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countClerkListForMonitor", form));
            return sqlMapClient.queryForList("findClerkListForMonitor", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("员工在线信息查询:", e, true));
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
    public List<OrgTaxMonitorDto> findOrgTaxByParamsForMng(EnterpriseQueryForm form)
    {
        try
        {
            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgTaxSelectionForServer", form));
            return sqlMapClient.queryForList("findOrgTaxSelectionForServer", form);
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询报表:", e, true));
        }
        return null;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findTaxDetail(Object... args)
    {
        try{
            final String sql = " SELECT * FROM mnt_taxcategories WHERE REID=? UNION SELECT * FROM mnt_taxcategories WHERE REID=-1 and not exists (select * from mnt_taxcategories where REID=?)";
            final Query query = this.getSession().createSQLQuery(sql);
            for (int i = 0; i < args.length; i = i + 1)
            {
                query.setParameter(i, args[i]);
            }
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            return query.list();
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询报税明细:", e, true));
        }
        return null;
    }
    
    @Override
    public int saveTaxAmount(int reid, List<TaxAmount> taxAmount)
    {
        try{
            String sql = " DELETE FROM mnt_taxcategories WHERE REID = ? ";
            Query query = this.getSession().createSQLQuery(sql);
            query.setParameter(0, reid);
            query.executeUpdate();
            sql = " INSERT INTO mnt_taxcategories (REID,SHOWNUM,NAME,BALANCE) VALUES (?,?,?,?) ";
            query = this.getSession().createSQLQuery(sql);
            for (TaxAmount amount : taxAmount)
            {
                query.setParameter(0, amount.getReid());
                query.setParameter(1, amount.getShowNum());
                query.setParameter(2, amount.getName());
                query.setParameter(3, amount.getBalance());
                query.executeUpdate();
            }
            return 0;
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("新增报税明细:", e, true));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return -1;
    }
}
