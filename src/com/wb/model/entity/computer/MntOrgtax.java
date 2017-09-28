package com.wb.model.entity.computer;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntOrgtax entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_orgtax")
public class MntOrgtax implements java.io.Serializable
{
    
    // Fields
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2814230392029529453L;
    
    private Integer id;
    
    private Integer orgId;
    
    private double amount;
    
    private String taxDate;
    
    private Date stamp;
    
    private Integer isMsg;
    
    private Date msgStamp;
    
    private String taxDetail;
    
    // Constructors
    
    /** default constructor */
    public MntOrgtax()
    {
    }
    
    /** full constructor */
    public MntOrgtax(Integer orgId, double amount, String taxDate, Date stamp, Integer isMsg, Date msgStamp, String taxDetail)
    {
        this.orgId = orgId;
        this.amount = amount;
        this.taxDate = taxDate;
        this.stamp = stamp;
        this.isMsg = isMsg;
        this.msgStamp = msgStamp;
        this.taxDetail = taxDetail;
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
    
    @Column(name = "amount", precision = 12)
    public double getAmount()
    {
        return this.amount;
    }
    
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "taxDate", length = 10)
    public String getTaxDate()
    {
        return this.taxDate;
    }
    
    public void setTaxDate(String taxDate)
    {
        this.taxDate = taxDate;
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
    
    @Column(name = "isMsg")
    public Integer getIsMsg()
    {
        return this.isMsg;
    }
    
    public void setIsMsg(Integer isMsg)
    {
        this.isMsg = isMsg;
    }
    
    @Column(name = "msgStamp", length = 19)
    public Date getMsgStamp()
    {
        return msgStamp;
    }
    
    public void setMsgStamp(Date msgStamp)
    {
        this.msgStamp = msgStamp;
    }
    
    @Column(name = "taxDetail", length = 300)
    public String getTaxDetail()
    {
        return this.taxDetail;
    }
    
    public void setTaxDetail(String taxDetail)
    {
        this.taxDetail = taxDetail;
    }
    
}