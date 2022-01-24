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
@Table(name = "citizen_user")
public class CitizenUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@NotNull
	private String fullname;

	@Column(name = "mobile", length = 10)
	@NotNull
	private String mobile;
	
	@ManyToOne
	@JoinColumn(name = "village_id", nullable = false)
	private VillageMaster village;
	
	@ManyToOne
	@JoinColumn(name = "gp_id", nullable = false)
	private GpMaster gp;
	
	@ManyToOne
	@JoinColumn(name = "block_id", nullable = false)
	private BlockMaster block;
	
	@ManyToOne
	@JoinColumn(name = "dist_id", nullable = false)
	private DistrictMasterDO district;

	@Column(name = "created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date userCreationDate;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	@Column(name = "created_by")
	private Integer craetedBy;

	@Column(name = "updated_by")
	private Integer updatedBy;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name = "deleted_status",columnDefinition = "boolean default false")
	private boolean deletedStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public VillageMaster getVillage() {
		return village;
	}

	public void setVillage(VillageMaster village) {
		this.village = village;
	}

	public GpMaster getGp() {
		return gp;
	}

	public void setGp(GpMaster gp) {
		this.gp = gp;
	}

	public BlockMaster getBlock() {
		return block;
	}

	public void setBlock(BlockMaster block) {
		this.block = block;
	}

	public DistrictMasterDO getDistrict() {
		return district;
	}

	public void setDistrict(DistrictMasterDO district) {
		this.district = district;
	}

	public Date getUserCreationDate() {
		return userCreationDate;
	}

	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
	
	

}
