package com.wb.component.computer.businessManage.service;


import java.util.List;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.Cooperation;
import com.wb.model.entity.computer.Mnt_resumes;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

/**
 * 流程管理Service层接口
 * 
 */
public interface IProcessInfoManageService
{
    /**
     * 获取流程列表
     * 
     */
    PageUtil getProcessList(int mngId, String processName, String cusName,String conType,String contractType ,boolean isAdmin,int adminId, PageInfo info);
    
    
    /**
     * 获取流程环节
     * 
     */
    PageUtil getNodeList (String processId, PageInfo info);
    
    PageUtil getProcessListByOrgId (int orgId, PageInfo info);
    
    int updateNode(String processId ,String nodeId,String status);
    
    PageUtil getDealNodeMan(String processId,String NodeId,PageInfo info);
    
    PageUtil getBeforeMan(String processId,String nodeId,String beforeNodeId,PageInfo info);
    
    PageUtil getAfterMan(String processId,String afterNodeId,PageInfo info);
    //获取该部门下的环节
    PageUtil getDepartmentNodeList (String processId, String departmentId ,boolean isAdmin ,int adminId,PageInfo info);
    
  //获取该部门下的所有员工
    PageUtil getDepartmentUserList (String departmentId ,String name ,String tel ,boolean isAdmin ,int adminId,PageInfo info);
    
    //重新指派环节
    int updateReAppoint(String processId ,String nodeId,String id);
    
    //根据id 查询biz_memeber表
    PageUtil getMemberInfo(String id,PageInfo info);
    
  //根据电话话号码 查询biz_memeber表
    PageUtil getMemberInfoByTel(String id,PageInfo info);

    //根据合同id 查询mnt_customContract表
    PageUtil getContractInfo(String contractId,PageInfo info);
    
  
    //根据合同id 查询mnt_customContract表
    PageUtil getProcessInfo(String processName,String contractId,String processType,String adminId,PageInfo info);

  //根据业务模板id 查询mnt_ProcessInfoTmp表
    PageUtil getProcessInfoTmp(String processId,PageInfo info);
    void saveProcessInfo(MntProcessInfo processInfo);
    void updateProcessInfo(MntProcessInfo processInfo);
 
    
  //添加业务
    int addProcessInfo(String processIdTemp,String mngId,String cusContractId,String adminId);
    //添加环节信息
    int addNodeInfo(String processIdTemp,String mngId,String processId,String adminId);
    
    //根据业务模板id 查询mnt_ProcessInfoTmp表
    PageUtil getProcessInfoById(String processId,PageInfo info);
    // 根据业务id删除业务数据和环节数据
    int deleteNode(String processId);
    int deleteProcess(String processId);
    
    PageUtil getProcessListByContractId (int contractId, PageInfo info);


	boolean checkRepectBusinessTemp(Integer id, String processName,String orgId,String contractId,String isOrgAndContract);


	void saveCooeration(Cooperation cooperation);


	void saveResume(Mnt_resumes resumes);
    
    
	int addInvestment(String invest_name,String invest_comp,String invest_post,String invest_phone,String invest_email,String invest_desc );
    PageUtil getInvstList (String invest_name, String invest_comp,String invest_phone, PageInfo info);
    int updateInvst(String id,String invest_name,String invest_comp,String invest_post,String invest_phone,String invest_email,String invest_desc);
    int deleteInvst(String id);
}
