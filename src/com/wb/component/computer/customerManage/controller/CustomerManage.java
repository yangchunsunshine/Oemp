package com.wb.component.computer.customerManage.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import util.SendMessageUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.computer.auditSettings.service.IAuditSettingsService;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.frameworkManage.service.IFrameworkManageService;
import com.wb.component.computer.sendMessageManager.service.ISendMessageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.ExcelUtil;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntOrgtax;
import com.wb.model.entity.computer.cusManage.MntCustomContract;
import com.wb.model.entity.computer.cusManage.MntCustomTaxInfo;
import com.wb.model.pojo.computer.CustomFollowInfo;
import com.wb.model.pojo.computer.CustomInfoVo;
import com.wb.model.pojo.computer.CustomPayVo;

/**
 * 客戶管理Controller
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class CustomerManage extends AjaxAction
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(CustomerManage.class);
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 审批设置Service层实例
     */
    @Autowired
    @Qualifier("auditSettingsService")
    private IAuditSettingsService auditSettingsService;
    
    /**
     * frameworkSer frameworkSer
     */
    @Autowired
    @Qualifier("frameworkManageService")
    private IFrameworkManageService frameworkSer;
    
	@Autowired
	private ISendMessageService sendMessageService;
    
    /**
     * 获取公司信息列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cmd 查询方式（BY_EMP,BY_DEP）
     * @param init 是否是初始化数据
     * @param queryId 查询ID
     * @param companySearchName 编号或客户名称
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getCompanyInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCompanyInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, boolean init, String cmd, String conType, String disFlag, String queryId, String companySearchName, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        //判断是不是管理员
        if(member.isAdmin()&& init){
        	final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, queryId, orgId, companySearchName, info);
   		    return util.initResult();
        }else if(!member.isAdmin()&& init){
        	if(queryId.equals(member.getEmpInfo().getId().toString())){
      			final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, queryId, orgId, companySearchName, info);
      			return util.initResult();
          	}else{
          		final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, "", orgId, companySearchName, info);
         		return util.initResult();
          	}
        }else{
        	if(!member.isAdmin()){
        		if(queryId.equals(member.getEmpInfo().getId().toString())){
          			final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, queryId, orgId, companySearchName, info);
          			return util.initResult();
              	}else{
              		final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, "", orgId, companySearchName, info);
             		return util.initResult();
              	}
        	}else{
        		final PageUtil util = customerManageService.getCompanyInfo(cmd, conType, disFlag, queryId, orgId, companySearchName, info);
         		return util.initResult();
        	}
        }
    }
    
    /**
     * 创建客户资料
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 客户资料POJO类
     * @param emp 分配的客戶主管
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/createCustom", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createCustom(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomInfoVo vo, String emp,
    		String bookName, String standard, String startTime)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            int userId;
            if (member.isAdmin())
            {
                userId = Integer.parseInt(emp);
            }
            else
            {
                userId = member.getId();
            }
            String code = customerManageService.createCustom(vo, Integer.toString(userId));
            if (code.length() > 2)
            {
                List<Map<String, Object>> bookInfo = customerManageService.getCreateBookMess(code);
                result.put("bookInfo", bookInfo);
                code = "1";
            }
            result.put("code", code);
            //加入会计准则
            if("on".equals(vo.getSaveCreate())){
            	result.put("bookName", bookName);
            	result.put("standard", standard);
            	result.put("startTime", startTime);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
        }
        return result;
    }
    
    /**
     * 编辑客户信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 客户资料POJO类
     * @param cusId 客户ID
     * @param emp 分配的客户主管
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/editCustom", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCustom(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomInfoVo vo, String cusId, String emp)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            final MntMember member = (MntMember)session.getAttribute("userInfo");
            int userId;
            if (member.isAdmin())
            {
                userId = Integer.parseInt(emp);
            }
            else
            {
                userId = member.getId();
            }
            final int code = customerManageService.updateCustom(vo, cusId, Integer.toString(userId));
            result.put("code", code);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
        }
        return result;
    }
    
    /**
     * 获取跟进信息列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cusId 客户ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getFollowInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFollowInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, String cusId, PageInfo info)
    {
        final PageUtil util = customerManageService.getFollowInfo(cusId, info);
        return util.initResult();
    }
    
    /**
     * 保存跟进信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 客户跟进信息POJO类
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/saveFollowInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveFollowInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomFollowInfo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final boolean code = customerManageService.saveFollowInfo(vo, Integer.toString(userId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 删除跟进信息
     * 
     * @param request request
     * @param response response
     * @param followId 跟进ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/deleteFollowInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteFollowInfo(HttpServletRequest request, HttpServletResponse response, String followId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.deleteFollowInfo(followId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 编辑跟进信息
     * 
     * @param request request
     * @param response response
     * @param followId 跟进ID
     * @param vo 客户跟进信息POJO类
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/editFollowInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editFollowInfo(HttpServletRequest request, HttpServletResponse response, String followId, CustomFollowInfo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.updateFollowInfo(followId, vo);
        result.put("code", code);
        return result;
    }
    
    /**
     * 根据文件ID删除单条文件信息记录
     * 
     * @param request request
     * @param response response
     * @param fileId 文件ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/deleteFileById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteFileById(HttpServletRequest request, HttpServletResponse response, String fileId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.deleteFileById(fileId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 附件下载
     * 
     * @param fileId 文件ID
     * @return ResponseEntity<byte[]>
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/download")
    public ResponseEntity<byte[]> download(String fileId)
        throws IOException
    {
        final ResponseEntity<byte[]> outFile = customerManageService.download(fileId);
        return outFile;
    }
    
    /**
     * 获取客户缴费信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param init 是否为初始化加载的数据
     * @param cmd BY_DEP，BY_EMP
     * @param queryId 查询所用的ID,orgId,memberId
     * @param orgId 公司ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getCusPayInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCusPayInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, boolean init, String cmd, String queryId, String orgId, String payYear, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngOrgId = member.getOrgId();
        if (!member.isAdmin() && init)
        {
            queryId = member.getDepartmentId();
        }
        final PageUtil util = customerManageService.getCusPayInfo(cmd, queryId, Integer.toString(mngOrgId), orgId, payYear, info);
        return util.initResult();
    }
    
    /**
     * 
     * 发送消息编辑-页面跳转
     * @param request
     * @param session
     * @param orgId
     * @param orgName
     * @param payMonths
     * @param payYear
     * @param bookFee
     * @param accNo
     * @param conId
     * @param bMonths
     * @param eMonths
     * @return
     */
    @RequestMapping(value = "asyn/gotoSMSTemplate", method = RequestMethod.GET)
    public String gotoSMSTemplate(HttpServletRequest request,  HttpSession session, 
    		String orgId,String orgName,String payMonths, String payYear,String bookFee,String accNo,
    		String conId,String bMonths,String eMonths,String type){
    	System.out.println("----------------"+type);
    	final MntMember member = (MntMember)session.getAttribute("userInfo");
    	String daiLiOrgName = member.getOrgName();
    	List<Map<String, Object>> custMobile = customerManageService.getTelByContractId(Integer.parseInt(conId));
    	
    	try {
    		String date = getDate(60);//往后推迟60分钟
    		//
    		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        	int minute = c.get(Calendar.MINUTE);
        	String checkDate = getDate(60-minute);//往后推迟下个小时数 例如：当前时间为 2016-10-20 17:12 返回的就是  2016-10-20 18:00
        	
    		orgName =  URLDecoder.decode(orgName,"UTF-8");
    		request.setAttribute("date", date);
    		request.setAttribute("checkDate", checkDate);
			request.setAttribute("orgName",orgName);
			request.setAttribute("daiLiOrgName", daiLiOrgName);
			if(null!=custMobile&&null!=custMobile.get(0)&&!"".equals(custMobile.get(0).get("mobile"))){
				request.setAttribute("custMobile", custMobile.get(0).get("mobile"));
			}else{
				request.setAttribute("custMobile", "");
			}
			request.setAttribute("type", type);
			request.setAttribute("orgId", orgId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "cusManage/editSmsContent";
    }
    
    /*
     * editSmsContent页面 发送消息处理
     */
    @RequestMapping(value = "asyn/sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendMsg(HttpServletRequest request, HttpServletResponse response,HttpSession session, String type,String senContent,String custMobile,String sendTime,String orgId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        int memberId = member.getId(); //登录人的id 
        int num =0;
        String code="";
        //custMobile = "18600992681";
        // arr 案列：18310822569|1294,18310822568|3999,18310822569|4007
        String arr = custMobile+"|"+orgId+",";
        if("1".equals(type)){//立即
        	//(String mytext, String array,Integer id,String orgName)
        	code=sendMessageService.add_sendRightNow(senContent,arr,memberId,member.getOrgName());
			
		}else{//定时
			//(String mytext, String array, String dateTime,Integer id)
			num=sendMessageService.savewaitSendMessage(senContent,arr,sendTime,memberId);
		}
       
		if("1".equals(type)){//立即
			result.put("code", code);
		}else{
			result.put("code", num);
		}
        
        return result;
    }
    
    public static void main(String[] args) {
    	Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
    	int minute = c.get(Calendar.MINUTE);
    	getDate(60-minute);

	}
    

   
    //获取当前时间的 前后多少分钟 desc:参数为分钟
    public static String getDate(int m) {
    	long curren = System.currentTimeMillis();
    	curren+=m*60*1000;
    	Date da =new Date(curren);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date =dateFormat.format(da);
    	System.out.println(date);
    	return date;
	}
    
    
    
    /**
     * 合同页面初始化获取合同列表信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cusId 客户ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getContractInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, String cusId, PageInfo info)
    {
        final PageUtil util = customerManageService.getContractInfo(cusId, info);
        return util.initResult();
    }
    
    /**
     * 保存客户合同信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 合同信息实体类
     * @param file 附件
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/saveContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveContractInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, MntCustomContract vo, MultipartFile file)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        vo.setMngId(member.getOrgId().toString());
        final int code = customerManageService.saveContractInfo(vo, file, Integer.toString(userId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 删除合同信息
     * 
     * @param request Map<String, Object>
     * @param response response
     * @param contractId 合同ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/deleteContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteContractInfo(HttpServletRequest request, HttpServletResponse response, String contractId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.deleteContractInfo(contractId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 编辑合同信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 合同信息实体类
     * @param file 上传附件
     * @param contractId 合同ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/editContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editContractInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, MntCustomContract vo, MultipartFile file, @RequestParam(value = "contractId")
    String contractId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int code = customerManageService.updateContractInfo(Integer.parseInt(contractId), file, vo, userId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 删除合同附件
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param contractId 合同ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/deleteContractFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteContractFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam(value = "contractId")
    String contractId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.deleteContractFile(Integer.parseInt(contractId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 下载合同附件
     * 
     * @param contractId 合同ID
     * @return ResponseEntity<byte[]>
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/downloadContractFile")
    public ResponseEntity<byte[]> downloadContractFile(String contractId)
        throws IOException
    {
        final ResponseEntity<byte[]> outFile = customerManageService.downloadContractFile(Integer.parseInt(contractId));
        return outFile;
    }
    
    /**
     * 根据部门获取人员列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param depSelect 所选的部门
     * @return Map<String, Object>
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getEmpByDep")
    @ResponseBody
    public Map<String, Object> getEmpByDep(HttpServletRequest request, HttpServletResponse response, HttpSession session, String depSelect)
        throws IOException
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final boolean isAdmin = member.isAdmin();
        final String orgId = Integer.toString(member.getOrgId());
        final List<Map<String, Object>> empList = customerManageService.getEmpByDep(depSelect, isAdmin, orgId);
        result.put("empList", empList);
        return result;
    }
    
    /**
     * 保存派工信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param dispMess 派工信息数据
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/saveDispatching", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveDispatching(HttpServletRequest request, HttpServletResponse response, HttpSession session, String dispMess)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final String userName = member.getUserName();
        final ObjectMapper objMapper = new ObjectMapper();
        JsonNode node;
        try
        {
            node = objMapper.readTree(dispMess);
            for (int i = 0; i < node.size(); i = i + 1)
            {
                final JsonNode disp = node.get(i);
                customerManageService.saveDispatching(userId, userName, disp);
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            e.printStackTrace();
            result.put("code", false);
        }
        result.put("code", true);
        return result;
    }
    
    /**
     * 删除派工信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param mBookId 派工ID
     * @param memId 员工ID
     * @param operator 管理员
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/delDisp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDisp(HttpServletRequest request, HttpServletResponse response, HttpSession session, String mBookId, String memId, String operator)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        if (!memId.equals(operator))
        {
            final boolean code = customerManageService.delDisp(Integer.parseInt(mBookId));
            result.put("code", code);
        }
        else
        {
            result.put("code", false);
        }
        return result;
    }
    
    /**
     * 派工列表查询
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getDispList")
    @ResponseBody
    public Map<String, Object> getDispList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int orgId = member.getOrgId();
        final List<Map<String, Object>> dispList = customerManageService.getDispList(userId, orgId);
        result.put("dispList", dispList);
        return result;
    }
    
    /**
     * 转派工
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param mBookId 派工ID
     * @param depId 部门编号
     * @param empId 员工编号
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/updateDisp", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDisp(HttpServletRequest request, HttpServletResponse response, HttpSession session, String mBookId, String depId, String empId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final int code = customerManageService.updateDisp(Integer.parseInt(mBookId), Integer.parseInt(depId), Integer.parseInt(empId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 授权查看者
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param tel 手机号
     * @param grantMess 授权信息数据
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/grantView", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> grantView(HttpServletRequest request, HttpServletResponse response, HttpSession session, String tel, String grantMess)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final String userName = member.getUserName();
        final ObjectMapper objMapper = new ObjectMapper();
        JsonNode node;
        boolean exp = true;
        try
        {
            node = objMapper.readTree(grantMess);
            for (int i = 0; i < node.size(); i = i + 1)
            {
                final JsonNode disp = node.get(i);
                exp = customerManageService.saveGrantView(userId, userName, tel, disp);
                if (!exp)
                {
                    result.put("code", 2);
                    return result;
                }
            }
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog(session, "授权查看者出错:", e, true));
            result.put("code", 1);
        }
        result.put("code", 0);
        return result;
    }
    
    /**
     * 获取授权查看信息列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getGrantViewList")
    @ResponseBody
    public Map<String, Object> getGrantViewList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int orgId = member.getOrgId();
        final List<Map<String, Object>> grantViewList = customerManageService.getGrantViewList(userId, orgId);
        result.put("grantViewList", grantViewList);
        return result;
    }
    
    /**
     * 获取客户下拉列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cmd BY_DEP,BY_EMP
     * @param queryId orgId,memberId
     * @param companySearchName companySearchName
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getCusSelect")
    @ResponseBody
    public Map<String, Object> getCusSelect(HttpServletRequest request, HttpServletResponse response, HttpSession session, String cmd, String queryId, String companySearchName)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        if (!member.isAdmin() && "BY_DEP".equals(cmd))
        {
            queryId = member.getDepartmentId();
        }
        final PageUtil util = customerManageService.getCompanyInfo(cmd, null, null, queryId, orgId, companySearchName, new PageInfo(1, 9999));
        result.put("cusSelectList", util.getRecords());
        return result;
    }
    
    /**
     * 进入记账功能
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgId 客户ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/toAccounting")
    @ResponseBody
    public Map<String, Object> toAccounting(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        String memberId = customerManageService.getBizMemberIdByOrgId(orgId);
        if ("".equals(memberId))
        {
            result.put("code", false);
            result.put("memberId", memberId);
        }
        else
        {
            result.put("code", true);
            result.put("memberId", memberId);
        }
        return result;
    }
    
    /**
     * 保存缴费信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo 缴费VO
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/saveCustomPay", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveCustomPay(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomPayVo vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int orgId = member.getOrgId();
        final boolean code = customerManageService.saveCustomPay(orgId, userId, vo);
        result.put("code", code);
        return result;
    }
    
    /**
     * 查看是否存在合同信息
     * 
     * @param request request
     * @param session session
     * @param orgId 公司ID
     * @param payMonths 已付月份
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/ifHasContract", method = RequestMethod.GET)
    public void ifHasContract(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId, String payMonths)
    {
        final Map<String, Object> contractInfo = customerManageService.getOrgCoTractByOrgId(orgId);
        request.setAttribute("contractInfo", contractInfo);
        if (contractInfo.get("id") == null)
        {
            returnAjaxBean(failureData("", "该客户暂无合同!"), response);
        }
        else
        {
            returnAjaxBean(successData("", "合同可用!"), response);
        }
    }
    
    /**
     * 获取报税列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgId 客户ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getTallyList")
    @ResponseBody
    public List<Map<String, Object>> getTallyList(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId)
    {
        List<Map<String, Object>> tallyList = customerManageService.getTallyList(orgId);
        return tallyList;
    }
    
    /**
     * 获取报税明细
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgId 客户ID
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("asyn/getTaxDetailList")
    @ResponseBody
    public Map<String, Object> getTaxDetailList(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final List<Map<String, Object>> taxList = customerManageService.getTaxDetailList(orgId);
        result.put("taxList", taxList);
        return result;
    }
    
    /**
     * 保存报税信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param vo
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/saveTally", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveTally(HttpServletRequest request, HttpServletResponse response, HttpSession session, MntOrgtax vo)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        final int code = customerManageService.saveTally(vo);
        result.put("code", code);
        return result;
    }
    
    /**
     * 发送报税短信提示
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/sendTaxNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendTaxNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, String taxId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> taxInfo = customerManageService.getTaxInfoByTaxId(taxId);
        String tel = taxInfo.get(0).get("tel").toString();
        String taxPerson = taxInfo.get(0).get("taxPerson").toString();
        String taxDetail = taxInfo.get(0).get("taxDetail").toString();
        String orgName = taxInfo.get(0).get("orgName").toString();
        String taxDate = taxInfo.get(0).get("taxDate").toString();
        String taxAmount = taxInfo.get(0).get("taxAmount").toString();
        String newTaxDetail = "";
        if (taxDetail != null && !taxDetail.equals(""))
        {
            newTaxDetail += "：" + taxDetail + "。更多详情";
        }
        newTaxDetail += "请登录 http://www.weibaobeijing.com/Accounting/mobileDownload.jspx 下载手机app";
        Map<String, Object> code = SMSSender.sendTaxNotice(tel, new String[] {taxPerson, orgName, taxDate, taxAmount, newTaxDetail});
        if ("000000".equals(code.get("statusCode")))
        {
            customerManageService.updateTaxUpdateMessDate(taxId);
            result.put("code", true);
            result.put("mess", SMSSender.messCode.get(code.get("statusCode")));
        }
        else
        {
            result.put("code", false);
            result.put("mess", SMSSender.messCode.get(code.get("statusCode")));
        }
        return result;
    }
    
    /**
     * 发送催费短信提示
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return Map<String, Object>
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/sendfeeNotice", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> sendfeeNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgIdArray)
    {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final String orgName = member.getOrgName();
        String[] orgIds = orgIdArray.split(",");
        for (String orgId : orgIds)
        {
            Map<String, Object> feeResult = customerManageService.saveFeeNoticeInfoByOrgId(member.getOrgId(), orgId, orgName);
            if (feeResult.size() > 0)
            {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("orgId", feeResult.get("orgId"));
                result.put("cusName", feeResult.get("cusName"));
                result.put("tel", feeResult.get("tel"));
                result.put("statusCode", feeResult.get("statusCode"));
                result.put("mess", feeResult.get("mess"));
                resultList.add(result);
            }
        }
        return resultList;
    }
    
    /**
     * 获取合同每月缴费信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgId 客户ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getMonthCost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Map<String, Object>> getMonthCost(HttpServletRequest request, HttpServletResponse response, HttpSession session, String conId,String payYear)
    {
        final Map<String, Map<String, Object>> conCostInfo = customerManageService.getContractMonthCost(conId,payYear);
        return conCostInfo;
    }
    
    /**
     * 更新报税总额与明细
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param taxId 税务ID
     * @param amount 报税总额
     * @param taxDetail 报税明细
     * @return Map<String,Object>
     */
    @RequestMapping(value = "asyn/updateTally", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateTally(HttpServletRequest request, HttpServletResponse response, HttpSession session, String taxId, String amount, String taxDetail)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.updateTally(taxId, amount, taxDetail);
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取主页面的跟进信息
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cusId 客户ID
     * @return Map<String, Object>
     */
   /* @RequestMapping(value = "asyn/getMainFollowInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMainFollowInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final Map<String, Object> result = customerManageService.getMainFollowInfo(userId);
        return result;
    }*/
    
    /**
     * 查询跟进列表
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgName 客户名称
     * @param followTime 跟进日期
     * @param isRead 是否完成
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value = "asyn/getMoreFollowList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMoreFollowList(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgName, String followTimeBegin, String followTimeEnd, String isRead, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final PageUtil util = customerManageService.getMoreFollowList(userId, orgName, followTimeBegin, followTimeEnd, isRead, info);
        return util.initResult();
    }
    
    /**
     * 批量完成跟进记录
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param cusId 客户ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/doneFollow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doneFollow(HttpServletRequest request, HttpServletResponse response, HttpSession session, String cusId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final boolean code = customerManageService.updateFollow(cusId, userId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 完成单条跟进记录
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param followId 跟进ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/doneSingleFollow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doneSingleFollow(HttpServletRequest request, HttpServletResponse response, HttpSession session, String followId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.updateSingleFollow(Integer.parseInt(followId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 重新开启
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param followId 跟进ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/reloadSingleFollow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reloadSingleFollow(HttpServletRequest request, HttpServletResponse response, HttpSession session, String followId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = customerManageService.updateReloadSingleFollow(Integer.parseInt(followId));
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取缴费审批列表
     * 
     * @param info 分页信息
     * @param cusName 客户名称
     * @param bDate 缴费开始日期
     * @param eDate 缴费结束日期
     * @return
     */
    /*@RequestMapping(value = "asyn/getAuditList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAuditList(HttpServletRequest request, HttpSession session, PageInfo info, String cusName, String bDate, String eDate)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int orgId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        int mainTotal = 0;
        Integer auditorLevel = null;
        final PageUtil util = customerManageService.getAuditList(info, isAdmin, userId, orgId, cusName, bDate, eDate);
        Map<String, Object> result = util.initResult();
        List<Map<String, Object>> list = (List<Map<String, Object>>)result.get("rows");
        if (isAdmin)
        {
            result.put("auditorLevel", 88);
        }
        else
        {
            auditorLevel = auditSettingsService.getAuditorLevel(userId, orgId, 1);
            result.put("auditorLevel", auditorLevel);
        }
        for (Map<String, Object> map : list)
        {
            if (!isAdmin && Integer.parseInt(map.get("auditFlag").toString()) != 0 && Integer.parseInt(map.get("auditFlag").toString()) == auditorLevel)
            {
                mainTotal = mainTotal + 1;
            }
            else if (isAdmin && Integer.parseInt(map.get("auditFlag").toString()) != 0)
            {
                mainTotal = mainTotal + 1;
            }
        }
        result.put("mainTotal", mainTotal);
        return result;
    }
    */
    /**
     * 获取缴费审批历史列表
     * 
     * @param session session
     * @param info 分页信息
     * @param orgId 客户ID
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getAuditListByOrg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAuditListByOrg(HttpSession session, PageInfo info, String orgId)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int mntId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        final PageUtil util = customerManageService.getAuditListByOrg(info, isAdmin, userId, mntId, orgId);
        return util.initResult();
    }
    
    /**
     * 合同Excel上传
     * 
     * @param excel excel
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/upLoadConExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upLoadConExcel(MultipartFile excelFile)
    {
        try
        {
            int index = excelFile.getOriginalFilename().lastIndexOf(".") + 1;
            String excelType = excelFile.getOriginalFilename().substring(index);
            ExcelUtil excel = new ExcelUtil(excelType, excelFile.getInputStream());
            List<String[]> list = excel.getAllData(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "asyn/downLoadExcel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> downLoadExcel(HttpSession session, String orgIdStr)
    {
        String[] orgIds = orgIdStr.split(",");
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        ResponseEntity<byte[]> outFile = null;
        try
        {
            outFile = customerManageService.downLoadExcel(mngId, orgIds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return outFile;
    }
    
    /**
     * 页面跳转 客户管理页面
     * 
     * @param request request
     * @param session session
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoCustomSetting", method = RequestMethod.GET)
    public String gotoCustomSetting(HttpServletRequest request, HttpSession session)
    {
        return "cusManage/customSetting";
    }
    
    /**
     * 页面跳转 创建客户页面
     * 
     * @param request request
     * @param session session
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoCustomCreate", method = RequestMethod.GET)
    public String gotoCustomCreate(HttpServletRequest request, HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        final List<Map<String, Object>> empList = customerManageService.getEmpList(orgId);
        if (member.isAdmin())
        {
            request.setAttribute("isAdmin", true);
        }
        else
        {
            request.setAttribute("isAdmin", false);
        }
        request.setAttribute("empList", empList);
        return "cusManage/customCreate";
    }
    
    /**
     * 页面跳转 客户信息编辑页面
     * 
     * @param request request
     * @param session session
     * @param cusId 客户ID
     * @param model model
     * @return String
     * @throws UnsupportedEncodingException 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoCustomEdit", method = RequestMethod.GET)
    public String gotoCustomEdit(HttpServletRequest request, HttpSession session, String cusId,String memberName, Model model) 
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int orgId = member.getOrgId();
        final List<Map<String, Object>> empList = customerManageService.getEmpList(orgId);
        if (member.isAdmin())
        {
            request.setAttribute("isAdmin", true);
        }
        else
        {
            request.setAttribute("isAdmin", false);
        }
        final CustomInfoVo cusInfo = customerManageService.getCustomInfoVo(cusId);
        final List<MntCustomTaxInfo> taxInfoList = customerManageService.getTaxInfoVo(cusId);
        final List<Map<String, Object>> attachmentInfoList = customerManageService.getAttachmentInfoVo(cusId);
        List<Map<String, Object>> bookInfo = customerManageService.getCreateBookMess(cusId);
        request.setAttribute("bookInfo", bookInfo);
        request.setAttribute("empList", empList);
        model.addAttribute("cusInfo", cusInfo);
        try {
			model.addAttribute("memberName", URLDecoder.decode(memberName,"UTF-8"));
	        request.setAttribute("bookInfo", bookInfo);
	        request.setAttribute("empList", empList);
	        model.addAttribute("cusInfo", cusInfo);
	        model.addAttribute("taxInfoList", taxInfoList);
	        model.addAttribute("attachmentInfoList", attachmentInfoList);
	        return "cusManage/customEdit";
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("memberName", "");
	        request.setAttribute("bookInfo", "");
	        request.setAttribute("empList", "");
	        model.addAttribute("cusInfo", "");
	        model.addAttribute("taxInfoList", "");
	        model.addAttribute("attachmentInfoList", "");
	        return "cusManage/customEdit";
		}
        
    }
    
    /**
     * 页面跳转 跟进信息创建页面
     * 
     * @param request request
     * @param session session
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoFollowCreate", method = RequestMethod.GET)
    public String gotoFollowCreate(HttpServletRequest request, HttpSession session, String cusId)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final String userName = member.getUserName();
        final String orgName = customerManageService.getCusInfo(cusId);
        request.setAttribute("cusId", cusId);
        request.setAttribute("orgName", orgName);
        request.setAttribute("userName", userName);
        return "cusManage/followCreate";
    }
    
    /**
     * 页面跳转 客户缴费管理页面
     * 
     * @param request request
     * @param session session
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoCustomPayLook", method = RequestMethod.GET)
    public String customPayLook(HttpServletRequest request, HttpSession session)
    {
        return "cusManage/customPayLook";
    }
    
    /**
     * 页面跳转 创建合同页面
     * 
     * @param request request
     * @param session session
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoContractCreate", method = RequestMethod.GET)
    public String gotoContractCreate(HttpServletRequest request, HttpSession session, String cusId)
    {
        final String orgName = customerManageService.getCusInfo(cusId);
        request.setAttribute("cusId", cusId);
        request.setAttribute("orgName", orgName);
        return "cusManage/contractCreate";
    }
    
    /**
     * 页面跳转 派工页面
     * 
     * @param request request
     * @param session session
     * @param dataStr 页面选择的数据
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoDispatchingCus", method = RequestMethod.GET)
    public String gotoDispatchingCus(HttpServletRequest request, HttpSession session, String dataStr)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int creater = member.getOrgId();
        final String partId = member.getDepartmentId();
        final int memberId = member.getId();
        final List<Map<String, Object>> depList = frameworkSer.findMntFrameWorkInfo(memberId, member.isAdmin(), Integer.toString(creater), "no", partId);
        final List<Map<String, Object>> bookOrgList = customerManageService.getBookOrgList(dataStr);
        request.setAttribute("bookOrgList", bookOrgList);
        request.setAttribute("depList", depList);
        return "cusManage/dispatchingCus";
    }
    
    /**
     * 页面跳转 缴费页面
     * 
     * @param request request
     * @param session session
     * @param orgId 公司ID
     * @param payMonths 已付月份
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoCustomPay", method = RequestMethod.GET)
    public String gotoCustomPay(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId, String orgName, String payYear, String bookFee,String accNo,String conId,String bMonths,String eMonths)
    {
        String payMonths = customerManageService.getPayMonth(orgId, payYear, accNo);
        request.setAttribute("orgId", orgId);
        request.setAttribute("orgName", orgName);
        request.setAttribute("payMonths", payMonths);
        request.setAttribute("payYear", payYear);
        request.setAttribute("bookFee", bookFee);
        request.setAttribute("accNo", accNo);
        request.setAttribute("conId", conId);
        request.setAttribute("bMonths", bMonths);
        request.setAttribute("eMonths", eMonths);
        return "cusManage/customPay";
    }
    
    /**
     * 页面跳转 报税页面
     * 
     * @param request request
     * @param response response
     * @param session session
     * @param orgId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/goToTally", method = RequestMethod.GET)
    public String goToTally(HttpServletRequest request, HttpServletResponse response, HttpSession session, String orgId)
    {
        List<Map<String, Object>> tallyInfo = customerManageService.getTallyInfo(orgId);
        boolean monthTallyFlag = customerManageService.getThisMonthTallyFlag(orgId);
        if (monthTallyFlag)
        {
            request.setAttribute("flag", "已报税");
        }
        else
        {
            request.setAttribute("flag", "未报税");
        }
        request.setAttribute("tallyInfo", tallyInfo);
        return "cusManage/tallyOrgList";
    }
    
    /**
     * 页面跳转 更多公告
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return String
     */
    @RequestMapping(value = "forward/gotoMoreNews", method = RequestMethod.GET)
    public String gotoMoreNews(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        return "login/news/moreNews";
    }
    
    /**
     * 页面跳转 更多跟进
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return String
     */
    @RequestMapping(value = "forward/gotoMoreFollows", method = RequestMethod.GET)
    public String gotoMoreFollows(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        return "cusManage/moreFollows";
    }
    
    /**
     * 页面跳转 收费审批
     * 
     * @param request request
     * @param response response
     * @param session session
     * @return String
     */
    @RequestMapping(value = "forward/gotoPayAudit", method = RequestMethod.GET)
    public String gotoPayAudit(HttpServletRequest request, HttpSession session)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int orgId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        if (isAdmin)
        {
            request.setAttribute("auditorLevel", 88);
        }
        else
        {
            int auditorLevel = auditSettingsService.getAuditorLevel(userId, orgId, 1);
            request.setAttribute("auditorLevel", auditorLevel);
        }
        return "cusManage/payAudit";
    }
    
    /**
     * 页面跳转 审批历史记录
     * 
     * @param request request
     * @param orgId 客户ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoPayAuditDetail", method = RequestMethod.GET)
    public String gotoPayAuditDetail(HttpServletRequest request, HttpSession session, String orgId)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int userId = member.getId();
        final int mntId = member.getOrgId();
        final boolean isAdmin = member.isAdmin();
        if (isAdmin)
        {
            request.setAttribute("auditorLevel", 88);
        }
        else
        {
            int auditorLevel = auditSettingsService.getAuditorLevel(userId, mntId, 1);
            request.setAttribute("auditorLevel", auditorLevel);
        }
        request.setAttribute("orgId", orgId);
        return "cusManage/payAuditDetail";
    }
    

    /**
     * 页面跳转 业务添加
     * 
     * @param request request
     * @param orgId 客户ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoDoServices", method = RequestMethod.GET)
    public String gotoDoServices(HttpServletRequest request, HttpSession session,int orgId)
    {
    	
    	Map bizOrganization =customerManageService.gotoDoServices(request, session, orgId);
    	request.setAttribute("orgId", orgId);
    	request.setAttribute("orgName", bizOrganization.get("Name"));
    	return "processManage/createService";
    }
    @RequestMapping(value = "forward/saveServices", method = RequestMethod.POST)
    public String saveServices(HttpServletRequest request, HttpSession session)
    {
    	
    	String[] param=request.getParameterMap().get("param");
    	ObjectMapper obj = new ObjectMapper();
    	JsonNode json = null;
		try {
			json = obj.readTree(param[0]);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	int orgId=json.findValue("orgId").asInt();
    	String servicesType=json.findValue("servicesType").asText();
    	String servicesName=json.findValue("servicesName").asText();
    	customerManageService.saveServices(orgId, servicesName, servicesType);
    	return "processManage/createService";
    }
   
}
