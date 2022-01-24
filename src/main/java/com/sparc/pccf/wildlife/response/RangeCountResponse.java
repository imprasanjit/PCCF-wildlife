package com.sparc.pccf.wildlife.response;

public class RangeCountResponse {
	String rangeId;
	   String rangeName;
	   Integer  count;
	public String getRangeId() {
		return rangeId;
	}
	public void setRangeId(String rangeId) {
		this.rangeId = rangeId;
	}
	public String getRangeName() {
		return rangeName;
	}
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
