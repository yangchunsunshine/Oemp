package com.wb.model.pojo.computer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;
import com.wb.framework.commonUtil.calendar.DateUtil;

public class SettleOrgDto
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
        try{
            return DateUtil.getYearOfSettled(year, months) + "年第" + DateUtil.getMonthOfSettled(year, months) + "期";
        }
        catch (Exception e)
        {
            return "";
        }
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
}
