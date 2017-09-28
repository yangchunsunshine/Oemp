package com.wb.component.computer.billManage.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.computer.billManage.entity.BillMember;
import com.wb.component.computer.billManage.entity.Order;
import com.wb.component.computer.billManage.entity.StatusVo;
import com.wb.component.computer.billManage.entity.SysSearchVo;
import com.wb.component.computer.billManage.server.IOempWithdrawalRecService;
import com.wb.component.computer.billManage.util.RespPageDto;
import com.wb.model.entity.computer.MntMember;

@Controller
@RequestMapping("supervisory")
public class WithdrawalRecManage{

    //private static final Logger log = Logger.getLogger(WithdrawalRecManage.class);
    
    @Autowired
    //@Qualifier("payManagerService")
    private IOempWithdrawalRecService oempWithdrawalRecService;

    /**
     * 
     * 提现记录界面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoWithdrawalRecList", method = RequestMethod.GET)
    public String gotoWithdrawalRecList(HttpSession session, HttpServletRequest request)
    {
        return "billManage/withdrawalRecManage";
    }
    
    @RequestMapping(value = "asyn/getWithdrawalRec", method = RequestMethod.POST)
    @ResponseBody
    public RespPageDto getWithdrawalRec(HttpSession session,String startTime,String endTime,SysSearchVo vo)
    {
    	try {
    		MntMember member = (MntMember)session.getAttribute("userInfo");
    	   	ObjectMapper mapper = new ObjectMapper();
        	vo.setMemberId(member.getId().toString());
        	if(startTime!=null && !"".equals(startTime)){
        		vo.setStartTime(startTime.substring(0,startTime.indexOf(",")));
        	}
        	if(endTime!=null && !"".equals(endTime)){
        		vo.setEndTime(endTime.substring(0,endTime.indexOf(",")));
        	}
			String listStr = oempWithdrawalRecService.withdrawalRec(mapper.writeValueAsString(vo));
			RespPageDto WithdrawalRecInfo = mapper.readValue(listStr, RespPageDto.class);
			return WithdrawalRecInfo ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 
     * 确认提现界面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoWithdrawalManage", method = RequestMethod.GET)
    public String gotoWithdrawalManage(HttpSession session, HttpServletRequest request)
    {
		try {
			MntMember member = (MntMember)session.getAttribute("userInfo");
		   	ObjectMapper mapper = new ObjectMapper();
		   	SysSearchVo vo = new SysSearchVo();
	    	vo.setMemberId(member.getId().toString());
			String listStr = oempWithdrawalRecService.memberInfo(mapper.writeValueAsString(vo));
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, BillMember.class);
			List<BillMember> list = mapper.readValue(listStr, javaType);
			if(list.size()!=0){
				int balance = list.get(0).getBalance().compareTo(BigDecimal.ZERO);
				DecimalFormat df = new DecimalFormat("0.00");
				if(balance==0){
					request.setAttribute("balance", 0);
				}else{
					String balanceStr = df.format(list.get(0).getBalance());
					request.setAttribute("balance", balanceStr);
				}
				int withdrawal = list.get(0).getWithdrawal().compareTo(BigDecimal.ZERO);
				if(withdrawal==0){
					request.setAttribute("withdrawal", 0);
				}else{
					String withdrawalStr = df.format(list.get(0).getWithdrawal());
					request.setAttribute("withdrawal", withdrawalStr);
				}
				int amount = list.get(0).getAmount().compareTo(BigDecimal.ZERO);
				if(amount==0){
					request.setAttribute("amount", 0);
				}else{
					String amountStr = df.format(list.get(0).getAmount());
					request.setAttribute("amount", amountStr);
				}
				return "billManage/withdrawalManage";
			}
			return "billManage/withdrawalManage";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }
    
    /**
     * 
     * 确认提现界面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/getOrderList", method = RequestMethod.POST)
    @ResponseBody
    public RespPageDto getOrderList(HttpSession session,SysSearchVo vo )
    {
    	try {
    		MntMember member = (MntMember)session.getAttribute("userInfo");
    	   	ObjectMapper mapper = new ObjectMapper();
        	vo.setMemberId(member.getId().toString());
			String listStr = oempWithdrawalRecService.orderList(mapper.writeValueAsString(vo));
			RespPageDto orderpageDtoInfo = mapper.readValue(listStr, RespPageDto.class);
			return orderpageDtoInfo ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 
     * 执行提现操作
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/launchWithdrawal", method = RequestMethod.POST)
    @ResponseBody
    public StatusVo launchWithdrawal(HttpSession session,@RequestBody String[] orderIds)
    {
    	try {
    		MntMember member = (MntMember)session.getAttribute("userInfo");
    	   	ObjectMapper mapper = new ObjectMapper();
    	   	SysSearchVo vo = new SysSearchVo();
        	vo.setMemberId(member.getId().toString());
        	vo.setMemberName(member.getOrgName());
        	vo.setOrderIds(orderIds);
			String listStr = oempWithdrawalRecService.launchWithdrawal(mapper.writeValueAsString(vo));
			StatusVo status = new StatusVo();
			status.setStatus(listStr);
			System.out.println(listStr);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    
    /**
     * 
     * 提现记录明细页面跳转
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoDetailWithdrawalRec", method = RequestMethod.GET)
    public String gotoDetailWithdrawalRec(HttpSession session, HttpServletRequest request,String id)
    {
    	request.setAttribute("id", id);
        return "billManage/detailWithdrawalRecManage";
    }
    
    /**
     * 
     * 查看提现申请明细
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "asyn/detailWithdrawalRec", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> detailWithdrawalRec(HttpSession session,String id)
    {
    	try {
    	   	ObjectMapper mapper = new ObjectMapper();
    	   	SysSearchVo vo = new SysSearchVo();
        	vo.setMemberId(id);
			String listStr = oempWithdrawalRecService.detailWithdrawalRec(mapper.writeValueAsString(vo));
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Order.class);
			List<Order> list = mapper.readValue(listStr, javaType);
			return list ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
