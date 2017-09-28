package com.wb.model.pojo.computer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;
import com.wb.framework.commonUtil.calendar.DateUtil;

public class OrgDetailDto
{
    
    private Integer orgId;
    
    private String orgName;
    
    private String seqcode;
    
    private String bookId;
    
    private String bookName;
    
    @SuppressWarnings("unused")
    private String settleMonth;
    
    private String userName;
    
    private String userTelphone;
    
    private Integer isSettled;
    
    @SuppressWarnings("unused")
    private String isSettledName;
    
    private Integer year;
    
    private Integer months;
    
    private Integer vchNum;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastVch;
    
    private String orgTaxPeriod;
    
    private Double orgTaxAmount;
    
    private Integer orgTaxState;
    
    @SuppressWarnings("unused")
    private String orgTaxStateName;
    
    private Integer isMsgState;
    
    @SuppressWarnings("unused")
    private String isMsgStateName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendMsgDate;
    
    private String orgTaxDetail;
    
    // 企业代帐到费日期
    private String offDate;
    
    public String getOffDate()
    {
        return offDate;
    }
    
    public void setOffDate(String offDate)
    {
        this.offDate = offDate;
    }
    
    public Integer getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getSeqcode()
    {
        return seqcode;
    }
    
    public void setSeqcode(String seqcode)
    {
        this.seqcode = seqcode;
    }
    
    public String getBookName()
    {
        return bookName;
    }
    
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }
    
    public String getSettleMonth()
    {
        return DateUtil.getYearOfSettled(year, months) + "年第" + DateUtil.getMonthOfSettled(year, months) + "期";
    }
    
    public void setSettleMonth(String settleMonth)
    {
        this.settleMonth = settleMonth;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getUserTelphone()
    {
        return userTelphone;
    }
    
    public void setUserTelphone(String userTelphone)
    {
        this.userTelphone = userTelphone;
    }
    
    public Integer getIsSettled()
    {
        return isSettled;
    }
    
    public void setIsSettled(Integer isSettled)
    {
        this.isSettled = isSettled;
    }
    
    public String getIsSettledName()
    {
        if (isSettled != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.ORG_IS_SETTLED.getItemNameByKeyVal(isSettled.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setIsSettledName(String isSettledName)
    {
        this.isSettledName = isSettledName;
    }
    
    public Integer getYear()
    {
        return year;
    }
    
    public void setYear(Integer year)
    {
        this.year = year;
    }
    
    public Integer getMonths()
    {
        return months;
    }
    
    public void setMonths(Integer months)
    {
        this.months = months;
    }
    
    public String getBookId()
    {
        return bookId;
    }
    
    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }
    
    public Integer getVchNum()
    {
        return vchNum;
    }
    
    public void setVchNum(Integer vchNum)
    {
        this.vchNum = vchNum;
    }
    
    public Date getLastVch()
    {
        return lastVch;
    }
    
    public void setLastVch(Date lastVch)
    {
        this.lastVch = lastVch;
    }
    
    public String getOrgTaxPeriod()
    {
        return orgTaxPeriod;
    }
    
    public void setOrgTaxPeriod(String orgTaxPeriod)
    {
        this.orgTaxPeriod = orgTaxPeriod;
    }
    
    public Double getOrgTaxAmount()
    {
        return orgTaxAmount;
    }
    
    public void setOrgTaxAmount(Double orgTaxAmount)
    {
        this.orgTaxAmount = orgTaxAmount;
    }
    
    public Integer getOrgTaxState()
    {
        return orgTaxState;
    }
    
    public void setOrgTaxState(Integer orgTaxState)
    {
        this.orgTaxState = orgTaxState;
    }
    
    public String getOrgTaxStateName()
    {
        if (orgTaxState != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.ORG_TAX_STATE.getItemNameByKeyVal(orgTaxState.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setOrgTaxStateName(String orgTaxStateName)
    {
        this.orgTaxStateName = orgTaxStateName;
    }
    
    public Integer getIsMsgState()
    {
        return isMsgState;
    }
    
    public void setIsMsgState(Integer isMsgState)
    {
        this.isMsgState = isMsgState;
    }
    
    public String getIsMsgStateName()
    {
        if (isMsgState != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.ORG_MSG_STATE.getItemNameByKeyVal(isMsgState.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setIsMsgStateName(String isMsgStateName)
    {
        this.isMsgStateName = isMsgStateName;
    }
    
    public Date getSendMsgDate()
    {
        return sendMsgDate;
    }
    
    public void setSendMsgDate(Date sendMsgDate)
    {
        this.sendMsgDate = sendMsgDate;
    }
    
    public String getOrgTaxDetail()
    {
        return orgTaxDetail;
    }
    
    public void setOrgTaxDetail(String orgTaxDetail)
    {
        this.orgTaxDetail = orgTaxDetail;
    }
}
