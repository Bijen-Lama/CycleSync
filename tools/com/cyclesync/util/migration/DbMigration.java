package com.cyclesync.util.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Phase 1 DB Migration — Adds GPS columns, paymentStatus, and impersonation_logs table.
 * Run once against a fresh database, or safely re-run (columns are guarded with try/catch).
 */
public class DbMigration {

    private static final String URL  = "jdbc:mysql://127.0.0.1:3307/cyclesync_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Running Phase 1 DB Migrations...");

            // 1. Add GPS columns to bicycles
            tryExecute(stmt,
                "ALTER TABLE bicycles ADD COLUMN latitude DECIMAL(9,6) DEFAULT 27.7172",
                "Added latitude to bicycles.");

            tryExecute(stmt,
                "ALTER TABLE bicycles ADD COLUMN longitude DECIMAL(9,6) DEFAULT 85.3240",
                "Added longitude to bicycles.");

            tryExecute(stmt,
                "ALTER TABLE bicycles ADD COLUMN lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
                "Added lastUpdated to bicycles.");

            // 2. Add paymentStatus to borrow_records
            tryExecute(stmt,
                "ALTER TABLE borrow_records ADD COLUMN paymentStatus VARCHAR(20) DEFAULT 'PENDING'",
                "Added paymentStatus to borrow_records.");

            // 3. Create impersonation_logs table
            String createLogsTable =
                "CREATE TABLE IF NOT EXISTS impersonation_logs (" +
                "logId INT AUTO_INCREMENT PRIMARY KEY, " +
                "adminId INT NOT NULL, " +
                "targetUserId INT NOT NULL, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (adminId)       REFERENCES users(userId) ON DELETE CASCADE, " +
                "FOREIGN KEY (targetUserId)  REFERENCES users(userId) ON DELETE CASCADE" +
                ")";
            stmt.execute(createLogsTable);
            System.out.println("Created impersonation_logs table.");

            System.out.println("Phase 1 Migrations completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tryExecute(Statement stmt, String sql, String successMsg) {
        try {
            stmt.execute(sql);
            System.out.println(successMsg);
        } catch (Exception e) {
            System.out.println("Skipped (" + e.getMessage() + ")");
        }
    }
}
