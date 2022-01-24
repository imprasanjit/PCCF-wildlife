package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_login_activity")
public class LoginActivity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visit_id")
	private Integer visitId;
	
	@JoinColumn(name = "user_login_id",nullable = false)
	@OneToOne
	private User user;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "logIn_date_time")
	private Date LoginDateTime;
	
	@Column(name = "logout_date_time")
	private Date LogOutDateTime;
	
	

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginDateTime() {
		return LoginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		LoginDateTime = loginDateTime;
	}

	public Date getLogOutDateTime() {
		return LogOutDateTime;
	}

	public void setLogOutDateTime(Date logOutDateTime) {
		LogOutDateTime = logOutDateTime;
	}


	

}
