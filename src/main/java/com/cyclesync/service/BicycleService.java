package com.cyclesync.service;


/*
 * File name: BicycleService.java
 * Description: CycleSync Service Layer Component
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.dao.BicycleDao;
import com.cyclesync.model.BicycleModel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * BicycleService - Fleet management and availability search logic.
 */

/**
 * Class: BicycleService
 * Role: Coordinates business logic and orchestrates database transactions
 *
 * This handles primary logic and coordinates system processes.
 */
public class BicycleService {

    private final BicycleDao bicycleDao = new BicycleDao();

    // ----------------------------------------------------------------
    // Admin — Fleet Management
    // ----------------------------------------------------------------


    // Applies business rules and communicates with the data access layer

    public int addBicycle(String bicycleName, String bicycleType,
                          String locationCode, String hourlyRateStr,
                          String description) throws SQLException {

        if (bicycleName == null || bicycleName.trim().isEmpty())
            throw new IllegalArgumentException("Bicycle name is required.");

        BigDecimal hourlyRate;
        try {
            hourlyRate = new BigDecimal(hourlyRateStr);
            if (hourlyRate.compareTo(BigDecimal.ZERO) < 0)
                throw new IllegalArgumentException("Hourly rate cannot be negative.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hourly rate format.");
        }

        BicycleModel bike = new BicycleModel(
            bicycleName.trim(), bicycleType.toUpperCase(),
            locationCode.trim(), hourlyRate, description
        );
        return bicycleDao.insertBicycle(bike);
    }


    // Applies business rules and communicates with the data access layer

    public boolean updateBicycle(int bicycleId, String bicycleName, String bicycleType,
                                 String bicycleStatus, String locationCode,
                                 String hourlyRateStr, String description) throws SQLException {

        BicycleModel bike = bicycleDao.findById(bicycleId);
        if (bike == null)
            throw new IllegalArgumentException("Bicycle not found.");

        BigDecimal hourlyRate;
        try {
            hourlyRate = new BigDecimal(hourlyRateStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hourly rate format.");
        }

        bike.setBicycleName(bicycleName.trim());
        bike.setBicycleType(bicycleType.toUpperCase());
        bike.setBicycleStatus(bicycleStatus.toUpperCase());
        bike.setLocationCode(locationCode.trim());
        bike.setHourlyRate(hourlyRate);
        bike.setDescription(description);
        return bicycleDao.updateBicycle(bike);
    }


    // Applies business rules and communicates with the data access layer

    public boolean updateStatus(int bicycleId, String bicycleStatus) throws SQLException {
        return bicycleDao.updateStatus(bicycleId, bicycleStatus.toUpperCase());
    }


    // Applies business rules and communicates with the data access layer

    public boolean deleteBicycle(int bicycleId) throws SQLException {
        return bicycleDao.deleteBicycle(bicycleId);
    }

    // ----------------------------------------------------------------
    // Member — Search & Browse
    // ----------------------------------------------------------------


    // Applies business rules and communicates with the data access layer

    public List<BicycleModel> getAllBicycles() throws SQLException {
        return bicycleDao.findAll();
    }


    // Applies business rules and communicates with the data access layer

    public List<BicycleModel> getAvailableBicycles() throws SQLException {
        return bicycleDao.findAvailable();
    }

    /**
     * Search available bikes. If type is null/empty, returns all available.
     */

    // Applies business rules and communicates with the data access layer
    public List<BicycleModel> searchByType(String bicycleType) throws SQLException {
        if (bicycleType == null || bicycleType.trim().isEmpty() || bicycleType.equalsIgnoreCase("ALL")) {
            return bicycleDao.findAvailable();
        }
        return bicycleDao.findByType(bicycleType.toUpperCase());
    }


    // Applies business rules and communicates with the data access layer

    public BicycleModel getBicycleById(int bicycleId) throws SQLException {
        return bicycleDao.findById(bicycleId);
    }


    // Applies business rules and communicates with the data access layer

    public int[] getFleetStatusCounts() throws SQLException {
        return bicycleDao.getStatusCounts();
    }
}