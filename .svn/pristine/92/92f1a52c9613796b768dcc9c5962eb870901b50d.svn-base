/*
 * 文 件 名:  IMailService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.service;

import java.util.List;
import java.util.Map;

import com.wb.framework.commonUtil.PageProxy;
import com.wb.model.entity.computer.MsgNotification;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IMailService
{
    /**
     * 
     * 获取站内信列表
     * 
     * @param mngId
     * @param pageProxy
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<MsgNotification> findNotificationForMng(Integer mngId, Integer adminId, PageProxy pageProxy);
    
    /**
     * 
     * 更新站内信状态
     * 
     * @param map
     * @param where
     * @see [类、类#方法、类#成员]
     */
    public void updateMsgNotification(Map map, String where);
}
