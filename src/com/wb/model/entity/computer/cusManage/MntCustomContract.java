package com.wb.model.entity.computer.cusManage;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * MntCustomContract entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_customContract")
public class MntCustomContract implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1301192453820455784L;

    private Integer id;
    
    private String cusId;
    
    private Integer reviseId;
    
    private Integer emId;
    
    private String cusName;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date signTime;
    
    @DateTimeFormat(pattern="yyyy-MM")
    private Date accStartTime;
    
    @DateTimeFormat(pattern="yyyy-MM")
    private Date accEndTime;
    
    private BigDecimal monthCost;
    
    private BigDecimal commission;
    
    private BigDecimal discount;
    
    private BigDecimal accBookCost;
    
    private String payType;
    
    private String demo;
    
    private String mngId;
    
    private String contractType;
    
    private byte[] attachmentFile;
    
    private String attachmentFileName;
    
    private Integer haveFile;
    
    private String cancleFlag;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String accNo;
    
    private int accBookCostPay;
    // Constructors
    
    /** default constructor */
    public MntCustomContract()
    {
    }
    
    /** full constructor */
    public MntCustomContract(String cusId, Integer reviseId, Integer emId, String cusName, Date signTime, Date accStartTime, Date accEndTime, BigDecimal monthCost, BigDecimal commission, BigDecimal discount, BigDecimal accBookCost, String payType, String demo, String mngId, String contractType,
        byte[] attachmentFile, String attachmentFileName, Integer haveFile, String cancleFlag, Date createTime, Date updateTime)
    {
        this.cusId = cusId;
        this.reviseId = reviseId;
        this.emId = emId;
        this.cusName = cusName;
        this.signTime = signTime;
        this.accStartTime = accStartTime;
        this.accEndTime = accEndTime;
        this.monthCost = monthCost;
        this.commission = commission;
        this.discount = discount;
        this.accBookCost = accBookCost;
        this.payType = payType;
        this.demo = demo;
        this.mngId = mngId;
        this.contractType = contractType;
        this.attachmentFile = attachmentFile;
        this.attachmentFileName = attachmentFileName;
        this.haveFile = haveFile;
        this.cancleFlag = cancleFlag;
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
    
    @Column(name = "cusId", length = 100)
    public String getCusId()
    {
        return this.cusId;
    }
    
    public void setCusId(String cusId)
    {
        this.cusId = cusId;
    }
    
    @Column(name = "reviseId")
    public Integer getReviseId()
    {
        return this.reviseId;
    }
    
    public void setReviseId(Integer reviseId)
    {
        this.reviseId = reviseId;
    }
    
    @Column(name = "emId")
    public Integer getEmId()
    {
        return this.emId;
    }
    
    public void setEmId(Integer emId)
    {
        this.emId = emId;
    }
    
    @Column(name = "cusName")
    public String getCusName()
    {
        return this.cusName;
    }
    
    public void setCusName(String cusName)
    {
        this.cusName = cusName;
    }
    
    @Column(name = "signTime", length = 19)
    public Date getSignTime()
    {
        return this.signTime;
    }
    
    public void setSignTime(Date signTime)
    {
        this.signTime = signTime;
    }
    
    @Column(name = "accStartTime", length = 19)
    public Date getAccStartTime()
    {
        return this.accStartTime;
    }
    
    public void setAccStartTime(Date accStartTime)
    {
        this.accStartTime = accStartTime;
    }
    
    @Column(name = "accEndTime", length = 19)
    public Date getAccEndTime()
    {
        return this.accEndTime;
    }
    
    public void setAccEndTime(Date accEndTime)
    {
        this.accEndTime = accEndTime;
    }
    
    @Column(name = "monthCost", length = 100)
    public BigDecimal getMonthCost()
    {
        return this.monthCost;
    }
    
    public void setMonthCost(BigDecimal monthCost)
    {
        this.monthCost = monthCost;
    }
    
    @Column(name = "commission", length = 100)
    public BigDecimal getCommission()
    {
        return this.commission;
    }
    
    public void setCommission(BigDecimal commission)
    {
        this.commission = commission;
    }
    
    @Column(name = "discount", length = 50)
    public BigDecimal getDiscount()
    {
        return this.discount;
    }
    
    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }
    
    @Column(name = "accBookCost", length = 100)
    public BigDecimal getAccBookCost()
    {
        return this.accBookCost;
    }
    
    public void setAccBookCost(BigDecimal accBookCost)
    {
        this.accBookCost = accBookCost;
    }
    
    @Column(name = "payType", length = 50)
    public String getPayType()
    {
        return this.payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    @Column(name = "demo", length = 500)
    public String getDemo()
    {
        return this.demo;
    }
    
    public void setDemo(String demo)
    {
        this.demo = demo;
    }
    
    @Column(name = "mngId", length = 10)
    public String getMngId()
    {
        return this.mngId;
    }
    
    public void setMngId(String mngId)
    {
        this.mngId = mngId;
    }
    
    @Column(name = "contractType", length = 10)
    public String getContractType()
    {
        return this.contractType;
    }
    
    public void setContractType(String contractType)
    {
        this.contractType = contractType;
    }
    
    @Column(name = "attachmentFile")
    public byte[] getAttachmentFile()
    {
        return this.attachmentFile;
    }
    
    public void setAttachmentFile(byte[] attachmentFile)
    {
        this.attachmentFile = attachmentFile;
    }
    
    @Column(name = "attachmentFileName", length = 100)
    public String getAttachmentFileName()
    {
        return this.attachmentFileName;
    }
    
    public void setAttachmentFileName(String attachmentFileName)
    {
        this.attachmentFileName = attachmentFileName;
    }
    
    @Column(name = "haveFile")
    public Integer getHaveFile()
    {
        return this.haveFile;
    }
    
    public void setHaveFile(Integer haveFile)
    {
        this.haveFile = haveFile;
    }
    
    @Column(name = "cancleFlag", length = 10)
    public String getCancleFlag()
    {
        return this.cancleFlag;
    }
    
    public void setCancleFlag(String cancleFlag)
    {
        this.cancleFlag = cancleFlag;
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

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public int getAccBookCostPay() {
		return accBookCostPay;
	}

	public void setAccBookCostPay(int accBookCostPay) {
		this.accBookCostPay = accBookCostPay;
	}
   
    
}