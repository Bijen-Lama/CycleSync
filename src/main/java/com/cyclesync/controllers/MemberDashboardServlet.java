package com.cyclesync.controllers;


/*
 * File name: MemberDashboardServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BorrowService;
import com.cyclesync.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/memberDashboard")

/**
 * Class: MemberDashboardServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class MemberDashboardServlet extends BaseServlet {

    private final BorrowService borrowService = new BorrowService();
    private final FineService   fineService   = new FineService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            int userId = user.getUserId();

            request.setAttribute("activeBorrow",    borrowService.getActiveBorrowByUser(userId));
            request.setAttribute("recentHistory",   borrowService.getRecordsByUser(userId));
            request.setAttribute("pendingFines",    fineService.getFinesByUser(userId));
            request.setAttribute("totalPendingFine",fineService.getTotalPendingFinesByUser(userId));

            request.getRequestDispatcher("/WEB-INF/pages/memberDashboard.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading member dashboard.", e);
        }
    }


}