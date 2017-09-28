package com.wb.model.entity.computer.processManage;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntOrgProcessHistory entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_orgProcessHistory")
public class MntOrgProcessHistory implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private Integer orgProcessId;
    
    private Integer orgId;
    
    private Integer memberId;
    
    private Integer currentNodeId;
    
    private Integer orderSeq;
    
    private Integer flag;
    
    private Date beginStamp;
    
    private Date endStamp;
    
    private Integer creatorId;
    
    private String creatorName;
    
    // Constructors
    
    /** default constructor */
    public MntOrgProcessHistory()
    {
    }
    
    /** full constructor */
    public MntOrgProcessHistory(Integer orgProcessId, Integer orgId, Integer memberId, Integer currentNodeId, Integer orderSeq, Integer flag, Date beginStamp, Date endStamp, Integer creatorId, String creatorName)
    {
        this.orgProcessId = orgProcessId;
        this.orgId = orgId;
        this.memberId = memberId;
        this.currentNodeId = currentNodeId;
        this.orderSeq = orderSeq;
        this.flag = flag;
        this.beginStamp = beginStamp;
        this.endStamp = endStamp;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
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
    
    @Column(name = "orgProcessId")
    public Integer getOrgProcessId()
    {
        return this.orgProcessId;
    }
    
    public void setOrgProcessId(Integer orgProcessId)
    {
        this.orgProcessId = orgProcessId;
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
    
    @Column(name = "memberId")
    public Integer getMemberId()
    {
        return this.memberId;
    }
    
    public void setMemberId(Integer memberId)
    {
        this.memberId = memberId;
    }
    
    @Column(name = "currentNodeId")
    public Integer getCurrentNodeId()
    {
        return this.currentNodeId;
    }
    
    public void setCurrentNodeId(Integer currentNodeId)
    {
        this.currentNodeId = currentNodeId;
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
    
    @Column(name = "flag")
    public Integer getFlag()
    {
        return this.flag;
    }
    
    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }
    
    @Column(name = "beginStamp", length = 19)
    public Date getBeginStamp()
    {
        return this.beginStamp;
    }
    
    public void setBeginStamp(Date beginStamp)
    {
        this.beginStamp = beginStamp;
    }
    
    @Column(name = "endStamp", length = 19)
    public Date getEndStamp()
    {
        return this.endStamp;
    }
    
    public void setEndStamp(Date endStamp)
    {
        this.endStamp = endStamp;
    }
    
    @Column(name = "creatorId")
    public Integer getCreatorId()
    {
        return this.creatorId;
    }
    
    public void setCreatorId(Integer creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "creatorName")
    public String getCreatorName()
    {
        return this.creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
}