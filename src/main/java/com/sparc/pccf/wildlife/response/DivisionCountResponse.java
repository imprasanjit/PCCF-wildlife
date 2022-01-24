package com.sparc.pccf.wildlife.response;

import java.util.List;

public class DivisionCountResponse {
	   String divisionId;
	   String divisionName;
	   Integer count;
	   private List<RangeCountResponse> range;
		public String getDivisionId() {
			return divisionId;
		}
		public void setDivisionId(String divisionId) {
			this.divisionId = divisionId;
		}
		public String getDivisionName() {
			return divisionName;
		}
		public void setDivisionName(String divisionName) {
			this.divisionName = divisionName;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public List<RangeCountResponse> getRange() {
			return range;
		}
		public void setRange(List<RangeCountResponse> range) {
			this.range = range;
		}
		
	

}
