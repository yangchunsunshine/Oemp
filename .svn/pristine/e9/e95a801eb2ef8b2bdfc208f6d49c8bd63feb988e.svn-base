///*
// * 文 件 名:  ReportService.java
// * 版    权:  gomyck
// * 描    述:  <描述>
// * 修 改 人:  郝洋
// * 修改时间:  2016-3-28
// * 跟踪单号:  <跟踪单号>
// * 修改单号:  <修改单号>
// * 修改内容:  <修改内容>
// */
//package com.wb.component.computer.report.service.imp;
//
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.hibernate.Criteria;
//import org.hibernate.Query;
//import org.springframework.stereotype.Service;
//
//import com.wb.component.computer.login.controller.Login;
//import com.wb.component.computer.report.service.IReportService;
//import com.wb.framework.commonDao.BaseDao;
//import com.wb.framework.nestLogger.NestLogger;
//import com.wb.model.pojo.computer.ClerkFeeStatisticDto;
//import com.wb.model.pojo.computer.ClerkTaxStatisticDto;
//import com.wb.model.pojo.computer.ClerkVchStatisticDto;
//import com.wb.model.pojo.computer.EnterpriseQueryForm;
//import com.wb.model.pojo.computer.OrgFeeStatisticDto;
//import com.wb.model.pojo.computer.OrgTaxStatisticDto;
//import com.wb.model.pojo.computer.OrgVchStatisticDto;
//import com.wb.model.pojo.computer.TrendDataDto;
//
///**
// * 报表ser
// * 
// * @author 郝洋
// * @version [版本号, 2016-3-28]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@Service(value = "reportService")
//public class ReportService extends BaseDao implements IReportService
//{
//    /**
//     * 日志服务
//     */
//    private static final Logger log = Logger.getLogger(Login.class);
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form form
//     * @return List<ClerkFeeStatisticDto> List<ClerkFeeStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<ClerkFeeStatisticDto> findClerkFeeData(EnterpriseQueryForm form)
//    {
//        try
//        {
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countClerkFeeForMng", form));
//            List<ClerkFeeStatisticDto> vo = sqlMapClient.queryForList("findClerkFeeForMng", form);
//            for (ClerkFeeStatisticDto temp : vo)
//            {
//                temp.getOrgId();
//            }
//            return sqlMapClient.queryForList("findClerkFeeForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form form
//     * @return List<ClerkTaxStatisticDto> List<ClerkTaxStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<ClerkTaxStatisticDto> findClerkTaxData(EnterpriseQueryForm form)
//    {
//        try
//        {
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countClerkTaxForMng", form));
//            return sqlMapClient.queryForList("findClerkTaxForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form form
//     * @return List<ClerkVchStatisticDto> List<ClerkVchStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<ClerkVchStatisticDto> findClerkVchData(EnterpriseQueryForm form)
//    {
//        try
//        {
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countClerkVchForMng", form));
//            return sqlMapClient.queryForList("findClerkVchForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form
//     * @return List<OrgFeeStatisticDto> List<OrgFeeStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<OrgFeeStatisticDto> findOrgFeeData(EnterpriseQueryForm form)
//    {
//        try
//        {
//         	form.setOrgName(URLDecoder.decode(form.getOrgName(),"UTF-8"));
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgFeeForMng", form));
//            return sqlMapClient.queryForList("findOrgFeeForMng", form);
//        }
//        catch (Exception e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form
//     * @return List<OrgTaxStatisticDto> List<OrgTaxStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<OrgTaxStatisticDto> findOrgTaxData(EnterpriseQueryForm form)
//    {
//        try
//        {
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgTaxForMng", form));
//            return sqlMapClient.queryForList("findOrgTaxForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form form
//     * @return List<OrgVchStatisticDto> List<OrgVchStatisticDto>
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<OrgVchStatisticDto> findOrgVchData(EnterpriseQueryForm form)
//    {
//        try
//        {
//            form.setTrendData((TrendDataDto)sqlMapClient.queryForObject("countOrgVchForMng", form));
//            return sqlMapClient.queryForList("findOrgVchForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /* ------------------------主界面chart用service开始--------------- */
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form
//     * @return List<TrendDataDto> List<TrendDataDto>
//     */
//    @Override
//    public List<TrendDataDto> findIntegerArrayForTax(EnterpriseQueryForm form)
//    {
//        try
//        {
//            return sqlMapClient.queryForList("findSearchTaxNumForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form
//     * @return List<TrendDataDto> List<TrendDataDto>
//     */
//    @Override
//    public List<TrendDataDto> findIntegerArrayForVch(EnterpriseQueryForm form)
//    {
//        try
//        {
//            return sqlMapClient.queryForList("findSearchVchNumForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    /**
//     * 
//     * 重载方法
//     * 
//     * @param form
//     * @return List<TrendDataDto> List<TrendDataDto>
//     */
//    @Override
//    public List<TrendDataDto> findIntegerArrayForFee(EnterpriseQueryForm form)
//    {
//        try
//        {
//            return sqlMapClient.queryForList("findSearchFeeForMng", form);
//        }
//        catch (SQLException e)
//        {
//            NestLogger.showException(e);
//            log.error(NestLogger.buildLog("查询报表:", e, true));
//        }
//        return null;
//    }
//    
//    @Override
//    public List<Map<String, Object>> getCommissionDetail(String sdId, String edId, int memberId)
//    {
//        DecimalFormat df = new DecimalFormat("######0.00");
//        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT");
//        sql.append(" ID AS orgId,Name AS orgName,");
//        sql.append(" mntCustomId AS cusId");
//        sql.append(" FROM biz_organization");
//        sql.append(" WHERE Enable = 1 AND ownerId = ?");
//        final Query query = this.getSession().createSQLQuery(sql.toString());
//        query.setParameter(0, memberId);
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Map<String, Object>> list = query.list();
//        for (Map<String, Object> map : list)
//        {
//            Double cost = 0.0;
//            String orgId = map.get("orgId").toString();
//            String cusId = map.get("cusId").toString();
//            List<String> queryTime = this.getPayMonthInQueryTime(sdId, edId, orgId);
//            Map<String, String> conRs = this.getConComCost(cusId);
//            for (String ym : queryTime)
//            {
//                if (conRs.get(ym) != null)
//                {
//                    cost = Double.parseDouble(conRs.get(ym)) + cost;
//                }
//            }
//            map.put("commission", df.format(cost));
//        }
//        return list;
//    }
//    
//    @Override
//    public Map<String, String> getConComCost(String cusId)
//    {
//        Map<String, String> result = new HashMap<String, String>();
//        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT");
//        sql.append(" DATE_FORMAT(accStartTime, '%Y-%m') AS bTime,");
//        sql.append(" DATE_FORMAT(accEndTime, '%Y-%m') AS eTime,");
//        sql.append(" monthCost * IFNULL(commission, 0) / 100 AS conComCost");
//        sql.append(" FROM mnt_customContract");
//        sql.append(" WHERE cusId = ?");
//        final Query query = this.getSession().createSQLQuery(sql.toString());
//        query.setParameter(0, cusId);
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Map<String, Object>> list = query.list();
//        for (Map<String, Object> map : list)
//        {
//            int bYear = Integer.parseInt(map.get("bTime").toString().split("-")[0]);
//            int bMonth = Integer.parseInt(map.get("bTime").toString().split("-")[1]);
//            int eYear = Integer.parseInt(map.get("eTime").toString().split("-")[0]);
//            int eMonth = Integer.parseInt(map.get("eTime").toString().split("-")[1]);
//            String conComCost = map.get("conComCost").toString();
//            if (bYear == eYear){
//                for (int i = bMonth; i <= eMonth; i++){
//                    result.put(bYear + "-" + i, conComCost);
//                }
//            }else{
//                for (int i = bYear; i <= eYear; i++){
//                    if (i != eYear){
//                        for (int b = 1; b <= 12; b++){
//                            result.put(i + "-" + b, conComCost);
//                        }
//                    }else{
//                        for (int b = 1; b <= eMonth; b++){
//                            result.put(i + "-" + b, conComCost);
//                        }
//                    }
//                }
//            }
//        }
//        return result;
//    }
//    
//    @Override
//    public List<String> getPayMonthInQueryTime(String sdId, String edId, String orgId)
//    {
//        List<String> payTime = new ArrayList<String>();
//        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT");
//        sql.append(" DATE_FORMAT(payDate, '%Y') AS payYear,");
//        sql.append(" payMonths");
//        sql.append(" FROM mnt_expenseDetail");
//        sql.append(" WHERE deleteFlag = 0");
//        sql.append(" AND orgId = ?");
//        sql.append(" AND DATE_FORMAT(payStamp,'%Y-%m') BETWEEN '"+sdId+"' AND '"+edId+"'");
//        final Query query = this.getSession().createSQLQuery(sql.toString());
//        query.setParameter(0, orgId);
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Map<String, Object>> list = query.list();
//        for (Map<String, Object> map : list)
//        {
//            String payYear = map.get("payYear").toString();
//            String[] payMonths = map.get("payMonths").toString().split(",");
//            for (String month : payMonths)
//            {
//                payTime.add(payYear + "-" + Integer.parseInt(month));
//            }
//        }
//        return payTime;
//    }
//    
//}
