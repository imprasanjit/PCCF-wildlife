package com.sparc.pccf.wildlife.response;

public class PrimaryLinkResponse {
	
	private Integer id;

	private String name;

	private String linkName;
	
	private String pIcon;
	
	private String isActive;
	
	private Integer psequence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Integer getPsequence() {
		return psequence;
	}

	public void setPsequence(Integer psequence) {
		this.psequence = psequence;
	}

	public String getpIcon() {
		return pIcon;
	}

	public void setpIcon(String pIcon) {
		this.pIcon = pIcon;
	}
}
