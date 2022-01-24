package com.sparc.pccf.wildlife.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_fire_details")
public class FireDataDO {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="latitude")
	private double latitude;
	
	@Column(name="longitude")
	private double longitude;
	
	@Column(name="incident_reported_on")
	private Date incidentReport;
	
	@Column(name="incident_photo")
	private String incidentPhoto;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name="sync_date")
	private Date syncDate;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="accuracy")
	private double accuracy;
	
	@Column(name="altitude")
	private double altitude;
	
	@ManyToOne
    @JoinColumn(name="circle_id", nullable=false)
    private CircleMasterDO circle;
	
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
	
	private String incidentReportType;
	
	@Column(name="incident_remarks")
	private String incidentRemark;

	public String getIncidentRemark() {
		return incidentRemark;
	}

	public void setIncidentRemark(String incidentRemark) {
		this.incidentRemark = incidentRemark;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="incident_id", nullable=false)
	private List<DamageDeatilsDO> damageDetails;
	

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIncidentPhoto() {
		return incidentPhoto;
	}

	public void setIncidentPhoto(String incidentPhoto) {
		this.incidentPhoto = incidentPhoto;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public Date getIncidentReport() {
		return incidentReport;
	}

	public void setIncidentReport(Date incidentReport) {
		this.incidentReport = incidentReport;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public CircleMasterDO getCircle() {
		return circle;
	}

	public void setCircle(CircleMasterDO circle) {
		this.circle = circle;
	}

	public String getIncidentReportType() {
		return incidentReportType;
	}

	public void setIncidentReportType(String incidentReportType) {
		this.incidentReportType = incidentReportType;
	}

	public List<DamageDeatilsDO> getDamageDetails() {
		return damageDetails;
	}

	public void setDamageDetails(List<DamageDeatilsDO> damageDetails) {
		this.damageDetails = damageDetails;
	}

	
}
