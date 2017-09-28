package com.wb.component.computer.processManage.service;

import java.util.List;
import java.util.Map;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntProcessInfoTmp;
import com.wb.model.entity.computer.processManage.MntNodeInfo;
import com.wb.model.entity.computer.processManage.MntOrgProcessInfo;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

/**
 * 流程管理Service层接口
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-5-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IProcessManageService
{
    /**
     * 获取流程列表
     * 
     * @param mngId 所属代账公司ID
     * @param processName 流程名称
     * @param mngName 所属代账公司
     * @param flag 流程状态
     * @param info 分页信息
     * @return PageUtil
     */
    PageUtil getProcessList(int mngId, String processName, String canUse, PageInfo info);
    
    /**
     * 添加流程
     * 
     * @param mngId 所属代账公司ID
     * @param processInfo 流程实体
     * @return int
     */
    int saveProcess(int mngId, MntProcessInfo processInfo);
    
    /**
     * 验证流程名称是否重复
     * 
     * @param processName 流程名称
     * @return boolean
     */
    boolean checkProcessNameRepeat(int mngId, String processName);
    
    /**
     * 删除流程
     * 
     * @param processInfo 流程实体
     * @return boolean
     */
    boolean deleteProcess(MntProcessInfo processInfo);
    
    /**
     * 删除流程软删除
     * 
     * @param processInfo 流程实体
     * @return boolean
     */
    boolean deleteProcessSoft(MntProcessInfo processInfo);
    
    /**
     * 给相应流程添加节点
     * 
     * @param nodes 节点实体
     * @return boolean
     */
    boolean saveNode(List<MntNodeInfo> nodes);
    
    /**
     * 获取节点版本号
     * 
     * @param processId
     * @return int
     */
    int getNodeVersion(int processId);
    
    /**
     * 获取待更新的节点信息
     * 
     * @param processId 流程ID
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> getUpdateNodes(int processId);
    
    /**
     * 获取业务列表
     * 
     * @param orgId 客户ID
     * @param info 分页信息
     * @return PageUtil
     */
    PageUtil getServiceList(int orgId, PageInfo info);
    
    /**
     * 启动业务流
     * 
     * @param orgProcessInfo 客户流程实体
     * @return boolean
     */
    boolean saveService(MntOrgProcessInfo orgProcessInfo);
    
    /**
     * 获取初始节点ID
     * 
     * @param processId 流程ID
     * @param version 流程版本号
     * @return Integer
     */
    Integer getInitNodeId(int processId, int version);
    
    /**
     * 关闭任务
     * 
     * @param orgProcessId 任务ID
     * @return boolean
     */
    boolean deleteService(int orgProcessId);
    
    /**
     * 获取主页面业务代办事件列表
     * 
     * @param isAdmin 是否为管理员
     * @param roleIdList 角色ID列表
     * @param mngId 所属公司ID
     * @return Map<String, Object>
     */
    public Map<String, Object> getMainServiceInfo(boolean isAdmin, int memberId, List<Map<String, Object>> roleIdList, int mngId);
    
    /**
     * 完成流程中的节点任务
     * 
     * @param ophId 历史记录ID
     * @return boolean
     */
    boolean updateService(int ophId, int creatorId, String creatorName);
    
    /**
     * 获取下一个节点信息
     * 
     * @param nodeId 当前节点ID
     * @return Map<String, Object>
     */
    public Map<String, Object> getNextNodeInfo(int nodeId);
    
    /**
     * 获取更多业务列表
     * 
     * @param isAdmin 是否为管理员
     * @param roleIdList 角色ID列表
     * @param mngId 所属公司ID
     * @param orgName 客户名称
     * @param stamp 办理时间
     * @param flag 完成度
     * @param info 分页信息
     * @return PageUtil
     */
    PageUtil getMoreServiceInfo(boolean isAdmin, int memberId, List<Map<String, Object>> roleIdList, int mngId, String orgName, String stamp, String flag, PageInfo info);
    
    /**
     * 根据客户流程ID获取相应的节点信息
     * 
     * @param orgProId 客户流程ID
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> getOrgProNodeList(int orgProId);
    
    /**
     * 指派业务人员
     * 
     * @param ophId 客户流程历史ID
     * @param memberId 成员ID
     * @return boolean
     */
    boolean updateServiceMember(String ophId, String memberId);
    
    /**
     * 创建默认的业务流程
     * 
     * @param processName 流程名称
     * @param mngId 所属代账公司ID
     * @return boolean
     */
    boolean createDefaultProcess(int mngId, String processName, List<Map<String, Object>> nodeInfo);
    /**
     * 
    * @Title: getModelList 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param mngId
    * @param @param canUse
    * @param @param info
    * @param @return
    * @author hechunyang 
    * @return PageUtil    返回类型 
    * @throws
     */
	PageUtil getModelList(int mngId, String canUse, PageInfo info);
	
	/***
	 * 
	* @Title: getModelNodeList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param processTmpId
	* @param @param info
	* @param @return
	* @author hechunyang 
	* @return PageUtil    返回类型 
	* @throws
	 */
	PageUtil getModelNodeList(String processTmpId, PageInfo info);
	
	/**
	 * 
	* @Title: addNodeInfo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @author hechunyang 
	* @return void    返回类型 
	* @throws
	 */
	void addNodeInfo(String id);
	
	/***
	 * 
	* @Title: saveNodes 
	* @Description: 给相应的业务模板增加节点 
	* @param @param mntNodeInfo
	* @param @return
	* @author hechunyang 
	* @return int    返回类型 
	* @throws
	 */
	int saveNodes(MntNodeInfo mntNodeInfo,Integer adminId);
	/***
	 * 
	* @Title: saveNodes 
	* @Description: 给相应的业务模板增加节点 
	* @param @param mntNodeInfo
	* @param @return
	* @author hechunyang 
	* @return int    返回类型 
	* @throws
	 */
	int saveProcessNodes(MntNodeInfo mntNodeInfo,Integer adminId);
	
	/***
	* @Title: getUpdateNodesAfter 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param processTmpId
	* @param @return
	* @author hechunyang 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	List<Map<String, Object>> getUpdateNodesAfter(int processTmpId);
	/***
	* @Title: getUpdateNodesAfter 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param processTmpId
	* @param @return
	* @author hechunyang 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	List<Map<String, Object>> getProcessNodesAfter(int processId);
	/****
	* @Title: saveNodeAfter 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param nodes
	* @param @return
	* @author hechunyang 
	* @return boolean    返回类型 
	* @throws
	 */
	boolean saveNodeAfter(List<MntNodeInfo> nodes,Integer adminId);
	
	/***
	 * 
	* @Title: deleteNode 
	* @Description: 根据主键删除节点 
	* @param @param id
	* @param @return
	* @author hechunyang 
	* @return boolean    返回类型 
	* @throws
	 */
	int deleteNode(Integer id,Integer adminId);

	List<Map<String,Object>> getProcessTemp(Integer processTmpId);
	/***
	 * 
	* @Title: updateProcessInfoTemp 
	* @Description: 更新业务模板 
	* @param @param mntProcessInfoTmp
	* @param @return
	* @author hechunyang 
	* @return boolean    返回类型 
	* @throws
	 */
	int updateProcessInfoTemp(MntProcessInfoTmp mntProcessInfoTmp,Integer adminId);
	
	/***
	 * 
	* @Title: getHelpNode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mngId
	* @param @param processTmpId
	* @param @param nodeId
	* @param @param info
	* @param @return
	* @author hechunyang 
	* @return PageUtil    返回类型 
	* @throws
	 */
	PageUtil getHelpNode(int mngId, Integer processTmpId, Integer nodeId,
			PageInfo info);
	PageUtil getHelpNodeInfo(int mngId, Integer processId, Integer nodeId,
			PageInfo info);
	/***
	* @Title: savegetTogether 
	* @Description: 协同保存节点
	* @param @param array
	* @param @return
	* @author hechunyang 
	* @return boolean    返回类型 
	* @throws
	 */
	int savegetTogether(String array,Integer processTmpId,Integer nodeId);
	
	int saveProcessTogether(String array,Integer processId,Integer nodeId);
	
	/***
	* @Title: addUserTempAndNode 
	* @Description: 给每个用户增加对象的业务模板 
	* @param @param adminId
	* @author hechunyang 
	* @return void    返回类型 
	* @throws
	 */
	int addUserTempAndNode(String array,int adminId);
	/***
	 * 
	* @Title: deleteNodetemp 
	* @Description: 删除业务模板 
	* @param @param id
	* @param @return
	* @author hechunyang 
	* @return boolean    返回类型 
	* @throws
	 */
	boolean deleteNodetemp(Integer id,Integer adminId);
	/**
	 * 
	* @Title: saveBusiness 
	* @Description:保存业务 (关联具体到哪个用户)
	* @param @param adminId
	* @param @param canUse
	* @param @param buinessName
	* @author hechunyang 
	* @return void    返回类型 
	* @throws
	 */
	int saveBusiness(int adminId, String canUse, String buinessName);

	boolean updateProcessInfo(Integer orgId,Integer processId);
	MntProcessInfo getProcessInfo(Integer id);
	
	boolean updateProcessInfoByContract(Integer contractId,Integer processId);

}
