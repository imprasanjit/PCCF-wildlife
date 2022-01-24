package com.sparc.pccf.wildlife.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@Entity
@Table(name = "mt_primary_link")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryLinkMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name="path")
	private String linkName;
	
	@Column(name="primary_sequence")
	private Integer plinkSequence;
	
	@Column(name="icon")
	private String iconName;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="glink_id", nullable=false) private GlobalLinkMaster glink;
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getPlinkSequence() {
		return plinkSequence;
	}

	public void setPlinkSequence(Integer plinkSequence) {
		this.plinkSequence = plinkSequence;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/*
	 * public GlobalLinkMaster getGlink() { return glink; }
	 * 
	 * public void setGlink(GlobalLinkMaster glink) { this.glink = glink; }
	 */

}
