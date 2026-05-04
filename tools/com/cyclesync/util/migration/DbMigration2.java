package com.cyclesync.util.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Phase 2 DB Migration — Adds city_name to bicycles, nationality to users,
 * and creates the transactions table.
 */
public class DbMigration2 {

    private static final String URL  = "jdbc:mysql://127.0.0.1:3307/cyclesync_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Running Phase 2 DB Migrations...");

            tryExecute(stmt,
                "ALTER TABLE bicycles ADD COLUMN city_name VARCHAR(100) DEFAULT 'Kathmandu'",
                "Added city_name to bicycles.");

            tryExecute(stmt,
                "ALTER TABLE users ADD COLUMN nationality VARCHAR(50) DEFAULT 'Nepalese'",
                "Added nationality to users.");

            String createTxTable =
                "CREATE TABLE IF NOT EXISTS transactions (" +
                "transactionId  INT AUTO_INCREMENT PRIMARY KEY, " +
                "recordId       INT NOT NULL, " +
                "userId         INT NOT NULL, " +
                "amount         DECIMAL(10,2) NOT NULL, " +
                "currency       VARCHAR(10) DEFAULT 'NPR', " +
                "paymentMethod  VARCHAR(50), " +
                "status         VARCHAR(20) DEFAULT 'PENDING', " +
                "createdAt      TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (recordId) REFERENCES borrow_records(recordId) ON DELETE CASCADE, " +
                "FOREIGN KEY (userId)   REFERENCES users(userId)         ON DELETE CASCADE" +
                ")";
            tryExecute(stmt, createTxTable, "Created transactions table.");

            System.out.println("Phase 2 Migrations completed successfully!");

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
