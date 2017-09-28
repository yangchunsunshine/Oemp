package com.wb.model.entity.computer.processManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntNodeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_nodeInfo")
@Table(name = "mnt_nodeInfo", catalog = "accounting")
public class MntNodeInfo implements java.io.Serializable,Comparable<MntNodeInfo>
{
    
    // Fields
    
    private Integer id;
    
    private Integer processId;
    
    private String beforeNodeId;
    
    private String afterNodeId;
    
    private String nodeName;
    
    private Integer roleId;
    
    private Integer orderSeq;
    
    private Date stamp;
    
    private Integer version;
    
    private Integer processTmpId;
    
    private Integer orhelp;
    
    private Integer adminId; 
    private Integer nodeTmpStatus;
    // Constructors
    @Column(name = "adminId")
    public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	/** default constructor */
    public MntNodeInfo()
    {
    }
    
    /** full constructor */
    public MntNodeInfo(Integer processId, String beforeNodeId, String afterNodeId, String nodeName, Integer roleId, Integer orderSeq, Date stamp, Integer version)
    {
        this.processId = processId;
        this.beforeNodeId = beforeNodeId;
        this.afterNodeId = afterNodeId;
        this.nodeName = nodeName;
        this.roleId = roleId;
        this.orderSeq = orderSeq;
        this.stamp = stamp;
        this.version = version;
    }
    /** full constructor */
    public MntNodeInfo(Integer id, Integer processId, String beforeNodeId,
    		String afterNodeId, String nodeName, Integer roleId,
			Integer orderSeq, Date stamp, Integer version, Integer processTmpId) {
		super();
		this.id = id;
		this.processId = processId;
		this.beforeNodeId = beforeNodeId;
		this.afterNodeId = afterNodeId;
		this.nodeName = nodeName;
		this.roleId = roleId;
		this.orderSeq = orderSeq;
		this.stamp = stamp;
		this.version = version;
		this.processTmpId = processTmpId;
	}
    
    public MntNodeInfo(Integer id, Integer processId, String beforeNodeId,
    		String afterNodeId, String nodeName, Integer roleId,
			Integer orderSeq, Date stamp, Integer version,
			Integer processTmpId, Integer orhelp) {
		this.id = id;
		this.processId = processId;
		this.beforeNodeId = beforeNodeId;
		this.afterNodeId = afterNodeId;
		this.nodeName = nodeName;
		this.roleId = roleId;
		this.orderSeq = orderSeq;
		this.stamp = stamp;
		this.version = version;
		this.processTmpId = processTmpId;
		this.orhelp = orhelp;
	}

	public MntNodeInfo(Integer id, Integer processId, String beforeNodeId,
			String afterNodeId, String nodeName, Integer roleId,
			Integer orderSeq, Date stamp, Integer version,
			Integer processTmpId, Integer orhelp, Integer adminId) {
		super();
		this.id = id;
		this.processId = processId;
		this.beforeNodeId = beforeNodeId;
		this.afterNodeId = afterNodeId;
		this.nodeName = nodeName;
		this.roleId = roleId;
		this.orderSeq = orderSeq;
		this.stamp = stamp;
		this.version = version;
		this.processTmpId = processTmpId;
		this.orhelp = orhelp;
		this.adminId = adminId;
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
    
    @Column(name = "processId")
    public Integer getProcessId()
    {
        return this.processId;
    }
    
    public void setProcessId(Integer processId)
    {
        this.processId = processId;
    }
    
    @Column(name = "beforeNodeId")
    public String getBeforeNodeId()
    {
        return this.beforeNodeId;
    }
    
    public void setBeforeNodeId(String beforeNodeId)
    {
        this.beforeNodeId = beforeNodeId;
    }
    
    @Column(name = "afterNodeId")
    public String getAfterNodeId()
    {
        return this.afterNodeId;
    }
    
    public void setAfterNodeId(String afterNodeId)
    {
        this.afterNodeId = afterNodeId;
    }
    
    @Column(name = "nodeName")
    public String getNodeName()
    {
        return this.nodeName;
    }
    
    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }
    
    @Column(name = "roleId")
    public Integer getRoleId()
    {
        return this.roleId;
    }
    
    public void setRoleId(Integer roleId)
    {
        this.roleId = roleId;
    }
    
    @Column(name = "orhelp")
    public Integer getOrhelp() {
		return orhelp;
	}

	public void setOrhelp(Integer orhelp) {
		this.orhelp = orhelp;
	}

	@Column(name = "orderSeq")
    public Integer getOrderSeq()
    {
        return this.orderSeq;
    }
    
    public void setOrderSeq(Integer orderSeq)
    {
        this.orderSeq = orderSeq;
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
    
    @Column(name = "version")
    public Integer getVersion()
    {
        return this.version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    @Column(name="processTmpId")
	public Integer getProcessTmpId() {
		return processTmpId;
	}

	public void setProcessTmpId(Integer processTmpId) {
		this.processTmpId = processTmpId;
	}

	@Override
	public int compareTo(MntNodeInfo o) {
		// TODO Auto-generated method stub
		return this.getOrderSeq() - o.getOrderSeq();
	}
	@Column(name = "nodeTmpStatus", length = 10)
	public Integer getNodeTmpStatus() {
		return nodeTmpStatus;
	}

	public void setNodeTmpStatus(Integer nodeTmpStatus) {
		this.nodeTmpStatus = nodeTmpStatus;
	}
    
}