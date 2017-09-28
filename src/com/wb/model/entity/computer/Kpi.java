package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "kpi", catalog = "accounting")
public class Kpi implements Serializable {
	private Integer id;
	private String znname;
	private String zid;
	private String time;
	private Integer sort;
	private String remark;
	private Integer zhibiaojiId;
	
	public Kpi() {
		super();
	}
	
	public Kpi(Integer id, String znname, String zid, String time, Integer sort, String remark, Integer zhibiaojiId) {
		super();
		this.id = id;
		this.znname = znname;
		this.zid = zid;
		this.time = time;
		this.sort = sort;
		this.remark = remark;
		this.zhibiaojiId = zhibiaojiId;
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
	@Column(name="znname")
	public String getZnname() {
		return znname;
	}
	public void setZnname(String znname) {
		this.znname = znname;
	}
	@Column(name="zid")
	public String getZid() {
		return zid;
	}
	public void setZid(String zid) {
		this.zid = zid;
	}
	@Column(name="time")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Column(name="sort")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="zhibiaoji_id")
	public Integer getZhibiaojiId() {
		return zhibiaojiId;
	}

	public void setZhibiaojiId(Integer zhibiaojiId) {
		this.zhibiaojiId = zhibiaojiId;
	}
	
	
	
	

}
