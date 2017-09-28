package com.wb.component.computer.login.service;

import java.util.List;
import java.util.Map;

public interface IDataFormatterService
{
    
    /**
     * 创建biz_organization表与mnt_customInfo的联系（根据mntCustomId）
     * 
     * @param list getOrganization_cusIdIsNull方法获取的list
     * @param userId biz_member中的id
     * @see [类、类#方法、类#成员]
     */
    void updateOrganizationCostomInfoContact(boolean isAdmin, String userId);
    
    /**
     * 获取biz_organization表中无mntCustomId的数据
     * 
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getOrganization_cusIdIsNull(String userId);
    
    /**
     * ID生成器
     * 
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String idCreater(String userId);
    
    /**
     * 如果登录人为管理员，则获取该管理员下面所有的userId
     * 
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String getUserIds(String userId);
}
