package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mnt_resumes", catalog = "accounting")
public class Mnt_resumes {
	private Integer id;
	private String position;
	private String telephone;
	private String email;
	private String resume;
	
	public Mnt_resumes() {
	}

	public Mnt_resumes(Integer id, String position, String telephone,
			String email, String resume) {
		this.id = id;
		this.position = position;
		this.telephone = telephone;
		this.email = email;
		this.resume = resume;
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
	@Column(name="position")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="resume")
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
	
	

}
