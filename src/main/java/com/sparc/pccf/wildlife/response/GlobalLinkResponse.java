package com.sparc.pccf.wildlife.response;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.sparc.pccf.wildlife.entity.PrimaryLinkMaster;

public class GlobalLinkResponse {
	
	private Integer glink_id;

	private String name;
	
	private String linkName;
	
	private String gIcon;

	private Integer globalSeq;

	private List<PrimaryLinkResponse> pLInk;
	
	private String isActive;


	public Integer getGlink_id() {
		return glink_id;
	}


	public void setGlink_id(Integer glink_id) {
		this.glink_id = glink_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLinkName() {
		return linkName;
	}


	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}


	public Integer getGlobalSeq() {
		return globalSeq;
	}


	public void setGlobalSeq(Integer globalSeq) {
		this.globalSeq = globalSeq;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public List<PrimaryLinkResponse> getpLInk() {
		return pLInk;
	}


	public void setpLInk(List<PrimaryLinkResponse> pLInk) {
		this.pLInk = pLInk;
	}


	public String getgIcon() {
		return gIcon;
	}


	public void setgIcon(String gIcon) {
		this.gIcon = gIcon;
	}

}
