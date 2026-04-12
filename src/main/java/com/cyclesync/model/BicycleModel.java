package com.cyclesync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BicycleModel {
	private int bicycleId;
	private String bicycleName;
	private String bicycleType;
	private String bicycleStatus;
	private String locationCode;
	private BigDecimal hourlyRate;
	private String description;
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

	public int getBicycleId() {
		return bicycleId;
	}

	public void setBicycleId(int bicycleId) {
		this.bicycleId = bicycleId;
	}

	public String getBicycleName() {
		return bicycleName;
	}

	public void setBicycleName(String bicycleName) {
		this.bicycleName = bicycleName;
	}

	public String getBicycleType() {
		return bicycleType;
	}

	public void setBicycleType(String bicycleType) {
		this.bicycleType = bicycleType;
	}

	public String getBicycleStatus() {
		return bicycleStatus;
	}

	public void setBicycleStatus(String bicycleStatus) {
		this.bicycleStatus = bicycleStatus;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public boolean isAvailable() { return "AVAILABLE".equalsIgnoreCase(this.bicycleStatus); }
    public boolean isBorrowed() { return "BORROWED".equalsIgnoreCase(this.bicycleStatus); }
    public boolean isInMaintenance(){ return "MAINTENANCE".equalsIgnoreCase(this.bicycleStatus); }

}
