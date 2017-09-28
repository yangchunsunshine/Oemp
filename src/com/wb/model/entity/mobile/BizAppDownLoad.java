package com.wb.model.entity.mobile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "biz_appdownload")
public class BizAppDownLoad {

	private Integer id;
	
	private String version;
	
	private Date stamp;
	
	private byte[] appbin;
	
	private String appName;
	
	private String signture;
	
	private String enable;
	
	private Integer allRecord;
	private Integer type;
	
	@Column(name ="type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Id
	@GeneratedValue
	@Column(name ="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name ="version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name ="appbin")
	public byte[] getAppbin() {
		return appbin;
	}

	public void setAppbin(byte[] appbin) {
		this.appbin = appbin;
	}

	@Column(name ="signture",length=32)
	public String getSignture() {
		return signture;
	}

	public void setSignture(String signture) {
		this.signture = signture;
	}

	@Column(name ="stamp")
	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	@Column(name ="appname",length=255)
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Transient
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Transient
	public Integer getAllRecord() {
		return allRecord;
	}

	public void setAllRecord(Integer allRecord) {
		this.allRecord = allRecord;
	}
}
