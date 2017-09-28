package com.wb.model.entity.computer.cusManage;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntCustomAttachment entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_customAttachment")
public class MntCustomAttachment implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String cusId;
    
    private String comments;
    
    private String fileName;
    
    private byte[] demoFile;
    
    private String upTime;
    
    private Integer flag;
    
    // Constructors
    
    /** default constructor */
    public MntCustomAttachment()
    {
    }
    
    /** full constructor */
    public MntCustomAttachment(String cusId, String comments, String fileName, byte[] demoFile, String upTime, Integer flag)
    {
        this.cusId = cusId;
        this.comments = comments;
        this.fileName = fileName;
        this.demoFile = demoFile;
        this.upTime = upTime;
        this.flag = flag;
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
    
    @Column(name = "comments", length = 500)
    public String getComments()
    {
        return this.comments;
    }
    
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    
    @Column(name = "fileName", length = 500)
    public String getFileName()
    {
        return this.fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    @Column(name = "demoFile")
    public byte[] getDemoFile()
    {
        return this.demoFile;
    }
    
    public void setDemoFile(byte[] demoFile)
    {
        this.demoFile = demoFile;
    }
    
    @Column(name = "upTime", length = 50)
    public String getUpTime()
    {
        return this.upTime;
    }
    
    public void setUpTime(String upTime)
    {
        this.upTime = upTime;
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
    
}