package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mnt_processInfoTmp", catalog = "accounting")
public class MntProcessInfoTmp implements Serializable {
	private Integer id;
	private String processName;
	private String processType;
	private Date createDate;
	private Integer state;
	private String adminId;
	
	public String getAdminId() {
		return adminId;
	}
	@Column(name="adminId")
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public MntProcessInfoTmp(Integer id, String processName,
			String processType, Date createDate, Integer state, String adminId) {
		super();
		this.id = id;
		this.processName = processName;
		this.processType = processType;
		this.createDate = createDate;
		this.state = state;
		this.adminId = adminId;
	}
	public MntProcessInfoTmp() {
	}

	public MntProcessInfoTmp(Integer id, String processName,
			String processType, Date createDate, Integer state) {
		this.id = id;
		this.processName = processName;
		this.processType = processType;
		this.createDate = createDate;
		this.state = state;
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
	@Column(name="processName")
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	@Column(name="processType")
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	@Column(name="createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	
}
