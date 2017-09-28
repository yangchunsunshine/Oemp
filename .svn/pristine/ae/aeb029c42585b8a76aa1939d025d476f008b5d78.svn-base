package com.wb.component.computer.billManage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 订单
 * 
 * @author `
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String phoneNum;
	private String companyId;
	private String companyName;
	private String orgId;
	private String orgName;
	private String basePackageId;
	private BigDecimal basePackageMoney;
	private BigDecimal avaBalance;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	// To Page Display Pattern
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	// To Page Display Pattern
	private Date endDate;
	private Integer orderStatus;
	private Integer operStatus;
	private Integer withStatus;
	private Integer payType;
	private Integer type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	// To Page Display Pattern
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	// To Page Display Pattern
	private Date updateTime;

	public Order() {
		super();
	}

	public Order(String id, String code, String phoneNum, String companyId,
			String companyName, String orgId, String orgName,
			String basePackageId, BigDecimal basePackageMoney, Date startDate,
			Date endDate, Integer orderStatus, Integer operStatus,
			Integer withStatus, Integer payType, Integer type, Date createTime,
			Date updateTime) {
		super();
		this.id = id;
		this.code = code;
		this.phoneNum = phoneNum;
		this.companyId = companyId;
		this.companyName = companyName;
		this.orgId = orgId;
		this.orgName = orgName;
		this.basePackageId = basePackageId;
		this.basePackageMoney = basePackageMoney;
		this.startDate = startDate;
		this.endDate = endDate;
		this.orderStatus = orderStatus;
		this.operStatus = operStatus;
		this.withStatus = withStatus;
		this.payType = payType;
		this.type = type;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBasePackageId() {
		return basePackageId;
	}

	public void setBasePackageId(String basePackageId) {
		this.basePackageId = basePackageId;
	}

	public BigDecimal getBasePackageMoney() {
		return basePackageMoney;
	}

	public void setBasePackageMoney(BigDecimal basePackageMoney) {
		this.basePackageMoney = basePackageMoney;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getWithStatus() {
		return withStatus;
	}

	public void setWithStatus(Integer withStatus) {
		this.withStatus = withStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getAvaBalance() {
		return avaBalance;
	}

	public void setAvaBalance(BigDecimal avaBalance) {
		this.avaBalance = avaBalance;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", code=" + code + ", phoneNum=" + phoneNum
				+ ", companyId=" + companyId + ", companyName=" + companyName
				+ ", orgId=" + orgId + ", orgName=" + orgName
				+ ", basePackageId=" + basePackageId + ", basePackageMoney="
				+ basePackageMoney + ", avaBalance=" + avaBalance
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", orderStatus=" + orderStatus + ", operStatus=" + operStatus
				+ ", withStatus=" + withStatus + ", payType=" + payType
				+ ", type=" + type + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}

}
