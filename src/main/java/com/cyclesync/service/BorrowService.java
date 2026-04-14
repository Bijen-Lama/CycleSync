package com.cyclesync.service;

import com.cyclesync.dao.BicycleDao;
import com.cyclesync.dao.BorrowRecordDao;
import com.cyclesync.model.BicycleModel;
import com.cyclesync.model.BorrowRecordModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * BorrowService - Core borrow/return workflow with fine-trigger logic.
 */
public class BorrowService {

    private final BorrowRecordDao borrowRecordDao = new BorrowRecordDao();
    private final BicycleDao      bicycleDao      = new BicycleDao();

    // Default loan duration in hours
    private static final int DEFAULT_LOAN_HOURS = 24;

    // ----------------------------------------------------------------
    // Borrow a Bike
    // ----------------------------------------------------------------

    /**
     * Borrows a bike for a member. Validates availability, creates the
     * borrow record, and flips the bicycle status to BORROWED.
     *
     * @return the new recordId
     */
    public int borrowBicycle(int userId, int bicycleId) throws SQLException {

        BicycleModel bike = bicycleDao.findById(bicycleId);
        if (bike == null)
            throw new IllegalArgumentException("Bicycle not found.");
        if (!bike.isAvailable())
            throw new IllegalStateException("This bicycle is not available for borrowing.");

        // Check the member doesn't already have an active borrow
        BorrowRecordModel existing = borrowRecordDao.findActiveBorrowByUser(userId);
        if (existing != null)
            throw new IllegalStateException("You already have an active borrow. Return it before borrowing another.");

        Timestamp borrowDate = new Timestamp(System.currentTimeMillis());
        Timestamp dueDate    = new Timestamp(
            System.currentTimeMillis() + TimeUnit.HOURS.toMillis(DEFAULT_LOAN_HOURS)
        );

        BorrowRecordModel record = new BorrowRecordModel(userId, bicycleId, borrowDate, dueDate);
        int recordId = borrowRecordDao.insertBorrowRecord(record);

        if (recordId == -1)
            throw new SQLException("Failed to create borrow record.");

        // Mark bike as BORROWED
        bicycleDao.updateStatus(bicycleId, "BORROWED");
        return recordId;
    }

    // ----------------------------------------------------------------
    // Return a Bike
    // ----------------------------------------------------------------

    /**
     * Processes a return. Calculates hours and cost, marks the record
     * RETURNED, and frees the bicycle back to AVAILABLE.
     *
     * @return the completed BorrowRecordModel (with totalHours/totalCost set)
     */
    public BorrowRecordModel returnBicycle(int recordId) throws SQLException {

        BorrowRecordModel record = borrowRecordDao.findById(recordId);
        if (record == null)
            throw new IllegalArgumentException("Borrow record not found.");
        if (record.isReturned())
            throw new IllegalStateException("This bike has already been returned.");

        BicycleModel bike = bicycleDao.findById(record.getBicycleId());
        if (bike == null)
            throw new IllegalArgumentException("Associated bicycle not found.");

        Timestamp returnDate = new Timestamp(System.currentTimeMillis());

        // Calculate time borrowed in hours
        long diffMillis = returnDate.getTime() - record.getBorrowDate().getTime();
        BigDecimal totalHours = BigDecimal.valueOf(diffMillis)
            .divide(BigDecimal.valueOf(3_600_000), 2, RoundingMode.CEILING);

        // Calculate cost
        BigDecimal totalCost = bike.getHourlyRate()
            .multiply(totalHours)
            .setScale(2, RoundingMode.HALF_UP);

        borrowRecordDao.markReturned(recordId, returnDate, totalHours, totalCost);
        bicycleDao.updateStatus(record.getBicycleId(), "AVAILABLE");

        record.setReturnDate(returnDate);
        record.setTotalHours(totalHours);
        record.setTotalCost(totalCost);
        record.setRecordStatus("RETURNED");
        return record;
    }

    // ----------------------------------------------------------------
    // Queries
    // ----------------------------------------------------------------

    public List<BorrowRecordModel> getAllRecords() throws SQLException {
        return borrowRecordDao.findAll();
    }

    public List<BorrowRecordModel> getRecordsByUser(int userId) throws SQLException {
        return borrowRecordDao.findByUserId(userId);
    }

    public BorrowRecordModel getActiveBorrowByUser(int userId) throws SQLException {
        return borrowRecordDao.findActiveBorrowByUser(userId);
    }

    public BorrowRecordModel getRecordById(int recordId) throws SQLException {
        return borrowRecordDao.findById(recordId);
    }

    public int countActiveLoans() throws SQLException {
        return borrowRecordDao.countActive();
    }

    /**
     * Marks any ACTIVE records whose dueDate has passed as OVERDUE.
     * Call this from AdminDashboardServlet on load, or via a scheduler.
     */
    public void flagOverdueRecords() throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        borrowRecordDao.flagOverdueRecords(now);
    }
}