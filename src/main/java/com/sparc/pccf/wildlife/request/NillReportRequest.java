package com.sparc.pccf.wildlife.request;

public class NillReportRequest {
	
	private String sightingTimefrom;

	private String sightingTimeTo;

	private String location;

	private String division;

	private String range;

	private String section;

	private String beat;
	
	private String reportThrough;

	private String reportUser;

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

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getBeat() {
		return beat;
	}

	public void setBeat(String beat) {
		this.beat = beat;
	}

	public String getReportThrough() {
		return reportThrough;
	}

	public void setReportThrough(String reportThrough) {
		this.reportThrough = reportThrough;
	}

	public String getReportUser() {
		return reportUser;
	}

	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}
	
}
