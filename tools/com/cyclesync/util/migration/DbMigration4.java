package com.cyclesync.util.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbMigration4 {

    private static final String URL  = "jdbc:mysql://127.0.0.1:3307/cyclesync_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Running Phase 4 DB Migrations...");

            String createFeedbackTable =
                "CREATE TABLE IF NOT EXISTS feedback (" +
                "feedbackId  INT AUTO_INCREMENT PRIMARY KEY, " +
                "userId      INT NOT NULL, " +
                "type        VARCHAR(20) NOT NULL, " +
                "subject     VARCHAR(200) NOT NULL, " +
                "message     TEXT NOT NULL, " +
                "status      VARCHAR(20) DEFAULT 'NEW', " +
                "createdAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE" +
                ")";
            
            stmt.execute(createFeedbackTable);
            System.out.println("Created feedback table.");

            System.out.println("Phase 4 Migrations completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
