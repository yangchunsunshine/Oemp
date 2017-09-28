package com.wb.component.computer.billManage.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.computer.billManage.entity.BillMemberSettlement;
import com.wb.component.computer.billManage.entity.SysSearchVo;
import com.wb.component.computer.billManage.server.IOempSettlementService;
import com.wb.component.computer.billManage.util.RespPageDto;
import com.wb.model.entity.computer.MntMember;

@Controller
@RequestMapping("supervisory")
public class SettlementManage{

    //private static final Logger log = Logger.getLogger(WithdrawalRecManage.class);
    
    @Autowired
    private IOempSettlementService settlementService;
  
    /**
     * 
     * 跳转到代账订单查询
     * 
     * @param session session
     * @param request request
     * @return String
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "forward/gotoSettlementManage", method = RequestMethod.GET)
    public String gotoWithdrawalManage(HttpSession session, HttpServletRequest request)
    {
		try {
			MntMember member = (MntMember)session.getAttribute("userInfo");
		   	ObjectMapper mapper = new ObjectMapper();
		   	SysSearchVo vo = new SysSearchVo();
	    	vo.setMemberId(member.getId().toString());
			String listStr = settlementService.memberSettleInfo(mapper.writeValueAsString(vo));
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, BillMemberSettlement.class);
			List<BillMemberSettlement> list = mapper.readValue(listStr, javaType);
			if(list.size()!=0){
				int settlemented = list.get(0).getSettlemented().compareTo(BigDecimal.ZERO);
				DecimalFormat df = new DecimalFormat("0.00");
				if(settlemented==0){
					request.setAttribute("settlemented", 0);
				}else{
					String settlementedStr = df.format(list.get(0).getSettlemented());
					request.setAttribute("settlemented", settlementedStr);
				}
				int unsettlement = list.get(0).getUnsettlement().compareTo(BigDecimal.ZERO);
				if(unsettlement==0){
					request.setAttribute("unsettlement", 0);
				}else{
					String unsettlementStr = df.format(list.get(0).getUnsettlement());
					request.setAttribute("unsettlement", unsettlementStr);
				}
				int totalAmount = list.get(0).getTotalAmount().compareTo(BigDecimal.ZERO);
				if(totalAmount==0){
					request.setAttribute("amount", 0);
				}else{
					String totalAmountStr = df.format(list.get(0).getTotalAmount());
					request.setAttribute("totalAmount", totalAmountStr);
				}
			}else{
				request.setAttribute("settlemented", 0);
				request.setAttribute("unsettlement", 0);
				request.setAttribute("totalAmount", 0);				
			}
			return "billManage/settlementManage";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }
    
    
    /**
	 * 
	 * 
	 * 
	 * @param session
	 *            session
	 * @param request
	 *            request
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "asyn/getAccountOrderList", method = RequestMethod.POST)
	@ResponseBody
	public RespPageDto getAccountOrderList(HttpSession session, SysSearchVo vo) {
		try {
			MntMember member = (MntMember) session.getAttribute("userInfo");
			ObjectMapper mapper = new ObjectMapper();
			vo.setMemberId(member.getId().toString());
			String listStr = settlementService.accountOrderList(mapper.writeValueAsString(vo));
			RespPageDto orderpageDtoInfo = mapper.readValue(listStr, RespPageDto.class);
			return orderpageDtoInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
