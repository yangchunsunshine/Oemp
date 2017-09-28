package com.wb.model.pojo.computer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;

public class SuperintendentDto
{
    
    // 记账人员ID
    private Integer clerkId;
    
    // 记账人员姓名
    private String clerkName;
    
    // 记账人员电话
    private String clerkTel;
    
    // 记账人员邮箱
    private String clerkEmail;
    
    // 记账人员最后登录时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
    
    // 记账人员在线状态
    private Integer clerkState;
    
    @SuppressWarnings("unused")
    private String clerkStateName;
    
    // 记账人员统计标识
    @SuppressWarnings("unused")
    private Integer countState;
    
    private String partName;

    public Integer getClerkId()
    {
        return clerkId;
    }
    
    public void setClerkId(Integer clerkId)
    {
        this.clerkId = clerkId;
    }
    
    public String getClerkName()
    {
        return clerkName;
    }
    
    public void setClerkName(String clerkName)
    {
        this.clerkName = clerkName;
    }
    
    public String getClerkTel()
    {
        return clerkTel;
    }
    
    public void setClerkTel(String clerkTel)
    {
        this.clerkTel = clerkTel;
    }
    
    public String getClerkEmail()
    {
        return clerkEmail;
    }
    
    public void setClerkEmail(String clerkEmail)
    {
        this.clerkEmail = clerkEmail;
    }
    
    public Date getLastLogin()
    {
        return lastLogin;
    }
    
    public void setLastLogin(Date lastLogin)
    {
        this.lastLogin = lastLogin;
    }
    
    public Integer getClerkState()
    {
        return clerkState;
    }
    
    public void setClerkState(Integer clerkState)
    {
        this.clerkState = clerkState;
    }
    
    public String getClerkStateName()
    {
        if (clerkState != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.CLERK_STATE.getItemNameByKeyVal(clerkState.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setClerkStateName(String clerkStateName)
    {
        this.clerkStateName = clerkStateName;
    }
    
    public Integer getCountState()
    {
        return 1;
    }
    
    public void setCountState(Integer countState)
    {
        this.countState = countState;
    }
    
    /**
     * 获取 partName
     * 
     * @return 返回 partName
     */
    public String getPartName()
    {
        return partName;
    }
    
    /**
     * 设置 partName
     * 
     * @param 对partName进行赋值
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }
}
