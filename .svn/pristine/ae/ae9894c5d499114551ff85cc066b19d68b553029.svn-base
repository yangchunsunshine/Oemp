package com.wb.model.entity.computer.processManage;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntOrgProcessInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_orgProcessInfo")
public class MntOrgProcessInfo implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private Integer orgId;
    
    private Integer processId;
    
    private Integer version;
    
    private Integer flag;
    
    private Date stamp;
    
    // Constructors
    
    /** default constructor */
    public MntOrgProcessInfo()
    {
    }
    
    /** full constructor */
    public MntOrgProcessInfo(Integer orgId, Integer processId, Integer version, Integer flag, Date stamp)
    {
        this.orgId = orgId;
        this.processId = processId;
        this.version = version;
        this.flag = flag;
        this.stamp = stamp;
    }
    
    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "orgId")
    public Integer getOrgId()
    {
        return this.orgId;
    }
    
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
    @Column(name = "processId")
    public Integer getProcessId()
    {
        return this.processId;
    }
    
    public void setProcessId(Integer processId)
    {
        this.processId = processId;
    }
    
    @Column(name = "version")
    public Integer getVersion()
    {
        return this.version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    
    @Column(name = "flag")
    public Integer getFlag()
    {
        return this.flag;
    }
    
    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }
    
    @Column(name = "stamp", length = 19)
    public Date getStamp()
    {
        return this.stamp;
    }
    
    public void setStamp(Date stamp)
    {
        this.stamp = stamp;
    }
    
}