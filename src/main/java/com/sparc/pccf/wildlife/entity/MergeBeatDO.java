package com.sparc.pccf.wildlife.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="merge_beat_32645")
public class MergeBeatDO 
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="gid")
  private int gid;
  
  @Column(name="divn_name")
  private String divisionName;
  
  @Column(name="rng_name")
  private String rangeName;
  
  @Column(name="sec_name")
  private String sectionName;
  
  @Column(name="beat_name")
  private String beatName;
  
  @Column(name="division")
  private String division;
  
  @Column(name="range")
  private String range;
  
  @Column(name="section")
  private String section;
  
  @Column(name="beat")
  private String beat;


  public int getGid() {
	return gid;
}

public void setGid(int gid) {
	this.gid = gid;
}

public String getDivisionName() {
	return divisionName;
}

public void setDivisionName(String divisionName) {
	this.divisionName = divisionName;
}

public String getRangeName() {
	return rangeName;
}

public void setRangeName(String rangeName) {
	this.rangeName = rangeName;
}

public String getSectionName() {
	return sectionName;
}

public void setSectionName(String sectionName) {
	this.sectionName = sectionName;
}

public String getBeatName() {
	return beatName;
}

public void setBeatName(String beatName) {
	this.beatName = beatName;
}

public String getDivision() {
	return division;
}

public void setDivision(String division) {
	this.division = division;
}

public String getRange() {
	return range;
}

public void setRange(String range) {
	this.range = range;
}

public String getSection() {
	return section;
}

public void setSection(String section) {
	this.section = section;
}

public String getBeat() {
	return beat;
}

public void setBeat(String beat) {
	this.beat = beat;
}
  
  
}
