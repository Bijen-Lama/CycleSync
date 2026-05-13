package com.cyclesync.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database configuration class.
 * Credentials are read from environment variables for security.
 * Set the following before running the application:
 *   DB_HOST     (default: 127.0.0.1)
 *   DB_PORT     (default: 3306)
 *   DB_NAME     (default: cyclesync_db)
 *   DB_USER     (default: root)
 *   DB_PASSWORD (default: "")
 */
public class DBConfig {

	private static final String DB_HOST     = getEnv("DB_HOST", "127.0.0.1");
	private static final String DB_PORT     = getEnv("DB_PORT", "3306");
	private static final String DB_NAME     = getEnv("DB_NAME", "cyclesync_db");
	private static final String USER        = getEnv("DB_USER", "root");
	private static final String PASSWORD    = getEnv("DB_PASSWORD", "");
	private static final String DB_DRIVER   = "com.mysql.cj.jdbc.Driver";

	private static final String URL =
		"jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME +
		"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	private DBConfig() {}

	/** Returns the value of the named environment variable, or the given default. */
	private static String getEnv(String name, String defaultValue) {
		String val = System.getenv(name);
		return (val != null && !val.isBlank()) ? val : defaultValue;
	}

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DB_DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("[DBConfig] MySQL Connector/J JAR not found.");
			throw new SQLException("MySQL JDBC driver is missing.");
		} catch (SQLException e) {
			System.err.println("[DBConfig] Connection failed — is XAMPP / MySQL running?");
			System.err.println("Error: " + e.getMessage());
			throw e;
		}
	}

	public static void closeConnection() {
		// Connections are closed by the callers (try-with-resources).
	}
}
