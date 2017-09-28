package com.wb.component.computer.billManage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 代理记账费订单
 * @author KK
 *
 */
public class BillProxyAccountOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String phoneNum;
	private String orgId;//企业ID
	private String orgName;//企业name
	private String memberId;//代理公司ID
	private String memberName;//代理公司name
	private String basePackageId;
	private BigDecimal basePackageMoney;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")//To Page Display Pattern
	private Date startDate;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")//To Page Display Pattern
	private Date endDate;
	private Integer orderStatus;
	private Integer operStatus;
	private Integer settleStatus;
	private Integer type;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")//To Page Display Pattern
	private Date createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")//To Page Display Pattern
	private Date updateTime;
	public BillProxyAccountOrder() {
		super();
	}
	public BillProxyAccountOrder(String id, String code, String phoneNum,
			String orgId, String orgName, String memberId, String memberName,
			String basePackageId, BigDecimal basePackageMoney, Date startDate,
			Date endDate, Integer orderStatus, Integer operStatus,
			Integer settleStatus, Integer type, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.code = code;
		this.phoneNum = phoneNum;
		this.orgId = orgId;
		this.orgName = orgName;
		this.memberId = memberId;
		this.memberName = memberName;
		this.basePackageId = basePackageId;
		this.basePackageMoney = basePackageMoney;
		this.startDate = startDate;
		this.endDate = endDate;
		this.orderStatus = orderStatus;
		this.operStatus = operStatus;
		this.settleStatus = settleStatus;
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public Integer getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(Integer settleStatus) {
		this.settleStatus = settleStatus;
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
	@Override
	public String toString() {
		return "BillProxyAccountOrder [id=" + id + ", code=" + code
				+ ", phoneNum=" + phoneNum + ", orgId=" + orgId + ", orgName="
				+ orgName + ", memberId=" + memberId + ", memberName="
				+ memberName + ", basePackageId=" + basePackageId
				+ ", basePackageMoney=" + basePackageMoney + ", startDate="
				+ startDate + ", endDate=" + endDate + ", orderStatus="
				+ orderStatus + ", operStatus=" + operStatus
				+ ", settleStatus=" + settleStatus + ", type=" + type
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
	
	

}
