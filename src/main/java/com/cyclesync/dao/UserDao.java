package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDao - All PreparedStatement-based DB operations for the 'users' table.
 */
public class UserDao {

    // ----------------------------------------------------------------
    // SQL Constants
    // ----------------------------------------------------------------
    private static final String SQL_INSERT =
        "INSERT INTO users (fullName, userEmail, userPassword, phoneNumber, userAddress, userRole, accountStatus) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_EMAIL =
        "SELECT * FROM users WHERE userEmail = ?";

    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM users WHERE userId = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM users ORDER BY createdAt DESC";

    private static final String SQL_FIND_ALL_MEMBERS =
        "SELECT * FROM users WHERE userRole = 'MEMBER' ORDER BY createdAt DESC";

    private static final String SQL_UPDATE_STATUS =
        "UPDATE users SET accountStatus = ? WHERE userId = ?";

    private static final String SQL_UPDATE_PROFILE =
        "UPDATE users SET fullName = ?, phoneNumber = ?, userAddress = ? WHERE userId = ?";

    private static final String SQL_EMAIL_EXISTS =
        "SELECT COUNT(*) FROM users WHERE userEmail = ?";

    private static final String SQL_DELETE =
        "DELETE FROM users WHERE userId = ?";

    // ----------------------------------------------------------------
    // Mapper — converts a ResultSet row into a UserModel object
    // ----------------------------------------------------------------
    private UserModel mapRow(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.setUserId(rs.getInt("userId"));
        user.setFullName(rs.getString("fullName"));
        user.setUserEmail(rs.getString("userEmail"));
        user.setUserPassword(rs.getString("userPassword"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setUserAddress(rs.getString("userAddress"));
        user.setUserRole(rs.getString("userRole"));
        user.setAccountStatus(rs.getString("accountStatus"));
        user.setCreatedAt(rs.getTimestamp("createdAt"));
        user.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return user;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------

    /**
     * Inserts a new user row. Returns the generated userId, or -1 on failure.
     */
    public int insertUser(UserModel user) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserEmail());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getUserAddress());
            ps.setString(6, user.getUserRole() != null ? user.getUserRole() : "MEMBER");
            ps.setString(7, user.getAccountStatus() != null ? user.getAccountStatus() : "ACTIVE");

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

    public UserModel findByEmail(String userEmail) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {

            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public UserModel findById(int userId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<UserModel> findAllUsers() throws SQLException {
        List<UserModel> userList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) userList.add(mapRow(rs));
        }
        return userList;
    }

    public List<UserModel> findAllMembers() throws SQLException {
        List<UserModel> memberList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL_MEMBERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) memberList.add(mapRow(rs));
        }
        return memberList;
    }

    public boolean emailExists(String userEmail) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EMAIL_EXISTS)) {

            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    public boolean updateAccountStatus(int userId, String accountStatus) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {

            ps.setString(1, accountStatus);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateProfile(int userId, String fullName,
                                 String phoneNumber, String userAddress) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PROFILE)) {

            ps.setString(1, fullName);
            ps.setString(2, phoneNumber);
            ps.setString(3, userAddress);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;
        }
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------

    public boolean deleteUser(int userId) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        }
    }
}