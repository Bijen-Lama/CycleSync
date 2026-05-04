package com.cyclesync.listeners;

import com.cyclesync.config.DBConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class BicycleSimulationListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Run every 10 seconds to simulate slight movement of ACTIVE and BORROWED bikes
        scheduler.scheduleAtFixedRate(() -> {
            try (Connection conn = DBConfig.getConnection()) {
                // Random shift between -0.0005 and +0.0005 degrees
                String sql = "UPDATE bicycles SET " +
                             "latitude = latitude + (RAND() * 0.001 - 0.0005), " +
                             "longitude = longitude + (RAND() * 0.001 - 0.0005), " +
                             "updatedAt = CURRENT_TIMESTAMP " +
                             "WHERE bicycleStatus IN ('AVAILABLE', 'BORROWED')";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("[Simulation] Error updating coordinates: " + e.getMessage());
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
