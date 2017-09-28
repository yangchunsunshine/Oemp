/*
 * 文 件 名:  MailService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wb.component.computer.login.service.IMailService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageProxy;
import com.wb.model.entity.computer.MsgNotification;

/**
 * 站内信ser
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "mailService")
public class MailService extends BaseDao implements IMailService
{
    /**
     * 
     * 重载方法
     * 
     * @param userId userId
     * @param pageProxy pageProxy
     * @return List<MsgNotification> List<MsgNotification>
     */
    @Override
    public List<MsgNotification> findNotificationForMng(Integer mngId,Integer userId, PageProxy pageProxy)
    {
        final DetachedCriteria criteria = DetachedCriteria.forClass(MsgNotification.class);
        criteria.add(Restrictions.eq("mngId", mngId));
      //update by zhouww date:20161020 desc:修复bug管理员和员工查询的公告信息一样 改成 只查询自己的公告信息 查询表msg_notification的时候添加约束userId
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("direction", 1));
        criteria.addOrder(Order.asc("isread"));
        criteria.addOrder(Order.desc("stamp"));
        return this.findByCriteria(MsgNotification.class, criteria, pageProxy);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param map map
     * @param where where
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateMsgNotification(Map map, String where)
    {
        this.update(MsgNotification.class, map, where);
    }
}
