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
@Table(name = "mt_public_users", uniqueConstraints = { @UniqueConstraint(columnNames = "mobile") })
public class PublicUserDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@NotNull 
	@Column(name = "user_name")
	private String username;

	@Column(name = "age")
	private Integer age;
	
	@Column(name = "mobile")
	@NotNull
	private Long mobile;

	@Column(name = "otp")
	private Integer otp;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on" ,columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@ManyToOne
	@JoinColumn(name = "division_id", nullable = false)
	private DivisionMasterDO division;
	
	@ManyToOne
	@JoinColumn(name = "range_id", nullable = false)
	private RangeMasterDO range;
	
	@ManyToOne
	@JoinColumn(name = "section_id", nullable = false)
	private SectionMasterDO section;
	
	@ManyToOne
	@JoinColumn(name = "beat_id", nullable = false)
	private BeatMasterDO beat;
	
	private String imgPath;
	
	@Column(name="lat")
	private Double latitude;
	
	@Column(name="long")
	private Double longitude;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

}
