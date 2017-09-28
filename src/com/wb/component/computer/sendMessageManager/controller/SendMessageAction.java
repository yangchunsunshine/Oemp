package com.wb.component.computer.sendMessageManager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.PageModel;

import com.wb.component.computer.sendMessageManager.service.ISendMessageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.SendMessage;

/**
 * 
* @ClassName: SendMessageAction 
* @Description: 发短信控制类
* @author hechunyang 
* @date 2016年10月17日 下午1:44:07 
 */

@Controller
@RequestMapping(value = "sendmessage")
public class SendMessageAction extends AjaxAction {
    
	/**
     * 日志
     */
	private static final Logger logger = Logger.getLogger(SendMessageAction.class);
    
	@Autowired
	private ISendMessageService sendMessageService;
	
	/**
	 * 
	* @Title: gotoMemberRegister 
	* @Description: 跳转发短信页面
	* @param @return
	* @date 2016年10月17日 下午2:23:22 
	* @author hechunyang 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/gotoSendMessageMain", method = RequestMethod.GET)
	public String gotoMemberRegister(HttpServletRequest request,HttpSession session){
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		int num=sendMessageService.getNumCount(member.getId());
		request.setAttribute("num", num);
		request.setAttribute("orgName", member.getOrgName());
		request.setAttribute("orgId", member.getId());
		request.setAttribute("mobile", member.getTelphone());
		return "sendMessage/sendMessageMain";
	}
	
	
	/***
	* @Title: showOrangizMessage 
	* @Description: 登陆之后查看这个代理公司旗下的所有小微企业和代理公司
	* @param @param request
	* @param @param session
	* @param @param info
	* @param @return
	* @date 2016年10月17日 下午2:37:16 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/showorganization", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showOrangizMessage(HttpServletRequest request,HttpSession session,PageInfo info){
	    MntMember member = (MntMember)session.getAttribute("userInfo"); 
		PageUtil util = sendMessageService.getAllOrganization(member.getId(), info);
		return util.initResult();
	}
	
	/**
	 * 
	* @Title: showorganizationByChose 
	* @Description: 根据条件查询公司
	* @param @param request
	* @param @param session
	* @param @return
	* @date 2016年10月18日 上午11:13:24 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/showorganizationByChose", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showorganizationByChose(HttpServletRequest request,HttpSession session,String orgName){
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		PageInfo info=new PageInfo();
		info.setRows(50);
		info.setPage(1);
		PageUtil util=null;
		try {
			util = sendMessageService.getAllOrganizationByChose(member.getId(), info,URLDecoder.decode(orgName,"UTF-8"));
			//return util.initResult();
		} catch (UnsupportedEncodingException e) {
			logger.debug("orgName="+orgName);
		}
		return util.initResult();
	}
	
	/***
	* @Title: savemessage 
	* @Description: 修改抬头信息
	* @param @param request
	* @param @param orgId
	* @param @param topname
	* @param @return
	* @date 2016年10月18日 下午4:20:33 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/savemessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> savemessage(HttpServletRequest request,Integer orgId,String topname){
		Map<String, Object> result=new HashMap<String, Object>();
		int num=sendMessageService.updateSendMessage(orgId,topname);
		result.put("code", num);
		return result;
	}
	
	/***
	 * 
	* @Title: rightNowsendmessage 
	* @Description: 立即发送短信
	* @param @param request
	* @param @param mytext
	* @param @return
	* @date 2016年10月18日 下午8:43:02 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/rightNowsendmessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> rightNowsendmessage(HttpServletRequest request,HttpSession session,String mytext,String array){
		Map<String, Object> result=new HashMap<String, Object>();
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		String code=sendMessageService.add_sendRightNow(mytext,array,member.getId(),member.getOrgName());
		result.put("code", code);
		return result;
	}
	/**
	 * 
	* @Title: waitsendMessage 
	* @Description: 定时发送短信
	* @param @param request
	* @param @param mytext
	* @param @param array
	* @param @param dateTime
	* @param @return
	* @date 2016年10月19日 下午2:19:37 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/waitsendMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> waitsendMessage(HttpServletRequest request,HttpSession session,String mytext,String array,String dateTime){
		Map<String, Object> result=new HashMap<String, Object>();
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		int code=sendMessageService.savewaitSendMessage(mytext,array,dateTime,member.getId());
		result.put("code", code);
		return result;
	}
	
	/***
	 * 
	* @Title: gotoMessage 
	* @Description: 短信详情页分页
	* @param @param request
	* @param @param session
	* @param @return
	* @date 2016年10月20日 下午4:00:59 
	* @author hechunyang 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/gotoMessage", method = RequestMethod.GET)
	public String gotoMessage(HttpServletRequest request,HttpSession session){
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		/*List<SendMessage> listMessages=sendMessageService.getAllMessage(member.getId());
		request.setAttribute("listMessages", listMessages);*/
		int pageNum=0;
		int pageSize=20;
		String pageNum_id=request.getParameter("pageNum");
		if(pageNum_id!=null&&!"".equals(pageNum_id)){
			pageNum=Integer.parseInt(pageNum_id);
		}
		PageModel pageModel=sendMessageService.findList(pageNum, pageSize,member.getId());
		List<SendMessage> list=pageModel.getList();
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("list", list);
		return "sendMessage/Message";
	}
	
	/***
	 * 
	* @Title: gotoMessageFenye 
	* @Description: TODO
	* @param @param request
	* @param @param session
	* @param @return
	* @date 2016年10月22日 下午3:13:09 
	* @author hechunyang 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/gotoMessageFenye", method = RequestMethod.GET)
	public String gotoMessageFenye(HttpServletRequest request,HttpSession session){
		return "sendMessage/Message_bak";
	}
	
	/***
	* @Title: gotoMessageFenye 
	* @Description:查看短信分页
	* @param @param request
	* @param @param session
	* @param @param info
	* @param @return
	* @date 2016年10月22日 下午3:00:13 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/gotoMessageFenye", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  gotoMessageFenye(HttpServletRequest request,HttpSession session,PageInfo info){
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		PageUtil util=sendMessageService.getFenyeResult(member.getId(), info);
		return util.initResult();
	}
	/***
	 * 
	* @Title: findCityByName 
	* @Description: 查询短信
	* @param @param request
	* @param @param response
	* @param @param id
	* @param @return
	* @date 2016年10月24日 上午10:02:06 
	* @author hechunyang 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value ="forward/findSendMessage", method = RequestMethod.POST)
	@ResponseBody
	public String findCityByName(HttpServletRequest request , HttpServletResponse response,Integer id){
		
		response.setContentType("textml;charset=UTF-8");
		String str=null;
		str=sendMessageService.findMessageById(id).getContent();
		
	    PrintWriter pw = null;
	    try {
	        pw = response.getWriter();
	        pw.print(str);
	    } catch (IOException e) {
		        //错误处理
	    }finally{
	    	
		        pw.close();
	    }
	    return null;

	}
	/**
	 * 
	* @Title: updateMessage 
	* @Description: 更新短信
	* @param @param request
	* @param @param session
	* @param @param sendMessage
	* @param @return
	* @date 2016年10月24日 上午10:02:30 
	* @author hechunyang 
	* @return Map<String,Object> 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/updateMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  updateMessage(HttpServletRequest request,HttpSession session,SendMessage sendMessage){
		Map<String, Object> result=new HashMap<String, Object>();
		MntMember member = (MntMember)session.getAttribute("userInfo"); 
		int code=sendMessageService.updateMessage(sendMessage,member.getId());	
		result.put("code",code );
		return result;
	}
	/***
	 * 
	* @Title: showMessages 
	* @Description: 查询短信详情
	* @param @param request
	* @param @param id
	* @param @return
	* @date 2016年10月25日 下午5:19:33 
	* @author hechunyang 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "forward/showMessages", method = RequestMethod.GET)
	public String showMessages(HttpServletRequest request,Integer id){
		SendMessage sendMessage= sendMessageService.findMessageById(id);
		Map<String,String> map=sendMessageService.getkeys(id);
		request.setAttribute("sendMessage", sendMessage);
		request.setAttribute("map", map);
		return "sendMessage/showMessage";
	}
	
	public void saveTuiSong(String content,String tel,Integer id ){
		sendMessageService.saveTuiSong(content,tel,id);
	}
}
