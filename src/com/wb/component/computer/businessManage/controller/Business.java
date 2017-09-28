package com.wb.component.computer.businessManage.controller;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.businessManage.service.IProcessInfoManageService;
import com.wb.component.computer.businessManage.util.AuthDto;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.component.computer.processManage.service.IProcessManageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.model.entity.computer.Cooperation;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.Mnt_resumes;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;
import com.wb.model.entity.computer.processManage.MntNodeInfo;
import com.wb.model.entity.computer.processManage.MntProcessInfo;

@Controller
@RequestMapping(value = "business")
public class Business extends AjaxAction
{
    private static final Logger logger = Logger.getLogger(Business.class);
    
    /**
     * 流程管理Service层实例
     */
    @Autowired
    //@Qualifier("iProcessInfoManageService")
    private IProcessInfoManageService iProcessInfoManageService;
    @Autowired
    private ICustomerManageService customerManageService;
    
    /**
     * 流程管理Service层实例
     */
    @Autowired
    @Qualifier("processManageService")
    private IProcessManageService processManageService;
	 /**
     * 电脑端组织架构ser
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;




    @Autowired()
    @Qualifier("loginService")
    private ILoginService cpuLoginSer;
    
    
  //返回值的枚举类型
  	public enum ReturnCodeEnum{
  		SUCCESS,FAIL
  	}
    
    
    /**
     * 业务管理模块-跳转页面
     * 
     */
    @RequestMapping(value = "forward/gotoBusinessView", method = RequestMethod.GET)
    public String gotoBusinessView(HttpServletRequest request)
    {
        request.setAttribute("startDate", DateUtil.getCurYearFirstDate());
        request.setAttribute("endDate", DateUtil.getPrevTaxDate());
        //pageName：页面名称
        String pageName =request.getParameter("pageName");
        if("progress".equals(pageName)){//进度查询
            return "business/progressList";
        }else if("model".equals(pageName)){
        	 //业务模板
        	 return "business/businessModel";
        }else if("queryNode".equals(pageName)){
       	 //进入查看业务详细页面
         String proId = request.getParameter("proId");
         String isAdmin = request.getParameter("isAdmin");
         request.setAttribute("proId", proId);
         request.setAttribute("isAdmin", isAdmin);
       	 return "business/queryBusi";
       }else if("zhiPaiNodePage".equals(pageName)){
    	   //进入查看业务详细页面
           String proId = request.getParameter("proId");
           request.setAttribute("proId", proId);
         	 return "business/zhiPaiNodePage";
       }else if("editNamePage".equals(pageName)){
    	   //进入查看业务指派-编辑执行人姓名页面
           String proId = request.getParameter("proId");
           String nodeId = request.getParameter("nodeId");
           String departmentId = request.getParameter("departmentId");
           String isAdmin = request.getParameter("isAdmin");
           String adminId = request.getParameter("adminId");
           System.out.println("-------------departmentId:"+departmentId);
           request.setAttribute("proId", proId);
           request.setAttribute("nodeId", nodeId);
           request.setAttribute("processType", "");
           request.setAttribute("departmentId", departmentId);
           request.setAttribute("adminId", adminId);
         	 //return "business/editName";
           if("true".equals(isAdmin)){
        	   return "business/frameworkManage";
           }else {
        	   return "business/frameworkManageNotAdmin";
           }
           
       }else if("flowPage".equals(pageName)){
         	 //进入流程图页面
           String proId = request.getParameter("proId");
           request.setAttribute("proId", proId);
         	 return "business/flow";
        }else if("selModel".equals(pageName)){
       	 //业务模板
        	String orgId = request.getParameter("orgId");
			request.setAttribute("orgId", orgId);
       	 return "business/selModel";
       }else if("createService".equals(pageName)){ //不带合同的添加业务的跳转
    	   int orgId = Integer.parseInt(request.getParameter("orgId"));
    	   BizOrganization nntCustomInfo=customerManageService.getCustomName(request.getParameter("orgId").toString());
    	   int processId = -1 ;
    	   List<Map<String, Object>> nodes = null  ;
    	   String proId = request.getParameter("processId");
    	   //proId = "4";
    	   if(!"".equals(proId)){
    		   
    		   processId= Integer.parseInt(proId);
    		   nodes = processManageService.getProcessNodesAfter(processId);
    		   MntProcessInfo processInfo=processManageService.getProcessInfo(processId);
    		   request.setAttribute("processName", processInfo.getProcessName());
        	   request.setAttribute("processType", processInfo.getProcessType());
    	   }
		   request.setAttribute("nodes", nodes);
    	   request.setAttribute("orgName", nntCustomInfo.getName());
    	   request.setAttribute("orgId", orgId);
    	   Integer seqCode = nntCustomInfo.getSeqCode();
    	   request.setAttribute("seqCode", seqCode==0?"":seqCode);
           request.setAttribute("processId", processId);
           return "processManage/createService";
       }else if("addProcessByContract".equals(pageName)){ //带合同的添加业务的跳转
    	   String contractId = request.getParameter("contractId");
    	   String processId = request.getParameter("processId");
    	   List<Map<String, Object>> nodes = null  ;
    	   if(!"".equals(processId)&&!"-1".equals(processId)){
    		   nodes = processManageService.getProcessNodesAfter(Integer.parseInt(processId));
    	   }
    	   request.setAttribute("nodes", nodes);
    	   request.setAttribute("contractId", contractId);
    	   request.setAttribute("processId", processId);
	       return "business/addProcessByContract";
       }else if("goToDoService".equals(pageName)){// 由合同页面跳转到业务查询添加页面
    	   String contractId = request.getParameter("contractId");
    	   request.setAttribute("contractId", contractId);
           return "business/doServiceByContract";
       }else if("selModelByContract".equals(pageName)){
         	 //业务模板
          	String contractId = request.getParameter("contractId");
          	String processId = request.getParameter("processId");
          	String orgId= request.getParameter("orgId");
          	request.setAttribute("contractId", contractId);
          	request.setAttribute("processId", processId);
          	request.setAttribute("orgId", orgId);
         	 return "business/selModelByContract";
         }else if("selModelByOrgId".equals(pageName)){
         	 //业务模板
    		String orgId = request.getParameter("orgId");
          	String processId = request.getParameter("processId");
          	request.setAttribute("orgId", orgId);
          	request.setAttribute("processId", processId);
         	 return "business/selModelByOrgId";
         }
       
       
       else{
        	logger.info("-------没有该页面");
        	return "";
        }
        
    }
    
