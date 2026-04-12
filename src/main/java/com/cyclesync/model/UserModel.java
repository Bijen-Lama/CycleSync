package com.cyclesync.model;

import java.sql.Timestamp;

public class UserModel {
	private int userId;
	private String fullName;
	private String userEmail;
	private String userPassword;
	private String phoneNumber;
	private String userAddress;
	private String userRole;
	private String accountStatus;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public boolean isAdmin()  { return "ADMIN".equalsIgnoreCase(this.userRole); }
	
	public boolean isActive() { return "ACTIVE".equalsIgnoreCase(this.accountStatus); }
}
