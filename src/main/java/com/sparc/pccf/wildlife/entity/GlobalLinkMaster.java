package com.sparc.pccf.wildlife.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "mt_global_link")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalLinkMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer glink_id;

	private String name;
	@Column(name = "path")
	private String linkName;

	private Integer globalSeq;
	
	@Column(name="icon")
	private String iconName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="glink_id", nullable=false)
	private Set<PrimaryLinkMaster> pLInk;

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

	public Set<PrimaryLinkMaster> getpLInk() {
		return pLInk;
	}

	public void setpLInk(Set<PrimaryLinkMaster> pLInk) {
		this.pLInk = pLInk;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

}
