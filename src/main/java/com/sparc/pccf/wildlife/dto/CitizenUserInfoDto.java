package com.sparc.pccf.wildlife.dto;

public class CitizenUserInfoDto {
	private String fullName;	
	private boolean isActive;
	private String mobile;
	private String lastUpdateOn;
	private String blockName;
	private String districtName;
	private String gpName;
	private String villName;
	private String createdOn;
	private String createdBy;
	private String divisionName;
	
	
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLastUpdateOn() {
		return lastUpdateOn;
	}
	public void setLastUpdateOn(String lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getGpName() {
		return gpName;
	}
	public void setGpName(String gpName) {
		this.gpName = gpName;
	}
	public String getVillName() {
		return villName;
	}
	public void setVillName(String villName) {
		this.villName = villName;
	}
	
}
