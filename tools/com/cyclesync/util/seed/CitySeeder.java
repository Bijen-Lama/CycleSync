package com.cyclesync.util.seed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

/**
 * CitySeeder — Distributes all existing bicycles across 11 major Nepalese
 * cities
 * with randomised coordinate jitter so markers are spread on the live map.
 * Run once after DbMigration2 has added the city_name / latitude / longitude
 * columns.
 */
public class CitySeeder {

    private static final String URL = "jdbc:mysql://127.0.0.1:3307/cyclesync_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    // Inner record representing a Nepalese city seed point
    static class City {
        final String name;
        final double lat;
        final double lng;

        City(String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }
    }

    private static final City[] CITIES = {
            new City("Kathmandu", 27.7172, 85.3240),
            new City("Lalitpur", 27.6756, 85.3123),
            new City("Bhaktapur", 27.6710, 85.4298),
            new City("Pokhara", 28.2096, 83.9856),
            new City("Biratnagar", 26.4525, 87.2718),
            new City("Birgunj", 27.0122, 84.8774),
            new City("Butwal", 27.7006, 83.4484),
            new City("Dharan", 26.8125, 87.2836),
            new City("Hetauda", 27.4287, 85.0272),
            new City("Nepalgunj", 28.0500, 81.6167),
            new City("Dhangadhi", 28.6900, 80.5933),
    };

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            PreparedStatement select = conn.prepareStatement("SELECT bicycleId FROM bicycles");
            ResultSet rs = select.executeQuery();

            PreparedStatement update = conn.prepareStatement(
                    "UPDATE bicycles SET city_name = ?, latitude = ?, longitude = ? WHERE bicycleId = ?");

            Random rand = new Random();
            int count = 0;

            while (rs.next()) {
                int id = rs.getInt("bicycleId");
                City city = CITIES[rand.nextInt(CITIES.length)];

                // Slight jitter so pins are never exactly on top of each other
                double jitterLat = (rand.nextDouble() - 0.5) * 0.02;
                double jitterLng = (rand.nextDouble() - 0.5) * 0.02;

                update.setString(1, city.name);
                update.setDouble(2, city.lat + jitterLat);
                update.setDouble(3, city.lng + jitterLng);
                update.setInt(4, id);
                update.executeUpdate();
                count++;
            }

            System.out.println("Seeded " + count + " bikes across Nepal cities.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
