package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_publication")
public class PublicationDO {
	@Id
	@Column(name = "publication_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(name = "seq_no")
	private Integer seqNo;
	
	@Column(name = "publication_title")
	private String publicationTitle;
	
	@Column(name = "thumbnil_img_path")
	private String thumbnilImgPath;
	
	@Column(name = "pdf_path")
	private String pdfPath;
	
	@Column(name ="publication_num")
	private Integer publicationNum;
	
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
	
	@Column(name = "deleted_status",columnDefinition = "boolean default false")
	private boolean deletedStatus;

	//getter and setter methods
	

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getPublicationTitle() {
		return publicationTitle;
	}

	public void setPublicationTitle(String publicationTitle) {
		this.publicationTitle = publicationTitle;
	}

	public String getThumbnilImgPath() {
		return thumbnilImgPath;
	}

	public void setThumbnilImgPath(String thumbnilImgPath) {
		this.thumbnilImgPath = thumbnilImgPath;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public Integer getPublicationNum() {
		return publicationNum;
	}

	public void setPublicationNum(Integer publicationNum) {
		this.publicationNum = publicationNum;
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

	public boolean isDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
	
	
	
}
