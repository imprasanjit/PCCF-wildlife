package com.sparc.pccf.wildlife.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String username;
	private String email;
	private List<String> roles;
	private Integer roleId;
	private String juridictionName;
	private Integer juridictionId;
	private Integer circleId;
	private Integer divisionId;
	private Integer rangeId;
	private Integer sectionId;
	private Integer beatId;
	private Integer seqNo;

	public JwtResponse(String accessToken, Integer id, String username, String email, List<String> roles,Integer roleId,Integer circleId,Integer divisionId,Integer rangeId,Integer sectionId,Integer beatId,String juridictionName,Integer juridictionId,Integer seqNo) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.roleId=roleId;
		this.juridictionName=juridictionName;
		this.juridictionId=juridictionId;
		this.circleId=circleId;
		this.divisionId=divisionId;
		this.rangeId=rangeId;
		this.sectionId=sectionId;
		this.beatId=beatId;
		this.seqNo=seqNo;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getJuridictionName() {
		return juridictionName;
	}

	public void setJuridictionName(String juridictionName) {
		this.juridictionName = juridictionName;
	}

	public Integer getJuridictionId() {
		return juridictionId;
	}

	public void setJuridictionId(Integer juridictionId) {
		this.juridictionId = juridictionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public Integer getRangeId() {
		return rangeId;
	}

	public void setRangeId(Integer rangeId) {
		this.rangeId = rangeId;
	}

	public Integer getBeatId() {
		return beatId;
	}

	public void setBeatId(Integer beatId) {
		this.beatId = beatId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

}

