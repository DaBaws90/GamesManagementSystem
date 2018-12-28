package com.example.AdrianCarrasco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="user_role")
public class UserRole {
	
	@Id
	@GeneratedValue
	@Column(name="user_role_id")
	private int userRoleId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="username")
	private User user;
	
	@Column(name="role")
	private String role;

	public UserRole() {
		super();
	}

	public UserRole(int userRoleId, User user, String role) {
		super();
		this.userRoleId = userRoleId; // Puede ser necesario crearlo sin el Id, como en el tutorial
		this.user = user;
		this.role = role;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", user=" + user + ", role=" + role + "]";
	}
	

}

