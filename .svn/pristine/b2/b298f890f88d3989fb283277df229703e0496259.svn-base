/*
 * 文 件 名:  ILoginService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-18
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.service;

import java.util.Map;

import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.HomeShowForComputer;

/**
 * 用户登陆service接口
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ILoginService
{
    /**
     * 
     * 根据电话号查询
     * 
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    MntMember findByTel(String tel);
    
    
    /**
     * 
     * 切换用户
     * 
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    MntMember findByTelForChangeRole(String tel,String userType);
    
    
    /**
     * 
     * 插入mntmember
     * 
     * @param member
     * @see [类、类#方法、类#成员]
     */
    void insert(MntMember member, BizMember bm);
    
    /**
     * 
     * 更新mntmember
     * 
     * @param map
     * @param sqlStr
     * @see [类、类#方法、类#成员]
     */
    void updateManager(Boolean isAdmin, Map map, String sqlStr);
    void updateCustom(Map map, String sqlStr);
    /**
     * 
     * 站内信条数(用于小红点)
     * 
     * @param userId
     * @param direction
     * @return
     * @see [类、类#方法、类#成员]
     */
    Long countNotificationById(Integer userId,Integer adminId, Integer direction);
    
    /**
     * 
     * 首页信息展示
     * 
     * @param form
     * @return
     * @see [类、类#方法、类#成员]
     */
    HomeShowForComputer findHomeShowData(EnterpriseQueryForm form);
    
    /**
     * 
     * 根据主键查询
     * 
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    MntMember findById(int id);
    
}
