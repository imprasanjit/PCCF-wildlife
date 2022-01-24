package com.sparc.pccf.wildlife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sms_counter")
public class SmSCounter {
	
		@Id
		@Column(name="slno")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer slno;
		
		@Column(name="report_id")
		private Integer ReportId;
		
		@Column(name="report_type")
		private String ReportType;
		
		@Column(name="sms_count")
		private Integer SmsCount;
		
		@Column(name="mobile_numbers",columnDefinition="TEXT")
		private String mobileNumbers;
		
		@Column(name="is_active",columnDefinition = "boolean default true")
		private Boolean isActive;
		
		@Column(name="created_on",columnDefinition ="timestamp without time zone DEFAULT now()")
		private Date craetedOn;
		
		@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
		private Date updatedOn;
		
		@Column(name = "deleted_status",columnDefinition = "boolean default false")
		private boolean deletedStatus;
		

		public Integer getSlno() {
			return slno;
		}

		public void setSlno(Integer slno) {
			this.slno = slno;
		}

		public Integer getReportId() {
			return ReportId;
		}

		public void setReportId(Integer reportId) {
			ReportId = reportId;
		}

		public String getReportType() {
			return ReportType;
		}

		public void setReportType(String reportType) {
			ReportType = reportType;
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

		public Integer getSmsCount() {
			return SmsCount;
		}

		public void setSmsCount(Integer smsCount) {
			SmsCount = smsCount;
		}

		public boolean isDeletedStatus() {
			return deletedStatus;
		}

		public void setDeletedStatus(boolean deletedStatus) {
			this.deletedStatus = deletedStatus;
		}

		public String getMobileNumbers() {
			return mobileNumbers;
		}

		public void setMobileNumbers(String mobileNumbers) {
			this.mobileNumbers = mobileNumbers;
		}
		
	
}
