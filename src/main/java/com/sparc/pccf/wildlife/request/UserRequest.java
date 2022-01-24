package com.sparc.pccf.wildlife.request;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserRequest {
	
	private String userName;
	
	private String userDesignation;
	
	private String userEmail;

	private String userAddress;

	private String userPhoneNumber;

	private String wlDivision;
	
	private String wlCircle;
	
	private String loginId;
	
	private String password;
	
	private Collection<GrantedAuthority> listOfgrantedAuthorities = new ArrayList<>();


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDesignation() {
		return userDesignation;
	}

	public void setUserDesignation(String userDesignation) {
		this.userDesignation = userDesignation;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getWlDivision() {
		return wlDivision;
	}

	public void setWlDivision(String wlDivision) {
		this.wlDivision = wlDivision;
	}

	public String getWlCircle() {
		return wlCircle;
	}

	public void setWlCircle(String wlCircle) {
		this.wlCircle = wlCircle;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Collection<GrantedAuthority> getListOfgrantedAuthorities() {
		return listOfgrantedAuthorities;
	}

	public void setListOfgrantedAuthorities(Collection<GrantedAuthority> listOfgrantedAuthorities) {
		this.listOfgrantedAuthorities = listOfgrantedAuthorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}		

}
