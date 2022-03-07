package com.example.checkads_backend_admin.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="tel")
    private String tel;

    @Column(name="career")
    private String career;
    
    @Column(name="policy")
    private String policy;
    
    @Column(name="regisDate")
    private String regisDate;
    
    @Column(name="expDate")
    private String expDate;
    
    @Column(name="fbId")
    private String fbId;
    
    @Column(name="lineId")
    private String lineId;
    
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role; //Admin  

    @Transient
    private String token;
    
    public Admin() {

    }
    
    public Admin(String email, String password, String firstname, String lastname, String tel, String career, String policy, String regisDate,
    		String expDate, String fbId, String lineId, Role role){
    	 this.email = email;
    	 this.password = password;
    	 this.firstname = firstname;
    	 this.lastname = lastname;
    	 this.tel = tel;
    	 this.career = career;
    	 this.policy = policy;
    	 this.regisDate = regisDate;
    	 this.expDate = expDate;
    	 this.fbId = fbId;
    	 this.lineId = lineId;
    	 this.role = role;
    	 
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getRegisDate() {
		return regisDate;
	}

	public void setRegisDate(String regisDate) {
		this.regisDate = regisDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}

