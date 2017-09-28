package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.wb.framework.nestLogger.NestLogger;

/**
 * MntMngandusers entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_mngandusers")
public class MntMngandusers implements java.io.Serializable, Cloneable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6170454001872723950L;
    
    // Fields

    private Integer id;
    
    private Integer mntMemberId;
    
    private Integer userMemberId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stamp;
    
    private Integer state;
    
    // Constructors
    /** default constructor */
    public MntMngandusers()
    {
    }
    
    /** minimal constructor */
    public MntMngandusers(Integer id)
    {
        this.id = id;
    }
    
    /** full constructor */
    public MntMngandusers(Integer id, Integer mntMemberId, Integer userMemberId)
    {
        this.id = id;
        this.mntMemberId = mntMemberId;
        this.userMemberId = userMemberId;
    }
    
    public Object clone(Date date)
    {
        MntMngandusers mmu = new MntMngandusers();
        try
        {
            mmu = (MntMngandusers)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            NestLogger.showException(e);
        }
        mmu.setStamp(date);
        mmu.setId(null);
        return mmu;
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
    
    @Column(name = "mntMemberId", length = 45)
    public Integer getMntMemberId()
    {
        return this.mntMemberId;
    }
    
    public void setMntMemberId(Integer mntMemberId)
    {
        this.mntMemberId = mntMemberId;
    }
    
    @Column(name = "userMemberId", length = 45)
    public Integer getUserMemberId()
    {
        return this.userMemberId;
    }
    
    public void setUserMemberId(Integer userMemberId)
    {
        this.userMemberId = userMemberId;
    }
    
    @Column(name = "stamp")
    public Date getStamp()
    {
        return stamp;
    }
    
    public void setStamp(Date stamp)
    {
        this.stamp = stamp;
    }
    
    @Column(name = "state")
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
}