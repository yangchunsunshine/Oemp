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
@Table(name = "mnt_sendmessage", catalog = "accounting")
public class SendMessage implements Serializable{
	private Integer id;
	private String content;// 短信内容
	private int status;//
	private Date sendtime;//发送时间
	private Date cratetime;//创建时间
	private Integer num;//发送短信数量
	private String topname;//抬头名字
	private Integer orchange;//短信抬头是否更改过 0 否  1 是
	private Integer memberId;// 关联代账公司id
	private String telephone;
	private String reason;
	private Integer smsstatus;
	private Integer type;
	public SendMessage() {	
	}

	public SendMessage(Integer id, String content, int status, Date sendtime,
			Date cratetime, Integer num, String topname, Integer orchange,
			Integer memberId, String telephone, String reason,
			Integer smsstatus, Integer type) {
		super();
		this.id = id;
		this.content = content;
		this.status = status;
		this.sendtime = sendtime;
		this.cratetime = cratetime;
		this.num = num;
		this.topname = topname;
		this.orchange = orchange;
		this.memberId = memberId;
		this.telephone = telephone;
		this.reason = reason;
		this.smsstatus = smsstatus;
		this.type = type;
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
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="sendtime")
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	@Column(name="createtime")
	public Date getCratetime() {
		return cratetime;
	}
	public void setCratetime(Date cratetime) {
		this.cratetime = cratetime;
	}
	@Column(name="num")
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Column(name="topname")
	public String getTopname() {
		return topname;
	}
	public void setTopname(String topname) {
		this.topname = topname;
	}
	@Column(name="orchange")
	public Integer getOrchange() {
		return orchange;
	}
	public void setOrchange(Integer orchange) {
		this.orchange = orchange;
	}
	@Column(name="member_id")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(name="smsstatus")
	public Integer getSmsstatus() {
		return smsstatus;
	}
	public void setSmsstatus(Integer smsstatus) {
		this.smsstatus = smsstatus;
	}
	@Column(name="type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
