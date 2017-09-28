package com.wb.model.entity.computer.processManage;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntProcessInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_processInfo")
public class MntProcessInfo implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String processName;
    
    private Integer mngId;
    
    private Integer canUse;
    
    private Date stamp;
    
    private Integer isDelete;
    
    private Integer cusContractId;
    
    private String processType;

    private Integer adminId;
    // Constructors
    
    /** default constructor */
    public MntProcessInfo()
    {
    }
    
    /** minimal constructor */
    public MntProcessInfo(Integer canUse)
    {
        this.canUse = canUse;
    }
    
    /** full constructor */
    public MntProcessInfo(String processName, Integer mngId, Integer canUse, Date stamp, Integer isDelete)
    {
        this.processName = processName;
        this.mngId = mngId;
        this.canUse = canUse;
        this.stamp = stamp;
        this.isDelete = isDelete;
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
    
    @Column(name = "processName")
    public String getProcessName()
    {
        return this.processName;
    }
    
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }
    
    @Column(name = "mngId")
    public Integer getMngId()
    {
        return this.mngId;
    }
    
    public void setMngId(Integer mngId)
    {
        this.mngId = mngId;
    }
    
    @Column(name = "canUse", nullable = false)
    public Integer getCanUse()
    {
        return this.canUse;
    }
    
    public void setCanUse(Integer canUse)
    {
        this.canUse = canUse;
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
    
    @Column(name = "isDelete")
    public Integer getIsDelete()
    {
        return this.isDelete;
    }
    
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }
    
    @Column(name = "processType", length = 200)
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
    @Column(name = "cusContractId", length = 50)
	public Integer getCusContractId() {
		return cusContractId;
	}
	
	public void setCusContractId(Integer cusContractId) {
		this.cusContractId = cusContractId;
	}
    @Column(name = "adminId", length = 12)
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
    
}