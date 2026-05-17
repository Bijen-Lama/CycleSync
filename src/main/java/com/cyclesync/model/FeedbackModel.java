package com.cyclesync.model;


/*
 * File name: FeedbackModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.sql.Timestamp;


/**
 * Class: FeedbackModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
public class FeedbackModel {
    private int feedbackId;
    private int userId;
    private String type; // SUGGESTION, COMPLAINT
    private String subject;
    private String message;
    private String status; // NEW, READ, RESOLVED
    private Timestamp createdAt;
    
    // Enriched fields for view
    private String userName;
    private String userEmail;

    public FeedbackModel() {}


    // Accesses or updates properties for this model entity

    public int getFeedbackId() { return feedbackId; }

    // Accesses or updates properties for this model entity
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }


    // Accesses or updates properties for this model entity

    public int getUserId() { return userId; }

    // Accesses or updates properties for this model entity
    public void setUserId(int userId) { this.userId = userId; }


    // Accesses or updates properties for this model entity

    public String getType() { return type; }

    // Accesses or updates properties for this model entity
    public void setType(String type) { this.type = type; }


    // Accesses or updates properties for this model entity

    public String getSubject() { return subject; }

    // Accesses or updates properties for this model entity
    public void setSubject(String subject) { this.subject = subject; }


    // Accesses or updates properties for this model entity

    public String getMessage() { return message; }

    // Accesses or updates properties for this model entity
    public void setMessage(String message) { this.message = message; }


    // Accesses or updates properties for this model entity

    public String getStatus() { return status; }

    // Accesses or updates properties for this model entity
    public void setStatus(String status) { this.status = status; }


    // Accesses or updates properties for this model entity

    public Timestamp getCreatedAt() { return createdAt; }

    // Accesses or updates properties for this model entity
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }


    // Accesses or updates properties for this model entity

    public String getUserName() { return userName; }

    // Accesses or updates properties for this model entity
    public void setUserName(String userName) { this.userName = userName; }


    // Accesses or updates properties for this model entity

    public String getUserEmail() { return userEmail; }

    // Accesses or updates properties for this model entity
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
