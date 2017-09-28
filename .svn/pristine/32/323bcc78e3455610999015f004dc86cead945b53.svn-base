package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * MsgNotification entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "msg_notification")
public class MsgNotification implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 823214903348130980L;
    
    // Fields

    private Integer id;
    
    private String message;
    
    private String path;
    
    private Integer isread;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stamp;
    
    private Integer userId;
    
    private Integer mngId;
    
    private String tabname;
    
    private Integer direction;
    
    private String conditions;
    
    private String departmentId;

    // Constructors
    
    /** default constructor */
    public MsgNotification()
    {
    }
    
    /** minimal constructor */
    public MsgNotification(Integer id)
    {
        this.id = id;
    }
    
    /** full constructor */
    public MsgNotification(Integer id, String message, String path, Integer isread, Date stamp, Integer userId, Integer mngId, String tabname, Integer direction, String conditions)
    {
        this.id = id;
        this.message = message;
        this.path = path;
        this.isread = isread;
        this.stamp = stamp;
        this.userId = userId;
        this.mngId = mngId;
        this.tabname = tabname;
        this.direction = direction;
        this.conditions = conditions;
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
    
    @Column(name = "message", length = 300)
    public String getMessage()
    {
        return this.message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    @Column(name = "path", length = 200)
    public String getPath()
    {
        return this.path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    @Column(name = "isread")
    public Integer getIsread()
    {
        return this.isread;
    }
    
    public void setIsread(Integer isread)
    {
        this.isread = isread;
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
    
    @Column(name = "userId")
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
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
    
    @Column(name = "tabname")
    public String getTabname()
    {
        return tabname;
    }
    
    public void setTabname(String tabname)
    {
        this.tabname = tabname;
    }
    
    @Column(name = "direction")
    public Integer getDirection()
    {
        return direction;
    }
    
    public void setDirection(Integer direction)
    {
        this.direction = direction;
    }
    
    @Column(name = "conditions")
    public String getConditions()
    {
        return conditions;
    }
    
    public void setConditions(String conditions)
    {
        this.conditions = conditions;
    }

    /**
     * 获取 departmentId
     * 
     * @return 返回 departmentId
     */
    @Column(name = "departmentId")
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * 设置 departmentId
     * 
     * @param 对departmentId进行赋值
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }
}