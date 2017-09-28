package com.wb.component.computer.processManage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;








import net.minidev.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.processManage.service.IProcessManageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntProcessInfoTmp;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;
import com.wb.model.entity.computer.processManage.MntNodeInfo;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

/**
 * 流程管理Controller
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-5-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class ProcessManage extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(ProcessManage.class);
    
    /**
     * 流程管理Service层实例
     */
    @Autowired
    @Qualifier("processManageService")
    private IProcessManageService processManageService;
    
    
    /**
     * 组织架构ser
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    
    /**
     * 获取流程列表
     * 
     * @param session session
     * @param processName 流程名称
     * @param mngName 所属代账公司名称
     * @param flag 流程状态
     * @param info 分页信息
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getProcessList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessList(HttpSession session, String processName, String canUse, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final PageUtil util = processManageService.getProcessList(mngId, processName, canUse, info);
        return util.initResult();
    }
    
    
    /***
     * 
    * @Title: getAllBusinessModel 
    * @Description: 根据业务状态查询业务模板
    * @param @param session
    * @param @param processName 流程名称
    * @param @param canUse 业务状态码表
    * @param @param info 页面大小数据
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>  返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/getAllBusinessModel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllBusinessModel(HttpServletRequest request,HttpSession session, String canUse, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        int adminId = member.getEmpInfo().getId();
        final PageUtil util = processManageService.getModelList(adminId,canUse, info);
       // request.setAttribute("id",util );
        return util.initResult();
    }
    
    /****
    * @Title: heleTogether 
    * @Description:协同按钮操作
    * @param @param array
    * @param @param processTmpId
    * @param @param nodeId
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/heleTogether", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> heleTogether(String array,Integer processTmpId,Integer nodeId)
    {	
    	Map<String, Object> result = new HashMap<String, Object>();
        final int code = processManageService.savegetTogether(array, processTmpId,nodeId);
        result.put("code", code);
        return result;
    	
    }
    @RequestMapping(value = "asyn/processHeleTogether", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> processHeleTogether(String array,Integer processId,Integer nodeId)
    {	
    	Map<String, Object> result = new HashMap<String, Object>();
        final int code = processManageService.saveProcessTogether(array, processId,nodeId);
        result.put("code", code);
        return result;
    	
    }
    
    
    
    /****
    * @Title: getPrecessNode 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param request
    * @param @param session
    * @param @param canUse
    * @param @param info
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/getPrecessNode",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPrecessNode(HttpServletRequest request,HttpSession session, Integer processTmpId,Integer nodeId,PageInfo info)
    {
    	 final MntMember member = (MntMember)session.getAttribute("userInfo");
         final int mngId = member.getOrgId();
         final PageUtil util = processManageService.getHelpNode(mngId,processTmpId,nodeId,info);
         return util.initResult();
    }
    
    /****
     * @Title: getPrecessNode 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param request
     * @param @param session
     * @param @param canUse
     * @param @param info
     * @param @return
     * @author hechunyang 
     * @return Map<String,Object>    返回类型 
     * @throws
      */
     @RequestMapping(value = "asyn/getPrecessNodeInfo",method = RequestMethod.POST)
     @ResponseBody
     public Map<String, Object> getPrecessNodeInfo(HttpServletRequest request,HttpSession session, Integer processId,Integer nodeId,PageInfo info)
     {
     	 final MntMember member = (MntMember)session.getAttribute("userInfo");
          final int mngId = member.getOrgId();
          final PageUtil util = processManageService.getHelpNodeInfo(mngId,processId,nodeId,info);
          return util.initResult();
     }
    
    /**
     * @Title: gotoBusinessModelShowNode 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param processTmpId 模板id
     * @param @param request
     * @param @return
     * @author hechunyang 
     * @return String    返回类型 
     * @throws
      */
     @RequestMapping(value="forward/gotoBusinessModelShowNode")
     @ResponseBody
     public Map<String, Object> gotoBusinessModelShowNode(String processTmpId,HttpServletRequest request){
     	PageInfo pageInfo=new PageInfo(1, 10);
     	 final PageUtil util =processManageService.getModelNodeList(processTmpId,pageInfo);
     	return util.initResult();
     }
    
    
    
    /**
     * 添加流程
     * 
     * @param session session
     * @param processInfo 流程实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/saveProcess", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProcess(HttpSession session, MntProcessInfo processInfo)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final int code = processManageService.saveProcess(mngId, processInfo);
        result.put("code", code);
        return result;
    }
    
    /***
     * 
    * @Title: SaveNode 
    * @Description:在外部增加节点
    * @param @param session
    * @param @param mntNodeInfo
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/SaveNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> SaveNode(HttpSession session, MntNodeInfo mntNodeInfo)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        int adminId = member.getEmpInfo().getId();
        final int code = processManageService.saveNodes(mntNodeInfo,adminId);
        result.put("code", code);
        return result;
    }
    
    /***
     * 
    * @Title: SaveNode 
    * @Description:在外部增加节点
    * @param @param session
    * @param @param mntNodeInfo
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/saveProcessNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProcessNode(HttpSession session, MntNodeInfo mntNodeInfo)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        int adminId = member.getEmpInfo().getId();
        final int code = processManageService.saveProcessNodes(mntNodeInfo,adminId);
        result.put("code", code);
        return result;
    }
    
    @RequestMapping(value = "asyn/deleteNodeAfter", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteNodeAfter(Integer id,HttpServletRequest request,HttpSession session)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
        int adminId = member.getEmpInfo().getId();
    	int code=processManageService.deleteNode(id,adminId);
    	result.put("code", code);
    	return result;
    }
    
   
    /**
     * 删除流程
     * 
     * @param processInfo 流程实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/deleteProcess", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProcess(MntProcessInfo processInfo)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = processManageService.deleteProcess(processInfo);
        result.put("code", code);
        return result;
    }
    
    @RequestMapping(value = "asyn/deleteProcessSoft", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProcessSoft(MntProcessInfo processInfo)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = processManageService.deleteProcessSoft(processInfo);
        result.put("code", code);
        return result;
    }
    
    /**
     * 保存节点
     * 
     * @param nodes 节点实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/saveNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveNode(@RequestBody
    List<MntNodeInfo> nodes)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = processManageService.saveNode(nodes);
        result.put("code", code);
        return result;
    }
    
    /***
     * 
    * @Title: saveNodeAfter 
    * @Description: 添加业务节点使其生成关系
    * @param @param nodes
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/asyn/saveNodeAfter", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> saveNodeAfter(@RequestBody List<MntNodeInfo> nodes,HttpSession session)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	final boolean code = processManageService.saveNodeAfter(nodes,adminId);
    	result.put("code", code);
    	return result;
    }
    /**
     * @throws JSONException *
     * 
    * @Title: saveNodeAfter 
    * @Description: 添加业务节点使其生成关系
    * @param @param nodes
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/asyn/saveNodeAfter1", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> saveNodeAfter1(String nodes,HttpSession session) throws JSONException
    {
    	Gson gson = new Gson();
    	List<MntNodeInfo> list =gson.fromJson(nodes, new TypeToken<List<MntNodeInfo>>() {}.getType());

    	Map<String, Object> result = new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	final boolean code = processManageService.saveNodeAfter(list,adminId);
    	result.put("code", code);
    	return result;
    }
    /****
    * @Title: saveProcessTemp 
    * @Description: 修改业务名称 
    * @param @param nodes
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/saveProcessTemp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProcessTemp(@RequestBody List<MntProcessInfoTmp> nodes,HttpSession session)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	final int code = processManageService.updateProcessInfoTemp(nodes.get(0),adminId);
    	result.put("code", code);
    	return result;
    }
    
    /***
     * 
    * @Title: addUserTempAndNode 
    * @Description:模板的导入
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/addUserTempAndNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUserTempAndNode(HttpSession session,String array)
    {
    	Map<String,Object> result=new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	int code=processManageService.addUserTempAndNode(array,adminId);
    	result.put("code", code);
    	return result;
    }
    
    /***
    * @Title: deleteProcessTemp 
    * @Description: 删除业务模板 
    * @param @param id
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/deleteProcessTemp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProcessTemp(Integer id,HttpSession session)
    {	
    	Map<String, Object> result=new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	boolean code=processManageService.deleteNodetemp(id,adminId);
    	result.put("code", code);
    	return result;
    }
    
    /***
     * 
    * @Title: saveBusiness 
    * @Description: 增加业务模板
    * @param canUser 业务类型
    * @param businessName 业务名称
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value = "asyn/saveBusiness", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveBusiness(String canUse,String buinessName,HttpSession session)
    {	
    	Map<String, Object> result=new HashMap<String, Object>();
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	int adminId = member.getEmpInfo().getId();
    	int code=processManageService.saveBusiness(adminId,canUse,buinessName);
    	result.put("code", code);
    	return result;
    }
    
    
    /**
     * 获取业务列表
     * 
     * @param orgId 客户ID
     * @param info 分页信息
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getServiceList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getServiceList(int orgId, PageInfo info)
    {
        final PageUtil util = processManageService.getServiceList(orgId, info);
        return util.initResult();
    }
    
//    /**
//     * 启动业务流
//     * 
//     * @param orgProcessInfo 客户流程实体
//     * @return Map<String, Object>
//     */
//    @RequestMapping(value = "asyn/openService", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> openService(MntOrgProcessInfo orgProcessInfo)
//    {
//        Map<String, Object> result = new HashMap<String, Object>();
//        final boolean code = processManageService.saveService(orgProcessInfo);
//        result.put("code", code);
//        return result;
//    }
    /**
     * 启动业务流
     * 
     * @param orgProcessInfo 客户流程实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/openService", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> openService(int orgId,int processId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code =processManageService.updateProcessInfo(orgId,processId);
        result.put("code", code);
        return result;
    }
    /**
     * 启动业务流
     * 
     * @param orgProcessInfo 客户流程实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/openServiceByContract", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> openServiceByContract(int contractId,int processId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code =processManageService.updateProcessInfoByContract(contractId,processId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 关闭业务流程
     * 
     * @param orgProcessId 客户业务流程ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/asyn/deleteService", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteService(int orgProcessId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = processManageService.deleteService(orgProcessId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取主页面业务代办事件列表
     * 
     * @param session session
     * @return Map<String, Object>
     */
    /*@RequestMapping(value = "asyn/getMainServiceInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMainServiceInfo(HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int memberId = member.getId();
        final int mngId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        final List<Map<String, Object>> roleIdList = member.getRoleInfo();
        final Map<String, Object> result = processManageService.getMainServiceInfo(isAdmin, memberId, roleIdList, mngId);
        return result;
    }*/
    
    /**
     * 完成业务节点
     * 
     * @param ophId 历史记录ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/doneService", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doneService(HttpSession session, int ophId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creatorId = member.getId();
        final String creatorName = member.getUserName();
        final boolean code = processManageService.updateService(ophId, creatorId, creatorName);
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取页面更多业务
     * 
     * @param session session
     * @param orgName 客户名称
     * @param stamp 时间戳
     * @param flag 完成度
     * @param info 分页信息
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getMoreServiceInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMoreServiceInfo(HttpSession session, String orgName, String stamp, String flag, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int memberId = member.getId();
        final int mngId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        final List<Map<String, Object>> roleIdList = member.getRoleInfo();
        final PageUtil util = processManageService.getMoreServiceInfo(isAdmin, memberId, roleIdList, mngId, orgName, stamp, flag, info);
        return util.initResult();
    }
    
    /**
     * 获取人员下拉列表
     * 
     * @param session
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value = "asyn/getChMemSel", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getChMemSel(HttpSession session)
    {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creater = member.getOrgId();
        final String partId = member.getDepartmentId();
        final int memberId = member.getId();
        List<Map<String, Object>> workInfo = frameworkSer.findMntFrameWorkInfo(memberId, member.isAdmin(), Integer.toString(creater), "yes", partId);
        for (Map<String, Object> map : workInfo)
        {
            if ("BY_EMP".equals(map.get("cmd")))
            {
                Map<String, Object> mem = new HashMap<String, Object>();
                mem.put("memId", map.get("id"));
                mem.put("memName", map.get("partName"));
                result.add(mem);
            }
        }
        return result;
    }
    
    /**
     * 指派人员
     * 
     * @param ophId 客户流程历史ID
     * @param memberId 成员ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/updateServiceMember", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateServiceMember(String ophId, String memberId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = processManageService.updateServiceMember(ophId, memberId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 页面跳转 流程设置页面
     * 
     * @return String
     */
    @RequestMapping(value = "forward/gotoProcessSetting", method = RequestMethod.GET)
    public String gotoProcessSetting()
    {
        return "processManage/processSetting";
    }
    
    /**
     * 页面跳转 添加节点页面
     * 
     * @param request request
     * @param proId 流程ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoAddNode", method = RequestMethod.GET)
    public String gotoAddNode(HttpServletRequest request, int proId)
    {
        request.setAttribute("proId", proId);
        return "processManage/addNode";
    }
    
    /**
     * 页面跳转 编辑节点页面
     * 
     * @param request request
     * @param proId 流程ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoUpdateNode", method = RequestMethod.GET)
    public String gotoUpdateNode(HttpServletRequest request, int proId)
    {
        final List<Map<String, Object>> nodes = processManageService.getUpdateNodes(proId);
        request.setAttribute("proId", proId);
        request.setAttribute("nodes", nodes);
        return "processManage/updateNode";
    }
    
    /****
    * @Title: gotoUpdateNodeAfter 
    * @Description: 点击编辑节点的时候
    * @param @param request
    * @param @param proId
    * @param @return
    * @author hechunyang 
    * @return String    返回类型 
    * @throws
     */
    @RequestMapping(value = "forward/gotoUpdateNodeAfter", method = RequestMethod.GET)
    public String gotoUpdateNodeAfter(HttpServletRequest request, int processTmpId)
    {
    	List<Map<String, Object>> nodes = processManageService.getUpdateNodesAfter(processTmpId);
    	request.setAttribute("processTmpId", processTmpId);
    	//Collections.sort(nodes);
    	request.setAttribute("nodes", nodes);
    	return "business/updateNode";
    }
    /****
    * @Title: getNodeInfo 
    * @Description: 点击编辑节点的时候
    * @param @param request
    * @param @param proId
    * @param @return
    * @author hechunyang 
    * @return String    返回类型 
    * @throws
     */
    @RequestMapping(value = "forward/getNodeInfo", method = RequestMethod.GET)
    public String getNodeInfo(HttpServletRequest request, int processId)
    {
    	MntProcessInfo processInfo=processManageService.getProcessInfo(processId);
    	 BizOrganization nntCustomInfo=customerManageService.getCustomName(processInfo.getMngId().toString());
    	List<Map<String, Object>> nodes = processManageService.getProcessNodesAfter(processId);
    	
    	request.setAttribute("nodes", nodes);
  	    request.setAttribute("orgName", nntCustomInfo.getName());
  	    request.setAttribute("processName", processInfo.getProcessName());
  	    request.setAttribute("orgId", processInfo.getMngId());
  	    request.setAttribute("seqCode", nntCustomInfo.getSeqCode());
  	    request.setAttribute("processType", processInfo.getProcessType());
        request.setAttribute("processId", processId);
    	//Collections.sort(nodes);
    	request.setAttribute("nodes", nodes);
    	return "processManage/createService";
    }
    
    @RequestMapping(value = "forward/getNodeInfoByContract", method = RequestMethod.GET)
    public String getNodeInfoByContract(HttpServletRequest request, int processId)
    {
    	MntProcessInfo processInfo=processManageService.getProcessInfo(processId);
    	 //BizOrganization nntCustomInfo=customerManageService.getCustomName(processInfo.getMngId().toString());
    	List<Map<String, Object>> nodes = processManageService.getProcessNodesAfter(processId);
    	
    	//request.setAttribute("nodes", nodes);
  	    //request.setAttribute("orgName", nntCustomInfo.getName());
  	    //request.setAttribute("processName", processInfo.getProcessName());
  	    //request.setAttribute("orgId", processInfo.getMngId());
  	    //request.setAttribute("processType", processInfo.getProcessType());
        request.setAttribute("processId", processId);
        request.setAttribute("contractId", processInfo.getCusContractId());
    	//Collections.sort(nodes);
    	request.setAttribute("nodes", nodes);
    	return "business/addProcessByContract";
    }
    
    /**
     * 页面跳转 业务办理页面
     * 
     * @param request
     * @param orgId
     * @return
     */
    @RequestMapping(value = "forward/goToDoService", method = RequestMethod.GET)
    public String goToDoService(HttpServletRequest request, int orgId)
    {
		request.setAttribute("orgId", orgId);
		return "processManage/doService";
    }
    
    /**
     * 页面跳转 更多业务页面
     * 
     * @return String
     */
    @RequestMapping(value = "forward/gotoMoreService", method = RequestMethod.GET)
    public String gotoMoreService()
    {
        return "processManage/moreService";
    }
    
}
