package com.RESTService.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1568126305597096488L;
	@Id
	private Long id;
	private String roleName;
	private String roleDiscription;
	
	public Long getId() {
		return id;
	}
	public void setId(Long role_id) {
		this.id = role_id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDiscription() {
		return roleDiscription;
	}
	public void setRoleDiscription(String roleDiscription) {
		this.roleDiscription = roleDiscription;
	}
	
}

 