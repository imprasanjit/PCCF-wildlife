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
@Table(name = "master_range",uniqueConstraints = { @UniqueConstraint(columnNames = { "range_name" ,"division_id"})})
public class RangeMasterDO {

	@Id
	@Column(name = "range_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rangeId;

	@Column(name = "range_name")
	@NotNull
	private String rangeName;

	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;

	@Column(name = "created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;

	@Column(name = "updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;

	@Column(name = "created_by")
	private Integer craetedBy;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@ManyToOne
	@JoinColumn(name = "division_id", nullable = false)
	private DivisionMasterDO division;

	public Integer getRangeId() {
		return rangeId;
	}

	public void setRangeId(Integer rangeId) {
		this.rangeId = rangeId;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
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
	
	public DivisionMasterDO getDivision() {
		return division;
	}

	public void setDivision(DivisionMasterDO division) {
		this.division = division;
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
