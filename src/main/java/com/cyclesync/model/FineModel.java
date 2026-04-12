package com.cyclesync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

	public int getFineId() {
		return fineId;
	}

	public void setFineId(int fineId) {
		this.fineId = fineId;
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

	public String getFineReason() {
		return fineReason;
	}

	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}

	public BigDecimal getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}

	public String getFineStatus() {
		return fineStatus;
	}

	public void setFineStatus(String fineStatus) {
		this.fineStatus = fineStatus;
	}

	public Timestamp getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Timestamp issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Timestamp getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(Timestamp resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

	public String getAdminNotes() {
		return adminNotes;
	}

	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}
	
	public String getMemberName()                      { return memberName; }
    public void setMemberName(String memberName)       { this.memberName = memberName; }

    public String getMemberEmail()                     { return memberEmail; }
    public void setMemberEmail(String memberEmail)     { this.memberEmail = memberEmail; }

    public String getBicycleName()                     { return bicycleName; }
    public void setBicycleName(String bicycleName)     { this.bicycleName = bicycleName; }
    
    public boolean isPending()  { return "PENDING".equalsIgnoreCase(this.fineStatus); }
    public boolean isPaid()     { return "PAID".equalsIgnoreCase(this.fineStatus); }
    public boolean isWaived()   { return "WAIVED".equalsIgnoreCase(this.fineStatus); }
}
