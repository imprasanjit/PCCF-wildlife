package com.sparc.pccf.wildlife.request;

public class LinkAssignToLinkRequest {
	
	private String roleID;
	
	private String gLinkId;
	
	private String pLinkId;
	
	private Boolean isActive;
	

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getgLinkId() {
		return gLinkId;
	}

	public void setgLinkId(String gLinkId) {
		this.gLinkId = gLinkId;
	}

	public String getpLinkId() {
		return pLinkId;
	}

	public void setpLinkId(String pLinkId) {
		this.pLinkId = pLinkId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
