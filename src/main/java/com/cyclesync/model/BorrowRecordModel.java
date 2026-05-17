package com.cyclesync.model;


/*
 * File name: BorrowRecordModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Class: BorrowRecordModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
public class BorrowRecordModel {

    private int recordId;
    private int userId;
    private int bicycleId;
    private Timestamp borrowDate;
    private Timestamp dueDate;
    private Timestamp returnDate;     
    private String recordStatus;   
    private BigDecimal totalHours;     
    private BigDecimal totalCost;      
    private String notes;

    private String memberName;         
    private String memberEmail;        
    private String bicycleName;        
    private String bicycleType;        

    public BorrowRecordModel() {}

    public BorrowRecordModel(int recordId, int userId, int bicycleId,
                             Timestamp borrowDate, Timestamp dueDate,
                             Timestamp returnDate, String recordStatus,
                             BigDecimal totalHours, BigDecimal totalCost,
                             String notes) {
        this.recordId     = recordId;
        this.userId       = userId;
        this.bicycleId    = bicycleId;
        this.borrowDate   = borrowDate;
        this.dueDate      = dueDate;
        this.returnDate   = returnDate;
        this.recordStatus = recordStatus;
        this.totalHours   = totalHours;
        this.totalCost    = totalCost;
        this.notes        = notes;
    }

    public BorrowRecordModel(int userId, int bicycleId,
                             Timestamp borrowDate, Timestamp dueDate) {
        this.userId       = userId;
        this.bicycleId    = bicycleId;
        this.borrowDate   = borrowDate;
        this.dueDate      = dueDate;
        this.recordStatus = "ACTIVE";
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

	public int getBicycleId() {
		return bicycleId;
	}


	// Accesses or updates properties for this model entity

	public void setBicycleId(int bicycleId) {
		this.bicycleId = bicycleId;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getBorrowDate() {
		return borrowDate;
	}


	// Accesses or updates properties for this model entity

	public void setBorrowDate(Timestamp borrowDate) {
		this.borrowDate = borrowDate;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getDueDate() {
		return dueDate;
	}


	// Accesses or updates properties for this model entity

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getReturnDate() {
		return returnDate;
	}


	// Accesses or updates properties for this model entity

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}


	// Accesses or updates properties for this model entity

	public String getRecordStatus() {
		return recordStatus;
	}


	// Accesses or updates properties for this model entity

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}


	// Accesses or updates properties for this model entity

	public BigDecimal getTotalHours() {
		return totalHours;
	}


	// Accesses or updates properties for this model entity

	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}


	// Accesses or updates properties for this model entity

	public BigDecimal getTotalCost() {
		return totalCost;
	}


	// Accesses or updates properties for this model entity

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}


	// Accesses or updates properties for this model entity

	public String getNotes() {
		return notes;
	}


	// Accesses or updates properties for this model entity

	public void setNotes(String notes) {
		this.notes = notes;
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

    public String getBicycleType()                     { return bicycleType; }

    // Accesses or updates properties for this model entity
    public void setBicycleType(String bicycleType)     { this.bicycleType = bicycleType; }


    // Accesses or updates properties for this model entity

    public boolean isActive() { return "ACTIVE".equalsIgnoreCase(this.recordStatus); }

    // Accesses or updates properties for this model entity
    public boolean isReturned() { return "RETURNED".equalsIgnoreCase(this.recordStatus); }

    // Accesses or updates properties for this model entity
    public boolean isOverdue() { return "OVERDUE".equalsIgnoreCase(this.recordStatus); }

}