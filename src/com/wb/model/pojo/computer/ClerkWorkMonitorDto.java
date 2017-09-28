package com.wb.model.pojo.computer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ClerkWorkMonitorDto
{
    
    // 记账人员ID
    private Integer ownerId;
    
    private Integer userId;
    
    private String clerkName;
    
    private String clerkTelphone;
    
    private Integer orgNum;
    
    private Integer settleNum;
    
    private Integer vchNum;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastVch;
    
    private Integer taxState;
    
    private Double taxAmount;
    
    private Integer msgState;
    
    private String period;
    
    private Double settleRate;
    
    private Double taxRate;
    
    public Integer getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(Integer ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public String getClerkName()
    {
        return clerkName;
    }
    
    public void setClerkName(String clerkName)
    {
        this.clerkName = clerkName;
    }
    
    public String getClerkTelphone()
    {
        return clerkTelphone;
    }
    
    public void setClerkTelphone(String clerkTelphone)
    {
        this.clerkTelphone = clerkTelphone;
    }
    
    public Integer getOrgNum()
    {
        return orgNum;
    }
    
    public void setOrgNum(Integer orgNum)
    {
        this.orgNum = orgNum;
    }
    
    public Integer getSettleNum()
    {
        return settleNum;
    }
    
    public void setSettleNum(Integer settleNum)
    {
        this.settleNum = settleNum;
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
    
    public Integer getTaxState()
    {
        return taxState;
    }
    
    public void setTaxState(Integer taxState)
    {
        this.taxState = taxState;
    }
    
    public Double getTaxAmount()
    {
        return taxAmount;
    }
    
    public void setTaxAmount(Double taxAmount)
    {
        this.taxAmount = taxAmount;
    }
    
    public Integer getMsgState()
    {
        return msgState;
    }
    
    public void setMsgState(Integer msgState)
    {
        this.msgState = msgState;
    }
    
    public String getPeriod()
    {
        return period;
    }
    
    public void setPeriod(String period)
    {
        this.period = period;
    }
    
    public Double getSettleRate()
    {
        return settleRate;
    }
    
    public void setSettleRate(Double settleRate)
    {
        this.settleRate = settleRate;
    }
    
    public Double getTaxRate()
    {
        return taxRate;
    }
    
    public void setTaxRate(Double taxRate)
    {
        this.taxRate = taxRate;
    }
}
