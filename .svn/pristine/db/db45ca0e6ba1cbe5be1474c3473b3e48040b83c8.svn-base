package com.wb.model.entity.computer.cusManage;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wb.model.entity.computer.accTableEntity.BizMember;

/**
 * BizOrgmemberbook entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "biz_orgmemberbook")
public class BizOrgmemberbook implements java.io.Serializable
{
    
    // Fields
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1071095984976966780L;

    private Integer id;
    
    private BizMember bizMemberByMemberId;
    
    private BizMember bizMemberByOperator;
    
    private Integer mobilePower;
    
    private String record;
    
    private Integer power;
    
    private Date stamp;
    
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID")
    public BizMember getBizMemberByMemberId()
    {
        return this.bizMemberByMemberId;
    }
    
    public void setBizMemberByMemberId(BizMember bizMemberByMemberId)
    {
        this.bizMemberByMemberId = bizMemberByMemberId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Operator")
    public BizMember getBizMemberByOperator()
    {
        return this.bizMemberByOperator;
    }
    
    public void setBizMemberByOperator(BizMember bizMemberByOperator)
    {
        this.bizMemberByOperator = bizMemberByOperator;
    }
    
    
    @Column(name = "mobilePower")
    public Integer getMobilePower()
    {
        return this.mobilePower;
    }
    
    public void setMobilePower(Integer mobilePower)
    {
        this.mobilePower = mobilePower;
    }
    
    @Column(name = "Record", length = 500)
    public String getRecord()
    {
        return this.record;
    }
    
    public void setRecord(String record)
    {
        this.record = record;
    }
    
    @Column(name = "Power")
    public Integer getPower()
    {
        return this.power;
    }
    
    public void setPower(Integer power)
    {
        this.power = power;
    }
    
    @Column(name = "Stamp", length = 19)
    public Date getStamp()
    {
        return this.stamp;
    }
    
    public void setStamp(Date stamp)
    {
        this.stamp = stamp;
    }
    
}