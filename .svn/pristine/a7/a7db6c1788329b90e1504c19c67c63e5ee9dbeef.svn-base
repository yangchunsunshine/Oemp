package com.wb.component.computer.auditSettings.service;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;

/**
 * 审批设置Service层接口
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-5-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IAuditSettingsService
{
    /**
     * 获取审批列表
     * 
     * @param cmd 查询方式（BY_EMP,BY_DEP）
     * @param queryId 查询ID
     * @param mngId 所属公司ID
     * @param auditorId 审批ID
     * @param auditorName 审批人姓名
     * @param info 分页信息
     * @return PageUtil
     */
    PageUtil getAuditSettingsList(String cmd, int queryId, int mngId, String auditorName, int auditType, String auditLevel, PageInfo info);
    
    /**
     * 添加审批人员
     * 
     * @param auditorId 审批人ID
     * @param auditType 审批流类别
     * @param auditLevel 审批级别
     * @return int
     */
    int saveAuditor(int mngId, String auditorId, String auditType, String auditLevel);
    
    /**
     * 查看引入的审批人是否重复
     * 
     * @param auditorId 审批人ID
     * @param auditType 审批流类别
     * @param auditLevel 审批级别
     * @return
     */
    boolean checkAuditorRepeat(int mngId, String auditId, String auditorId, String auditType, String auditLevel, boolean ifUpdate);
    
    /**
     * 删除审批人员信息
     * 
     * @param auditId 审批ID
     * @return boolean
     */
    boolean deleteAuditor(String auditId);
    
    /**
     * 跟新审批人员信息
     * 
     * @param mngId 所属公司ID
     * @param auditId 审批ID
     * @param auditorId 审批人ID
     * @param auditType 审批流类型
     * @param auditLevel 审批级别
     * @return int
     */
    int updateAuditor(int mngId, String auditId, String auditorId, String auditType, String auditLevel);
    
    /**
     * 引入审批流程
     * 
     * @param mngId 所属公司ID
     * @param correlationId 关联ID
     * @param auditType 审批流类型
     * @param auditFlag 流程标识
     * @param maxAuditLevel 最大审批级别
     */
    void createAudit(int mngId, int correlationId, int auditType, int auditFlag, int maxAuditLevel);
    
    /**
     * 获取审批流标识
     * 
     * @param auditType 审批流类型
     * @return int
     */
    int getAuditFlag(int auditType, int mngId);
    
    /**
     * 获取最大审批级别
     * 
     * @param auditType 审批流类型
     * @param mngId 所属公司ID
     * @return int
     */
    int getMaxAuditLevel(int auditType, int mngId);
    
    /**
     * 验证审批流程完整性
     * 
     * @param auditType 审批流类型
     * @param mngId 所属公司ID
     * @return boolean
     */
    boolean checkAuditIntegrity(int auditType, int mngId);
    
    /**
     * 获取审批人员级别
     * 
     * @param uerId 审批人员ID
     * @param mngId 所属公司ID
     * @param auditType 审批流类型
     * @return int
     */
    int getAuditorLevel(int userId, int mngId, int auditType);
    
    /**
     * 通过审批
     * 
     * @param routeId 审批ID
     * @param auditFlag 审批流程节点
     * @param mngId 所属公司ID
     * @return boolean
     */
    boolean updatePassAudit(int mngId, int auditType, int routeId, int auditFlag, int auditorLevel);
    
    /**
     * 取消审批
     * 
     * @param routeId 审批ID
     * @return boolean
     */
    boolean updateBackAudit(int routeId);
    
    /**
     * 取消缴费
     * 
     * @param expId 缴费ID
     * @return boolean
     */
    boolean deleteExp(int expId);
}
