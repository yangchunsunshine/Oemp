package com.wb.model.pojo.computer;

import java.util.List;

/**
 * 创建客户
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomInfoVo
{
    
    /**
     * 装配时用到的cusId
     */
    private String tempId;
    
    /**
     * 分配的会计
     */
    private Integer creator;
    
    /**
     * 企业编号
     */
    private String orgNum;
    
    /**
     * 企业名称
     */
    private String orgName;
    
    /**
     * 企业简称
     */
    private String orgShortName;
    
    /**
     * 行业性质
     */
    private String bussType;
    
    /**
     * 法人代表名称
     */
    private String rawPeople;
    
    /**
     * 法人身份证
     */
    private String cardNo;
    
    /**
     * 组织机构代码
     */
    private String institutionNo;
    
    /**
     * 营业执照号
     */
    private String bussLicence;
    
    /**
     * 成立日期
     */
    private String createDate;
    
    /**
     * 开始代账日期
     */
    private String startAcc;
    
    /**
     * 备注
     */
    private String demo;
    
    /**
     * 客户主管
     */
    private String cusAdmin;
    
    /**
     * 保存时同步创建会计账套
     */
    private String saveCreate;
    
    /**
     * 联系人
     */
    private String contact;
    
    /**
     * 办公电话
     */
    private String workTel;
    
    /**
     * 电话
     */
    private String tel;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 传真
     */
    private String fax;
    
    /**
     * qq
     */
    private String qq;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 其他输入项
     */
    private String orther;
    
    /**
     * 地址
     */
    private String addr;
    
 
	public Integer getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(Integer hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	/**
     * 纳税人类别
     */
    private String taxType;
    
    /**
     * 税率
     */
    private String taxRates;
    
    /**
     * 国税税务登记号
     */
    private String nationalTaxNo;
    
    /**
     * 电脑编号
     */
    private String nationalTaxCpu;
    
    /**
     * 国税主管税务分局(科)
     */
    private String nationalTaxAddr;
    
    /**
     * 国税备注
     */
    private String nationalTaxDemo;
    
    /**
     * 地税税号
     */
    private String landTaxNo;
    
    /**
     * 地税报税电脑编号
     */
    private String landTaxNoCpu;
    
    /**
     * 地税局名称
     */
    private String landTaxNoAddr;
    
    /**
     * 地税备注
     */
    private String landTaxNoDemo;
    
    /**
     * 税务提醒
     */
    private List<CustomTaxInfo> taxList;
    
    /**
     * 上传附件
     */
    private List<CustomAttachment> fileListInfo;
    
    /**
     * 隐藏按钮手机号标示
     */
    private Integer hiddenValue;
    
    public String getTempId()
    {
        return tempId;
    }
    
    public void setTempId(String tempId)
    {
        this.tempId = tempId;
    }
    
    public Integer getCreator()
    {
        return creator;
    }
    
    public void setCreator(Integer creator)
    {
        this.creator = creator;
    }
    
    /**
     * 获取 orgNum
     * 
     * @return 返回 orgNum
     */
    public String getOrgNum()
    {
        return orgNum;
    }
    
    /**
     * 设置 orgNum
     * 
     * @param 对orgNum进行赋值
     */
    public void setOrgNum(String orgNum)
    {
        this.orgNum = orgNum;
    }
    
    /**
     * 获取 orgName
     * 
     * @return 返回 orgName
     */
    public String getOrgName()
    {
        return orgName;
    }
    
    /**
     * 设置 orgName
     * 
     * @param 对orgName进行赋值
     */
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    /**
     * 获取 orgShortName
     * 
     * @return 返回 orgShortName
     */
    public String getOrgShortName()
    {
        return orgShortName;
    }
    
    /**
     * 设置 orgShortName
     * 
     * @param 对orgShortName进行赋值
     */
    public void setOrgShortName(String orgShortName)
    {
        this.orgShortName = orgShortName;
    }
    
    /**
     * 获取 bussType
     * 
     * @return 返回 bussType
     */
    public String getBussType()
    {
        return bussType;
    }
    
    /**
     * 设置 bussType
     * 
     * @param 对bussType进行赋值
     */
    public void setBussType(String bussType)
    {
        this.bussType = bussType;
    }
    
    /**
     * 获取 rawPeople
     * 
     * @return 返回 rawPeople
     */
    public String getRawPeople()
    {
        return rawPeople;
    }
    
    /**
     * 设置 rawPeople
     * 
     * @param 对rawPeople进行赋值
     */
    public void setRawPeople(String rawPeople)
    {
        this.rawPeople = rawPeople;
    }
    
    /**
     * 获取 cardNo
     * 
     * @return 返回 cardNo
     */
    public String getCardNo()
    {
        return cardNo;
    }
    
    /**
     * 设置 cardNo
     * 
     * @param 对cardNo进行赋值
     */
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
    
    /**
     * 获取 bussLicence
     * 
     * @return 返回 bussLicence
     */
    public String getBussLicence()
    {
        return bussLicence;
    }
    
    /**
     * 设置 bussLicence
     * 
     * @param 对bussLicence进行赋值
     */
    public void setBussLicence(String bussLicence)
    {
        this.bussLicence = bussLicence;
    }
    
    /**
     * 获取 createDate
     * 
     * @return 返回 createDate
     */
    public String getCreateDate()
    {
        return createDate;
    }
    
    /**
     * 设置 createDate
     * 
     * @param 对createDate进行赋值
     */
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    
    /**
     * 获取 startAcc
     * 
     * @return 返回 startAcc
     */
    public String getStartAcc()
    {
        return startAcc;
    }
    
    /**
     * 设置 startAcc
     * 
     * @param 对startAcc进行赋值
     */
    public void setStartAcc(String startAcc)
    {
        this.startAcc = startAcc;
    }
    
    /**
     * 获取 demo
     * 
     * @return 返回 demo
     */
    public String getDemo()
    {
        return demo;
    }
    
    /**
     * 设置 demo
     * 
     * @param 对demo进行赋值
     */
    public void setDemo(String demo)
    {
        this.demo = demo;
    }
    
    /**
     * 获取 cusAdmin
     * 
     * @return 返回 cusAdmin
     */
    public String getCusAdmin()
    {
        return cusAdmin;
    }
    
    /**
     * 设置 cusAdmin
     * 
     * @param 对cusAdmin进行赋值
     */
    public void setCusAdmin(String cusAdmin)
    {
        this.cusAdmin = cusAdmin;
    }
    
    /**
     * 获取 saveCreate
     * 
     * @return 返回 saveCreate
     */
    public String getSaveCreate()
    {
        return saveCreate;
    }
    
    /**
     * 设置 saveCreate
     * 
     * @param 对saveCreate进行赋值
     */
    public void setSaveCreate(String saveCreate)
    {
        this.saveCreate = saveCreate;
    }
    
    /**
     * 获取 contact
     * 
     * @return 返回 contact
     */
    public String getContact()
    {
        return contact;
    }
    
    /**
     * 设置 contact
     * 
     * @param 对contact进行赋值
     */
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    
    /**
     * 获取 workTel
     * 
     * @return 返回 workTel
     */
    public String getWorkTel()
    {
        return workTel;
    }
    
    /**
     * 设置 workTel
     * 
     * @param 对workTel进行赋值
     */
    public void setWorkTel(String workTel)
    {
        this.workTel = workTel;
    }
    
    /**
     * 获取 tel
     * 
     * @return 返回 tel
     */
    public String getTel()
    {
        return tel;
    }
    
    /**
     * 设置 tel
     * 
     * @param 对tel进行赋值
     */
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    /**
     * 获取 mobile
     * 
     * @return 返回 mobile
     */
    public String getMobile()
    {
        return mobile;
    }
    
    /**
     * 设置 mobile
     * 
     * @param 对mobile进行赋值
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    /**
     * 获取 fax
     * 
     * @return 返回 fax
     */
    public String getFax()
    {
        return fax;
    }
    
    /**
     * 设置 fax
     * 
     * @param 对fax进行赋值
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }
    
    /**
     * 获取 qq
     * 
     * @return 返回 qq
     */
    public String getQq()
    {
        return qq;
    }
    
    /**
     * 设置 qq
     * 
     * @param 对qq进行赋值
     */
    public void setQq(String qq)
    {
        this.qq = qq;
    }
    
    /**
     * 获取 email
     * 
     * @return 返回 email
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * 设置 email
     * 
     * @param 对email进行赋值
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * 获取 orther
     * 
     * @return 返回 orther
     */
    public String getOrther()
    {
        return orther;
    }
    
    /**
     * 设置 orther
     * 
     * @param 对orther进行赋值
     */
    public void setOrther(String orther)
    {
        this.orther = orther;
    }
    
    /**
     * 获取 addr
     * 
     * @return 返回 addr
     */
    public String getAddr()
    {
        return addr;
    }
    
    /**
     * 设置 addr
     * 
     * @param 对addr进行赋值
     */
    public void setAddr(String addr)
    {
        this.addr = addr;
    }
    
    /**
     * 获取 taxType
     * 
     * @return 返回 taxType
     */
    public String getTaxType()
    {
        return taxType;
    }
    
    /**
     * 设置 taxType
     * 
     * @param 对taxType进行赋值
     */
    public void setTaxType(String taxType)
    {
        this.taxType = taxType;
    }
    
    /**
     * 获取 taxRates
     * 
     * @return 返回 taxRates
     */
    public String getTaxRates()
    {
        return taxRates;
    }
    
    /**
     * 设置 taxRates
     * 
     * @param 对taxRates进行赋值
     */
    public void setTaxRates(String taxRates)
    {
        this.taxRates = taxRates;
    }
    
    /**
     * 获取 nationalTaxNo
     * 
     * @return 返回 nationalTaxNo
     */
    public String getNationalTaxNo()
    {
        return nationalTaxNo;
    }
    
    /**
     * 设置 nationalTaxNo
     * 
     * @param 对nationalTaxNo进行赋值
     */
    public void setNationalTaxNo(String nationalTaxNo)
    {
        this.nationalTaxNo = nationalTaxNo;
    }
    
    /**
     * 获取 nationalTaxCpu
     * 
     * @return 返回 nationalTaxCpu
     */
    public String getNationalTaxCpu()
    {
        return nationalTaxCpu;
    }
    
    /**
     * 设置 nationalTaxCpu
     * 
     * @param 对nationalTaxCpu进行赋值
     */
    public void setNationalTaxCpu(String nationalTaxCpu)
    {
        this.nationalTaxCpu = nationalTaxCpu;
    }
    
    /**
     * 获取 nationalTaxAddr
     * 
     * @return 返回 nationalTaxAddr
     */
    public String getNationalTaxAddr()
    {
        return nationalTaxAddr;
    }
    
    /**
     * 设置 nationalTaxAddr
     * 
     * @param 对nationalTaxAddr进行赋值
     */
    public void setNationalTaxAddr(String nationalTaxAddr)
    {
        this.nationalTaxAddr = nationalTaxAddr;
    }
    
    /**
     * 获取 nationalTaxDemo
     * 
     * @return 返回 nationalTaxDemo
     */
    public String getNationalTaxDemo()
    {
        return nationalTaxDemo;
    }
    
    /**
     * 设置 nationalTaxDemo
     * 
     * @param 对nationalTaxDemo进行赋值
     */
    public void setNationalTaxDemo(String nationalTaxDemo)
    {
        this.nationalTaxDemo = nationalTaxDemo;
    }
    
    /**
     * 获取 landTaxNo
     * 
     * @return 返回 landTaxNo
     */
    public String getLandTaxNo()
    {
        return landTaxNo;
    }
    
    /**
     * 设置 landTaxNo
     * 
     * @param 对landTaxNo进行赋值
     */
    public void setLandTaxNo(String landTaxNo)
    {
        this.landTaxNo = landTaxNo;
    }
    
    /**
     * 获取 landTaxNoCpu
     * 
     * @return 返回 landTaxNoCpu
     */
    public String getLandTaxNoCpu()
    {
        return landTaxNoCpu;
    }
    
    /**
     * 设置 landTaxNoCpu
     * 
     * @param 对landTaxNoCpu进行赋值
     */
    public void setLandTaxNoCpu(String landTaxNoCpu)
    {
        this.landTaxNoCpu = landTaxNoCpu;
    }
    
    /**
     * 获取 landTaxNoAddr
     * 
     * @return 返回 landTaxNoAddr
     */
    public String getLandTaxNoAddr()
    {
        return landTaxNoAddr;
    }
    
    /**
     * 设置 landTaxNoAddr
     * 
     * @param 对landTaxNoAddr进行赋值
     */
    public void setLandTaxNoAddr(String landTaxNoAddr)
    {
        this.landTaxNoAddr = landTaxNoAddr;
    }
    
    /**
     * 获取 landTaxNoDemo
     * 
     * @return 返回 landTaxNoDemo
     */
    public String getLandTaxNoDemo()
    {
        return landTaxNoDemo;
    }
    
    /**
     * 设置 landTaxNoDemo
     * 
     * @param 对landTaxNoDemo进行赋值
     */
    public void setLandTaxNoDemo(String landTaxNoDemo)
    {
        this.landTaxNoDemo = landTaxNoDemo;
    }
    
    public List<CustomTaxInfo> getTaxList()
    {
        return taxList;
    }
    
    public void setTaxList(List<CustomTaxInfo> taxList)
    {
        this.taxList = taxList;
    }
    
    public List<CustomAttachment> getFileListInfo()
    {
        return fileListInfo;
    }
    
    public void setFileListInfo(List<CustomAttachment> fileListInfo)
    {
        this.fileListInfo = fileListInfo;
    }
    
    public String getInstitutionNo()
    {
        return institutionNo;
    }
    
    public void setInstitutionNo(String institutionNo)
    {
        this.institutionNo = institutionNo;
    }
    
}
