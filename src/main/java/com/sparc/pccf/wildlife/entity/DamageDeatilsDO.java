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
@Table(name = "t_damage_details")
public class DamageDeatilsDO {
	@Id
	@Column(name="damage_details_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	private String name;
	
	private double value;
	
	@Column(name="is_active",columnDefinition = "boolean default true")
	private Boolean isActive;
	
	@Column(name="created_on" ,columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date craetedOn;
	
	@Column(name="updated_on",columnDefinition ="timestamp without time zone DEFAULT now()")
	private Date updatedOn;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "inicent_id", nullable = false) private FireDataDO
	 * incidentReport;
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setCraetedOn(Date craetedOn) {
		this.craetedOn = craetedOn;
	}

	/*
	 * public FireDataDO getIncidentReport() { return incidentReport; }
	 * 
	 * public void setIncidentReport(FireDataDO incidentReport) {
	 * this.incidentReport = incidentReport; }
	 */
}