    /**
    * @Title: gotoBusinessModel 
    * @Description: 跳转到业务模板
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return String    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/gotoBusinessModel",method=RequestMethod.GET)
    public String gotoBusinessModel(HttpServletRequest request){
    	return "business/businessModel";
    }
    
    
   /**
    * 
   * @Title: gotoBusinessModelNode 
   * @Description: TODO(这里用一句话描述这个方法的作用) 
   * @param @param modelId 模板的id
   * @param @param request
   * @param @return
   * @author hechunyang 
   * @return String    返回类型 
   * @throws
    */
    @RequestMapping(value="forward/gotoBusinessModelNode",method=RequestMethod.GET)
    public String gotoBusinessModelNode(String modelId,HttpServletRequest request){
    	
    	return "business/businessModel";
    }
   
    /***
    * @Title: gotoBusinesslNode 
    * @Description: 专供跳页面使用 
    * @param @param modelId
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return String    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/gotoBusinesslNode",method=RequestMethod.GET)
    public String gotoBusinesslNode(String processTmpId,HttpServletRequest request){
    	request.setAttribute("processTmpId", processTmpId);
    	return "business/showNode";
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
    @RequestMapping(value="forward/gotoBusinessModelShowNode",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gotoBusinessModelShowNode(@RequestBody String processTmpId,HttpServletRequest request){
    	PageInfo pageInfo=new PageInfo(1, 50);
    	 final PageUtil util =processManageService.getModelNodeList(processTmpId,pageInfo);
    	return util.initResult();
    }
    
    
    /****
     * 
    * @Title: gotoBusinessModelAddNode 
    * @Description: 弹出框增加节点 
    * @param @param processTmpId
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/gotoBusinessModelAddNode",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gotoBusinessModelAddNode( String processTmpId,HttpServletRequest request){
    	PageInfo pageInfo=new PageInfo(1, 10);
    	final PageUtil util =processManageService.getModelNodeList(processTmpId,pageInfo);
    	return util.initResult();
    }
    
    /**
    * @Title: saveNode 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param id
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/saveNode",method=RequestMethod.POST)
    public  Map<String, Object> saveNode(String id,HttpServletRequest request){
    	Map<String, Object> result = new HashMap<String, Object>();
    	processManageService.addNodeInfo(id);
    	return result;
    }
    
    
    /***
     * 
    * @Title: gotoBusinesslAddNode 
    * @Description: 增加节点的小框 
    * @param @param id
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/gotoBusinesslAddNode",method=RequestMethod.GET)
    public  String gotoBusinesslAddNode(String value,HttpServletRequest request){
    	request.setAttribute("value", value);
    	return "business/addNode";	
    }
    
    /**
    * @Title: gotoBusinesslAddNode 
    * @Description:修改业务模板跳转
    * @param @param value
    * @param @param request
    * @param @return
    * @author hechunyang 
    * @return String    返回类型 
    * @throws
     */
    @RequestMapping(value="forward/gotoUpdateProceTemp",method=RequestMethod.GET)
    public  String gotoUpdateProceTemp(HttpServletRequest request,Integer processTmpId){
    	request.setAttribute("processTmpId", processTmpId);
    	List<Map<String,Object>> mntProcessInfoTmp=processManageService.getProcessTemp(processTmpId);
    	request.setAttribute("processInfoTmp", mntProcessInfoTmp);
    	return "business/updateProceTemp";	
    }
    
