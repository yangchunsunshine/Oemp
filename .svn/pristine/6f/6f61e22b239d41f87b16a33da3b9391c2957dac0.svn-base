/*
 * 文 件 名:  MyHessianServer.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.hessian.server.imp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.wb.component.computer.common.hessian.server.OempHessianServer;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.login.service.ILoginService;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.cusManage.MntCustomContract;
import com.wb.model.pojo.computer.CustomInfoVo;
import com.wb.model.pojo.computer.CustomPayVo;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyHessianServer implements OempHessianServer
{
    /**
     * 登陆用SER
     */
    @Autowired
    @Qualifier("loginService")
    private ILoginService loginSer;
    
    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(MyHessianServer.class);

    /**
     * 重载方法
     * 
     * @return
     */
    @Override
    public MntMember getMngInfo(String tel)
    {
        MntMember member = loginSer.findByTel(tel);
        if (member != null)
        {
            member.setPassword("权限不足!");
            member.setEmail("权限不足!");
            member.setEndTime("权限不足!");
            member.setHotline("权限不足!");
            member.setIsCanBeModify(0);
            member.setLogoUrl("权限不足!");
            member.setSelectMethod(0);
            member.setUserName("权限不足!");
        }
        return member;
    }
    
    /**
     * 重载方法
     * 
     * @param tel
     * @return
     */
    @Override
    public MntMember getMngInfoByContract(String tel)
    {
        MntMember member = customerManageService.getMemberInfoByCusMobile(tel);
        if (member != null)
        {
            member.setPassword("权限不足!");
            member.setEmail("权限不足!");
            member.setEmpInfo(null);
            member.setEndTime("权限不足!");
            member.setHotline("权限不足!");
            member.setIsCanBeModify(0);
            member.setLogoUrl("权限不足!");
            member.setSelectMethod(0);
            member.setUserName("权限不足!");
        }
        return member;
    }
    
    /**
     * 重载方法
     * 
     * @param info
     * @return
     */
    @Override
    public int createCustomContractInfo(String bizId, String orgCode, String orgName, String telphone, String feeMonth, String bookFee, String accBegin, String accEnd, String contractType)
    {
        try
        {
            CustomInfoVo vo = new CustomInfoVo();
            vo.setOrgNum(orgCode);
            vo.setOrgName(orgName);
            vo.setMobile(telphone);
            String cusId = customerManageService.createCustom(vo, bizId);
            if (cusId.length() < 2)
            {
                return Integer.parseInt(cusId);
            }
            String cusName = customerManageService.getCusInfo(cusId);
            MntCustomContract contract = new MntCustomContract();
            contract.setCusId(cusId);
            contract.setMonthCost(new BigDecimal(feeMonth));
            contract.setAccBookCost(new BigDecimal(bookFee));
            contract.setSignTime(new Date());
            contract.setDiscount(new BigDecimal(100));
            contract.setCusName(cusName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            contract.setAccStartTime(sdf.parse(accBegin));
            contract.setAccEndTime(sdf.parse(accEnd));
            contract.setPayType("1");
            contract.setCommission(new BigDecimal(0));
            contract.setContractType(contractType);
            customerManageService.saveContractInfo(contract, null, bizId);
            return 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

	@Override
	public int createCustomContractInfo(MntCustomContract entity, MultipartFile file, String userId) {
		// TODO Auto-generated method stub
		return  customerManageService.saveContractInfo(entity, file, userId);
	}

	@Override
	public String beforePayValidate(String str) {
		// TODO Auto-generated method stub
		return  customerManageService.beforePayValidate(str);
	}

	@Override
	public boolean createMntExpenseDetail(String str) {
		
		return  customerManageService.createMntExpenseDetail(str);
	}

	@Override
	public MntMember getPayInfo(String memberId) {
		return customerManageService.getPayInfo(memberId);
	}

}
