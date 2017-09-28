package com.wb.model.pojo.computer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;

public class OrgTaxMonitorDto
{
    
    private Integer orgId;
    
    private Integer motId;
    
    private String acronym;
    
    private String orgCode;
    
    private String orgName;
    
    private String clerkName;
    
    private String orgTelphone;
    
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
    
    public Integer getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    public Integer getMotId()
    {
        return motId;
    }
    
    public void setMotId(Integer motId)
    {
        this.motId = motId;
    }
    
    public String getAcronym()
    {
        return acronym;
    }
    
    public void setAcronym(String acronym)
    {
        this.acronym = acronym;
    }
    
    public String getOrgCode()
    {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getOrgTelphone()
    {
        return orgTelphone;
    }
    
    public void setOrgTelphone(String orgTelphone)
    {
        this.orgTelphone = orgTelphone;
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
    
    public String getClerkName()
    {
        return clerkName;
    }
    
    public void setClerkName(String clerkName)
    {
        this.clerkName = clerkName;
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
