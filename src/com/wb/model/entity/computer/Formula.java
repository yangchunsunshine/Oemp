package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "formula", catalog = "accounting")
public class Formula implements Serializable {
	
	private Integer id;
	private String fname;
	private String fid;
	private Integer num;
	private Integer zhibiaojiid;
	private String remark;
	
	public Formula() {
		super();
	}
	
	public Formula(Integer id, String fname, String fid, Integer num, Integer zhibiaojiid, String remark) {
		super();
		this.id = id;
		this.fname = fname;
		this.fid = fid;
		this.num = num;
		this.zhibiaojiid = zhibiaojiid;
		this.remark = remark;
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
	@Column(name="formula_name")
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Column(name="formula_id")
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	@Column(name="num")
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Column(name="zhibiaoji_id")
	public Integer getZhibiaojiid() {
		return zhibiaojiid;
	}
	public void setZhibiaojiid(Integer zhibiaojiid) {
		this.zhibiaojiid = zhibiaojiid;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
