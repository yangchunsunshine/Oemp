package com.wb.model.pojo.computer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.wb.framework.commonUtil.calendar.DateUtil;

public class OrgDetailMsgDto
{
    
    private Integer orgId;
    
    private String name;
    
    private String userName;
    
    private String telphone;
    
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
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endExpense;
    
    @SuppressWarnings("unused")
    private String endDate;
    
    @SuppressWarnings("unused")
    private String pdtDate;
    
    private Double epsAmount;
    
    public Integer getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getTelphone()
    {
        return telphone;
    }
    
    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
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
    
    public Date getEndExpense()
    {
        return endExpense;
    }
    
    public void setEndExpense(Date endExpense)
    {
        this.endExpense = endExpense;
    }
    
    public String getEndDate()
    {
        if (offYear != null && offYear != 0 && offMonth != 0 && offMonth != null)
        {
            return DateUtil.getYearOfSettled(offYear, offMonth + 1) + "年" + DateUtil.getMonthOfSettled(offYear, offMonth + 1) + "月";
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
        if (offYear != null && offYear != 0 && offMonth != 0 && offMonth != null && payMonths != 0 && payMonths != null)
        {
            return DateUtil.getYearOfSettled(offYear, offMonth + payMonths + 1) + "年" + DateUtil.getMonthOfSettled(offYear, offMonth + payMonths + 1) + "月";
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
}
