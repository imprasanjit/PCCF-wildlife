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

@Entity
@Table(name = "mt_wl_corridor_details")
public class WlCorriodorDO {
	
	@Id
	@Column(name="cor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="corridor_name")
	private String corridorName;
	
	@Column(name="length")
	private Double length;
	
	@Column(name="width")
	private Double width;
	
	@Column(name="area")
	private Double area;
	
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

	@ManyToOne
    @JoinColumn(name="range_id", nullable=false)
    private RangeMasterDO range;
	
	@ManyToOne
    @JoinColumn(name="section_id", nullable=false)
    private SectionMasterDO section;
	
	@ManyToOne
    @JoinColumn(name="beat_id", nullable=false)
    private BeatMasterDO beat;
	
	@Column(name="accuracy")
	private double accuracy;
	
	@Column(name="altitude")
	private double altitude;
	
	@Column(name="latitude")
	private double latitude;
	
	@Column(name="longitude")
	private double longitude;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorridorName() {
		return corridorName;
	}

	public void setCorridorName(String corridorName) {
		this.corridorName = corridorName;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
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

	public RangeMasterDO getRange() {
		return range;
	}

	public void setRange(RangeMasterDO range) {
		this.range = range;
	}

	public SectionMasterDO getSection() {
		return section;
	}

	public void setSection(SectionMasterDO section) {
		this.section = section;
	}

	public BeatMasterDO getBeat() {
		return beat;
	}

	public void setBeat(BeatMasterDO beat) {
		this.beat = beat;
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

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getAltitude() {
		return altitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
