package com.cyclesync.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
	private static final String DB_NAME = "cyclesync_db";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/"+DB_NAME;
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection connection = null;
	
	private DBConfig() {}
	
	public static Connection getConnection() throws SQLException {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName(DB_DRIVER);
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("[DBConfig] Connected to cyclesync_db via XAMPP MySQL.");
				
			}
		} catch (ClassNotFoundException e) {
			System.err.println("[DBConfig] MySQL Connector/J JAR not found. ");
			throw new SQLException("MySQL JDBC driver is missing.");
		} catch (SQLException e) {
			System.err.println("[DBConfig] Connection failed - is Xampp running?");
			System.err.println("Error: " + e.getMessage());
			throw e;
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("[DBCOnfig] XAMPP MySQL connection is closed successfully.");
			} catch (SQLException e) {
				System.err.println("[DbConfig] Error while closing connection: " + e.getMessage());
			}
		}
	}
}
