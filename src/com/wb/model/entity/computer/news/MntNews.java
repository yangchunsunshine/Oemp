package com.wb.model.entity.computer.news;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntNews entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_news")
public class MntNews implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5308502815372276248L;

    private Integer id;
    
    private Integer orgId;
    
    private Integer creater;
    
    private String createrName;
    
    private String title;
    
    private String context;
    
    private byte[] image;
    
    private String imageName;
    
    private Date createTime;
    
    private String stamp;
    
    private String ifPushCus;
    
    private String cusId;

    // Constructors
    
    /** default constructor */
    public MntNews()
    {
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
    
    @Column(name = "creater")
    public Integer getCreater()
    {
        return this.creater;
    }
    
    public void setCreater(Integer creater)
    {
        this.creater = creater;
    }
    
    @Column(name = "createrName", length = 100)
    public String getCreaterName()
    {
        return this.createrName;
    }
    
    public void setCreaterName(String createrName)
    {
        this.createrName = createrName;
    }
    
    @Column(name = "title", length = 200)
    public String getTitle()
    {
        return this.title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    @Column(name = "context", length = 500)
    public String getContext()
    {
        return this.context;
    }
    
    public void setContext(String context)
    {
        this.context = context;
    }
    
    @Column(name = "image")
    public byte[] getImage()
    {
        return this.image;
    }
    
    public void setImage(byte[] image)
    {
        this.image = image;
    }
    
    @Column(name = "imageName", length = 200)
    public String getImageName()
    {
        return this.imageName;
    }
    
    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }
    
    @Column(name = "createTime")
    public Date getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "stamp", length = 100)
    public String getStamp()
    {
        return this.stamp;
    }
    
    public void setStamp(String stamp)
    {
        this.stamp = stamp;
    }
    
    /**
     * 获取 ifPushCus
     * 
     * @return 返回 ifPushCus
     */
    public String getIfPushCus()
    {
        return ifPushCus;
    }
    
    /**
     * 设置 ifPushCus
     * 
     * @param 对ifPushCus进行赋值
     */
    @Column(name = "ifPushCus", length = 10)
    public void setIfPushCus(String ifPushCus)
    {
        this.ifPushCus = ifPushCus;
    }
    
    /**
     * 获取 cusId
     * 
     * @return 返回 cusId
     */
    public String getCusId()
    {
        return cusId;
    }
    
    /**
     * 设置 cusId
     * 
     * @param 对cusId进行赋值
     */
    @Column(name = "cusId", length = 20)
    public void setCusId(String cusId)
    {
        this.cusId = cusId;
    }
    
}