package com.sparc.pccf.wildlife.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mt_sanctuary_details")
public class SanctuaryInfoMasterDO {
	
	@Id
	@Column(name="sanctuary_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sancId;
	
	@Column(name="date_of_ntification")
	private Date dateOfNtification;
	
	@Column(name="notified_area")
	private double notifiedArea;
	
	@Column(name="details")
	private String details;
	
	@Column(name="img_url")
	private String imgUrl;
	
	@Column(name="sanctuary_name",unique = true)
	@NotNull
	private String sanctuaryName;
	
	@Column(name="is_active")
	private Boolean isActive;
	
	@Column(name="created_on")
	private Date craetedOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@Column(name="created_by")
	private Integer craetedBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@ManyToOne
    @JoinColumn(name="division_id", nullable=false)
    private DivisionMasterDO division;
	
//	@ManyToOne
//    @JoinColumn(name="range_id", nullable=false)
//    private RangeMasterDO range;
	
//	@ManyToOne
//    @JoinColumn(name="section_id", nullable=false)
//    private SectionMasterDO section;
	
//	@ManyToOne
//    @JoinColumn(name="beat_id", nullable=false)
//    private BeatMasterDO beat;
	
	@Column(name="district_id")
    private Integer district;
	
    public Integer getSancId() {
		return sancId;
	}

	public void setSancId(Integer sancId) {
		this.sancId = sancId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSanctuaryName() {
		return sanctuaryName;
	}

	public void setSanctuaryName(String sanctuaryName) {
		this.sanctuaryName = sanctuaryName;
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

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
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

	public Date getDateOfNtification() {
		return dateOfNtification;
	}

	public void setDateOfNtification(Date dateOfNtification) {
		this.dateOfNtification = dateOfNtification;
	}

	public double getNotifiedArea() {
		return notifiedArea;
	}

	public void setNotifiedArea(double notifiedArea) {
		this.notifiedArea = notifiedArea;
	}


}
