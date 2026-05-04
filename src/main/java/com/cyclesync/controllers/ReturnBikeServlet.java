package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.model.BorrowRecordModel;
import com.cyclesync.model.BicycleModel;
import com.cyclesync.service.BicycleService;
import com.cyclesync.service.BorrowService;
import com.cyclesync.dao.BorrowRecordDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

@WebServlet("/returnBike")
public class ReturnBikeServlet extends BaseServlet {

    private final BorrowService borrowService = new BorrowService();
    private final BicycleService bicycleService = new BicycleService();
    private final BorrowRecordDao borrowDao = new BorrowRecordDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            int recordId = Integer.parseInt(request.getParameter("recordId"));
            BorrowRecordModel record = borrowDao.findById(recordId);
            
            if (record == null || record.getUserId() != user.getUserId() || !"ACTIVE".equals(record.getRecordStatus())) {
                request.getSession().setAttribute("errorMessage", "Invalid active rental record.");
                response.sendRedirect("memberDashboard");
                return;
            }

            BicycleModel bike = bicycleService.getBicycleById(record.getBicycleId());
            
            // Calculate rental duration in hours
            long diffMs = System.currentTimeMillis() - record.getBorrowDate().getTime();
            long diffHours = diffMs / (1000 * 60 * 60);
            if (diffMs % (1000 * 60 * 60) > 0) diffHours++; // round up
            if (diffHours == 0) diffHours = 1;
            
            BigDecimal amount = bike.getHourlyRate().multiply(new BigDecimal(diffHours));
            String currency = "Foreign".equals(user.getNationality()) ? "USD" : "NPR";
            if ("USD".equals(currency)) {
                amount = amount.divide(new BigDecimal("135.0"), 2, RoundingMode.HALF_UP);
            }

            request.setAttribute("borrowRecord", record);
            request.setAttribute("selectedBike", bike);
            request.setAttribute("rentalHours", diffHours);
            request.setAttribute("paymentAmount", amount);
            request.setAttribute("paymentCurrency", currency);
            
            request.getRequestDispatcher("/WEB-INF/pages/returnBike.jsp").forward(request, response);
            
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error loading return page.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            int recordId = Integer.parseInt(request.getParameter("recordId"));
            String paymentMethod = request.getParameter("paymentMethod");
            BigDecimal amount = new BigDecimal(request.getParameter("paymentAmount"));
            String currency = request.getParameter("paymentCurrency");

            // Process return — updates bike status + borrow record
            BorrowRecordModel returned = borrowService.returnBicycle(recordId);

            if (returned != null) {
                // Update transaction record with payment info
                try (java.sql.Connection conn = com.cyclesync.config.DBConfig.getConnection()) {
                    // Try update first (pending tx already exists from borrow)
                    int updated;
                    try (java.sql.PreparedStatement ps = conn.prepareStatement(
                            "UPDATE transactions SET amount = ?, currency = ?, paymentMethod = ?, status = 'COMPLETED' WHERE recordId = ? AND status = 'PENDING'")) {
                        ps.setBigDecimal(1, amount);
                        ps.setString(2, currency);
                        ps.setString(3, paymentMethod);
                        ps.setInt(4, recordId);
                        updated = ps.executeUpdate();
                    }
                    // If no pending tx existed, insert one
                    if (updated == 0) {
                        try (java.sql.PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO transactions (recordId, userId, amount, currency, paymentMethod, status) VALUES (?, ?, ?, ?, ?, 'COMPLETED')")) {
                            ps.setInt(1, recordId);
                            ps.setInt(2, user.getUserId());
                            ps.setBigDecimal(3, amount);
                            ps.setString(4, currency);
                            ps.setString(5, paymentMethod);
                            ps.executeUpdate();
                        }
                    }
                }
                request.getSession().setAttribute("successMessage",
                    "Bike returned! Payment of " + currency + " " + String.format("%.2f", amount) + " via " + paymentMethod + " — Thank you!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to return bike. Already returned?");
            }

            response.sendRedirect("memberDashboard");

        } catch (Exception e) {
            throw new ServletException("Error processing return.", e);
        }
    }
}
