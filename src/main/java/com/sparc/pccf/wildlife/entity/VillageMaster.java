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
@Table(name = "mt_village")
public class VillageMaster {	
	@Id
	@Column(name="village_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer villageId;
	
	@Column(name="revenue_village_name")
	@NotNull
	private String villageName;
	
	@Column(name="revenue_village_code")
	@NotNull
	private String villageCode;
	
	@Column(name="confirm_rv_status")
	@NotNull
	private String confirmRvStatus;
	
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
	
	@ManyToOne
	@JoinColumn(name = "gp_id", nullable = false)
	private GpMaster gp;

	
	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public String getConfirmRvStatus() {
		return confirmRvStatus;
	}

	public void setConfirmRvStatus(String confirmRvStatus) {
		this.confirmRvStatus = confirmRvStatus;
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

	public GpMaster getGp() {
		return gp;
	}

	public void setGp(GpMaster gp) {
		this.gp = gp;
	}

	
	
}
