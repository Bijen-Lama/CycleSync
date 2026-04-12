package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.BorrowRecordModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BorrowRecordDao - DB operations for 'borrow_records'.
 * JOIN queries populate the enrichment fields (memberName, bicycleName, etc.)
 */
public class BorrowRecordDao {

    // Base SELECT with JOINs for enriched display data
    private static final String SQL_SELECT_ENRICHED =
        "SELECT br.*, u.fullName AS memberName, u.userEmail AS memberEmail, " +
        "       b.bicycleName, b.bicycleType " +
        "FROM borrow_records br " +
        "JOIN users     u ON br.userId    = u.userId " +
        "JOIN bicycles  b ON br.bicycleId = b.bicycleId ";

    private static final String SQL_INSERT =
        "INSERT INTO borrow_records (userId, bicycleId, borrowDate, dueDate, recordStatus) " +
        "VALUES (?, ?, ?, ?, 'ACTIVE')";

    private static final String SQL_FIND_BY_ID =
        SQL_SELECT_ENRICHED + "WHERE br.recordId = ?";

    private static final String SQL_FIND_ALL =
        SQL_SELECT_ENRICHED + "ORDER BY br.borrowDate DESC";

    private static final String SQL_FIND_BY_USER =
        SQL_SELECT_ENRICHED + "WHERE br.userId = ? ORDER BY br.borrowDate DESC";

    private static final String SQL_FIND_ACTIVE_BY_USER =
        SQL_SELECT_ENRICHED + "WHERE br.userId = ? AND br.recordStatus = 'ACTIVE'";

    private static final String SQL_FIND_ACTIVE_BY_BIKE =
        "SELECT * FROM borrow_records WHERE bicycleId = ? AND recordStatus = 'ACTIVE' LIMIT 1";

    private static final String SQL_UPDATE_RETURN =
        "UPDATE borrow_records SET returnDate = ?, recordStatus = 'RETURNED', " +
        "totalHours = ?, totalCost = ? WHERE recordId = ?";

    private static final String SQL_UPDATE_STATUS =
        "UPDATE borrow_records SET recordStatus = ? WHERE recordId = ?";

    private static final String SQL_COUNT_ACTIVE =
        "SELECT COUNT(*) FROM borrow_records WHERE recordStatus = 'ACTIVE'";

    // ----------------------------------------------------------------
    // Mapper (enriched)
    // ----------------------------------------------------------------
    private BorrowRecordModel mapRow(ResultSet rs) throws SQLException {
        BorrowRecordModel record = new BorrowRecordModel();
        record.setRecordId(rs.getInt("recordId"));
        record.setUserId(rs.getInt("userId"));
        record.setBicycleId(rs.getInt("bicycleId"));
        record.setBorrowDate(rs.getTimestamp("borrowDate"));
        record.setDueDate(rs.getTimestamp("dueDate"));
        record.setReturnDate(rs.getTimestamp("returnDate"));
        record.setRecordStatus(rs.getString("recordStatus"));
        record.setTotalHours(rs.getBigDecimal("totalHours"));
        record.setTotalCost(rs.getBigDecimal("totalCost"));
        record.setNotes(rs.getString("notes"));
        // Enrichment from JOIN
        try {
            record.setMemberName(rs.getString("memberName"));
            record.setMemberEmail(rs.getString("memberEmail"));
            record.setBicycleName(rs.getString("bicycleName"));
            record.setBicycleType(rs.getString("bicycleType"));
        } catch (SQLException ignored) {
            // Enrichment columns absent in non-JOIN queries — safe to ignore
        }
        return record;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------

    public int insertBorrowRecord(BorrowRecordModel record) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, record.getUserId());
            ps.setInt(2, record.getBicycleId());
            ps.setTimestamp(3, record.getBorrowDate());
            ps.setTimestamp(4, record.getDueDate());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) return -1;

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
            }
        }
        return -1;
    }

    // ----------------------------------------------------------------
    // READ
    // ----------------------------------------------------------------

    public BorrowRecordModel findById(int recordId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, recordId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<BorrowRecordModel> findAll() throws SQLException {
        List<BorrowRecordModel> records = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) records.add(mapRow(rs));
        }
        return records;
    }

    public List<BorrowRecordModel> findByUserId(int userId) throws SQLException {
        List<BorrowRecordModel> records = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USER)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) records.add(mapRow(rs));
            }
        }
        return records;
    }

    public BorrowRecordModel findActiveBorrowByUser(int userId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ACTIVE_BY_USER)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public BorrowRecordModel findActiveBorrowByBike(int bicycleId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ACTIVE_BY_BIKE)) {

            ps.setInt(1, bicycleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public int countActive() throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_ACTIVE);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    public boolean markReturned(int recordId, Timestamp returnDate,
                                BigDecimal totalHours, BigDecimal totalCost) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RETURN)) {

            ps.setTimestamp(1, returnDate);
            ps.setBigDecimal(2, totalHours);
            ps.setBigDecimal(3, totalCost);
            ps.setInt(4, recordId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int recordId, String recordStatus) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {

            ps.setString(1, recordStatus);
            ps.setInt(2, recordId);
            return ps.executeUpdate() > 0;
        }
    }
}