package com.wb.model.entity.computer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntAlipayInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_alipayInfo")
public class MntAlipayInfo implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String mntId;
    
    private String payKey;
    
    private String payValue;
    
    private String isMustBe;
    
    private String requestType;
    
    private String recommend;
    
    private String deleteFlag;
    
    // Constructors
    
    /** default constructor */
    public MntAlipayInfo()
    {
    }
    
    /** full constructor */
    public MntAlipayInfo(String mntId, String payKey, String payValue, String isMustBe, String requestType, String recommend, String deleteFlag)
    {
        this.mntId = mntId;
        this.payKey = payKey;
        this.payValue = payValue;
        this.isMustBe = isMustBe;
        this.requestType = requestType;
        this.recommend = recommend;
        this.deleteFlag = deleteFlag;
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
    
    @Column(name = "mntId", nullable = false, length = 20)
    public String getMntId()
    {
        return this.mntId;
    }
    
    public void setMntId(String mntId)
    {
        this.mntId = mntId;
    }
    
    @Column(name = "payKey", nullable = false)
    public String getPayKey()
    {
        return this.payKey;
    }
    
    public void setPayKey(String payKey)
    {
        this.payKey = payKey;
    }
    
    @Column(name = "payValue", nullable = false)
    public String getPayValue()
    {
        return this.payValue;
    }
    
    public void setPayValue(String payValue)
    {
        this.payValue = payValue;
    }
    
    @Column(name = "isMustBe", nullable = false, length = 10)
    public String getIsMustBe()
    {
        return this.isMustBe;
    }
    
    public void setIsMustBe(String isMustBe)
    {
        this.isMustBe = isMustBe;
    }
    
    @Column(name = "requestType", nullable = false, length = 10)
    public String getRequestType()
    {
        return this.requestType;
    }
    
    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }
    
    @Column(name = "recommend", nullable = false)
    public String getRecommend()
    {
        return this.recommend;
    }
    
    public void setRecommend(String recommend)
    {
        this.recommend = recommend;
    }
    
    @Column(name = "deleteFlag", nullable = false)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    
}