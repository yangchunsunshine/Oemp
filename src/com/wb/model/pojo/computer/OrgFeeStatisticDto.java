package com.wb.model.pojo.computer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class OrgFeeStatisticDto
{
    
    private Integer ownerId;
    
    private Integer orgId;
    
    private String seqcode;
    
    private String Acronym;
    
    private String orgName;
    
    private String legalperson;
    
    private String lptelphone;
    
    private Integer payMonths;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date onDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date offDate;
    
    private Double realAmount;
    
    private Integer totalMonths;
    
    public Integer getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(Integer ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public Integer getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    public String getSeqcode()
    {
        return seqcode;
    }
    
    public void setSeqcode(String seqcode)
    {
        this.seqcode = seqcode;
    }
    
    public String getAcronym()
    {
        return Acronym;
    }
    
    public void setAcronym(String acronym)
    {
        Acronym = acronym;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getLegalperson()
    {
        return legalperson;
    }
    
    public void setLegalperson(String legalperson)
    {
        this.legalperson = legalperson;
    }
    
    public String getLptelphone()
    {
        return lptelphone;
    }
    
    public void setLptelphone(String lptelphone)
    {
        this.lptelphone = lptelphone;
    }
    
    public Integer getPayMonths()
    {
        return payMonths;
    }
    
    public void setPayMonths(Integer payMonths)
    {
        this.payMonths = payMonths;
    }
    
    public Date getOnDate()
    {
        return onDate;
    }
    
    public void setOnDate(Date onDate)
    {
        this.onDate = onDate;
    }
    
    public Date getOffDate()
    {
        return offDate;
    }
    
    public void setOffDate(Date offDate)
    {
        this.offDate = offDate;
    }
    
    public Double getRealAmount()
    {
        return realAmount;
    }
    
    public void setRealAmount(Double realAmount)
    {
        this.realAmount = realAmount;
    }
    
    public Integer getTotalMonths()
    {
        return totalMonths;
    }
    
    public void setTotalMonths(Integer totalMonths)
    {
        this.totalMonths = totalMonths;
    }
}
