package com.sparc.pccf.wildlife.dto;

import java.util.Date;

public class BlogDto 
{
    private String title;
    
    private Integer blog_id;
	
	private String blogDesc;
	
	private String blogImgPath;
	
	private Integer seqNo;
	
	private Integer blogNum;
	
	private Boolean isActive;
	
	private Date craetedOn;
	
	private Date updatedOn;
	
	private Integer craetedBy;
	
	private Integer updatedBy;
	
	private Boolean deletedStatus;
	
	private String  createdByName;
	
	private String userDesignation;
	

	public Integer getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(Integer blog_id) {
		this.blog_id = blog_id;
	}

	public String getUserDesignation() {
		return userDesignation;
	}

	public void setUserDesignation(String userDesignation) {
		this.userDesignation = userDesignation;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlogDesc() {
		return blogDesc;
	}

	public void setBlogDesc(String blogDesc) {
		this.blogDesc = blogDesc;
	}

	public String getBlogImgPath() {
		return blogImgPath;
	}

	public void setBlogImgPath(String blogImgPath) {
		this.blogImgPath = blogImgPath;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getBlogNum() {
		return blogNum;
	}

	public void setBlogNum(Integer blogNum) {
		this.blogNum = blogNum;
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

	public Boolean getDeletedStatus() {
		return deletedStatus;
	}

	public void setDeletedStatus(Boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
	}
	
	
	


}
