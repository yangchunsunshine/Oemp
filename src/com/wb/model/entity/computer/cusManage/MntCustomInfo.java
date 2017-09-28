package com.wb.model.entity.computer.cusManage;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 测试新版
 */
@Entity(name = "mnt_customInfo")
public class MntCustomInfo implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    //private static final long serialVersionUID = -8589748054508152058L;

    private String id;
    
    private String creater;
    
    private String bussType;
    
    private String rawPeople;
    
    private String cardNo;
    
    private String demo;
    
    private String cusAdmin;
    
    private String saveCreate;
    
    private String contact;
    
    private String workTel;
    
    private String tel;
    
    private String mobile;
    
    private String fax;
    
    private String qq;
    
    private String email;
    
    private String orther;
    
    private String addr;
    
    private String taxType;
    
    private String taxRates;
    
    private String nationalTaxNo;
    
    private String nationalTaxCpu;
    
    private String nationalTaxAddr;
    
    private String nationalTaxDemo;
    
    private String landTaxNo;
    
    private String landTaxNoCpu;
    
    private String landTaxNoAddr;
    
    private String landTaxNoDemo;
    
    private String iosDeviceToken;
    
    private String androidDeviceToken;
    
    private Integer hiddenValue;
    
    // Constructors
   

	/** default constructor */
    public MntCustomInfo()
    {
    }
    
    /** minimal constructor */
    public MntCustomInfo(String id)
    {
        this.id = id;
    }
    
    /** full constructor */
    
    
    public MntCustomInfo(String id, String creater, String bussType,
			String rawPeople, String cardNo, String demo, String cusAdmin,
			String saveCreate, String contact, String workTel, String tel,
			String mobile, String fax, String qq, String email, String orther,
			String addr, String taxType, String taxRates, String nationalTaxNo,
			String nationalTaxCpu, String nationalTaxAddr,
			String nationalTaxDemo, String landTaxNo, String landTaxNoCpu,
			String landTaxNoAddr, String landTaxNoDemo, String iosDeviceToken,
			String androidDeviceToken, int hiddenValue) {
		super();
		this.id = id;
		this.creater = creater;
		this.bussType = bussType;
		this.rawPeople = rawPeople;
		this.cardNo = cardNo;
		this.demo = demo;
		this.cusAdmin = cusAdmin;
		this.saveCreate = saveCreate;
		this.contact = contact;
		this.workTel = workTel;
		this.tel = tel;
		this.mobile = mobile;
		this.fax = fax;
		this.qq = qq;
		this.email = email;
		this.orther = orther;
		this.addr = addr;
		this.taxType = taxType;
		this.taxRates = taxRates;
		this.nationalTaxNo = nationalTaxNo;
		this.nationalTaxCpu = nationalTaxCpu;
		this.nationalTaxAddr = nationalTaxAddr;
		this.nationalTaxDemo = nationalTaxDemo;
		this.landTaxNo = landTaxNo;
		this.landTaxNoCpu = landTaxNoCpu;
		this.landTaxNoAddr = landTaxNoAddr;
		this.landTaxNoDemo = landTaxNoDemo;
		this.iosDeviceToken = iosDeviceToken;
		this.androidDeviceToken = androidDeviceToken;
		this.hiddenValue = hiddenValue;
	}
  
   

	// Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 100)
    public String getId()
    {
        return this.id;
    }
    
   

	public void setId(String id)
    {
        this.id = id;
    }
    
    @Column(name = "creater", length = 20)
    public String getCreater()
    {
        return this.creater;
    }
    
    public void setCreater(String creater)
    {
        this.creater = creater;
    }
    
    @Column(name = "bussType", length = 20)
    public String getBussType()
    {
        return this.bussType;
    }
    
    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }
    
    @Column(name = "rawPeople", length = 100)
    public String getRawPeople()
    {
        return this.rawPeople;
    }
    
    public void setRawPeople(String rawPeople)
    {
        this.rawPeople = rawPeople;
    }
    
    @Column(name = "cardNo", length = 50)
    public String getCardNo()
    {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
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
    
    @Column(name = "cusAdmin", length = 50)
    public String getCusAdmin()
    {
        return this.cusAdmin;
    }
    
    public void setCusAdmin(String cusAdmin)
    {
        this.cusAdmin = cusAdmin;
    }
    
    @Column(name = "saveCreate", length = 50)
    public String getSaveCreate()
    {
        return this.saveCreate;
    }
    
    public void setSaveCreate(String saveCreate)
    {
        this.saveCreate = saveCreate;
    }
    
    @Column(name = "contact", length = 100)
    public String getContact()
    {
        return this.contact;
    }
    
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    
    @Column(name = "workTel", length = 50)
    public String getWorkTel()
    {
        return this.workTel;
    }
    
    public void setWorkTel(String workTel)
    {
        this.workTel = workTel;
    }
    
    @Column(name = "tel", length = 50)
    public String getTel()
    {
        return this.tel;
    }
    
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    @Column(name = "mobile", length = 50)
    public String getMobile()
    {
        return this.mobile;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    @Column(name = "fax", length = 100)
    public String getFax()
    {
        return this.fax;
    }
    
    public void setFax(String fax)
    {
        this.fax = fax;
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
    
    @Column(name = "email", length = 100)
    public String getEmail()
    {
        return this.email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Column(name = "orther", length = 100)
    public String getOrther()
    {
        return this.orther;
    }
    
    public void setOrther(String orther)
    {
        this.orther = orther;
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
    
    @Column(name = "taxType", length = 50)
    public String getTaxType()
    {
        return this.taxType;
    }
    
    public void setTaxType(String taxType)
    {
        this.taxType = taxType;
    }
    
    @Column(name = "taxRates", length = 20)
    public String getTaxRates()
    {
        return this.taxRates;
    }
    
    public void setTaxRates(String taxRates)
    {
        this.taxRates = taxRates;
    }
    
    @Column(name = "nationalTaxNo", length = 100)
    public String getNationalTaxNo()
    {
        return this.nationalTaxNo;
    }
    
    public void setNationalTaxNo(String nationalTaxNo)
    {
        this.nationalTaxNo = nationalTaxNo;
    }
    
    @Column(name = "nationalTaxCpu", length = 100)
    public String getNationalTaxCpu()
    {
        return this.nationalTaxCpu;
    }
    
    public void setNationalTaxCpu(String nationalTaxCpu)
    {
        this.nationalTaxCpu = nationalTaxCpu;
    }
    
    @Column(name = "nationalTaxAddr")
    public String getNationalTaxAddr()
    {
        return this.nationalTaxAddr;
    }
    
    public void setNationalTaxAddr(String nationalTaxAddr)
    {
        this.nationalTaxAddr = nationalTaxAddr;
    }
    
    @Column(name = "nationalTaxDemo", length = 500)
    public String getNationalTaxDemo()
    {
        return this.nationalTaxDemo;
    }
    
    public void setNationalTaxDemo(String nationalTaxDemo)
    {
        this.nationalTaxDemo = nationalTaxDemo;
    }
    
    @Column(name = "landTaxNo", length = 100)
    public String getLandTaxNo()
    {
        return this.landTaxNo;
    }
    
    public void setLandTaxNo(String landTaxNo)
    {
        this.landTaxNo = landTaxNo;
    }
    
    @Column(name = "landTaxNoCpu", length = 100)
    public String getLandTaxNoCpu()
    {
        return this.landTaxNoCpu;
    }
    
    public void setLandTaxNoCpu(String landTaxNoCpu)
    {
        this.landTaxNoCpu = landTaxNoCpu;
    }
    
    @Column(name = "landTaxNoAddr")
    public String getLandTaxNoAddr()
    {
        return this.landTaxNoAddr;
    }
    
    public void setLandTaxNoAddr(String landTaxNoAddr)
    {
        this.landTaxNoAddr = landTaxNoAddr;
    }
    
    @Column(name = "landTaxNoDemo", length = 500)
    public String getLandTaxNoDemo()
    {
        return this.landTaxNoDemo;
    }
    
    public void setLandTaxNoDemo(String landTaxNoDemo)
    {
        this.landTaxNoDemo = landTaxNoDemo;
    }
    @Column(name = "iosDeviceToken", length = 500)
	public String getIosDeviceToken() {
		return iosDeviceToken;
	}

	public void setIosDeviceToken(String iosDeviceToken) {
		this.iosDeviceToken = iosDeviceToken;
	}
	@Column(name = "androidDeviceToken", length = 500)
	public String getAndroidDeviceToken() {
		return androidDeviceToken;
	}

	public void setAndroidDeviceToken(String androidDeviceToken) {
		this.androidDeviceToken = androidDeviceToken;
	}
	@Column(name = "hiddenValue",length = 12)
	public Integer getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(Integer hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	
}