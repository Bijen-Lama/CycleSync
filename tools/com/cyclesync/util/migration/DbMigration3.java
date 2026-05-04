package com.cyclesync.util.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Phase 3 DB Migration — Makes paymentMethod nullable and adds bikeId shortcut column
 * to the transactions table.
 */
public class DbMigration3 {

    private static final String URL  = "jdbc:mysql://127.0.0.1:3307/cyclesync_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Running Phase 3 DB Migrations...");

            tryExecute(stmt,
                "ALTER TABLE transactions MODIFY paymentMethod VARCHAR(50) NULL",
                "transactions.paymentMethod made nullable.");

            tryExecute(stmt,
                "ALTER TABLE transactions ADD COLUMN bikeId INT NULL",
                "Added bikeId column to transactions.");

            System.out.println("Phase 3 Migrations completed!");

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
