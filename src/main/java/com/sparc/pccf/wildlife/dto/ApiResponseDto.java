package com.sparc.pccf.wildlife.dto;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseDto {

	String beat_id;
   
	List<String> village_id;
	String[] village;

	public String getBeat_id() {
		return beat_id;
	}

	public void setBeat_id(String beat_id) {
		this.beat_id = beat_id;
	}

	public List<String> getVillage_id() {
		return village_id;
	}

	public void setVillage_id(List<String> village_id) {
		this.village_id = village_id;
	}

	public String[] getVillage() {
		return village;
	}

	public void setVillage(String[] village) {
		this.village = village;
	}
	

	
	
	
}
