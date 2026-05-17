package com.cyclesync.model;


/*
 * File name: TransactionModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Class: TransactionModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
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


    // Accesses or updates properties for this model entity

    public int getTransactionId() { return transactionId; }

    // Accesses or updates properties for this model entity
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }


    // Accesses or updates properties for this model entity

    public int getRecordId() { return recordId; }

    // Accesses or updates properties for this model entity
    public void setRecordId(int recordId) { this.recordId = recordId; }


    // Accesses or updates properties for this model entity

    public int getUserId() { return userId; }

    // Accesses or updates properties for this model entity
    public void setUserId(int userId) { this.userId = userId; }


    // Accesses or updates properties for this model entity

    public BigDecimal getAmount() { return amount; }

    // Accesses or updates properties for this model entity
    public void setAmount(BigDecimal amount) { this.amount = amount; }


    // Accesses or updates properties for this model entity

    public String getCurrency() { return currency; }

    // Accesses or updates properties for this model entity
    public void setCurrency(String currency) { this.currency = currency; }


    // Accesses or updates properties for this model entity

    public String getPaymentMethod() { return paymentMethod; }

    // Accesses or updates properties for this model entity
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }


    // Accesses or updates properties for this model entity

    public String getStatus() { return status; }

    // Accesses or updates properties for this model entity
    public void setStatus(String status) { this.status = status; }


    // Accesses or updates properties for this model entity

    public Timestamp getCreatedAt() { return createdAt; }

    // Accesses or updates properties for this model entity
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }


    // Accesses or updates properties for this model entity

    public String getMemberName() { return memberName; }

    // Accesses or updates properties for this model entity
    public void setMemberName(String memberName) { this.memberName = memberName; }


    // Accesses or updates properties for this model entity

    public String getMemberEmail() { return memberEmail; }

    // Accesses or updates properties for this model entity
    public void setMemberEmail(String memberEmail) { this.memberEmail = memberEmail; }


    // Accesses or updates properties for this model entity

    public String getMemberNationality() { return memberNationality; }

    // Accesses or updates properties for this model entity
    public void setMemberNationality(String memberNationality) { this.memberNationality = memberNationality; }


    // Accesses or updates properties for this model entity

    public String getBicycleName() { return bicycleName; }

    // Accesses or updates properties for this model entity
    public void setBicycleName(String bicycleName) { this.bicycleName = bicycleName; }


    // Accesses or updates properties for this model entity

    public Timestamp getBorrowDate() { return borrowDate; }

    // Accesses or updates properties for this model entity
    public void setBorrowDate(Timestamp borrowDate) { this.borrowDate = borrowDate; }


    // Accesses or updates properties for this model entity

    public Timestamp getReturnDate() { return returnDate; }

    // Accesses or updates properties for this model entity
    public void setReturnDate(Timestamp returnDate) { this.returnDate = returnDate; }


    // Accesses or updates properties for this model entity

    public boolean isPending() { return "PENDING".equalsIgnoreCase(status); }

    // Accesses or updates properties for this model entity
    public boolean isCompleted() { return "COMPLETED".equalsIgnoreCase(status); }

    // Accesses or updates properties for this model entity
    public boolean isFailed() { return "FAILED".equalsIgnoreCase(status); }
}
