package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.BicycleModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BicycleDao - All PreparedStatement-based DB operations for the 'bicycles' table.
 */
public class BicycleDao {

    private static final String SQL_INSERT =
        "INSERT INTO bicycles (bicycleName, bicycleType, bicycleStatus, locationCode, hourlyRate, description) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM bicycles WHERE bicycleId = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM bicycles ORDER BY addedAt DESC";

    private static final String SQL_FIND_BY_STATUS =
        "SELECT * FROM bicycles WHERE bicycleStatus = ? ORDER BY bicycleName ASC";

    private static final String SQL_FIND_BY_TYPE =
        "SELECT * FROM bicycles WHERE bicycleType = ? AND bicycleStatus = 'AVAILABLE' ORDER BY bicycleName ASC";

    private static final String SQL_FIND_AVAILABLE =
        "SELECT * FROM bicycles WHERE bicycleStatus = 'AVAILABLE' ORDER BY bicycleType, bicycleName";

    private static final String SQL_UPDATE_STATUS =
        "UPDATE bicycles SET bicycleStatus = ? WHERE bicycleId = ?";

    private static final String SQL_UPDATE_FULL =
        "UPDATE bicycles SET bicycleName = ?, bicycleType = ?, bicycleStatus = ?, " +
        "locationCode = ?, hourlyRate = ?, description = ? WHERE bicycleId = ?";

    private static final String SQL_DELETE =
        "DELETE FROM bicycles WHERE bicycleId = ?";

    private static final String SQL_COUNT_BY_STATUS =
        "SELECT bicycleStatus, COUNT(*) as total FROM bicycles GROUP BY bicycleStatus";

    // ----------------------------------------------------------------
    // Mapper
    // ----------------------------------------------------------------
    private BicycleModel mapRow(ResultSet rs) throws SQLException {
        BicycleModel bike = new BicycleModel();
        bike.setBicycleId(rs.getInt("bicycleId"));
        bike.setBicycleName(rs.getString("bicycleName"));
        bike.setBicycleType(rs.getString("bicycleType"));
        bike.setBicycleStatus(rs.getString("bicycleStatus"));
        bike.setLocationCode(rs.getString("locationCode"));
        bike.setHourlyRate(rs.getBigDecimal("hourlyRate"));
        bike.setDescription(rs.getString("description"));
        bike.setAddedAt(rs.getTimestamp("addedAt"));
        bike.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return bike;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------

    public int insertBicycle(BicycleModel bike) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, bike.getBicycleName());
            ps.setString(2, bike.getBicycleType());
            ps.setString(3, bike.getBicycleStatus() != null ? bike.getBicycleStatus() : "AVAILABLE");
            ps.setString(4, bike.getLocationCode());
            ps.setBigDecimal(5, bike.getHourlyRate());
            ps.setString(6, bike.getDescription());

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

    public BicycleModel findById(int bicycleId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, bicycleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<BicycleModel> findAll() throws SQLException {
        List<BicycleModel> bikeList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) bikeList.add(mapRow(rs));
        }
        return bikeList;
    }

    public List<BicycleModel> findAvailable() throws SQLException {
        List<BicycleModel> bikeList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_AVAILABLE);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) bikeList.add(mapRow(rs));
        }
        return bikeList;
    }

    public List<BicycleModel> findByType(String bicycleType) throws SQLException {
        List<BicycleModel> bikeList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_TYPE)) {

            ps.setString(1, bicycleType.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) bikeList.add(mapRow(rs));
            }
        }
        return bikeList;
    }

    public List<BicycleModel> findByStatus(String bicycleStatus) throws SQLException {
        List<BicycleModel> bikeList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_STATUS)) {

            ps.setString(1, bicycleStatus.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) bikeList.add(mapRow(rs));
            }
        }
        return bikeList;
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    public boolean updateStatus(int bicycleId, String bicycleStatus) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {

            ps.setString(1, bicycleStatus);
            ps.setInt(2, bicycleId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateBicycle(BicycleModel bike) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_FULL)) {

            ps.setString(1, bike.getBicycleName());
            ps.setString(2, bike.getBicycleType());
            ps.setString(3, bike.getBicycleStatus());
            ps.setString(4, bike.getLocationCode());
            ps.setBigDecimal(5, bike.getHourlyRate());
            ps.setString(6, bike.getDescription());
            ps.setInt(7, bike.getBicycleId());
            return ps.executeUpdate() > 0;
        }
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------

    public boolean deleteBicycle(int bicycleId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, bicycleId);
            return ps.executeUpdate() > 0;
        }
    }

    // ----------------------------------------------------------------
    // STATS — used by the admin dashboard
    // ----------------------------------------------------------------

    /**
     * Returns a count array: [0]=available, [1]=borrowed, [2]=maintenance
     */
    public int[] getStatusCounts() throws SQLException {
        int[] counts = {0, 0, 0};
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_BY_STATUS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String status = rs.getString("bicycleStatus");
                int total = rs.getInt("total");
                switch (status) {
                    case "AVAILABLE":    counts[0] = total; break;
                    case "BORROWED":     counts[1] = total; break;
                    case "MAINTENANCE":  counts[2] = total; break;
                }
            }
        }
        return counts;
    }
}