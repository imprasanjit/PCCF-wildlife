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
@Table(name = "master_beat",uniqueConstraints = { @UniqueConstraint(columnNames = { "beat_name" ,"range_id","section_id","division_id"})})
public class BeatMasterDO {

	@Id
	@Column(name="beat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer beatId;
	
	@Column(name="beat_name")
	@NotNull
	private String beatName;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@ManyToOne
	@JoinColumn(name="division_id",nullable=false)
	private DivisionMasterDO division;
	
	@ManyToOne
	@JoinColumn(name = "range_id", nullable = false)
	private RangeMasterDO range;
	
	@ManyToOne
	@JoinColumn(name = "section_id", nullable = false)
	private SectionMasterDO section;
	
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name="created_by")
	private Integer craetedBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	
	public Integer getBeatId() {
		return beatId;
	}

	public void setBeatId(Integer beatId) {
		this.beatId = beatId;
	}

	public String getBeatName() {
		return beatName;
	}

	public void setBeatName(String beatName) {
		this.beatName = beatName;
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

	public SectionMasterDO getSection() {
		return section;
	}

	public void setSection(SectionMasterDO section) {
		this.section = section;
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

	public DivisionMasterDO getDivision() {
		return division;
	}

	public void setDivision(DivisionMasterDO division) {
		this.division = division;
	}

	public RangeMasterDO getRange() {
		return range;
	}

	public void setRange(RangeMasterDO range) {
		this.range = range;
	}
	
}
