package com.cyclesync.controllers;

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
public class AdminDashboardServlet extends HttpServlet {

    private final UserService    userService    = new UserService();
    private final BicycleService bicycleService = new BicycleService();
    private final BorrowService  borrowService  = new BorrowService();
    private final FineService    fineService    = new FineService();

    @Override
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
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login"));
            return false;
        }
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (!user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));
            return false;
        }
        return true;
    }
}