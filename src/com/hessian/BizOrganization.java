package com.hessian;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;

/**
 * BizOrganization entity. @author MyEclipse Persistence Tools
 */
public class BizOrganization implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -534548840136432453L;

    // Fields
    private Integer id;
    
    private Integer creator;
    
    private String name;
    
    private String orgCode;
    
    private String license;
    
    private String abbreviation;
    
    private String acronym;
    
    private Date createTime;
    
    private Date startTime;
    
    private Date endTime;
    
    private Integer authState;
    
    private Integer enable;
    
    private Integer isDefault;
    
    private String authStateName;
    
    private String adjunctName;
    
    private byte[] adjunct;
    
    private String lisence;
    
    private String userName;
    
    private String userTelphone;
    
    private String userEmail;
    
    // private String seqcode;将string修改为int
    private Integer seqCode;
    
    private Integer ownerId;
    
    private String mntCustomId;

    // Constructors
    /** default constructor */
    public BizOrganization()
    {
    }
    
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Integer getCreator()
    {
        return creator;
    }
    
    public void setCreator(Integer creator)
    {
        this.creator = creator;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getOrgCode()
    {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    public String getLicense()
    {
        return this.license;
    }
    
    public void setLicense(String license)
    {
        this.license = license;
    }
    
    public String getAbbreviation()
    {
        return this.abbreviation;
    }
    
    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }
    
    public String getAcronym()
    {
        return this.acronym;
    }
    
    public void setAcronym(String acronym)
    {
        this.acronym = acronym;
    }
    
    public Date getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getStartTime()
    {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getEndTime()
    {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public Integer getAuthState()
    {
        return authState;
    }
    
    public void setAuthState(Integer authState)
    {
        this.authState = authState;
    }
    
    public Integer getEnable()
    {
        return this.enable;
    }
    
    public void setEnable(Integer enable)
    {
        this.enable = enable;
    }
    
    public Integer getIsDefault()
    {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault)
    {
        this.isDefault = isDefault;
    }
    
    public String getAuthStateName()
    {
        if (authState != null)
        {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return SelectModule.ENTERPRISE_QUALIFICATION.getItemNameByKeyVal(authState.toString(), request);
        }
        else
        {
            return "";
        }
    }
    
    public void setAuthStateName(String authStateName)
    {
        this.authStateName = authStateName;
    }
    
    public String getAdjunctName()
    {
        return adjunctName;
    }
    
    public void setAdjunctName(String adjunctName)
    {
        this.adjunctName = adjunctName;
    }
    
    public byte[] getAdjunct()
    {
        return adjunct;
    }
    
    public void setAdjunct(byte[] adjunct)
    {
        this.adjunct = adjunct;
    }
    
    public String getLisence()
    {
        return lisence;
    }
    
    public void setLisence(String lisence)
    {
        this.lisence = lisence;
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
    
    public String getUserEmail()
    {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
    
    public Integer getSeqCode()
    {
        return seqCode;
    }
    
    public void setSeqCode(Integer seqcode)
    {
        this.seqCode = seqcode;
    }
    
    public Integer getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(Integer ownerId)
    {
        this.ownerId = ownerId;
    }
    
    /**
     * 获取 mntCustomId
     * 
     * @return 返回 mntCustomId
     */
    public String getMntCustomId()
    {
        return mntCustomId;
    }
    
    /**
     * 设置 mntCustomId
     * 
     * @param 对mntCustomId进行赋值
     */
    public void setMntCustomId(String mntCustomId)
    {
        this.mntCustomId = mntCustomId;
    }

}