package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="t_tender")
public class TenderDO {
	@Id
	@Column(name="tender_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tenderId;
	
	@Column(name="tender_num")
	private String tenderNum;
	
	@Column(name="tender_name")
	private String tenderName;
	
	/*
	 * @Column(name="tender_date")
	 * 
	 * @DateTimeFormat(pattern = "yyyy-MM-dd") private Date tenderDate;
	 */
	
	@Column(name="tender_path")
	private String tenderPath;
	
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
	
	@Column(name = "upload_file_path")
	private String uploadPath;
	
	@Column(name = "date")
	private Date publicationDate;
	
	@Column(name = "deleted_status",columnDefinition = "boolean default false")
	private boolean deletedStatus;
	
	/*
	 * @Column(name = "month") private String month;
	 * 
	 * @Column(name = "year") private Integer year;
	 */
	
	@Column(name = "title")
	private String title;
	
	
    public String getTenderNum() {
		return tenderNum;
	}

	public void setTenderNum(String tenderNum) {
		this.tenderNum = tenderNum;
	}

	public String getTenderName() {
		return tenderName;
	}

	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	public String getTenderPath() {
		return tenderPath;
	}

	public void setTenderPath(String tenderPath) {
		this.tenderPath = tenderPath;
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

	/*
	 * public Date getTenderDate() { return tenderDate; }
	 * 
	 * public void setTenderDate(Date tenderDate) { this.tenderDate = tenderDate; }
	 */

	public Integer getTenderId() {
		return tenderId;
	}

	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

    public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	/*
	 * public String getMonth() { return month; }
	 * 
	 * public void setMonth(String month) { this.month = month; }
	 * 
	 * public Integer getYear() { return year; }
	 * 
	 * public void setYear(Integer year) { this.year = year; }
	 */

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
	
	
}


