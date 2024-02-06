package com.RESTService.Models;

import java.util.Set;

import com.RESTService.Entity.RoleEntity;

//outgoing user response data model (template)
public class UserDetailsResponseModel {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private Set<RoleEntity> role;
	private String token;
	//private String password;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<RoleEntity> getRole() {
		return role;
	}
	public void setRole(Set<RoleEntity> role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
