package com.sparc.pccf.wildlife.response;

import java.util.List;
import java.util.Set;

import com.sparc.pccf.wildlife.entity.DamageDeatilsDO;

public class IncidentReportResponse {
	
    private String name;
    
    private String countValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountValue() {
		return countValue;
	}

	public void setCountValue(String countValue) {
		this.countValue = countValue;
	}	
}
