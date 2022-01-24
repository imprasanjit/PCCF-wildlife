package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="t_blog")
public class BlogDO {
	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	
	
	//@Type(type = "org.hibernate.type.TextType")
	//@JsonSerialize
    @Column(name = "blog_title")
    @Lob
    @Basic(fetch = FetchType.LAZY)
	private String title;
	
    //@Type(type = "org.hibernate.type.TextType")
	@Column(name="blog_description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String blogDesc;
	

	@Column(name="blog_img_path")
	private String blogImgPath;
	
	@Column(name = "seq_no")
	private Integer seqNo;
	
	@Column(name="blog_num")
	private Integer blogNum;
	
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
	
	@Column(name="deleted_status",columnDefinition = "boolean default false")
	private Boolean deletedStatus;
	

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
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
