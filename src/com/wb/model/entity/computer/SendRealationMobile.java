package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "mnt_sendrealationmoblie", catalog = "accounting")
public class SendRealationMobile {
	private Integer id;
	private Integer sendId;
	private Integer orgId;
	private String mobile;
	private String orgName;
	private Date createDate;
	
	public SendRealationMobile() {
		super();
	}
	public SendRealationMobile(Integer id, Integer sendId, Integer orgId,
			String mobile, String orgName, Date createDate) {
		super();
		this.id = id;
		this.sendId = sendId;
		this.orgId = orgId;
		this.mobile = mobile;
		this.orgName = orgName;
		this.createDate = createDate;
	}
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="sendId")
	public Integer getSendId() {
		return sendId;
	}
	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}
	@Column(name="orgId")
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	@Column(name="mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name="orgName")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name="createDate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
