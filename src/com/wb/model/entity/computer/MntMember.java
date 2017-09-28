package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.wb.model.entity.computer.accTableEntity.BizMember;

/**
 * MntMember entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_member")
public class MntMember implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4902629060704730366L;

    /**
     * 登陆人ID
     */
    private Integer id;
    
    /**
     * 登陆人名称
     */
    private String userName;
    
    /**
     * 登陆人电话
     */
    private String telphone;
    
    /**
     * 登陆人密码
     */
    private String password;
    
    /**
     * 登陆人部门ID
     */
    private String departmentId;

    /**
     * 登陆人邮件
     */
    private String email;
    
    /**
     * 企业名称
     */
    private String orgName;
    
    /**
     * 企业建立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    
    /**
     * 最后登陆时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stamp;
    
    /**
     * 企业简称
     */
    private String acronym;
    
    /**
     * 咨询电话
     */
    private String hotline;
    
    /**
     * 收款持卡人
     */
    private String cardNo;
    
    /**
     * 收款人
     */
    private String cardMaster;
    
    /**
     * 登陆限制开始时间
     */
    private String startTime;
    
    /**
     * 登陆限制截止时间
     */
    private String endTime;
    
    /**
     * 是否可记账
     */
    private Integer isCanBeModify;
    
    /**
     * 登陆限制选项
     */
    private Integer selectMethod;
    
    /**
     * 企业介绍
     */
    private String orgDepict;
    
    /**
     * 经营范围
     */
    private String orgCatchArea;
    
    /**
     * 企业简称
     */
    private String orgSortName;
    
    /**
     * logo本地存储路径
     */
    private String logoUrl;
    
    /**
     * 大logo名字
     */
    private String bigLogoName;
    
    /**
     * 小logo名字
     */
    private String littleLogoName;
    
    /**
     * 员工详细实体
     */
    private BizMember empInfo;
    
    /**
     * 是否是管理员
     */
    private boolean isAdmin;
    
    /**
     * 授权时间
     */
    private Date rawTime;
    
    /**
     * 是否可以保存企业更多信息(权限)
     */
    private String ifCanSaveMore;
    
    /**
     * 权限信息
     */
    private List<Map<String, Object>> roleInfo;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
        if (isAdmin())
        {
            return this.id;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getId();
            }
            else
            {
                return this.id;
            }
        }
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "userName", length = 20)
    public String getUserName()
    {
        if (isAdmin())
        {
            return this.userName;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getName();
            }
            else
            {
                return this.userName;
            }
        }
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    @Column(name = "telphone", unique = true, length = 15)
    public String getTelphone()
    {
        if (isAdmin())
        {
            return this.telphone;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getTelphone();
            }
            else
            {
                return this.telphone;
            }
        }
    }
    
    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }
    
    @Column(name = "password", length = 32)
    public String getPassword()
    {
        if (isAdmin())
        {
            return this.password;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getPassword();
            }
            else
            {
                return this.password;
            }
        }
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Column(name = "email", length = 50)
    public String getEmail()
    {
        if (isAdmin())
        {
            return this.email;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getEmail();
            }
            else
            {
                return this.email;
            }
        }
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Column(name = "orgName", length = 72)
    public String getOrgName()
    {
        return this.orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    @Column(name = "startDate", length = 19)
    public Date getStartDate()
    {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
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
    
    @Column(name = "acronym", length = 72)
    public String getAcronym()
    {
        return this.acronym;
    }
    
    public void setAcronym(String acronym)
    {
        this.acronym = acronym;
    }
    
    @Column(name = "hotline", length = 20)
    public String getHotline()
    {
        return this.hotline;
    }
    
    public void setHotline(String hotline)
    {
        this.hotline = hotline;
    }
    
    @Column(name = "cardNo", length = 20)
    public String getCardNo()
    {
        if (isAdmin())
        {
            return this.cardNo;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return "权限不足";
            }
            else
            {
                return this.cardNo;
            }
        }
    }
    
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
    
    @Column(name = "cardMaster", length = 20)
    public String getCardMaster()
    {
        if (isAdmin())
        {
            return this.cardMaster;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return "权限不足";
            }
            else
            {
                return this.cardMaster;
            }
        }
    }
    
    public void setCardMaster(String cardMaster)
    {
        this.cardMaster = cardMaster;
    }
    
    @Column(name = "startTime", length = 5)
    public String getStartTime()
    {
        return this.startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "endTime", length = 5)
    public String getEndTime()
    {
        return this.endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "isCanBeModify")
    public Integer getIsCanBeModify()
    {
        return this.isCanBeModify;
    }
    
    public void setIsCanBeModify(Integer isCanBeModify)
    {
        this.isCanBeModify = isCanBeModify;
    }
    
    @Column(name = "selectMethod")
    public Integer getSelectMethod()
    {
        return this.selectMethod;
    }
    
    public void setSelectMethod(Integer selectMethod)
    {
        this.selectMethod = selectMethod;
    }
    
    @Column(name = "orgDepict")
    public String getOrgDepict()
    {
        return this.orgDepict;
    }
    
    public void setOrgDepict(String orgDepict)
    {
        this.orgDepict = orgDepict;
    }
    
    @Column(name = "orgCatchArea")
    public String getOrgCatchArea()
    {
        return this.orgCatchArea;
    }
    
    public void setOrgCatchArea(String orgCatchArea)
    {
        this.orgCatchArea = orgCatchArea;
    }
    
    @Column(name = "orgSortName", length = 50)
    public String getOrgSortName()
    {
        return this.orgSortName;
    }
    
    public void setOrgSortName(String orgSortName)
    {
        this.orgSortName = orgSortName;
    }
    
    @Column(name = "logoUrl")
    public String getLogoUrl()
    {
        return this.logoUrl;
    }
    
    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }
    
    @Column(name = "bigLogoName")
    public String getBigLogoName()
    {
        return this.bigLogoName;
    }
    
    public void setBigLogoName(String bigLogoName)
    {
        this.bigLogoName = bigLogoName;
    }
    
    @Column(name = "littleLogoName")
    public String getLittleLogoName()
    {
        return this.littleLogoName;
    }
    
    public void setLittleLogoName(String littleLogoName)
    {
        this.littleLogoName = littleLogoName;
    }

    /**
     * 获取 departmentId
     * 
     * @return 返回 departmentId
     */
    @Column(name = "departmentId", length = 50)
    public String getDepartmentId()
    {
        if (isAdmin())
        {
            return this.departmentId;
        }
        else
        {
            if (this.getEmpInfo() != null && !isAdmin())
            {
                return this.getEmpInfo().getDepartmentId();
            }
            else
            {
                return this.departmentId;
            }
        }
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
    
    /**
     * 获取 empInfo
     * 
     * @return 返回 empInfo
     */
    @Transient
    public BizMember getEmpInfo()
    {
        return empInfo;
    }
    
    /**
     * 设置 empInfo
     * 
     * @param 对empInfo进行赋值
     */
    public void setEmpInfo(BizMember empInfo)
    {
        this.empInfo = empInfo;
    }
    
    /**
     * 获取 isAdmin
     * 
     * @return 返回 isAdmin
     */
    @Transient
    public boolean isAdmin()
    {
        return isAdmin;
    }
    
    /**
     * 设置 isAdmin
     * 
     * @param 对isAdmin进行赋值
     */
    public void setAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取 rawTime
     * 
     * @return 返回 rawTime
     */
    @Column(name = "rawTime")
    public Date getRawTime()
    {
        return rawTime;
    }

    /**
     * 设置 rawTime
     * 
     * @param 对rawTime进行赋值
     */
    public void setRawTime(Date rawTime)
    {
        this.rawTime = rawTime;
    }
    
    /**
     * 获取 ifCanSaveMore
     * 
     * @return 返回 ifCanSaveMore
     */
    @Column(name = "ifCanSaveMore")
    public String getIfCanSaveMore()
    {
        return ifCanSaveMore;
    }
    
    /**
     * 设置 ifCanSaveMore
     * 
     * @param 对ifCanSaveMore进行赋值
     */
    public void setIfCanSaveMore(String ifCanSaveMore)
    {
        this.ifCanSaveMore = ifCanSaveMore;
    }
    
    /**
     * 
     * 公司ID(抽象字段,无set,有get,返回为mnt_member表ID)
     * 
     * 
     * @return 返回 orgId
     */
    @Transient
    public Integer getOrgId()
    {
        return this.id;
    }
    
    /**
     * 获取 roleInfo
     * 
     * @return 返回 roleInfo
     */
    @Transient
    public List<Map<String, Object>> getRoleInfo()
    {
        return roleInfo;
    }
    
    /**
     * 设置 roleInfo
     * 
     * @param 对roleInfo进行赋值
     */
    public void setRoleInfo(List<Map<String, Object>> roleInfo)
    {
        this.roleInfo = roleInfo;
    }

    
    private String payId;
    
    private String aliPay;
    
    private String aliPayKey;

    @Column(name = "payId")
	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	@Column(name = "aliPay")
	public String getAliPay() {
		return aliPay;
	}

	public void setAliPay(String aliPay) {
		this.aliPay = aliPay;
	}

	@Column(name = "aliPayKey")
	public String getAliPayKey() {
		return aliPayKey;
	}

	public void setAliPayKey(String aliPayKey) {
		this.aliPayKey = aliPayKey;
	}
    
}