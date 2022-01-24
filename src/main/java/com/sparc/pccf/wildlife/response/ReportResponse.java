package com.sparc.pccf.wildlife.response;

public class ReportResponse {
	private String id;
	private String accuracy;
	private String altitude;
	private String calf;
	private String createdBy;
	private String createdOn;
	private String female;
	private String heard;
	private String imgAcqlocation;
	private String indirectReportType;
	private String isActive;
	private String latitude;
	private String location;
	private String longitude;
	private String mukhna;
	private String remarks;
	private String reportThrough;
	private String reportType;
	private String sightingDate;
	private String sightingTimeTo; 
	private String sightingTimefrom;
	private String surveyorUserId;
	private String total;
	private String tusker;
	private String updatedBy;
	private String updatedOn;
	private String beatName;
	private String divisionName;
	private String rangeName;
	private String secName;
	private String divisionId;
	private String duplicateReport;

	/* getters and setters method */
	
	public String getDuplicateReport() {
		return duplicateReport;
	}

	public void setDuplicateReport(String duplicateReport) {
		this.duplicateReport = duplicateReport;
	}

	public String getSightingDate() {
		return sightingDate;
	}

	public void setSightingDate(String sightingDate) {
		this.sightingDate = sightingDate;
	}

	public String getSightingTimefrom() {
		return sightingTimefrom;
	}

	public void setSightingTimefrom(String sightingTimefrom) {
		this.sightingTimefrom = sightingTimefrom;
	}

	public String getSightingTimeTo() {
		return sightingTimeTo;
	}

	public void setSightingTimeTo(String sightingTimeTo) {
		this.sightingTimeTo = sightingTimeTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getBeatName() {
		return beatName;
	}

	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}

	public String getHeard() {
		return heard;
	}

	public void setHeard(String heard) {
		this.heard = heard;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTusker() {
		return tusker;
	}

	public void setTusker(String tusker) {
		this.tusker = tusker;
	}

	public String getMukhna() {
		return mukhna;
	}

	public void setMukhna(String mukhna) {
		this.mukhna = mukhna;
	}

	public String getFemale() {
		return female;
	}

	public void setFemale(String female) {
		this.female = female;
	}

	public String getCalf() {
		return calf;
	}

	public void setCalf(String calf) {
		this.calf = calf;
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

	public String getReportThrough() {
		return reportThrough;
	}

	public void setReportThrough(String reportThrough) {
		this.reportThrough = reportThrough;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getImgAcqlocation() {
		return imgAcqlocation;
	}

	public void setImgAcqlocation(String imgAcqlocation) {
		this.imgAcqlocation = imgAcqlocation;
	}

	public String getSurveyorUserId() {
		return surveyorUserId;
	}

	public void setSurveyorUserId(String surveyorUserId) {
		this.surveyorUserId = surveyorUserId;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndirectReportType() {
		return indirectReportType;
	}

	public void setIndirectReportType(String indirectReportType) {
		this.indirectReportType = indirectReportType;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	@Override
	public String toString() {
		return "ReportResponse [id=" + id + ", accuracy=" + accuracy + ", altitude=" + altitude + ", calf=" + calf
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", female=" + female + ", heard=" + heard
				+ ", imgAcqlocation=" + imgAcqlocation + ", indirectReportType=" + indirectReportType + ", isActive="
				+ isActive + ", latitude=" + latitude + ", location=" + location + ", longitude=" + longitude
				+ ", mukhna=" + mukhna + ", remarks=" + remarks + ", reportThrough=" + reportThrough + ", reportType="
				+ reportType + ", sightingDate=" + sightingDate + ", sightingTimeTo=" + sightingTimeTo
				+ ", sightingTimefrom=" + sightingTimefrom + ", surveyorUserId=" + surveyorUserId + ", total=" + total
				+ ", tusker=" + tusker + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", beatName="
				+ beatName + ", divisionName=" + divisionName + ", rangeName=" + rangeName + ", secName=" + secName
				+ ", divisionId=" + divisionId + "]";
	}



	

}
