package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "master_section",uniqueConstraints = { @UniqueConstraint(columnNames = { "section_name" ,"range_id","division_id"})})
public class SectionMasterDO {
	
	@Id
	@Column(name="section_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer secId;
	
	@Column(name="section_name",nullable=false)
	@NotNull
	private String secName;
	
	@ManyToOne
    @JoinColumn(name="range_id", nullable=false)
    private RangeMasterDO range;
	
	@ManyToOne
	@JoinColumn(name = "division_id", nullable = false)
	private DivisionMasterDO division;

	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="Timestamp default now()")
	private Date craetedOn;
	
	@Column(name="updated_on",columnDefinition ="Timestamp default now()")
	private Date updatedOn;
	
	@Column(name="created_by")
	private Integer craetedBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	public Integer getSecId() {
		return secId;
	}
	public void setSecId(Integer secId) {
		this.secId = secId;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCraetedOn() {
		return craetedOn;
	}
	public void setCraetedOn(Date craetedOn) {
		this.craetedOn = craetedOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public RangeMasterDO getRange() {
		return range;
	}
	public void setRange(RangeMasterDO range) {
		this.range = range;
	}
	public Integer getCraetedBy() {
		return craetedBy;
	}
	public void setCraetedBy(Integer craetedBy) {
		this.craetedBy = craetedBy;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
		
}
