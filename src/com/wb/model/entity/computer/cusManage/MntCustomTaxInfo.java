package com.wb.model.entity.computer.cusManage;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntCustomTaxInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_customTaxInfo")
public class MntCustomTaxInfo implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String cusId;
    
    private String taxKind;
    
    private String taxName;
    
    private String repeortType;
    
    private String days;
    
    private String taxPeople;
    
    private Date createTime;
    
    // Constructors
    
    /** default constructor */
    public MntCustomTaxInfo()
    {
    }
    
    /** full constructor */
    public MntCustomTaxInfo(String cusId, String taxKind, String taxName, String repeortType, String days, String taxPeople, Date createTime)
    {
        this.cusId = cusId;
        this.taxKind = taxKind;
        this.taxName = taxName;
        this.repeortType = repeortType;
        this.days = days;
        this.taxPeople = taxPeople;
        this.createTime = createTime;
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
    
    @Column(name = "cusId", length = 100)
    public String getCusId()
    {
        return this.cusId;
    }
    
    public void setCusId(String cusId)
    {
        this.cusId = cusId;
    }
    
    @Column(name = "taxKind", length = 20)
    public String getTaxKind()
    {
        return this.taxKind;
    }
    
    public void setTaxKind(String taxKind)
    {
        this.taxKind = taxKind;
    }
    
    @Column(name = "taxName", length = 255)
    public String getTaxName()
    {
        return taxName;
    }

    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }

    @Column(name = "repeortType", length = 20)
    public String getRepeortType()
    {
        return this.repeortType;
    }
    
    public void setRepeortType(String repeortType)
    {
        this.repeortType = repeortType;
    }
    
    @Column(name = "days", length = 20)
    public String getDays()
    {
        return this.days;
    }
    
    public void setDays(String days)
    {
        this.days = days;
    }
    
    @Column(name = "taxPeople", length = 20)
    public String getTaxPeople()
    {
        return this.taxPeople;
    }
    
    public void setTaxPeople(String taxPeople)
    {
        this.taxPeople = taxPeople;
    }
    
    @Column(name = "createTime", length = 19)
    public Date getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
}