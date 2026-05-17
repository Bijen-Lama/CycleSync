package com.cyclesync.model;


/*
 * File name: FineModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Class: FineModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
public class FineModel {
	private int fineId;
	private int recordId;
	private int userId;
	private String fineReason;
	private BigDecimal fineAmount;
	private String fineStatus;
	private Timestamp issuedAt;
	private Timestamp resolvedAt;
	private String adminNotes;
	
	private String memberName;
	private String memberEmail;
	private String bicycleName;
	
	public FineModel() {}

	public FineModel(int fineId, int recordId, int userId, String fineReason, BigDecimal fineAmount, String fineStatus,
			Timestamp issuedAt, Timestamp resolvedAt, String adminNotes, String memberName, String memberEmail,
			String bicycleName) {
		this.fineId = fineId;
		this.recordId = recordId;
		this.userId = userId;
		this.fineReason = fineReason;
		this.fineAmount = fineAmount;
		this.fineStatus = fineStatus;
		this.issuedAt = issuedAt;
		this.resolvedAt = resolvedAt;
		this.adminNotes = adminNotes;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.bicycleName = bicycleName;
	}
	
	public FineModel(int recordId, int userId,
            String fineReason, BigDecimal fineAmount,
            String adminNotes) {
		this.recordId   = recordId;
		this.userId     = userId;
		this.fineReason = fineReason;
		this.fineAmount = fineAmount;
		this.fineStatus = "PENDING";
		this.adminNotes = adminNotes;
	}


	// Accesses or updates properties for this model entity

	public int getFineId() {
		return fineId;
	}


	// Accesses or updates properties for this model entity

	public void setFineId(int fineId) {
		this.fineId = fineId;
	}


	// Accesses or updates properties for this model entity

	public int getRecordId() {
		return recordId;
	}


	// Accesses or updates properties for this model entity

	public void setRecordId(int recordId) {
		this.recordId = recordId;
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

	public String getFineReason() {
		return fineReason;
	}


	// Accesses or updates properties for this model entity

	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}


	// Accesses or updates properties for this model entity

	public BigDecimal getFineAmount() {
		return fineAmount;
	}


	// Accesses or updates properties for this model entity

	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}


	// Accesses or updates properties for this model entity

	public String getFineStatus() {
		return fineStatus;
	}


	// Accesses or updates properties for this model entity

	public void setFineStatus(String fineStatus) {
		this.fineStatus = fineStatus;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getIssuedAt() {
		return issuedAt;
	}


	// Accesses or updates properties for this model entity

	public void setIssuedAt(Timestamp issuedAt) {
		this.issuedAt = issuedAt;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getResolvedAt() {
		return resolvedAt;
	}


	// Accesses or updates properties for this model entity

	public void setResolvedAt(Timestamp resolvedAt) {
		this.resolvedAt = resolvedAt;
	}


	// Accesses or updates properties for this model entity

	public String getAdminNotes() {
		return adminNotes;
	}


	// Accesses or updates properties for this model entity

	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}

	
	// Accesses or updates properties for this model entity
	
	public String getMemberName()                      { return memberName; }

    // Accesses or updates properties for this model entity
    public void setMemberName(String memberName)       { this.memberName = memberName; }


    // Accesses or updates properties for this model entity

    public String getMemberEmail()                     { return memberEmail; }

    // Accesses or updates properties for this model entity
    public void setMemberEmail(String memberEmail)     { this.memberEmail = memberEmail; }


    // Accesses or updates properties for this model entity

    public String getBicycleName()                     { return bicycleName; }

    // Accesses or updates properties for this model entity
    public void setBicycleName(String bicycleName)     { this.bicycleName = bicycleName; }

    
    // Accesses or updates properties for this model entity
    
    public boolean isPending()  { return "PENDING".equalsIgnoreCase(this.fineStatus); }

    // Accesses or updates properties for this model entity
    public boolean isPaid()     { return "PAID".equalsIgnoreCase(this.fineStatus); }

    // Accesses or updates properties for this model entity
    public boolean isWaived()   { return "WAIVED".equalsIgnoreCase(this.fineStatus); }
}
