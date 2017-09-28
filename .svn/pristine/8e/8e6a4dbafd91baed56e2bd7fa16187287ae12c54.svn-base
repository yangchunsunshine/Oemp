package com.wb.model.entity.computer.frameworkManage;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntDepartmentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_departmentInfo")
public class MntDepartmentInfo implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String idPath;
    
    private Integer level;
    
    private Integer adminId;
    
    private String partNum;
    
    private String partName;
    
    private String topId;
    
    private String rootId;
    
    private String icon;
    
    private String deleteFlag;
    
    private Date createTime;
    
    private Date updateTime;
    
    // Constructors
    
    /** default constructor */
    public MntDepartmentInfo()
    {
    }
    
    /** full constructor */
    public MntDepartmentInfo(String idPath, Integer level, Integer adminId, String partNum, String partName, String topId, String rootId, String icon, String deleteFlag, Date createTime, Date updateTime)
    {
        this.idPath = idPath;
        this.level = level;
        this.adminId = adminId;
        this.partNum = partNum;
        this.partName = partName;
        this.topId = topId;
        this.rootId = rootId;
        this.icon = icon;
        this.deleteFlag = deleteFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
    
    @Column(name = "idPath", length = 100)
    public String getIdPath()
    {
        return this.idPath;
    }
    
    public void setIdPath(String idPath)
    {
        this.idPath = idPath;
    }
    
    @Column(name = "level")
    public Integer getLevel()
    {
        return this.level;
    }
    
    public void setLevel(Integer level)
    {
        this.level = level;
    }
    
    @Column(name = "adminId")
    public Integer getAdminId()
    {
        return this.adminId;
    }
    
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }
    
    @Column(name = "partNum", length = 50)
    public String getPartNum()
    {
        return this.partNum;
    }
    
    public void setPartNum(String partNum)
    {
        this.partNum = partNum;
    }
    
    @Column(name = "partName")
    public String getPartName()
    {
        return this.partName;
    }
    
    public void setPartName(String partName)
    {
        this.partName = partName;
    }
    
    @Column(name = "topId", length = 50)
    public String getTopId()
    {
        return this.topId;
    }
    
    public void setTopId(String topId)
    {
        this.topId = topId;
    }
    
    @Column(name = "rootId", length = 50)
    public String getRootId()
    {
        return this.rootId;
    }
    
    public void setRootId(String rootId)
    {
        this.rootId = rootId;
    }
    
    @Column(name = "icon")
    public String getIcon()
    {
        return this.icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    @Column(name = "deleteFlag", length = 10)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
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
    
    @Column(name = "updateTime", length = 19)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
}