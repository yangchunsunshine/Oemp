package com.wb.model.entity.computer.processManage;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntFeedBack entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_feedBack")
public class MntFeedBack implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private Integer mngId;
    
    private Integer ophId;
    
    private Integer orgId;
    
    private Integer score;
    
    private String detail;
    
    private Date stamp;
    
    // Constructors
    
    /** default constructor */
    public MntFeedBack()
    {
    }
    
    /** full constructor */
    public MntFeedBack(Integer mngId, Integer ophId, Integer orgId, Integer score, String detail, Date stamp)
    {
        this.mngId = mngId;
        this.ophId = ophId;
        this.orgId = orgId;
        this.score = score;
        this.detail = detail;
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
    
    @Column(name = "mngId")
    public Integer getMngId()
    {
        return this.mngId;
    }
    
    public void setMngId(Integer mngId)
    {
        this.mngId = mngId;
    }
    
    @Column(name = "ophId")
    public Integer getOphId()
    {
        return this.ophId;
    }
    
    public void setOphId(Integer ophId)
    {
        this.ophId = ophId;
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
    
    @Column(name = "score")
    public Integer getScore()
    {
        return this.score;
    }
    
    public void setScore(Integer score)
    {
        this.score = score;
    }
    
    @Column(name = "detail")
    public String getDetail()
    {
        return this.detail;
    }
    
    public void setDetail(String detail)
    {
        this.detail = detail;
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