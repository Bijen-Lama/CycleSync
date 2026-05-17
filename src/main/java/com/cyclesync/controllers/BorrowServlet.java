package com.cyclesync.controllers;


/*
 * File name: BorrowServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BicycleService;
import com.cyclesync.service.BorrowService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/borrowBike")

/**
 * Class: BorrowServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class BorrowServlet extends BaseServlet {

    private final BorrowService  borrowService  = new BorrowService();
    private final BicycleService bicycleService = new BicycleService();

    /** GET — load the borrow confirmation page with bike details */
    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        String bikeIdParam = request.getParameter("bicycleId");
        if (bikeIdParam == null) {
            response.sendRedirect(response.encodeRedirectURL("searchBicycles")); return;
        }

        try {
            int bicycleId = Integer.parseInt(bikeIdParam);
            request.setAttribute("selectedBike", bicycleService.getBicycleById(bicycleId));
            request.getRequestDispatcher("/WEB-INF/pages/borrowBike.jsp")
                   .forward(request, response);
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error loading borrow page.", e);
        }
    }

    /** POST — confirm and process the borrow */
    @Override

    // Special method handling request lifecycle or component initialization
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            int bicycleId = Integer.parseInt(request.getParameter("bicycleId"));
            int recordId = borrowService.borrowBicycle(user.getUserId(), bicycleId);
            
            // Log a pending transaction
            try (java.sql.Connection conn = com.cyclesync.config.DBConfig.getConnection()) {
                try (java.sql.PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO transactions (recordId, userId, amount, currency, status) VALUES (?, ?, 0, 'NPR', 'PENDING')")) {
                    ps.setInt(1, recordId);
                    ps.setInt(2, user.getUserId());
                    ps.executeUpdate();
                }
            }

            request.getSession().setAttribute("successMessage", "Bicycle borrowed successfully! Return within 24 hours.");
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("errorMessage", e.getMessage());
            try {
				request.setAttribute("selectedBike",
				    bicycleService.getBicycleById(Integer.parseInt(request.getParameter("bicycleId"))));
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            request.getRequestDispatcher("/WEB-INF/pages/borrowBike.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error during borrow.", e);
        }
    }


}