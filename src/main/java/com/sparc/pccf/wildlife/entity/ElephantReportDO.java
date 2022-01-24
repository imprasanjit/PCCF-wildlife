package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_elephant_report")
public class ElephantReportDO {
	
	@Id
	@Column(name="report_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reportId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="broadcast_descri")
	private String broadcastDescription;
	
	@Column(name="img_path")
	private String imgPath;
	
	@Column(name="audio_message_path")
	private String audioMessagePath;
	
	@Column(name="video_message_path")
	private String videoMessagePath;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	@Column(name="user_id")
	private Long userId;
	
//	@ManyToOne
//    @JoinColumn(name="circle_id", nullable=false)
//    private CircleMasterDO circle;
	
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
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name = "reporting_date")
	private Date reportingDate;
	
	@Column(name = "action_date")
	private Date actionDate;
	
	private String status;
	
	@Column(name="action_by")
	private Integer actionBy;
	
	@Column(name="sync_date")
	private Date syncDate;
	
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Integer getActionBy() {
		return actionBy;
	}

	public void setActionBy(Integer actionBy) {
		this.actionBy = actionBy;
	}

	public Integer getReportId() {
		return reportId;
	}

	public String getBroadcastDescription() {
		return broadcastDescription;
	}

	public void setBroadcastDescription(String broadcastDescription) {
		this.broadcastDescription = broadcastDescription;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getAudioMessagePath() {
		return audioMessagePath;
	}

	public void setAudioMessagePath(String audioMessagePath) {
		this.audioMessagePath = audioMessagePath;
	}

	public String getVideoMessagePath() {
		return videoMessagePath;
	}

	public void setVideoMessagePath(String videoMessagePath) {
		this.videoMessagePath = videoMessagePath;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Date getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}
	

}
