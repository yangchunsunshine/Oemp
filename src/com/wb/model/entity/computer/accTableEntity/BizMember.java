package com.wb.model.entity.computer.accTableEntity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BizMember entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "biz_member")
public class BizMember implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4125832789439507025L;

    private Integer id;
    
    private String name;
    
    private String telphone;
    
    private String password;
    
    private Date createTime;
    
    private String email;
    
    private Integer userId;
    
    private String cardNo;
    
    private Integer enable;
    
    private String addr;
    
    private String sex;
    
    private String qq;
    
    private String departmentId;

    private Integer InvitationCode;
    
    private String demo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastStamp;
    
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
    
    @Column(name = "Name", length = 50)
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "Telphone", length = 50)
    public String getTelphone()
    {
        return this.telphone;
    }
    
    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }
    
    @Column(name = "Password")
    public String getPassword()
    {
        return this.password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Column(name = "CreateTime")
    public Date getCreateTime()
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "Email", length = 50)
    public String getEmail()
    {
        return this.email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Column(name = "CardNo", length = 18)
    public String getCardNo()
    {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
    
    @Column(name = "Enable")
    public Integer getEnable()
    {
        return this.enable;
    }
    
    public void setEnable(Integer enable)
    {
        this.enable = enable;
    }
    
    @Column(name = "userID")
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "lastStamp")
    public Date getLastStamp()
    {
        return lastStamp;
    }
    
    public void setLastStamp(Date lastStamp)
    {
        this.lastStamp = lastStamp;
    }
    
    @Column(name = "demo")
    public String getDemo()
    {
        return this.demo;
    }
    
    public void setDemo(String demo)
    {
        this.demo = demo;
    }
    
    @Column(name = "addr")
    public String getAddr()
    {
        return this.addr;
    }
    
    public void setAddr(String addr)
    {
        this.addr = addr;
    }
    
    @Column(name = "sex", length = 10)
    public String getSex()
    {
        return this.sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    @Column(name = "qq", length = 50)
    public String getQq()
    {
        return this.qq;
    }
    
    public void setQq(String qq)
    {
        this.qq = qq;
    }
    
    @Column(name = "departmentId", length = 50)
    public String getDepartmentId()
    {
        return this.departmentId;
    }
    
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * 获取 invitationCode
     * 
     * @return 返回 invitationCode
     */
    @Column(name = "InvitationCode", length = 11)
    public Integer getInvitationCode()
    {
        return InvitationCode;
    }
    
    /**
     * 设置 invitationCode
     * 
     * @param 对invitationCode进行赋值
     */
    public void setInvitationCode(Integer invitationCode)
    {
        InvitationCode = invitationCode;
    }

}