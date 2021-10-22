package com.mmit.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mmit.entity.Users.Role;

import javax.persistence.Basic;
import javax.persistence.Column;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NamedQuery(name = "findAllUser",query = "SELECT u FROM Users u WHERE u.email <> :email")
@NamedQuery(name = "findByLoginIdUser",query = "SELECT u FROM Users u WHERE u.email=:email")
@NamedQuery(name = "countUsers",query = "SELECT COUNT(u.email) FROM Users u")
@NamedQuery(name = "findByIdUsers",query = "SELECT u FROM Users u WHERE u.id=:id")
public class Users implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "Require Email")
	@Column(unique = true)
	private String email;
	@NotEmpty(message = "Require Password")
	private String password;
	private String userName;
	private String phone;
	private String address;
	@Enumerated(EnumType.STRING)
	private Role role;
	@CreationTimestamp
	private LocalDate created_at;
	@UpdateTimestamp
	private LocalDate update_at;
	
	
	public enum Role {Admin,Staff,Customer}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public LocalDate getCreated_at() {
		return created_at;
	}


	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}


	public LocalDate getUpdate_at() {
		return update_at;
	}


	public void setUpdate_at(LocalDate update_at) {
		this.update_at = update_at;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	

}
