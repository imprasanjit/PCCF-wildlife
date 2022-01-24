package com.sparc.pccf.wildlife.response;

import java.util.List;

public class CircleCountResponse {
	   Integer circleId;
	   String circleName;
	   Integer count;
	   private List<DivisionCountResponse> div;
		//private List<RangeCountResponse> rng;
	public List<DivisionCountResponse> getDiv() {
			return div;
		}
	public void setDiv(List<DivisionCountResponse> div) {
			this.div = div;
		}
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	/*
	 * public List<RangeCountResponse> getRng() { return rng; } public void
	 * setRng(List<RangeCountResponse> rng) { this.rng = rng; }
	 */
	
	   
}
