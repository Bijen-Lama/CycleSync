package com.cyclesync.model;


/*
 * File name: UserModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.sql.Timestamp;


/**
 * Class: UserModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
public class UserModel {
	private int userId;
	private String fullName;
	private String userEmail;
	private String userPassword;
	private String phoneNumber;
	private String userAddress;
	private String userRole;
	private String accountStatus;
	private String nationality;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public UserModel() {}
	
	public UserModel(int userId, String fullName, String userEmail, String userPassword, String phoneNumber,
			String userAddress, String userRole, String accountStatus, Timestamp createdAt, Timestamp updatedAt) {
		this.userId = userId;
		this.fullName = fullName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.phoneNumber = phoneNumber;
		this.userAddress = userAddress;
		this.userRole = userRole;
		this.accountStatus = accountStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public UserModel(String fullName, String userEmail, String userPassword, String phoneNumber, String userAddress) {
		this.fullName = fullName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.phoneNumber = phoneNumber;
		this.userAddress  = userAddress;
        this.userRole     = "MEMBER";
        this.accountStatus = "ACTIVE";
	}


	// Accesses or updates properties for this model entity

	public int getUserId() {
		return userId;
	}


	// Accesses or updates properties for this model entity

	public void setUserId(int userId) {
		this.userId = userId;
	}


	// Accesses or updates properties for this model entity

	public String getFullName() {
		return fullName;
	}


	// Accesses or updates properties for this model entity

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	// Accesses or updates properties for this model entity

	public String getUserEmail() {
		return userEmail;
	}


	// Accesses or updates properties for this model entity

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	// Accesses or updates properties for this model entity

	public String getUserPassword() {
		return userPassword;
	}


	// Accesses or updates properties for this model entity

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	// Accesses or updates properties for this model entity

	public String getPhoneNumber() {
		return phoneNumber;
	}


	// Accesses or updates properties for this model entity

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	// Accesses or updates properties for this model entity

	public String getUserAddress() {
		return userAddress;
	}


	// Accesses or updates properties for this model entity

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}


	// Accesses or updates properties for this model entity

	public String getUserRole() {
		return userRole;
	}


	// Accesses or updates properties for this model entity

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	// Accesses or updates properties for this model entity

	public String getAccountStatus() {
		return accountStatus;
	}


	// Accesses or updates properties for this model entity

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	// Accesses or updates properties for this model entity

	public String getNationality() { return nationality; }

	// Accesses or updates properties for this model entity
	public void setNationality(String nationality) { this.nationality = nationality; }

	
	// Accesses or updates properties for this model entity
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}


	// Accesses or updates properties for this model entity

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}


	// Accesses or updates properties for this model entity

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	// Accesses or updates properties for this model entity
	
	public boolean isAdmin()  { return "ADMIN".equalsIgnoreCase(this.userRole); }

	
	// Accesses or updates properties for this model entity
	
	public boolean isActive() { return "ACTIVE".equalsIgnoreCase(this.accountStatus); }
}
