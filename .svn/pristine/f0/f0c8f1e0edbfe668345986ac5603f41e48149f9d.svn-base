package com.wb.component.computer.feedBackManage.service;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.processManage.MntFeedBack;

public interface IFeedBackManageService
{
    /**
     * 保存评论信息
     * 
     * @param feedBack 评论实体
     * @param mngId 所属代账公司ID
     * @return boolean
     */
    boolean saveFeedBack(MntFeedBack feedBack, int mngId);
    
    /**
     * 获取评论列表
     * 
     * @param userId 当前登录人ID
     * @param mngId 所属公司ID
     * @param info 分页信息
     * @return PageUtil
     */
    PageUtil getFeedBackList(boolean isAdmin, int userId, int mngId, String queryName, String queryCreatorName, String queryScore, PageInfo info);
    
    /**
     * 获取最近一次的客户流程ID
     * 
     * @param orgId
     * @param mngId
     * @return String
     */
    String getNearOrgProId(String orgId, String mngId);
    
    /**
     * 根据orgId获取mngId
     * 
     * @param orgId 客户ID
     * @return String
     */
    String getMngIdByOrgId(String orgId);
}
