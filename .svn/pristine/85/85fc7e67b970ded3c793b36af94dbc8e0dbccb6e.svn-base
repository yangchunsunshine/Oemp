package com.wb.model.entity.computer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName: Accountingagency
 * @Description: 监控与会计视图层
 * @author 王磊
 * @date 2015-10-19 上午12:50:46
 */
@Entity(name = "accountingagency")
public class Accountingagency implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5406016671184144396L;

    // Fields
    private Integer mngId;
    
    private String mngName;
    
    private String mngTelphone;
    
    private String mngEmail;
    
    private String mngOrgName;
    
    private Date mngStartDate;
    
    private Date mngStamp;
    
    private String mngacronym;
    
    private Integer id;
    
    private Integer mntMemberId;
    
    private Integer userMemberId;
    
    private Date stamp;
    
    private Integer state;
    
    private Integer userId;
    
    private String userName;
    
    private String userTelphone;
    
    private String userEmail;
    
    private Date userStartDate;
    
    private Integer userEnable;
    
    private Date userStamp;
    
    private String startTime;
    
    private String endTime;
    
    private Integer isCanBeModify;
    
    private Integer selectMethod;
    
    // Constructors
    /** default constructor */
    public Accountingagency()
    {
    }
    
    /** minimal constructor */
    public Accountingagency(Integer mngId, Integer id, Integer state, Integer userId)
    {
        this.mngId = mngId;
        this.id = id;
        this.state = state;
        this.userId = userId;
    }
    
    /** full constructor */
    public Accountingagency(Integer mngId, String mngName, String mngTelphone, String mngEmail, String mngOrgName, Date mngStartDate, Date mngStamp, String mngacronym, Integer id, Integer mntMemberId, Integer userMemberId, Date stamp, Integer state, Integer userId, String userName,
        String userTelphone, String userEmail, Date userStartDate, Integer userEnable, Date userStamp)
    {
        this.mngId = mngId;
        this.mngName = mngName;
        this.mngTelphone = mngTelphone;
        this.mngEmail = mngEmail;
        this.mngOrgName = mngOrgName;
        this.mngStartDate = mngStartDate;
        this.mngStamp = mngStamp;
        this.mngacronym = mngacronym;
        this.id = id;
        this.mntMemberId = mntMemberId;
        this.userMemberId = userMemberId;
        this.stamp = stamp;
        this.state = state;
        this.userId = userId;
        this.userName = userName;
        this.userTelphone = userTelphone;
        this.userEmail = userEmail;
        this.userStartDate = userStartDate;
        this.userEnable = userEnable;
        this.userStamp = userStamp;
    }
    
    // Property accessors
    
    @Column(name = "mngId", nullable = false)
    public Integer getMngId()
    {
        return this.mngId;
    }
    
    public void setMngId(Integer mngId)
    {
        this.mngId = mngId;
    }
    
    @Column(name = "mngName", length = 20)
    public String getMngName()
    {
        return this.mngName;
    }
    
    public void setMngName(String mngName)
    {
        this.mngName = mngName;
    }
    
    @Column(name = "mngTelphone", length = 15)
    public String getMngTelphone()
    {
        return this.mngTelphone;
    }
    
    public void setMngTelphone(String mngTelphone)
    {
        this.mngTelphone = mngTelphone;
    }
    
    @Column(name = "mngEmail", length = 50)
    public String getMngEmail()
    {
        return this.mngEmail;
    }
    
    public void setMngEmail(String mngEmail)
    {
        this.mngEmail = mngEmail;
    }
    
    @Column(name = "mngOrgName", length = 72)
    public String getMngOrgName()
    {
        return this.mngOrgName;
    }
    
    public void setMngOrgName(String mngOrgName)
    {
        this.mngOrgName = mngOrgName;
    }
    
    @Column(name = "mngStartDate", length = 19)
    public Date getMngStartDate()
    {
        return this.mngStartDate;
    }
    
    public void setMngStartDate(Date mngStartDate)
    {
        this.mngStartDate = mngStartDate;
    }
    
    @Column(name = "mngStamp", length = 19)
    public Date getMngStamp()
    {
        return this.mngStamp;
    }
    
    public void setMngStamp(Date mngStamp)
    {
        this.mngStamp = mngStamp;
    }
    
    @Column(name = "mngacronym", length = 72)
    public String getMngacronym()
    {
        return this.mngacronym;
    }
    
    public void setMngacronym(String mngacronym)
    {
        this.mngacronym = mngacronym;
    }
    
    @Id
    @Column(name = "id", nullable = false)
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "mntMemberId")
    public Integer getMntMemberId()
    {
        return this.mntMemberId;
    }
    
    public void setMntMemberId(Integer mntMemberId)
    {
        this.mntMemberId = mntMemberId;
    }
    
    @Column(name = "userMemberId")
    public Integer getUserMemberId()
    {
        return this.userMemberId;
    }
    
    public void setUserMemberId(Integer userMemberId)
    {
        this.userMemberId = userMemberId;
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
    
    @Column(name = "state", nullable = false)
    public Integer getState()
    {
        return this.state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    @Column(name = "userId", nullable = false)
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "userName", length = 50)
    public String getUserName()
    {
        return this.userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    @Column(name = "userTelphone", length = 50)
    public String getUserTelphone()
    {
        return this.userTelphone;
    }
    
    public void setUserTelphone(String userTelphone)
    {
        this.userTelphone = userTelphone;
    }
    
    @Column(name = "userEmail", length = 50)
    public String getUserEmail()
    {
        return this.userEmail;
    }
    
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
    
    @Column(name = "userStartDate", length = 19)
    public Date getUserStartDate()
    {
        return this.userStartDate;
    }
    
    public void setUserStartDate(Date userStartDate)
    {
        this.userStartDate = userStartDate;
    }
    
    @Column(name = "userEnable")
    public Integer getUserEnable()
    {
        return this.userEnable;
    }
    
    public void setUserEnable(Integer userEnable)
    {
        this.userEnable = userEnable;
    }
    
    @Column(name = "userStamp", length = 19)
    public Date getUserStamp()
    {
        return this.userStamp;
    }
    
    public void setUserStamp(Date userStamp)
    {
        this.userStamp = userStamp;
    }
    
    @Column(name = "startTime")
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "endTime")
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "isCanBeModify")
    public Integer getIsCanBeModify()
    {
        return isCanBeModify;
    }
    
    public void setIsCanBeModify(Integer isCanBeModify)
    {
        this.isCanBeModify = isCanBeModify;
    }
    
    @Column(name = "selectMethod")
    public Integer getSelectMethod()
    {
        return selectMethod;
    }
    
    public void setSelectMethod(Integer selectMethod)
    {
        this.selectMethod = selectMethod;
    }
}