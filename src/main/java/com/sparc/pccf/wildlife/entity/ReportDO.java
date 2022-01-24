package com.sparc.pccf.wildlife.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_sighting_report")
public class ReportDO {
	
	@Id
	@Column(name="report_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reportId;
	
	@Column(name="sighting_date")
	@NotNull
	private Date sightingDate; 
	
	@Column(name="sighting_time_from")
	private Date sightingTimefrom;
	
	@Column(name="sighting_time_to")
	private Date sightingTimeTo;
	
	@Column(name="sync_date")
	private Date syncDate;
	
	@Column(name="location")
	private String location;
	
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
	
	@Column(name="heard_num")
	private Integer heard;
	
	@Column(name="total_elephant_num")
	private Integer total;
	
	@Column(name="tusker_num")
	private Integer tusker;
	
	@Column(name="mukhna_num")
	private Integer mukhna;
	
	@Column(name="female_num")
	private Integer female;
	
	@Column(name="calf_num")
	private Integer calf;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	@Column(name="report_imei")
	private String reportThrough;
	
	@Column(name="report_type")
	private String reportType;
	
	@Column(name="accuracy")
	private double accuracy;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date createdOn;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="img_acq_location")
	private String imgAcqlocation;
	
	@Column(name="surveyor_userid")
	private Integer surveyorUserId;
	
	private double altitude;
	
	@Column(name="indirect_report_type")
	private String indirectReportType;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="is_duplicate")
	private String isDuplicate;


	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public String getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(String isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Date getSightingDate() {
		return sightingDate;
	}

	public void setSightingDate(Date sightingDate) {
		this.sightingDate = sightingDate;
	}

	public Date getSightingTimefrom() {
		return sightingTimefrom;
	}

	public void setSightingTimefrom(Date sightingTimefrom) {
		this.sightingTimefrom = sightingTimefrom;
	}

	public Date getSightingTimeTo() {
		return sightingTimeTo;
	}

	public void setSightingTimeTo(Date sightingTimeTo) {
		this.sightingTimeTo = sightingTimeTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getHeard() {
		return heard;
	}

	public void setHeard(Integer heard) {
		this.heard = heard;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTusker() {
		return tusker;
	}

	public void setTusker(Integer tusker) {
		this.tusker = tusker;
	}

	public Integer getMukhna() {
		return mukhna;
	}

	public void setMukhna(Integer mukhna) {
		this.mukhna = mukhna;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getCalf() {
		return calf;
	}

	public void setCalf(Integer calf) {
		this.calf = calf;
	}

	public String getReportThrough() {
		return reportThrough;
	}

	public void setReportThrough(String reportThrough) {
		this.reportThrough = reportThrough;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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


	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getImgAcqlocation() {
		return imgAcqlocation;
	}

	public void setImgAcqlocation(String imgAcqlocation) {
		this.imgAcqlocation = imgAcqlocation;
	}

	public Integer getSurveyorUserId() {
		return surveyorUserId;
	}

	public void setSurveyorUserId(Integer surveyorUserId) {
		this.surveyorUserId = surveyorUserId;
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

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getIndirectReportType() {
		return indirectReportType;
	}

	public void setIndirectReportType(String indirectReportType) {
		this.indirectReportType = indirectReportType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
