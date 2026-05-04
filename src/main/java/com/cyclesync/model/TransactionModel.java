package com.cyclesync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionModel {

    private int transactionId;
    private int recordId;
    private int userId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String status;
    private Timestamp createdAt;

    // Enrichment from JOINs
    private String memberName;
    private String memberEmail;
    private String memberNationality;
    private String bicycleName;
    private Timestamp borrowDate;
    private Timestamp returnDate;

    public TransactionModel() {}

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getMemberEmail() { return memberEmail; }
    public void setMemberEmail(String memberEmail) { this.memberEmail = memberEmail; }

    public String getMemberNationality() { return memberNationality; }
    public void setMemberNationality(String memberNationality) { this.memberNationality = memberNationality; }

    public String getBicycleName() { return bicycleName; }
    public void setBicycleName(String bicycleName) { this.bicycleName = bicycleName; }

    public Timestamp getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Timestamp borrowDate) { this.borrowDate = borrowDate; }

    public Timestamp getReturnDate() { return returnDate; }
    public void setReturnDate(Timestamp returnDate) { this.returnDate = returnDate; }

    public boolean isPending() { return "PENDING".equalsIgnoreCase(status); }
    public boolean isCompleted() { return "COMPLETED".equalsIgnoreCase(status); }
    public boolean isFailed() { return "FAILED".equalsIgnoreCase(status); }
}
