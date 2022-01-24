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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "master_division")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DivisionMasterDO {
	
	@Id
	@Column(name="division_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer divisionId;
	
	@Column(name="division_name",unique = true)
	@NotNull
	private String divisionName;
	
	@Column(name = "control_room_PhNo",length = 10)
	private String controlRoomPHno;
	

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
	
	@Column(name = "type_of_forest")
	private String forestType;
	
	@ManyToOne
	@JoinColumn(name = "circle_id", nullable = false)
	private CircleMasterDO circle;

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
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

	public String getForestType() {
		return forestType;
	}

	public void setForestType(String forestType) {
		this.forestType = forestType;
	}

	public CircleMasterDO getCircle() {
		return circle;
	}

	public void setCircle(CircleMasterDO circle) {
		this.circle = circle;
	}

	
}
