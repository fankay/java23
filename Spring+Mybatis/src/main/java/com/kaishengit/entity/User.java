package com.kaishengit.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userName;
	private String address;
	private String password;
	private Integer deptId;
	
	
	public User(){}
	
	public User(String userName, String address, String password, Integer deptId) {
		this.userName = userName;
		this.address = address;
		this.password = password;
		this.deptId = deptId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", address=" + address + ", password=" + password + "]";
	}
	
	
}
