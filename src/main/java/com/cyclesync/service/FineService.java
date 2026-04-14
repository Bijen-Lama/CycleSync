package com.cyclesync.service;

import com.cyclesync.dao.FineDao;
import com.cyclesync.model.BorrowRecordModel;
import com.cyclesync.model.FineModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * FineService - Fine calculation and lifecycle management.
 */
public class FineService {

    private final FineDao fineDao = new FineDao();

    // Fine rate per hour overdue (adjust as needed)
    private static final BigDecimal LATE_FEE_PER_HOUR = new BigDecimal("2.50");

    // ----------------------------------------------------------------
    // Issue Fines
    // ----------------------------------------------------------------

    /**
     * Calculates and issues a LATE_RETURN fine based on hours overdue.
     * Skips if a fine already exists for this record.
     *
     * @return the new fineId, or -1 if skipped
     */
    public int issueLateReturnFine(BorrowRecordModel record) throws SQLException {
        if (fineDao.existsForRecord(record.getRecordId()))
            return -1;

        if (record.getReturnDate() == null || record.getDueDate() == null)
            return -1;

        long overdueMillis = record.getReturnDate().getTime() - record.getDueDate().getTime();
        if (overdueMillis <= 0)
            return -1; // Returned on time — no fine

        BigDecimal hoursLate = BigDecimal.valueOf(overdueMillis)
                .divide(BigDecimal.valueOf(3_600_000), 2, RoundingMode.CEILING);

        BigDecimal fineAmount = LATE_FEE_PER_HOUR
                .multiply(hoursLate)
                .setScale(2, RoundingMode.HALF_UP);

        String adminNotes = "Auto-issued: " + hoursLate + " hours overdue.";
        FineModel fine = new FineModel(
                record.getRecordId(), record.getUserId(),
                "LATE_RETURN", fineAmount, adminNotes);
        return fineDao.insertFine(fine);
    }

    /**
     * Admin manually issues a DAMAGE or LOSS fine.
     */
    public int issueDamageFine(int recordId, int userId,
            String fineReason, BigDecimal fineAmount,
            String adminNotes) throws SQLException {
        if (fineAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Fine amount must be greater than zero.");

        FineModel fine = new FineModel(recordId, userId, fineReason, fineAmount, adminNotes);
        return fineDao.insertFine(fine);
    }

    // ----------------------------------------------------------------
    // Admin — Fine Management
    // ----------------------------------------------------------------

    public boolean markFinePaid(int fineId, String adminNotes) throws SQLException {
        return fineDao.updateFineStatus(fineId, "PAID", adminNotes);
    }

    public boolean waiveFine(int fineId, String adminNotes) throws SQLException {
        return fineDao.updateFineStatus(fineId, "WAIVED", adminNotes);
    }

    public List<FineModel> getAllFines() throws SQLException {
        return fineDao.findAll();
    }

    public List<FineModel> getPendingFines() throws SQLException {
        return fineDao.findPending();
    }

    // ----------------------------------------------------------------
    // Member — Fine Viewing
    // ----------------------------------------------------------------

    public List<FineModel> getFinesByUser(int userId) throws SQLException {
        return fineDao.findByUserId(userId);
    }

    public BigDecimal getTotalPendingFinesByUser(int userId) throws SQLException {
        return fineDao.getTotalPendingByUser(userId);
    }

    public FineModel getFineById(int fineId) throws SQLException {
        return fineDao.findById(fineId);
    }
}