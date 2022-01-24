package com.sparc.pccf.wildlife.request;

import java.util.Date;
import java.util.List;

public class IncidentReportRequest {
	
	private String location;
	
	private String reportType;
	private String incidentRemark;
	
	private String incidentDate;
	

	private String incidentOf;
	
	private String latitude;
	private String longitude;
	private List<DamageRequest> damage;
	private String accuracy;
	private String altitude;
	private String user;
	private String imgPath;
	
	private String deathReason;

	public String getDeathReason() {
		return deathReason;
	}

	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}

	public String getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(String incidentDate) {
		this.incidentDate = incidentDate;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	private String circleId;
	
	
	public String getIncidentRemark() {
		return incidentRemark;
	}

	public void setIncidentRemark(String incidentRemark) {
		this.incidentRemark = incidentRemark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getIncidentOf() {
		return incidentOf;
	}

	public void setIncidentOf(String incidentOf) {
		this.incidentOf = incidentOf;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<DamageRequest> getDamage() {
		return damage;
	}

	public void setDamage(List<DamageRequest> damage) {
		this.damage = damage;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
}
