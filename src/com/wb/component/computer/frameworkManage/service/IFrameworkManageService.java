/*
 * 文 件 名:  IFrameworkManageService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.frameworkManage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wb.model.entity.computer.MntMngandusers;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.entity.computer.frameworkManage.MntDepartmentInfo;
import com.wb.model.pojo.computer.ClerkManagementDto;
import com.wb.model.pojo.computer.SuperintendentQueryForm;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFrameworkManageService
{
    /**
     * 查询bizmember信息
     * 
     * @param name name
     * @param telphone telphone
     * @return BizMember
     * @see [类、类#方法、类#成员]
     */
    BizMember findMemberByParams(String name, String telphone);
    
    /**
     * 查询监控中间表信息
     * 
     * @param memId memId
     * @param userId userId
     * @param request request
     * @return Integer
     * @see [类、类#方法、类#成员]
     */
    Integer findAssociationByParams(Integer memId, Integer userId, HttpServletRequest request);
    /*
     * 员工引入新增初始化权限
     */
    void saveMemberIntroduceRoleInit(Integer memId, Integer userId);
    /**
     * 插入监控权限
     * 
     * @param mmu mmu
     * @see [类、类#方法、类#成员]
     */
    void insertClerkAssociation(MntMngandusers mmu);
    
    /**
     * 查询站内信信息
     * 
     * @param mnc mnc
     * @see [类、类#方法、类#成员]
     */
    void insertMsgNotification(MsgNotification mnc);
    
    /**
     * 查询监控权限信息
     * 
     * @param id id
     * @return MntMngandusers
     * @see [类、类#方法、类#成员]
     */
    MntMngandusers findAssociationById(Integer id);
    
    /**
     * 更新会计登陆权限状态
     * 
     * @param map map
     * @param sqlStr sqlStr
     * @see [类、类#方法、类#成员]
     */
    void updateClerkState(Map map, String sqlStr);
    
    /**
     * 更新bizmember
     * 
     * @param map map
     * @param sqlStr sqlStr
     * @see [类、类#方法、类#成员]
     */
    void updateBizMember(Map map, String sqlStr);
    
    /**
     * 查询组织架构数
     * 
     * @param adminId adminId
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findMntFrameWorkInfo(int memberId, boolean isAdmin, String adminId, String ifHasEmp, String partId);
    
    /**
     * 更新mntmember信息
     * 
     * @param map map
     * @param sqlWhere sqlWhere
     * @see [类、类#方法、类#成员]
     */
    void updateMntMember(Map map, String sqlWhere);
    
    /**
     * 查询mntmember用户信息
     * 
     * @param tel tel
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> findUserInfo(String tel);
    
    /**
     * 权限交接
     * 
     * @param nowId nowId
     * @param toId toId
     * @param memberIDS memberIDS
     * @see [类、类#方法、类#成员]
     */
    int updateOrgToOrther(int nowId, int toId, String[] memberIDS);
    
    /**
     * 查询被监控的会计
     * 
     * @param memberId memberId
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findControEmpInfo(int memberId);
    
    /**
     * 查询当前登录用户的其他角色信息
     * 
     * @param tel 手机号
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> findOtherRoleInfo(String tel);
    
    /**
     * 查询会计信息
     * 
     * @param form form
     * @return List<ClerkManagementDto>
     * @see [类、类#方法、类#成员]
     */
    List<ClerkManagementDto> findClerkListByParams(SuperintendentQueryForm form);
    
    /**
     * 添加部门
     * 
     * @param vo ztree实体类
     * @param creater 当前系统登录人
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    int addPartInfo(MntDepartmentInfo vo, int creater);
    
    /**
     * 删除部门
     * 
     * @param vo vo
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int deletePartInfo(MntDepartmentInfo vo);
    
    /**
     * 更新部门
     * 
     * @param vo ztree实体类
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int updatePartInfo(MntDepartmentInfo vo, int creater);
    
    /**
     * 
     * 查询是否有biz_member信息
     * 
     * @param tel
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean ifHasBm(String tel);
    
    /**
     * 
     * 插入员工信息
     * 
     * @param member
     * @see [类、类#方法、类#成员]
     */
    int addEmpInfo(BizMember member, MntMngandusers mmu);
    
    /**
     * 
     * 查询员工ID根据监控关联表
     * 
     * @param mmuId
     * @return
     * @see [类、类#方法、类#成员]
     */
    String findEmpIdByMMUID(String mmuId);
    
    /**
     * 
     * 查询员工信息(biz_member)
     * 
     * @param empId
     * @return
     * @see [类、类#方法、类#成员]
     */
    BizMember findEmpInfoById(String empId);
    
    /**
     * 
     * 更新bizmember信息
     * 
     * @param member
     * @return
     * @see [类、类#方法、类#成员]
     */
    int updateEmpInfo(BizMember member);
    
    /**
     * 删除员工
     * 
     * @param orgId
     * @param empId
     * @param orgName
     * @return
     * @see [类、类#方法、类#成员]
     */
    int delEmp(String orgId, String empId, String orgName);
}
