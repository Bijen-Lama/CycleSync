package com.cyclesync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    
    public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBicycleId() {
		return bicycleId;
	}

	public void setBicycleId(int bicycleId) {
		this.bicycleId = bicycleId;
	}

	public Timestamp getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Timestamp borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public BigDecimal getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMemberName()                      { return memberName; }
    public void setMemberName(String memberName)       { this.memberName = memberName; }

    public String getMemberEmail()                     { return memberEmail; }
    public void setMemberEmail(String memberEmail)     { this.memberEmail = memberEmail; }

    public String getBicycleName()                     { return bicycleName; }
    public void setBicycleName(String bicycleName)     { this.bicycleName = bicycleName; }

    public String getBicycleType()                     { return bicycleType; }
    public void setBicycleType(String bicycleType)     { this.bicycleType = bicycleType; }

    public boolean isActive() { return "ACTIVE".equalsIgnoreCase(this.recordStatus); }
    public boolean isReturned() { return "RETURNED".equalsIgnoreCase(this.recordStatus); }
    public boolean isOverdue() { return "OVERDUE".equalsIgnoreCase(this.recordStatus); }

}