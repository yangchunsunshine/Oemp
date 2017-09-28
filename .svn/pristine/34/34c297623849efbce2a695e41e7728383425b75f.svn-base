package com.wb.model.entity.computer.accTableEntity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wb.framework.commonEnum.SelectModule;

/**
 * BizOrganization entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "biz_organization")
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
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    
    private Integer authState;
    
    private Integer enable;
    
    private Integer isDefault;
    
    @SuppressWarnings("unused")
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
    
    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "Name", length = 50)
    public String getName()
    {
        return this.name;
    }
    
    @Column(name = "Creator")
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
    
    @Column(name = "OrgCode", length = 20)
    public String getOrgCode()
    {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "License", length = 50)
    public String getLicense()
    {
        return this.license;
    }
    
    public void setLicense(String license)
    {
        this.license = license;
    }
    
    @Column(name = "Abbreviation")
    public String getAbbreviation()
    {
        return this.abbreviation;
    }
    
    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }
    
    @Column(name = "Acronym")
    public String getAcronym()
    {
        return this.acronym;
    }
    
    public void setAcronym(String acronym)
    {
        this.acronym = acronym;
    }
    
    @Column(name = "CreateTime", length = 19)
    public Date getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "StartTime", length = 19)
    public Date getStartTime()
    {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "EndTime", length = 19)
    public Date getEndTime()
    {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "authState")
    public Integer getAuthState()
    {
        return authState;
    }
    
    public void setAuthState(Integer authState)
    {
        this.authState = authState;
    }
    
    @Column(name = "Enable")
    public Integer getEnable()
    {
        return this.enable;
    }
    
    public void setEnable(Integer enable)
    {
        this.enable = enable;
    }
    
    @Transient
    public Integer getIsDefault()
    {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault)
    {
        this.isDefault = isDefault;
    }
    
    @Transient
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
    
    @Column(name = "adjunctName")
    public String getAdjunctName()
    {
        return adjunctName;
    }
    
    public void setAdjunctName(String adjunctName)
    {
        this.adjunctName = adjunctName;
    }
    
    @Transient
    public byte[] getAdjunct()
    {
        return adjunct;
    }
    
    public void setAdjunct(byte[] adjunct)
    {
        this.adjunct = adjunct;
    }
    
    @Column(name = "lisence")
    public String getLisence()
    {
        return lisence;
    }
    
    public void setLisence(String lisence)
    {
        this.lisence = lisence;
    }
    
    @Transient
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    @Transient
    public String getUserTelphone()
    {
        return userTelphone;
    }
    
    public void setUserTelphone(String userTelphone)
    {
        this.userTelphone = userTelphone;
    }
    
    @Transient
    public String getUserEmail()
    {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
    
    @Column(name = "seqCode")
    public Integer getSeqCode()
    {
        return seqCode;
    }
    
    public void setSeqCode(Integer seqcode)
    {
        this.seqCode = seqcode;
    }
    
    @Column(name = "ownerId")
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
    @Column(name = "mntCustomId")
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