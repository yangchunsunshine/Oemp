package com.wb.model.pojo.computer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.wb.framework.commonUtil.calendar.DateUtil;

public class FeeNoticeMonitorDto
{
    
    private Integer org;
    
    private Integer orgId;
    
    private Integer userId;
    
    private String mngOrg;
    
    private String orgName;
    
    private String userName;
    
    private String userName2;
    
    private String telphone;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endExpense;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date noticeDate;
    
    private Integer warnRate;
    
    private Double amount;
    
    private Integer onYear;
    
    private Integer onMonth;
    
    private Integer offYear;
    
    private Integer offMonth;
    
    private Integer payMonths;
    
    @SuppressWarnings("unused")
    private String onDate;
    
    @SuppressWarnings("unused")
    private String offDate;
    
    private Double stundard;
    
    @SuppressWarnings("unused")
    private String endDate;
    
    @SuppressWarnings("unused")
    private String pdtDate;
    
    private Double epsAmount;
    
    private String hotline;
    
    private String cardNo;
    
    private String cardMaster;
    
    public Integer getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public String getMngOrg()
    {
        return mngOrg;
    }
    
    public void setMngOrg(String mngOrg)
    {
        this.mngOrg = mngOrg;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getUserName2()
    {
        return userName2;
    }
    
    public void setUserName2(String userName2)
    {
        this.userName2 = userName2;
    }
    
    public String getTelphone()
    {
        return telphone;
    }
    
    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }
    
    public Date getEndExpense()
    {
        return endExpense;
    }
    
    public void setEndExpense(Date endExpense)
    {
        this.endExpense = endExpense;
    }
    
    public Date getNoticeDate()
    {
        return noticeDate;
    }
    
    public void setNoticeDate(Date noticeDate)
    {
        this.noticeDate = noticeDate;
    }
    
    public Integer getWarnRate()
    {
        return warnRate;
    }
    
    public void setWarnRate(Integer warnRate)
    {
        this.warnRate = warnRate;
    }
    
    public Integer getOrg()
    {
        return org;
    }
    
    public void setOrg(Integer org)
    {
        this.org = org;
    }
    
    public Double getAmount()
    {
        return amount;
    }
    
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
    
    public Integer getOnYear()
    {
        return onYear;
    }
    
    public void setOnYear(Integer onYear)
    {
        this.onYear = onYear;
    }
    
    public Integer getOnMonth()
    {
        return onMonth;
    }
    
    public void setOnMonth(Integer onMonth)
    {
        this.onMonth = onMonth;
    }
    
    public Integer getOffYear()
    {
        return offYear;
    }
    
    public void setOffYear(Integer offYear)
    {
        this.offYear = offYear;
    }
    
    public Integer getOffMonth()
    {
        return offMonth;
    }
    
    public void setOffMonth(Integer offMonth)
    {
        this.offMonth = offMonth;
    }
    
    public Integer getPayMonths()
    {
        return payMonths;
    }
    
    public void setPayMonths(Integer payMonths)
    {
        this.payMonths = payMonths;
    }
    
    public String getOnDate()
    {
        if (onYear != null && onYear != 0 && onMonth != 0 && onMonth != null)
        {
            return onYear + "年第" + onMonth + "期";
        }
        else
        {
            return "";
        }
    }
    
    public void setOnDate(String onDate)
    {
        this.onDate = onDate;
    }
    
    public String getOffDate()
    {
        if (offYear != null && offMonth != null)
        {
            return offYear + "年第" + offMonth + "期";
        }
        else
        {
            return "";
        }
    }
    
    public void setOffDate(String offDate)
    {
        this.offDate = offDate;
    }
    
    public Double getStundard()
    {
        return stundard;
    }
    
    public void setStundard(Double stundard)
    {
        this.stundard = stundard;
    }
    
    public String getEndDate()
    {
        if (onYear != null && onYear != 0 && onMonth != 0 && onMonth != null && payMonths != 0 && payMonths != null)
        {
            return DateUtil.getYearOfSettled(onYear, onMonth + payMonths) + "年" + DateUtil.getMonthOfSettled(onYear, onMonth + payMonths) + "月";
        }
        else
        {
            return "";
        }
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    public String getPdtDate()
    {
        if (onYear != null && onYear != 0 && onMonth != 0 && onMonth != null)
        {
            return DateUtil.getYearOfSettled(onYear, onMonth + 1) + "年" + DateUtil.getMonthOfSettled(onYear, onMonth + 1) + "月";
        }
        else
        {
            return "";
        }
    }
    
    public void setPdtDate(String pdtDate)
    {
        this.pdtDate = pdtDate;
    }
    
    public Double getEpsAmount()
    {
        return epsAmount;
    }
    
    public void setEpsAmount(Double epsAmount)
    {
        this.epsAmount = epsAmount;
    }
    
    public String getHotline()
    {
        return hotline;
    }
    
    public void setHotline(String hotline)
    {
        this.hotline = hotline;
    }
    
    public String getCardNo()
    {
        return cardNo;
    }
    
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
    
    public String getCardMaster()
    {
        return cardMaster;
    }
    
    public void setCardMaster(String cardMaster)
    {
        this.cardMaster = cardMaster;
    }
}
