package com.sparc.pccf.wildlife.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mt_tbl_user",uniqueConstraints = {@UniqueConstraint(columnNames = "user_name")})
public class Newuser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="user_name")
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@Column(name="user_level")
	@NotNull
	private int userlevel;
	
	@Column(name="role_id")
	@NotNull
	private int roleid;
	
	@Column(name="employee_id")
	@NotNull
	private int empid;
	
	@Column(name="insert_by")
	@NotNull
	private int insertby;
	
	@Column(name="update_by")
	@NotNull
	private int updateby;
	
	@Column(name="insert_time")
	private Timestamp inserttime;
	
	@Column(name="update_time")
	private Timestamp updatetime;
	
	private int active;
	
	@NotNull
	private int status;
	
	@Column(name="password_type")
	@NotNull
	private int passwordtype;
	
	public Newuser()
	{
		
	}

	public Newuser(Integer id, @NotNull String username, @NotNull String password, @NotNull int userlevel,
			@NotNull int roleid, @NotNull int empid, @NotNull int insertby, @NotNull int updateby, Timestamp inserttime,
			Timestamp updatetime, int active, @NotNull int status, @NotNull int passwordtype) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userlevel = userlevel;
		this.roleid = roleid;
		this.empid = empid;
		this.insertby = insertby;
		this.updateby = updateby;
		this.inserttime = inserttime;
		this.updatetime = updatetime;
		this.active = active;
		this.status = status;
		this.passwordtype = passwordtype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getInsertby() {
		return insertby;
	}

	public void setInsertby(int insertby) {
		this.insertby = insertby;
	}

	public int getUpdateby() {
		return updateby;
	}

	public void setUpdateby(int updateby) {
		this.updateby = updateby;
	}

	public Timestamp getInserttime() {
		return inserttime;
	}

	public void setInserttime(Timestamp inserttime) {
		this.inserttime = inserttime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPasswordtype() {
		return passwordtype;
	}

	public void setPasswordtype(int passwordtype) {
		this.passwordtype = passwordtype;
	}

	
}
