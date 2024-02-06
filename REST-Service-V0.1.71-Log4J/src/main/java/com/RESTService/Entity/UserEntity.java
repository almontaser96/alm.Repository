package com.RESTService.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
@Entity(name="users")
public class UserEntity implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 587100730883578822L;
	@Id
	@GeneratedValue
	private Long system_id;
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false,length=50)
	private String firstName;
	
	@Column(nullable=false,length=50)
	private String lastName;
	
	@Column(nullable=false,length=100/*,unique=true*/)
	private String email;
	

	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	private String encryptedPassword;
	
	private String emailVerificationToken;
	
	@Column(nullable=false)
	private Boolean emailVerificationStatus=false;

	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="User_Roles",
	
	joinColumns={
		    @JoinColumn(name="SYSTEM_ID")	
			},
	inverseJoinColumns={
			@JoinColumn(name="ROLE_ID")
			}
		)
	
	private Set<RoleEntity> role; 
	
	@Column//(nullable=false)
	private long registrationDate;
	
	@Column//(nullable=false)
	private LocalDate expirationDate;
	
	
	public long getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(long registrationDate) {
		this.registrationDate = registrationDate;
	}

	
    public Set<RoleEntity> getRole() {
		return this.role;
	}

	public void setRole(Set<RoleEntity> role) {
		this.role=role;
	}

	public Long getId() {
		return system_id;
	}

	public void setId(Long id) {
		this.system_id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
       Set<RoleEntity> role = this.getRole();
	   List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
	   java.util.Iterator<RoleEntity> i=role.iterator();
		
		while(i.hasNext()) {
			roles.add(new SimpleGrantedAuthority("ROLE_"+i.next().getRoleName()));			
		 }
		return roles;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		//System.out.println(LocalDate.now().compareTo(this.getExpirationDate()));
		if(LocalDate.now().compareTo(this.getExpirationDate())<0)
		{return true;} else {return false;}
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
