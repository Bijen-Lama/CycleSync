package com.cyclesync.dao;

import com.cyclesync.config.DBConfig;
import com.cyclesync.model.TransactionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class TransactionDao {

    private static final String SQL_SELECT_ENRICHED =
        "SELECT t.*, u.fullName AS memberName, u.userEmail AS memberEmail, u.nationality AS memberNationality, " +
        "       b.bicycleName, br.borrowDate, br.returnDate " +
        "FROM transactions t " +
        "JOIN users u ON t.userId = u.userId " +
        "JOIN borrow_records br ON t.recordId = br.recordId " +
        "JOIN bicycles b ON br.bicycleId = b.bicycleId ";

    public List<TransactionModel> findAll() throws SQLException {
        List<TransactionModel> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ENRICHED + "ORDER BY t.createdAt DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public List<TransactionModel> findByUserId(int userId) throws SQLException {
        List<TransactionModel> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ENRICHED + "WHERE t.userId = ? ORDER BY t.createdAt DESC")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public BigDecimal getTotalRevenue() throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE status = 'COMPLETED' AND currency = 'NPR'");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getBigDecimal(1);
        }
        return BigDecimal.ZERO;
    }

    private TransactionModel mapRow(ResultSet rs) throws SQLException {
        TransactionModel t = new TransactionModel();
        t.setTransactionId(rs.getInt("transactionId"));
        t.setRecordId(rs.getInt("recordId"));
        t.setUserId(rs.getInt("userId"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setCurrency(rs.getString("currency"));
        t.setPaymentMethod(rs.getString("paymentMethod"));
        t.setStatus(rs.getString("status"));
        t.setCreatedAt(rs.getTimestamp("createdAt"));
        t.setMemberName(rs.getString("memberName"));
        t.setMemberEmail(rs.getString("memberEmail"));
        t.setMemberNationality(rs.getString("memberNationality"));
        t.setBicycleName(rs.getString("bicycleName"));
        t.setBorrowDate(rs.getTimestamp("borrowDate"));
        t.setReturnDate(rs.getTimestamp("returnDate"));
        return t;
    }
}