    @RequestMapping(value="forward/gotoHelpTogether",method=RequestMethod.GET)
    public  String gotoHelpTogether(HttpServletRequest request,Integer processTmpId,Integer nodeId){
    	request.setAttribute("processTmpId", processTmpId);
    	request.setAttribute("nodeId", nodeId);
    	return "business/updateTogether";	
    }
    
    @RequestMapping(value="forward/gotoProcessHelpTogether",method=RequestMethod.GET)
    public  String gotoProcessHelpTogether(HttpServletRequest request,Integer processId,Integer nodeId){
    	request.setAttribute("processId", processId);
    	request.setAttribute("nodeId", nodeId);
    	return "business/updateProcessTogether";	
    
    }
	/***
	 * zhouwenwen
	 */
    
    /**
     * 获取流程列表
     * 
     */
    @RequestMapping(value = "/asyn/getProcessList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessList(HttpSession session, String processName, String cusName,String conType,String contractType, PageInfo info)
    {
    	
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        //判断是不是管理员 
        boolean isAdmin = member.isAdmin();
        int adminId = member.getEmpInfo().getId();
        try {
        	cusName =URLDecoder.decode(cusName,"UTF-8");
        	processName =URLDecoder.decode(processName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final PageUtil util = iProcessInfoManageService.getProcessList(mngId, processName, cusName,conType,contractType,isAdmin ,adminId, info );
        return util.initResult();
    }
    
    /**
     * 获取流程下环节
     * 
     */
    @RequestMapping(value = "/asyn/getNodeList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getNodeList(HttpSession session, String processId, PageInfo info)
    {
    	System.out.println(processId);
    	Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final PageUtil util = iProcessInfoManageService.getNodeList(processId, info);
      
        return util.initResult();
    }
    
    
    /*
     * 启动环节
     * 参数列表：环节id，状态
     */
    
    @RequestMapping(value = "asyn/startNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> startNode(HttpSession session,String processId, String nodeId,String nodeStatus)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final int code = iProcessInfoManageService.updateNode(processId,nodeId,nodeStatus);
        result.put("code", code);
        return result;
    }
    
    /*
     * 交接环节
     * 参数列表：环节id，状态
     */
    
    @RequestMapping(value = "asyn/commitNode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commitNode(HttpSession session,HttpServletRequest request,String processId, String nodeId,String nodeStatus,PageInfo info)
    {
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	// 查询协同人员和下一个处理人员
    	info.setPage(0);
    	info.setRows(50);
    	PageUtil page = iProcessInfoManageService.getDealNodeMan(processId,nodeId ,info);
    	List record = page.getRecords();
    	String beforeNodeId ="" ;
    	String afterNodeId ="" ;
    	for(int i = 0 ; i<record.size();i++){
    		Map map = (Map) record.get(i);
    		beforeNodeId = (String) map.get("beforeNodeId");
    		afterNodeId = (String) map.get("afterNodeId");
    	}
    	String beforeName ="";
    	String beforeTelphone ="";
    	Integer beforeAdminId = 0;
    	Integer mntMemberId =0;
    	//通过processId，beforeNodeId 查询协同人员
    	PageUtil pageBefore = iProcessInfoManageService.getBeforeMan(processId,nodeId,beforeNodeId ,info);
    	List beforeList = pageBefore.getRecords();
    	//获取人员信息 
    	
    	for(int i = 0 ; i<beforeList.size();i++){
    		Map map = (Map) beforeList.get(i);
    		beforeAdminId = (Integer)map.get("adminId");
    		beforeName = (String) map.get("Name");
    		beforeTelphone = (String) map.get("Telphone");
    		mntMemberId = (Integer)map.get("mntMemberId");
    		System.out.println("mntMemberId:"+mntMemberId);
    		System.out.println("beforeAdminId:"+beforeAdminId);
    		
    		// 交接之前 推送webapp提醒  start
       	 	final String message = beforeName+"您好！您的协同环节已经完成,请尽快执行您的环节！";
            final String tabname = "协同环节处理";
            final String path = "business/progressList";
            final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), beforeAdminId, mntMemberId, tabname, 1,beforeTelphone);
            mnc.setDepartmentId("0");
            frameworkSer.insertMsgNotification(mnc);
            // 交接之前 推送webapp提醒  end
            
            
        	// 交接之前 推送短信提醒 start
//       	 beforeTelphone = "18600992681";
//            Map<String, Object> map = SMSSender.sendfeeNotice(beforeTelphone,
//   					new String[] { message });
         // 交接之前 推送短信提醒 end
            
    	}
    	
    	String afterName = "";
    	String afterTelphone = "" ;
    	//通过processId，afterNodeId 查询下一个处理人
    	
    	//afterNodeId 格式：1&2&3
    	String[] afterNodeIds = afterNodeId.split(",");//解析afterNodeId
    	for(int i =0 ; i <afterNodeIds.length;i++){
    		PageUtil pageAfter = iProcessInfoManageService.getAfterMan(processId,afterNodeIds[i] ,info);
    		List afterList = pageAfter.getRecords();
    		//获取人员信息 
        	
        	for(int j = 0 ; j<afterList.size();j++){
        		Map map = (Map) afterList.get(j);
        		Integer afterAdminId = (Integer)map.get("adminId");
        		afterName = (String) map.get("Name");
        		afterTelphone = (String) map.get("Telphone");
        		mntMemberId = (Integer)map.get("mntMemberId");
        		System.out.println("afterAdminId:"+afterAdminId);
        		
        		
        		// 交接之前 推送webapp提醒  start
           	 	final String message = afterName+"您好！您的上级环节已经执行完成,请执行尽快您的环节！";
                final String tabname = "交接环节处理";
                final String path = "business/progressList";
                final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), afterAdminId, mntMemberId, tabname, 1,afterTelphone);
                mnc.setDepartmentId("0");
                frameworkSer.insertMsgNotification(mnc);
                // 交接之前 推送webapp提醒  end
                
                
            	// 交接之前 推送短信提醒 start
//           	 beforeTelphone = "18600992681";
//                Map<String, Object> map = SMSSender.sendfeeNotice(beforeTelphone,
//       					new String[] { message });
             // 交接之前 推送短信提醒 end
        	}
    	}
         
        Map<String, Object> result = new HashMap<String, Object>();
        final int code = iProcessInfoManageService.updateNode(processId,nodeId,nodeStatus);
        result.put("code", code);
        return result;
    }
    
   
    
    /**
     * 获取登录人部门下的流程环节
     * 
     */
    @RequestMapping(value = "/asyn/getDepartmentNodeList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDepartmentNodeList(HttpSession session, String processId, PageInfo info)
    {
    	Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        //判断是不是管理员 
        boolean isAdmin = member.isAdmin();
        int adminId = member.getEmpInfo().getId();
        String departmentId = member.getDepartmentId();
        System.out.println("departmentId:"+departmentId);
    	System.out.println(processId);
        final PageUtil util = iProcessInfoManageService.getDepartmentNodeList(processId,departmentId,isAdmin,adminId, info);
        return util.initResult();
    }
    
    
    /**
     * 根据部门id获取该部门id的所有有效员工
     * 参数：业务id（防止以后使用）,环节id（防止以后使用），部门id
     */
    @RequestMapping(value = "/asyn/getDepartmentUserList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDepartmentUserList(HttpSession session, String processId,String nodeId,String departmentId,String name , String tel, PageInfo info)
    {
    	try {
			name = URLDecoder.decode(name,"UTF-8");
			tel = URLDecoder.decode(tel,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	 boolean isAdmin = member.isAdmin();
         int adminId = member.getEmpInfo().getId();
    	System.out.println("name:"+name);
    	System.out.println("tel:"+tel);
        final PageUtil util = iProcessInfoManageService.getDepartmentUserList(departmentId,name,tel,isAdmin,adminId, info);
        return util.initResult();
    }
    
    
    
    /*
     * 重新指派环节
     * 参数列表：业务id，环节id，员工手机号
     */
    
    @RequestMapping(value = "asyn/reAppoint", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reAppoint(HttpSession session,String processId, String nodeId,String clerkTel,String adminId ,PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	// 根据clerkTel获取人员id 
    	PageUtil pageMember = iProcessInfoManageService.getMemberInfoByTel(clerkTel,info);
    	List listMember = pageMember.getRecords();
    	String id ="" ;
    	for(int l = 0 ;l<listMember.size();l++){
    		Map map = (Map) listMember.get(l);
    		id = (Integer)map.get("id")+"";
    	}
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(id.equals(adminId)){
    		result.put("code", 2);
    		return result;
    	}
        
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final int code = iProcessInfoManageService.updateReAppoint(processId,nodeId,id);
        if(code==0){//指派成功
        	//根据id获取人员信息 id为adminid
        	info.setPage(0);
        	info.setRows(50);
        	PageUtil page = iProcessInfoManageService.getMemberInfo(id,info);
        	List record = page.getRecords();
        	Integer userId = 0;
        	String name="";
        	String telphone = "";
        	Integer mntMemberId = 0;
        	for(int i = 0 ; i<record.size();i++){
        		Map map = (Map) record.get(i);
        		userId = (Integer)map.get("id");
        		name= (String) map.get("Name");
        		telphone = (String) map.get("Telphone");
        		mntMemberId = (Integer)map.get("mntMemberId");
        	}
        		
        	// 重新指派之后 推送webapp提醒  start
        	final String message =name+"，您好！有新环节指派给您,请执行您的环节！";
            final String tabname = "指派环节处理";
            final String path = "business/progressList";
            final MsgNotification mnc = new MsgNotification(null, message, path, 0, new Date(), userId, mntMemberId, tabname, 1,telphone);
            mnc.setDepartmentId("0");
            frameworkSer.insertMsgNotification(mnc);
            // 重新指派之后 推送webapp提醒  end
        	// 重新指派之后 推送短信提醒 
        }
       
        result.put("code", code);
        
        
        return result;
    }
    
    
    
    
    /**
     * 获取流程操作数据-主要作为加载流程图使用
     * 
     */
    @RequestMapping(value = "/asyn/getflowDate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getflowDate(HttpSession session, String processId, PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	final PageUtil util = iProcessInfoManageService.getNodeList(processId, info);
        Map<String, Object> result = new HashMap<String, Object>();
        //String array = JSONArray.toJSONString(util.getRecords());
        //System.out.println("array:"+array);
        
        
        result.put("flowDate", util.getRecords());
        return result;
    }
    /*
     * 获取公司下的所有业务列表
     * orgId 公司id
     * info 分页信息
     */
    @RequestMapping(value = "/asyn/getProcessListByOrgId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessListByOrgId(HttpSession session, int orgId , PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	final PageUtil util =iProcessInfoManageService.getProcessListByOrgId(orgId,info);
    	return util.initResult();
    }
    
    
    
    
    
    
    /**
     * 根据合同id查询合同信息
     * 
     */
    @RequestMapping(value = "/asyn/getContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getContractInfo(HttpSession session, String contractId ,PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	final PageUtil util = iProcessInfoManageService.getContractInfo(contractId, info);
        Map<String, Object> result = new HashMap<String, Object>();
        //String array = JSONArray.toJSONString(util.getRecords());
        //System.out.println("array:"+array);
        if(util.getRecords().size()>0){
        	result.put("contractInfo", util.getRecords().get(0));
            return result;
        }else{
        	result.put("contractInfo", null);
        	 return result;
        }
        
    }
    
    /**
     * 根据业务模板id查询业务模板信息
     * 
     */
    @RequestMapping(value = "/asyn/getProcessInfoTmp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessInfoTmp(HttpSession session, String processId ,PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	final PageUtil util = iProcessInfoManageService.getProcessInfoTmp(processId, info);
        Map<String, Object> result = new HashMap<String, Object>();
        //String array = JSONArray.toJSONString(util.getRecords());
        //System.out.println("array:"+array);
        if(util.getRecords().size()>0){
        	result.put("ProcessInfoTmp", util.getRecords().get(0));
            return result;
        }else{
        	result.put("ProcessInfoTmp", null);
        	 return result;
        }
        
    }
    @RequestMapping(value = "/asyn/saveProcessInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProcessInfo(HttpSession session, MntProcessInfo processInfo)
    {
    	try{
    		final MntMember member = (MntMember)session.getAttribute("userInfo");
    		String processName=processInfo.getProcessName().trim();
    		String orgId = processInfo.getMngId()+"";
    		String contractId = processInfo.getCusContractId()+""; 
    		String isOrgAndContract = "org";
    		boolean flag=iProcessInfoManageService.checkRepectBusinessTemp(member.getEmpInfo().getId(),processName,orgId,contractId,isOrgAndContract);
    		if(flag){
    			Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 10);
        		 result.put("processId", processInfo.getId());
        		return result;
    		}
        	if(null==processInfo.getId()||processInfo.getId().equals("")||processInfo.getId()==-1){
        		processInfo.setId(null);
        		processInfo.setCanUse(0);
        		processInfo.setStamp(new Date());
         		processInfo.setIsDelete(0);
        		processInfo.setCusContractId(0);
        		processInfo.setAdminId(member.getEmpInfo().getId());
        		iProcessInfoManageService.saveProcessInfo(processInfo);
        		Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 0);
        		result.put("processId", processInfo.getId());
        		return result;
        	}else{
        		processInfo.setCanUse(0);
        		processInfo.setStamp(new Date());
        		processInfo.setIsDelete(0);
        		processInfo.setCusContractId(0);
        		processInfo.setAdminId(member.getEmpInfo().getId());
        		iProcessInfoManageService.updateProcessInfo(processInfo);
        		Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 0);
        		result.put("processId", processInfo.getId());
        		return result;
        	}
    	}catch(Exception e){
    		Map<String, Object> result=new HashMap<String, Object>();
    		result.put("code", 1);
    		return result;
    	}
    }
    
    @RequestMapping(value = "/asyn/saveProcessInfoByContract", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProcessInfoByContract(HttpSession session, MntProcessInfo processInfo)
    {
    	try{
    		final MntMember member = (MntMember)session.getAttribute("userInfo");
    		String processName=processInfo.getProcessName().trim();
    		String orgId = processInfo.getMngId()+"";
    		String contractId = processInfo.getCusContractId()+""; 
    		String isOrgAndContract = "contract";
    		boolean flag=iProcessInfoManageService.checkRepectBusinessTemp(member.getEmpInfo().getId(),processName,orgId,contractId,isOrgAndContract);
    		if(flag){
    			Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 10);
        		 result.put("processId", processInfo.getId());
        		return result;
    		}
    		
        	if(null==processInfo.getId()||processInfo.getId().equals("")||processInfo.getId()==-1){
        		processInfo.setId(null);
        		processInfo.setCanUse(0);
        		processInfo.setStamp(new Date());
         		processInfo.setIsDelete(0);
        		processInfo.setAdminId(member.getEmpInfo().getId());
        		processInfo.setProcessName(processName);
        		iProcessInfoManageService.saveProcessInfo(processInfo);
        		Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 0);
        		result.put("processId", processInfo.getId());
        		return result;
        	}else{
        		processInfo.setCanUse(0);
        		processInfo.setStamp(new Date());
        		processInfo.setIsDelete(0);
        		processInfo.setAdminId(member.getEmpInfo().getId());
        		processInfo.setProcessName(processName);
        		iProcessInfoManageService.updateProcessInfo(processInfo);
        		Map<String, Object> result=new HashMap<String, Object>();
        		result.put("code", 0);
        		result.put("processId", processInfo.getId());
        		return result;
        	}
    	}catch(Exception e){
    		Map<String, Object> result=new HashMap<String, Object>();
    		result.put("code", 1);
    		return result;
    	}
    }
    
  
    
    /**
     * 选择业务模板将业务模板数据添加到业务表中-业务id=-1
     * 
     */
    @RequestMapping(value = "/asyn/addProcessInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProcessInfo(HttpSession session, String processIdTemp ,String contractId,String orgId ,PageInfo info,MntProcessInfo processInfo)
    {
    	try {
    		info.setPage(0);
        	info.setRows(50);
        	PageUtil util = iProcessInfoManageService.getProcessInfoTmp(processIdTemp, info); //查询模板业务数据
        	List list = util.getRecords();
        	Map map = (Map) list.get(0);
        	String processName  = (String) map.get("processName");
        	String processType = (String) map.get("processType");
        	final MntMember member = (MntMember)session.getAttribute("userInfo");
        	int adminId = member.getEmpInfo().getId();
        	//添加业务 start
        	processInfo.setId(null);
     		processInfo.setCanUse(0);
     		processInfo.setStamp(new Date());
      		processInfo.setIsDelete(0);
      		processInfo.setProcessName(processName);
      		if(!"".equals(orgId)){
      			processInfo.setMngId(Integer.parseInt(orgId));
      		}
      		
      		processInfo.setProcessType(processType);
      		if(null==contractId||contractId.equals("")){
      			processInfo.setCusContractId(0);
      		}else{
      			processInfo.setCusContractId(Integer.parseInt(contractId));
      		}
     		processInfo.setAdminId(member.getEmpInfo().getId());
     		iProcessInfoManageService.saveProcessInfo(processInfo);
     		
     		//添加业务 end
        	 Integer processId =processInfo.getId();
        	
        	//添加环节
        	int nodeCode = iProcessInfoManageService.addNodeInfo( processIdTemp, orgId, processId+"", adminId+"");

        	Map<String, Object> result = new HashMap<String, Object>();
        	result.put("code", 0);
        	result.put("processId", processId);
            return result;
			
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
        	result.put("code", 1);
        	result.put("processId", -1);
            return result;
		}
    	
        
    }
    
    /**
     * 根据业务查询业务信息
     * 
     */
    @RequestMapping(value = "/asyn/getProcessInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessInfoById(HttpSession session, String processId ,PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(1);
    	final PageUtil util = iProcessInfoManageService.getProcessInfoById(processId, info);
        Map<String, Object> result = new HashMap<String, Object>();
        //String array = JSONArray.toJSONString(util.getRecords());
        //System.out.println("array:"+array);
        if(util.getRecords().size()>0){
        	result.put("ProcessInfo", util.getRecords().get(0));
            return result;
        }else{
        	result.put("ProcessInfo", null);
        	 return result;
        }
        
    }
    
    
    
    /**
     * 选择业务模板将业务模板数据添加到业务表中：业务id!=-1
     * 
     */
    @RequestMapping(value = "/asyn/updateProcessInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProcessInfo(HttpSession session, String processIdTemp ,String processId,String contractId,String orgId ,PageInfo info,MntProcessInfo processInfo)
    {
    	try {
    		//根据老的业务id删除掉环节数据和业务数据
        	int deletNodeCode = iProcessInfoManageService.deleteNode(processId);
        	int deletProCode = iProcessInfoManageService.deleteProcess(processId);
        	if(deletProCode==0&&deletNodeCode==0){
        		
        		info.setPage(0);
            	info.setRows(50);
            	PageUtil util = iProcessInfoManageService.getProcessInfoTmp(processIdTemp, info); //查询模板业务数据
            	List list = util.getRecords();
            	Map map = (Map) list.get(0);
            	String processName  = (String) map.get("processName");
            	String processType = (String) map.get("processType");
            	final MntMember member = (MntMember)session.getAttribute("userInfo");
            	int adminId = member.getEmpInfo().getId();
                //final int processCode = iProcessInfoManageService.addProcessInfo(processIdTemp,orgId,contractId,adminId+"");
            	
            	//添加业务 start
            	processInfo.setId(null);
         		processInfo.setCanUse(0);
         		processInfo.setStamp(new Date());
          		processInfo.setIsDelete(0);
          		if(!"".equals(orgId)){
          			processInfo.setMngId(Integer.parseInt(orgId));
          		}
          		processInfo.setProcessName(processName);
          		processInfo.setProcessType(processType);
          		if(null==contractId||contractId.equals("")){
          			processInfo.setCusContractId(0);
          		}else{
          			processInfo.setCusContractId(Integer.parseInt(contractId));
          		}
         		
         		processInfo.setAdminId(member.getEmpInfo().getId());
         		iProcessInfoManageService.saveProcessInfo(processInfo);
         		
         		//添加业务 end
            	 Integer newProcessId =processInfo.getId();
            	
            	
            	//添加环节
            	int nodeCode = iProcessInfoManageService.addNodeInfo( processIdTemp, orgId, newProcessId+"", adminId+"");

            	Map<String, Object> result = new HashMap<String, Object>();
            	result.put("code", 0);
            	result.put("processId", newProcessId);
                return result;
        		
        	}else{
        		Map<String, Object> result = new HashMap<String, Object>();
        		result.put("code", 1);
            	result.put("processId", -1);
                return result;
        		
        	}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
    		result.put("code", 1);
        	result.put("processId", -1);
            return result;
		}
        
    }
    
    
    /*
     * 获取合同下的所有业务列表
     * orgId 公司id
     * info 分页信息
     */
    @RequestMapping(value = "/asyn/getProcessListByContractId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProcessListByContractId(HttpSession session, int contractId , PageInfo info)
    {
    	info.setPage(0);
    	info.setRows(50);
    	final PageUtil util =iProcessInfoManageService.getProcessListByContractId(contractId,info);
    	return util.initResult();
    }
    
//    @RequestMapping(value = "/asyn/subInvst", method = RequestMethod.POST)
//    public Map<String, Object> subInvst(HttpSession session,HttpServletResponse res,@RequestParam(value = "callBack", ) String callBack)
//    
    @RequestMapping(value="/asyn/subInvst")
	public void subInvst(HttpServletResponse res,String invest_name,String invest_comp,String invest_post,String invest_phone,String invest_email,String invest_desc , @RequestParam(value = "callBack", required = false, defaultValue = "") String callBack){
    	Map<String, Object> result=new HashMap<String, Object>();
		
		int code =iProcessInfoManageService.addInvestment(invest_name, invest_comp, invest_post, invest_phone, invest_email, invest_desc);
		result.put("code", code);
    	returnAjax(ReturnCodeEnum.SUCCESS,"成功",result,res,callBack);
    }
    
  //返回工具方法
  	public void returnAjax(ReturnCodeEnum em,String msg,Object results,HttpServletResponse res,String callBack){
  			AuthDto dto=new AuthDto();
  			dto.setReturnMSG(msg);
//  			String resultsStr=JSONValue.toJSONString(results);
//  			dto.setResults(resultsStr);
  			dto.setResults(results);
  			switch (em) {
  			case SUCCESS:
  				dto.setReturnCode("0");
  				break;
  			case FAIL:
  				dto.setReturnCode("1");				
  				break;
  			}
  			returnAjaxBeanCallBack2(dto,res,callBack);
//  			returnAjaxBean(results, res);
  	}
  	
  //跨域ajax增加callBack()外包
  	public void returnAjaxBeanCallBack2(Object bean,HttpServletResponse response,String callBack){
  		PrintWriter writer = null;
  		try {
  			response.setCharacterEncoding("UTF-8");
  			response.setContentType("text/html;charset=UTF-8"); 
  			writer =  response.getWriter();
//  			writer.println(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));
  			//增加一个json字符串的变量，将其中的转义字符"\/"替换为"/"：解决数据库中的路径格式转义问题。
  			String jsonStr="";
  			if(null==callBack||"".equals(callBack)){
  				jsonStr=(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS));	
  			}else{
  				jsonStr=callBack+"("+(JSONValue.toJSONString(bean, JSONStyle.NO_COMPRESS))+")";	
  			}
  			
  			jsonStr=jsonStr.replaceAll("\\\\/","/");
  			writer.println(jsonStr);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			logger.error(e.getMessage(), e);
  		}finally{
  			if(writer != null){
  				writer.close();
  			}
  		}
  	}
    

    
    /**
     * 
     *  查询投资意向列表
     * 条件：投资人姓名，投资人公司名称，投资人电话
     * @param session
     * @param invest_name
     * @param invest_comp
     * @param invest_phone
     * @param info
     * @return
     */
     
    
    @RequestMapping(value = "/asyn/queryInvst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryInvst(HttpSession session, String invest_name, String invest_comp,String invest_phone, PageInfo info)
    {
    	
        try {
        	invest_comp =URLDecoder.decode(invest_comp,"UTF-8");
        	invest_name =URLDecoder.decode(invest_name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final PageUtil util = iProcessInfoManageService.getInvstList(invest_name, invest_comp, invest_phone, info );
        return util.initResult();
    }
    
    /** 根据id修改投资信息
     * 
     * @param session
     * @param id
     * @param invest_name
     * @param invest_comp
     * @param invest_post
     * @param invest_phone
     * @param invest_email
     * @param invest_desc
     * @return
     */
    @RequestMapping(value = "/asyn/saveInvst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveInvst(HttpSession session, String id,String invest_name,String invest_comp,String invest_post,String invest_phone,String invest_email,String invest_desc)
    {
    	int code = iProcessInfoManageService.updateInvst(id, invest_name, invest_comp, invest_post, invest_phone, invest_email, invest_desc);
    	Map<String, Object> result=new HashMap<String, Object>();
		result.put("code", code);
		return result;
    	
    }
    
    
    /**
     * 根据id删除投资信息
     * @param session
     * @param id
     * @return
     */
    @RequestMapping(value = "/asyn/deleteInvst", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteInvst(HttpSession session, String id)
    {
    	int code = iProcessInfoManageService.deleteInvst(id);
    	Map<String, Object> result=new HashMap<String, Object>();
		result.put("code", code);
		return result;
    	
    }
    
    
    
    public static void main(String[] args) {
		List<Integer> lis= new ArrayList<Integer>();
		lis.add(1);
		lis.add(2);
		for(int i = 0 ; i <lis.size()-1;i++){
			System.out.println(lis.get(i)+1!=lis.get(i+1));
		}
    	
	}
    
    @RequestMapping(value = "/asyn/testList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> testList(@RequestBody List<MntNodeInfo> nodes)
    {
    	System.out.println(nodes.size());
    	
    	return null ;
    }
    
    /***
     * 
    * @Title: saveCooeration 
    * @Description:合作意向填写
    * @param @param cooperation
    * @param @return
    * @date 2016年10月31日 下午4:37:09 
    * @author hechunyang 
    * @return Map<String,Object> 返回类型 
    * @throws
     */
    
    @RequestMapping(value="/asyn/saveCooeration")
	public void saveCooeration(HttpServletResponse res,Cooperation cooperation , @RequestParam(value = "callBack", required = false, defaultValue = "") String callBack){
    	Map<String, Object> map=new HashMap<String, Object>();
    	iProcessInfoManageService.saveCooeration(cooperation);
    	map.put("code", 0);
    	returnAjax(ReturnCodeEnum.SUCCESS,"成功",map,res,callBack);
    }
    /***
     * 
    * @Title: saveResume 
    * @Description: 简历填写保存
    * @param @param cooperation
    * @param @return
    * @date 2016年10月31日 下午4:57:07 
    * @author hechunyang 
    * @return Map<String,Object> 返回类型 
    * @throws
     */
    
    @RequestMapping(value="/asyn/saveResume")
   	public void saveResume(HttpServletResponse res,Mnt_resumes resumes , @RequestParam(value = "callBack", required = false, defaultValue = "") String callBack){
    	Map<String, Object> map=new HashMap<String, Object>();
    	iProcessInfoManageService.saveResume(resumes);
    	map.put("code", 0);
       	returnAjax(ReturnCodeEnum.SUCCESS,"成功",map,res,callBack);
       }
    
    
}
