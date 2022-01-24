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
@Table(name = "t_gallery")
public class Gallery {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/*
	 * @Column(name = "type") private String type;
	 */
	@ManyToOne
    @JoinColumn(name="type_id", nullable=false)
    private GalleryTypeMaster typemaster;
	
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "img_path")
	private String imgPath;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name = "created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date createdOn;
	
	@Column(name = "updated_by")
	private Integer updatedBy;
	
	@Column(name = "updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;

	@Column(name = "deleted_status",columnDefinition = "boolean default false")
	private boolean deletedStatus;
	
	public boolean isDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public GalleryTypeMaster getTypemaster() {
		return typemaster;
	}

	public void setTypemaster(GalleryTypeMaster typemaster) {
		this.typemaster = typemaster;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}
