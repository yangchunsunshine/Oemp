package com.wb.model.entity.computer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mnt_cooperation", catalog = "accounting")
public class Cooperation {
	private Integer id;
	private String name;
	private String company;
	private String telephone;
	private String email;
	private String intention;
	
	
	public Cooperation() {
		
	}


	public Cooperation(Integer id, String name, String company,
			String telephone, String email, String intention) {
		
		this.id = id;
		this.name = name;
		this.company = company;
		this.telephone = telephone;
		this.email = email;
		this.intention = intention;
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

	@Column(name="name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Column(name="company")
	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
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

	@Column(name="intention")
	public String getIntention() {
		return intention;
	}


	public void setIntention(String intention) {
		this.intention = intention;
	}
	

}
