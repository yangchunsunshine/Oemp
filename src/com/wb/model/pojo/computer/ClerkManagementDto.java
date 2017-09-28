package com.wb.model.pojo.computer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;

public class ClerkManagementDto
{
	// 关联ID
    private Integer id;
    
    // 关联ID
    private Integer associateId;
    
    // 记账人员姓名
    private String clerkName;
    
    // 记账人员电话
    private String clerkTel;
    
    // 记账人员邮箱
    private String clerkEmail;
    
    // 公司名称
    private String orgName;
    
    // 请求申请时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date queryTime;
    
    // 申请状态
    private Integer queryState;
    
    @SuppressWarnings("unused")
    private String queryStateName;
    
    
    private String partName;
    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssociateId()
    {
        return associateId;
    }
    
    public void setAssociateId(Integer associateId)
    {
        this.associateId = associateId;
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
    
    public Date getQueryTime()
    {
        return queryTime;
    }
    
    public void setQueryTime(Date queryTime)
    {
        this.queryTime = queryTime;
    }
    
    public Integer getQueryState()
    {
        return queryState;
    }
    
    public void setQueryState(Integer queryState)
    {
        this.queryState = queryState;
    }
    
    public String getQueryStateName()
    {
        if (queryState != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.CLERK_MANAGE_STATE.getItemNameByKeyVal(queryState.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setQueryStateName(String queryStateName)
    {
        this.queryStateName = queryStateName;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
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
