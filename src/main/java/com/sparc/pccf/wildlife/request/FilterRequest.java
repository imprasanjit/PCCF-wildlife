package com.sparc.pccf.wildlife.request;

public class FilterRequest {
	private String circle_id;
	private String division_id;
	private String range_id;
	private String section_id;
	private String beat_id;
	private Integer [] array;
	public String getCircle_id() {
		return circle_id;
	}
	public void setCircle_id(String circle_id) {
		this.circle_id = circle_id;
	}
	public String getDivision_id() {
		return division_id;
	}
	public void setDivision_id(String division_id) {
		this.division_id = division_id;
	}
	public String getRange_id() {
		return range_id;
	}
	public void setRange_id(String range_id) {
		this.range_id = range_id;
	}
	public String getSection_id() {
		return section_id;
	}
	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}
	public String getBeat_id() {
		return beat_id;
	}
	public void setBeat_id(String beat_id) {
		this.beat_id = beat_id;
	}
	public Integer[] getArray() {
		return array;
	}
	public void setArray(Integer[] array) {
		this.array = array;
	}
	
	
}
