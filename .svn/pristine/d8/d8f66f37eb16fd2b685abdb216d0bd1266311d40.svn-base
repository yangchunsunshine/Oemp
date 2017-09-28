package com.wb.model.entity.computer.cusManage;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MntExpenseDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mnt_expenseDetail", catalog = "accounting")
public class MntExpenseDetail implements java.io.Serializable
{
    
    // Fields
    
    private Integer id;
    
    private String orgId;
    
    private Date payDate;
    
    private double realAmount;
    
    private String payMonths;
    
    private double payAmount;
    
    private String discount;
    
    private Integer payment;
    
    private Date payStamp;
    
    private Date onDate;
    
    private Date offDate;
    
    private String charger;
    
    private String demo;
    
    private String bookFee;
    
    private Integer deleteFlag;
    
    private String accNo;
    
    private String accId;
    
    private Date payFromDate;
    
    private Date payToDate;
    // Constructors

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
    
	public Date getPayFromDate() {
		return payFromDate;
	}

	public void setPayFromDate(Date payFromDate) {
		this.payFromDate = payFromDate;
	}

	public Date getPayToDate() {
		return payToDate;
	}

	public void setPayToDate(Date payToDate) {
		this.payToDate = payToDate;
	}

    public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
    /** default constructor */
    public MntExpenseDetail()
    {
    }

	/** minimal constructor */
    public MntExpenseDetail(String orgId)
    {
        this.orgId = orgId;
    }
    
    /** full constructor */
    public MntExpenseDetail(String orgId, Date payDate, double realAmount, String payMonths, double payAmount, String discount, Integer payment, Date payStamp, Date onDate, Date offDate, String charger, String demo, String bookFee, Integer deleteFlag)
    {
        this.orgId = orgId;
        this.payDate = payDate;
        this.realAmount = realAmount;
        this.payMonths = payMonths;
        this.payAmount = payAmount;
        this.discount = discount;
        this.payment = payment;
        this.payStamp = payStamp;
        this.onDate = onDate;
        this.offDate = offDate;
        this.charger = charger;
        this.demo = demo;
        this.bookFee = bookFee;
        this.deleteFlag = deleteFlag;
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
    
    @Column(name = "orgId", nullable = false, length = 100)
    public String getOrgId()
    {
        return this.orgId;
    }
    
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }
    
    @Column(name = "payDate", length = 19)
    public Date getPayDate()
    {
        return this.payDate;
    }
    
    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }
    
    @Column(name = "realAmount", precision = 12)
    public double getRealAmount()
    {
        return this.realAmount;
    }
    
    public void setRealAmount(double realAmount)
    {
        this.realAmount = realAmount;
    }
    
    @Column(name = "payMonths", length = 60)
    public String getPayMonths()
    {
        return this.payMonths;
    }
    
    public void setPayMonths(String payMonths)
    {
        this.payMonths = payMonths;
    }
    
    @Column(name = "payAmount", precision = 12)
    public double getPayAmount()
    {
        return this.payAmount;
    }
    
    public void setPayAmount(double payAmount)
    {
        this.payAmount = payAmount;
    }
    
    @Column(name = "discount", length = 50)
    public String getDiscount()
    {
        return this.discount;
    }
    
    public void setDiscount(String discount)
    {
        this.discount = discount;
    }
    
    @Column(name = "payment")
    public Integer getPayment()
    {
        return this.payment;
    }
    
    public void setPayment(Integer payment)
    {
        this.payment = payment;
    }
    
    @Column(name = "payStamp", length = 19)
    public Date getPayStamp()
    {
        return this.payStamp;
    }
    
    public void setPayStamp(Date payStamp)
    {
        this.payStamp = payStamp;
    }
    
    @Column(name = "onDate", length = 19)
    public Date getOnDate()
    {
        return this.onDate;
    }
    
    public void setOnDate(Date onDate)
    {
        this.onDate = onDate;
    }
    
    @Column(name = "offDate", length = 19)
    public Date getOffDate()
    {
        return this.offDate;
    }
    
    public void setOffDate(Date offDate)
    {
        this.offDate = offDate;
    }
    
    @Column(name = "charger", length = 20)
    public String getCharger()
    {
        return this.charger;
    }
    
    public void setCharger(String charger)
    {
        this.charger = charger;
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
    
    @Column(name = "bookFee", length = 10)
    public String getBookFee()
    {
        return this.bookFee;
    }
    
    public void setBookFee(String bookFee)
    {
        this.bookFee = bookFee;
    }
    
    @Column(name = "deleteFlag")
    public Integer getDeleteFlag()
    {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(Integer deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    
}