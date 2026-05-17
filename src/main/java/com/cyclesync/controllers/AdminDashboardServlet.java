package com.cyclesync.controllers;


/*
 * File name: AdminDashboardServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BicycleService;
import com.cyclesync.service.BorrowService;
import com.cyclesync.service.FineService;
import com.cyclesync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminDashboard")

/**
 * Class: AdminDashboardServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class AdminDashboardServlet extends BaseServlet {

    private final UserService    userService    = new UserService();
    private final BicycleService bicycleService = new BicycleService();
    private final BorrowService  borrowService  = new BorrowService();
    private final FineService    fineService    = new FineService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Guard: admin only
        if (!isAdmin(request, response)) return;

        try {
            // Flag overdue records on each dashboard load
            borrowService.flagOverdueRecords();

            // Fleet stats: [available, borrowed, maintenance]
            int[] fleetCounts = bicycleService.getFleetStatusCounts();

            request.setAttribute("totalAvailable",   fleetCounts[0]);
            request.setAttribute("totalBorrowed",     fleetCounts[1]);
            request.setAttribute("totalMaintenance",  fleetCounts[2]);
            request.setAttribute("totalMembers",      userService.getAllMembers().size());
            request.setAttribute("activeLoans",       borrowService.countActiveLoans());
            request.setAttribute("pendingFines",      fineService.getPendingFines().size());
            request.setAttribute("recentRecords",     borrowService.getAllRecords());

            request.getRequestDispatcher("/WEB-INF/pages/adminDashboard.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading admin dashboard.", e);
        }
    }

    // Admin dashboard has no POST — all actions handled by dedicated servlets

}