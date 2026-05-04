package com.cyclesync.controllers;

import com.cyclesync.model.BicycleModel;
import com.cyclesync.service.BicycleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/bicycle-locations")
public class BicycleLocationServlet extends HttpServlet {
    
    private final BicycleService bicycleService = new BicycleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            List<BicycleModel> bikes = bicycleService.getAllBicycles();
            
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < bikes.size(); i++) {
                BicycleModel b = bikes.get(i);
                // In a real app we would use Gson/Jackson
                json.append("{")
                    .append("\"id\":").append(b.getBicycleId()).append(",")
                    .append("\"name\":\"").append(b.getBicycleName().replace("\"", "\\\"")).append("\",")
                    .append("\"type\":\"").append(b.getBicycleType()).append("\",")
                    .append("\"status\":\"").append(b.getBicycleStatus()).append("\",")
                    .append("\"latitude\":").append(b.getLatitude()).append(",")
                    .append("\"longitude\":").append(b.getLongitude()).append(",")
                    .append("\"cityName\":\"").append(b.getCityName() != null ? b.getCityName() : "Unknown").append("\",")
                    .append("\"updatedAt\":\"").append(b.getUpdatedAt() != null ? b.getUpdatedAt().toString() : "").append("\"")
                    .append("}");
                if (i < bikes.size() - 1) json.append(",");
            }
            json.append("]");
            
            out.print(json.toString());
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }
}
