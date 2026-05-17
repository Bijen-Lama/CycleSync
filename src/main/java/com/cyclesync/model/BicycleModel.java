package com.cyclesync.model;


/*
 * File name: BicycleModel.java
 * Description: CycleSync Data Model Entity
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Class: BicycleModel
 * Role: Encapsulates application state and properties for this domain object
 *
 * This handles primary logic and coordinates system processes.
 */
public class BicycleModel {
	private int bicycleId;
	private String bicycleName;
	private String bicycleType;
	private String bicycleStatus;
	private String locationCode;
	private BigDecimal hourlyRate;
	private String description;
	private String cityName;
	private double latitude;
	private double longitude;
	private Timestamp addedAt;
	private Timestamp updatedAt;
	
	public BicycleModel() {}
	
	public BicycleModel(int bicycleId, String bicycleName, String bicycleType, String bicycleStatus, String locationCode,
			BigDecimal hourlyRate, String description, Timestamp addedAt, Timestamp updatedAt) {
		this.bicycleId = bicycleId;
		this.bicycleName = bicycleName;
		this.bicycleType = bicycleType;
		this.bicycleStatus = bicycleStatus;
		this.locationCode = locationCode;
		this.hourlyRate = hourlyRate;
		this.description = description;
		this.addedAt = addedAt;
		this.updatedAt = updatedAt;
	}
	
	public BicycleModel(String bicycleName, String bicycleType,
            String locationCode, BigDecimal hourlyRate,
            String description) {
		this.bicycleName   = bicycleName;
		this.bicycleType   = bicycleType;
		this.bicycleStatus = "AVAILABLE"; 
		this.locationCode  = locationCode;
		this.hourlyRate    = hourlyRate;
		this.description   = description;
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

	public String getBicycleName() {
		return bicycleName;
	}


	// Accesses or updates properties for this model entity

	public void setBicycleName(String bicycleName) {
		this.bicycleName = bicycleName;
	}


	// Accesses or updates properties for this model entity

	public String getBicycleType() {
		return bicycleType;
	}


	// Accesses or updates properties for this model entity

	public void setBicycleType(String bicycleType) {
		this.bicycleType = bicycleType;
	}


	// Accesses or updates properties for this model entity

	public String getBicycleStatus() {
		return bicycleStatus;
	}


	// Accesses or updates properties for this model entity

	public void setBicycleStatus(String bicycleStatus) {
		this.bicycleStatus = bicycleStatus;
	}


	// Accesses or updates properties for this model entity

	public String getLocationCode() {
		return locationCode;
	}


	// Accesses or updates properties for this model entity

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	// Accesses or updates properties for this model entity

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}


	// Accesses or updates properties for this model entity

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}


	// Accesses or updates properties for this model entity

	public String getDescription() {
		return description;
	}


	// Accesses or updates properties for this model entity

	public void setDescription(String description) {
		this.description = description;
	}


	// Accesses or updates properties for this model entity

	public Timestamp getAddedAt() {
		return addedAt;
	}


	// Accesses or updates properties for this model entity

	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
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
	
	public double getLatitude() { return latitude; }

	// Accesses or updates properties for this model entity
	public void setLatitude(double latitude) { this.latitude = latitude; }

	
	// Accesses or updates properties for this model entity
	
	public double getLongitude() { return longitude; }

	// Accesses or updates properties for this model entity
	public void setLongitude(double longitude) { this.longitude = longitude; }

	
	// Accesses or updates properties for this model entity
	
	public String getCityName() { return cityName; }

	// Accesses or updates properties for this model entity
	public void setCityName(String cityName) { this.cityName = cityName; }

	
	// Accesses or updates properties for this model entity
	
	public boolean isAvailable() { return "AVAILABLE".equalsIgnoreCase(this.bicycleStatus); }

    // Accesses or updates properties for this model entity
    public boolean isBorrowed() { return "BORROWED".equalsIgnoreCase(this.bicycleStatus); }

    // Accesses or updates properties for this model entity
    public boolean isInMaintenance(){ return "MAINTENANCE".equalsIgnoreCase(this.bicycleStatus); }

}
