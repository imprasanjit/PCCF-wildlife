package com.sparc.pccf.wildlife.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_role_permission")
public class RolePermissionDO {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(name="role_id")
	@NotNull
	private String roleId;
	
	@Column(name="glink_Id")
	@NotNull
	private String glinkId;
	
	@Column(name="plink_Id")
	private String plinkId;
	
	private Boolean isActive;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGlinkId() {
		return glinkId;
	}

	public void setGlinkId(String glinkId) {
		this.glinkId = glinkId;
	}

	public String getPlinkId() {
		return plinkId;
	}

	public void setPlinkId(String plinkId) {
		this.plinkId = plinkId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
