package com.hessian;

/*
 * 文 件 名:  OrderInfo.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-4-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */



/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OrderInfo implements java.io.Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2954343442234789374L;
    
    /**
     * 客户公司编号(UUID类的主键,长度不要超过9位)
     */
    private String orgCode;
    
    /**
     * 客户公司名称
     */
    private String orgName;
    
    /**
     * 客户联系电话(非常重要,微信接口以及后续对客户的服务,通过此号码去查询合同信息)
     */
    private String telphone;
    
    /**
     * 发布服务的账号ID (必须用这个账号的telphone去查一下代帐公司信息:调用 MntMember getMngInfo(String tel);) 如果存在代帐公司信息,则继续调用,如果不存在.不可以继续调用合同生成接口
     */
    private String bizId;

    /**
     * 每月收费金额(合同)
     */
    private String feeMonth;
    
    /**
     * 账本费(合同)
     */
    private String bookFee;

    /**
     * 服务开始时间(yyyy-mm-dd)
     */
    private String accBegin;
    
    /**
     * 服务截止时间
     */
    private String accEnd;
    
    /**
     * 获取 orgCode
     * 
     * @return 返回 orgCode
     */
    public String getOrgCode()
    {
        return orgCode;
    }
    
    /**
     * 设置 orgCode
     * 
     * @param 对orgCode进行赋值
     */
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
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
     * 获取 telphone
     * 
     * @return 返回 telphone
     */
    public String getTelphone()
    {
        return telphone;
    }

    /**
     * 设置 telphone
     * 
     * @param 对telphone进行赋值
     */
    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

    /**
     * 获取 bizId
     * 
     * @return 返回 bizId
     */
    public String getBizId()
    {
        return bizId;
    }

    /**
     * 设置 bizId
     * 
     * @param 对bizId进行赋值
     */
    public void setBizId(String bizId)
    {
        this.bizId = bizId;
    }

    /**
     * 获取 feeMonth
     * 
     * @return 返回 feeMonth
     */
    public String getFeeMonth()
    {
        return feeMonth;
    }

    /**
     * 设置 feeMonth
     * 
     * @param 对feeMonth进行赋值
     */
    public void setFeeMonth(String feeMonth)
    {
        this.feeMonth = feeMonth;
    }

    /**
     * 获取 bookFee
     * 
     * @return 返回 bookFee
     */
    public String getBookFee()
    {
        return bookFee;
    }

    /**
     * 设置 bookFee
     * 
     * @param 对bookFee进行赋值
     */
    public void setBookFee(String bookFee)
    {
        this.bookFee = bookFee;
    }

    /**
     * 获取 accBegin
     * 
     * @return 返回 accBegin
     */
    public String getAccBegin()
    {
        return accBegin;
    }

    /**
     * 设置 accBegin
     * 
     * @param 对accBegin进行赋值
     */
    public void setAccBegin(String accBegin)
    {
        this.accBegin = accBegin;
    }

    /**
     * 获取 accEnd
     * 
     * @return 返回 accEnd
     */
    public String getAccEnd()
    {
        return accEnd;
    }

    /**
     * 设置 accEnd
     * 
     * @param 对accEnd进行赋值
     */
    public void setAccEnd(String accEnd)
    {
        this.accEnd = accEnd;
    }

    /**
     * 获取 serialversionuid
     * 
     * @return 返回 serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

}
