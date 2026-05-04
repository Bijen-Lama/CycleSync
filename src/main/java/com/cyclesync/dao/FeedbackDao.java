package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.FeedbackModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    public boolean insertFeedback(FeedbackModel feedback) throws SQLException {
        String sql = "INSERT INTO feedback (userId, type, subject, message) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getUserId());
            ps.setString(2, feedback.getType());
            ps.setString(3, feedback.getSubject());
            ps.setString(4, feedback.getMessage());
            return ps.executeUpdate() > 0;
        }
    }

    public List<FeedbackModel> findAll() throws SQLException {
        List<FeedbackModel> list = new ArrayList<>();
        String sql = "SELECT f.*, u.fullName, u.userEmail FROM feedback f " +
                     "JOIN users u ON f.userId = u.userId ORDER BY f.createdAt DESC";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FeedbackModel f = new FeedbackModel();
                f.setFeedbackId(rs.getInt("feedbackId"));
                f.setUserId(rs.getInt("userId"));
                f.setType(rs.getString("type"));
                f.setSubject(rs.getString("subject"));
                f.setMessage(rs.getString("message"));
                f.setStatus(rs.getString("status"));
                f.setCreatedAt(rs.getTimestamp("createdAt"));
                f.setUserName(rs.getString("fullName"));
                f.setUserEmail(rs.getString("userEmail"));
                list.add(f);
            }
        }
        return list;
    }

    public boolean updateStatus(int feedbackId, String status) throws SQLException {
        String sql = "UPDATE feedback SET status = ? WHERE feedbackId = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, feedbackId);
            return ps.executeUpdate() > 0;
        }
    }
}
