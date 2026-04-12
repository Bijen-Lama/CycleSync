package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.FineModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FineDao - DB operations for the 'fines' table.
 */
public class FineDao {

    private static final String SQL_SELECT_ENRICHED =
        "SELECT f.*, u.fullName AS memberName, u.userEmail AS memberEmail, " +
        "       b.bicycleName " +
        "FROM fines f " +
        "JOIN users          u  ON f.userId   = u.userId " +
        "JOIN borrow_records br ON f.recordId = br.recordId " +
        "JOIN bicycles       b  ON br.bicycleId = b.bicycleId ";

    private static final String SQL_INSERT =
        "INSERT INTO fines (recordId, userId, fineReason, fineAmount, fineStatus, adminNotes) " +
        "VALUES (?, ?, ?, ?, 'PENDING', ?)";

    private static final String SQL_FIND_BY_ID =
        SQL_SELECT_ENRICHED + "WHERE f.fineId = ?";

    private static final String SQL_FIND_ALL =
        SQL_SELECT_ENRICHED + "ORDER BY f.issuedAt DESC";

    private static final String SQL_FIND_BY_USER =
        SQL_SELECT_ENRICHED + "WHERE f.userId = ? ORDER BY f.issuedAt DESC";

    private static final String SQL_FIND_PENDING =
        SQL_SELECT_ENRICHED + "WHERE f.fineStatus = 'PENDING' ORDER BY f.issuedAt DESC";

    private static final String SQL_FIND_BY_RECORD =
        "SELECT * FROM fines WHERE recordId = ?";

    private static final String SQL_UPDATE_STATUS =
        "UPDATE fines SET fineStatus = ?, resolvedAt = NOW(), adminNotes = ? WHERE fineId = ?";

    private static final String SQL_TOTAL_PENDING_BY_USER =
        "SELECT COALESCE(SUM(fineAmount), 0) FROM fines WHERE userId = ? AND fineStatus = 'PENDING'";

    // ----------------------------------------------------------------
    // Mapper (enriched)
    // ----------------------------------------------------------------
    private FineModel mapRow(ResultSet rs) throws SQLException {
        FineModel fine = new FineModel();
        fine.setFineId(rs.getInt("fineId"));
        fine.setRecordId(rs.getInt("recordId"));
        fine.setUserId(rs.getInt("userId"));
        fine.setFineReason(rs.getString("fineReason"));
        fine.setFineAmount(rs.getBigDecimal("fineAmount"));
        fine.setFineStatus(rs.getString("fineStatus"));
        fine.setIssuedAt(rs.getTimestamp("issuedAt"));
        fine.setResolvedAt(rs.getTimestamp("resolvedAt"));
        fine.setAdminNotes(rs.getString("adminNotes"));
        try {
            fine.setMemberName(rs.getString("memberName"));
            fine.setMemberEmail(rs.getString("memberEmail"));
            fine.setBicycleName(rs.getString("bicycleName"));
        } catch (SQLException ignored) {}
        return fine;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------

    public int insertFine(FineModel fine) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, fine.getRecordId());
            ps.setInt(2, fine.getUserId());
            ps.setString(3, fine.getFineReason());
            ps.setBigDecimal(4, fine.getFineAmount());
            ps.setString(5, fine.getAdminNotes());

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

    public FineModel findById(int fineId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, fineId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<FineModel> findAll() throws SQLException {
        List<FineModel> fineList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) fineList.add(mapRow(rs));
        }
        return fineList;
    }

    public List<FineModel> findByUserId(int userId) throws SQLException {
        List<FineModel> fineList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USER)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) fineList.add(mapRow(rs));
            }
        }
        return fineList;
    }

    public List<FineModel> findPending() throws SQLException {
        List<FineModel> fineList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_PENDING);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) fineList.add(mapRow(rs));
        }
        return fineList;
    }

    public boolean existsForRecord(int recordId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_RECORD)) {

            ps.setInt(1, recordId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public BigDecimal getTotalPendingByUser(int userId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_TOTAL_PENDING_BY_USER)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal(1);
            }
        }
        return BigDecimal.ZERO;
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    public boolean updateFineStatus(int fineId, String fineStatus, String adminNotes) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {

            ps.setString(1, fineStatus);
            ps.setString(2, adminNotes);
            ps.setInt(3, fineId);
            return ps.executeUpdate() > 0;
        }
    }
}