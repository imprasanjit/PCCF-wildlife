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

@Entity
@Table(name = "mt_gp")
public class GpMaster {
	@Id
	@Column(name="gp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gpId;
	
	@Column(name="grampanchayat_name")
	@NotNull
	private String gpName;
	
	@Column(name="grampanchayat_code",unique = true)
	@NotNull
	private String gpCode;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name="created_by")
	private Integer craetedBy;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "dist_id", nullable = false)
	private DistrictMasterDO district;
	
	@ManyToOne
	@JoinColumn(name = "block_id", nullable = false)
	private BlockMaster block;

	public Integer getGpId() {
		return gpId;
	}

	public void setGpId(Integer gpId) {
		this.gpId = gpId;
	}

	public String getGpName() {
		return gpName;
	}

	public void setGpName(String gpName) {
		this.gpName = gpName;
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

	public DistrictMasterDO getDistrict() {
		return district;
	}

	public void setDistrict(DistrictMasterDO district) {
		this.district = district;
	}

	public BlockMaster getBlock() {
		return block;
	}

	public void setBlock(BlockMaster block) {
		this.block = block;
	}
	

	
}
